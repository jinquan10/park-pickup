package org.parkpickup.api.exception;

public class ClientRequestException extends Exception {
	public final FailedRequest failedRequest;

	public ClientRequestException(FailedRequest failedRequest) {
		this.failedRequest = failedRequest;
	}
}
