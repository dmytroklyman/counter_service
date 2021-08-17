package de.klyman.increment.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this();
       	this.status = status;
       	this.message = "Error processing the request";
       	this.debugMessage = ex.getLocalizedMessage();
	}

	public HttpStatus getStatus() {
		return this.status;
	}
	
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getDebugMessage() {
		return this.debugMessage;
	}

}
