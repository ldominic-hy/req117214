/**
 * 
 */
package com.gov.bc.ca.supplement.error.handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 */
@ControllerAdvice
public class GenericExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException error) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now());
		errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
		errorDetails.put("errors", error.getBindingResult().getFieldErrors().stream().collect(
			Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
		));
		logger.error(errorDetails.get("timestamp").toString());
		logger.error(errorDetails.get("status").toString());
		logger.error(errorDetails.get("errors").toString());

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
