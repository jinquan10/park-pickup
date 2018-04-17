package org.parkpickup.api.exception;

public class UserInitiatedException extends Exception {
	public final FailedRequest failedRequest;

	public UserInitiatedException(FailedRequest failedRequest) {
		this.failedRequest = failedRequest;
	}
}
