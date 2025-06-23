package com.learning.rms.exceptions;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learning.rms.utils.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> al = new LinkedList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String defaultMessage = error.getDefaultMessage();
			System.out.println(defaultMessage);
			al.add(defaultMessage);
		});
		CommonResponse api = new CommonResponse(al.get(0), false, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		Map<String, String> response = new LinkedHashMap<>();
		response.put("Message", ex.getMessage());
		response.put("Error", "HTTP method not supported");
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CommonResponse> resourceNotFoundException(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		CommonResponse api = new CommonResponse(message, false, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<CommonResponse>(api, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<CommonResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
		String message = "Please Add Proper Request";
		CommonResponse api = new CommonResponse(message, false, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<CommonResponse>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<CommonResponse> dataIntegrityViolationException(DataIntegrityViolationException ex) {
		String message = "Please insert Unique Data";
		CommonResponse api = new CommonResponse(message, false, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<CommonResponse>(api, HttpStatus.BAD_REQUEST);
	}
}