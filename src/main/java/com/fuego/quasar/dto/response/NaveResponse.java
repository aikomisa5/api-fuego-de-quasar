package com.fuego.quasar.dto.response;

import java.io.Serializable;

public class NaveResponse implements Serializable{

	private static final long serialVersionUID = 8549836967126667601L;

	private Long id;

	private String nombre;

	public NaveResponse() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
