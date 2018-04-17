package org.parkpickup.app;

import org.parkpickup.api.exception.FailedRequest;
import org.parkpickup.api.exception.UserInitiatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.parkpickup.Util.logger;
import static org.parkpickup.Util.stackTrace;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserInitiatedException.class)
	public ResponseEntity<FailedRequest> handleUserInitiatedException(UserInitiatedException e) {
		return new ResponseEntity<FailedRequest>(e.failedRequest, BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public void handleUncaughtExceptions(Throwable e) {
		logger(getClass()).error(stackTrace(e), e);
	}
}
