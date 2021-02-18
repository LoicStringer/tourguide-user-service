package com.tourguideuserservice.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicatedUserException.class)
	public ResponseEntity<ExceptionResponse> handleGpsUtilException(DuplicatedUserException duplicatedUserException) {
		ExceptionResponse exceptionResponse = buildExceptionResponse(duplicatedUserException);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(duplicatedUserException));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleGpsUtilException(UserNotFoundException userNotFoundException) {
		ExceptionResponse exceptionResponse = buildExceptionResponse(userNotFoundException);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(userNotFoundException));
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionResponse> handleFeignException(FeignException feignException,HttpServletResponse response) {
		response.setStatus(feignException.status());
		ExceptionResponse exceptionResponse = buildFeignExceptionResponse(feignException,response);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(feignException));
	}
	
	private ExceptionResponse buildFeignExceptionResponse(FeignException fEx,HttpServletResponse response) {
		response.setStatus(fEx.status());
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		String feignExceptionStatus = status.toString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), feignExceptionStatus,
				fEx.getCause().toString(), fEx.getMessage());
		return exceptionResponse;
	}
	

	private ExceptionResponse buildExceptionResponse(Exception ex) {
		String statusCode = getStatusCodeFromException(ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode,
				ex.getClass().getSimpleName(), ex.getMessage());
		return exceptionResponse;
	}

	private HttpStatus getHttpStatusFromException(Exception ex) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.value();
		return status;
	}

	private String getStatusCodeFromException(Exception ex) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.code();
		return status.toString();
	}

}
