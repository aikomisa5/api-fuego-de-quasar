package com.fuego.quasar.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SateliteDto implements Serializable{
	
	private static final long serialVersionUID = -8843328781533972192L;

	@JsonProperty("name")
	private String nombre;
	
	@JsonProperty("distance")
	private float distanciaANave;
	
	@JsonProperty("message")
	private String[] mensaje;

	public SateliteDto() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
