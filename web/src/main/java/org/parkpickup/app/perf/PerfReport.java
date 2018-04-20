package org.parkpickup.app.perf;

public class PerfReport {
	public final String fullMethodName;
	public final long averageElapsedMillis;

	public PerfReport(String fullMethodName, long averageElapsedMillis) {
		this.fullMethodName = fullMethodName;
		this.averageElapsedMillis = averageElapsedMillis;
	}
}
