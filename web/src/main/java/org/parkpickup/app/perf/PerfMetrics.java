package org.parkpickup.app.perf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PerfMetrics {
	private final int averagesNumLast;

	private final Map<String, PerfArray> methodAverages = new HashMap<>();

	public PerfMetrics(int averagesNumLast) {
		this.averagesNumLast = averagesNumLast;
	}

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
