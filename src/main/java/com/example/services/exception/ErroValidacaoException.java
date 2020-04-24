package com.example.services.exception;

public class ErroValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String field;
	private String message;
	
	public ErroValidacaoException(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}
	
	public ErroValidacaoException(String message) {
		super();
		this.message = message;
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
