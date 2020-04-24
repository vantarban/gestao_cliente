package com.example.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.services.exception.ErroValidacaoException;
import com.example.services.exception.ObjetoNaoEncontradoException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ErroValidacaoException.class)
	public ResponseEntity<StandardError> erroValidacao(ErroValidacaoException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado(ObjetoNaoEncontradoException e,
			HttpServletRequest request) {

		new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
