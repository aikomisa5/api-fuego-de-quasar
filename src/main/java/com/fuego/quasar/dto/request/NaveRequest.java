package com.fuego.quasar.dto.request;

import java.io.Serializable;

public class NaveRequest implements Serializable{

	private static final long serialVersionUID = 3289337815662847641L;

	private String nombre;

	public NaveRequest() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
