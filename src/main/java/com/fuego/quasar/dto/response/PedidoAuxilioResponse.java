package com.fuego.quasar.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fuego.quasar.dto.UbicacionDto;

public class PedidoAuxilioResponse implements Serializable{
	
	private static final long serialVersionUID = 7858764763731682251L;

	@JsonProperty("location")
	private UbicacionDto ubicacion;
	
	@JsonProperty("message")
	private String mensaje;

	public PedidoAuxilioResponse() {
		super();
	}

	public UbicacionDto getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDto ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
