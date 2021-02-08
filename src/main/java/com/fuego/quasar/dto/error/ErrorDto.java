package com.fuego.quasar.dto.error;

import java.io.Serializable;

public class ErrorDto implements Serializable{

	private static final long serialVersionUID = -8007228170797757789L;
	
	private String mensaje;
	
	private String exceptionType;

	public ErrorDto() {
		super();
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
}
