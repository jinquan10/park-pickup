package org.parkpickup.api.exception;

public class UserException extends BaseException {
	public UserException(FailedRequest failedRequest) {
		super(failedRequest);
	}
}
