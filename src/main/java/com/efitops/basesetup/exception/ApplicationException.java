package com.efitops.basesetup.exception;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6587075753703182359L;
	/** Logger instance for {@link AuthPassAPI} */
	public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationException.class);

	/**
	 * @param msg
	 */
	public ApplicationException(String msg) {

		super(msg);
		LOGGER.info("{}", msg);
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public ApplicationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @return
	 */
	public <T> ResponseEntity<T> getResponse() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@RestControllerAdvice
	public class GlobalExceptionHandler {

	    @ExceptionHandler(ConstraintViolationException.class)
	    public Map<String, Object> handleValidationException(ConstraintViolationException ex) {

	        Map<String, Object> response = new HashMap<>();

	        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	            response.put("errorMessage", violation.getMessage());
	            break; // show only first error
	        }

	        response.put("status", false);
	        response.put("statusFlag", "Error");

	        return response;
	    }
	}
}
