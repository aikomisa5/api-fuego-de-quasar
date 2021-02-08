package com.fuego.quasar.exceptions.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fuego.quasar.dto.error.ErrorDto;
import com.fuego.quasar.exceptions.ConflictException;
import com.fuego.quasar.exceptions.NoContentException;
import com.fuego.quasar.exceptions.NotFoundException;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus statusm, WebRequest request){
		
		ErrorDto response = new ErrorDto();
		response.setMensaje(e.getLocalizedMessage());
		response.setExceptionType(e.getClass().getSimpleName());
		
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({ConflictException.class})
	@ResponseBody
	public ResponseEntity<ErrorDto> handleConflictException(Exception e){
		ConflictException exception = (ConflictException) e;
		ErrorDto response = new ErrorDto();
		
		response.setMensaje(exception.getLocalizedMessage());;
		response.setExceptionType(exception.getClass().getSimpleName());
		
		return new ResponseEntity<ErrorDto>(response, HttpStatus.CONFLICT);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler({NoContentException.class})
	@ResponseBody
	public ResponseEntity<ErrorDto> handleNoContentException(Exception e){
		NoContentException exception = (NoContentException) e;
		ErrorDto response = new ErrorDto();
		
		response.setMensaje(exception.getLocalizedMessage());;
		response.setExceptionType(exception.getClass().getSimpleName());
		
		return new ResponseEntity<ErrorDto>(response, HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NotFoundException.class})
	@ResponseBody
	public ResponseEntity<ErrorDto> handleNotFoundException(Exception e){
		NotFoundException exception = (NotFoundException) e;
		ErrorDto response = new ErrorDto();
		
		response.setMensaje(exception.getLocalizedMessage());;
		response.setExceptionType(exception.getClass().getSimpleName());
		
		return new ResponseEntity<ErrorDto>(response, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public ResponseEntity<ErrorDto> handleException(Exception e){
		ErrorDto response = new ErrorDto();
		
		response.setMensaje(e.getLocalizedMessage());;
		response.setExceptionType(e.getClass().getSimpleName());
		
		return new ResponseEntity<ErrorDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
