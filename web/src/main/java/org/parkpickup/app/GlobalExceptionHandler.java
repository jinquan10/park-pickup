package org.parkpickup.app;

import org.parkpickup.api.exception.ClientRequestException;
import org.parkpickup.api.exception.FailedReason;
import org.parkpickup.api.exception.FailedRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.parkpickup.Util.logger;
import static org.parkpickup.Util.stackTrace;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final FailedRequest failedRequest = new FailedRequest(FailedReason.SERVER_ERROR);

	@ExceptionHandler(ClientRequestException.class)
	public ResponseEntity<FailedRequest> handleUserInitiatedException(ClientRequestException e) {
		return new ResponseEntity<>(e.failedRequest, BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<FailedRequest> handleUncaughtExceptions(Throwable e) {
		logger(getClass()).error(stackTrace(e), e);
		return new ResponseEntity<>(failedRequest, INTERNAL_SERVER_ERROR);
	}
}
