package org.parkpickup.api.exception;

public class BaseException extends Exception {
	public final FailedRequest failedRequest;

	public BaseException(Throwable e) {
		super(e);
		this.failedRequest = null;
	}

	public BaseException(FailedRequest failedRequest) {
		this.failedRequest = failedRequest;
	}
}
