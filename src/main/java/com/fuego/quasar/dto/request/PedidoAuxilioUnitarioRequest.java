package com.fuego.quasar.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PedidoAuxilioUnitarioRequest implements Serializable{
	
	private static final long serialVersionUID = 9204675904766203694L;

	@JsonProperty("distance")
	private float distanciaANave;
	
	@JsonProperty("message")
	private String[] mensaje;

	public PedidoAuxilioUnitarioRequest() {
		super();
	}

	public float getDistanciaANave() {
		return distanciaANave;
	}

	public void setDistanciaANave(float distanciaANave) {
		this.distanciaANave = distanciaANave;
	}

	public String[] getMensaje() {
		return mensaje;
	}

	public void setMensaje(String[] mensaje) {
		this.mensaje = mensaje;
	}
}
