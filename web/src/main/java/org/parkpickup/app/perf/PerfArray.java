package org.parkpickup.app.perf;

public class PerfArray {
	private final long[] millisArray;
	private int currIndx = 0;

	public PerfArray() {
		throw new RuntimeException("Must initialize millis with a size.");
	}

	public PerfArray(int size) {
		this.millisArray = new long[size];
	}

	public void add(long millis) {
		this.millisArray[this.currIndx++] = millis;
		if (this.currIndx == this.millisArray.length) {
			this.currIndx = 0;
		}
	}

	public long getAvg() {
		long sum = 0;
		for (Long num : millisArray) {
			sum += num;
		}

		return sum / millisArray.length;
	}
}
