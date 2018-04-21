package org.parkpickup.app.perf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//@Value("${metrics.perf.enabled}")
@Component
public class PerfMetrics {
	@Value("${perf.averages.num.last}")
	private int averagesNumLast;
	private final Map<String, PerfArray> methodAverages = new HashMap<>();

	public void add(String fullMethodName, long millisElapsed) {
		synchronized (this.methodAverages) {
			PerfArray perfArray = this.methodAverages.get(fullMethodName);

			if (perfArray == null) {
				this.methodAverages.put(fullMethodName, new PerfArray(this.averagesNumLast));
			} else {
				perfArray.add(millisElapsed);
			}
		}
	}

	public Collection<PerfReport> getPerfReports() {
		synchronized (this.methodAverages) {
			Collection<PerfReport> reports = new ArrayList<>();
			for (Map.Entry<String, PerfArray> element : this.methodAverages.entrySet()) {
				reports.add(new PerfReport(element.getKey(), element.getValue().getAvg()));
			}

			return reports;
		}
	}
}
