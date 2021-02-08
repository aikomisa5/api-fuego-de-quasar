package com.fuego.quasar.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UbicacionDto implements Serializable{
	
	private static final long serialVersionUID = -3330306403905231552L;

	@JsonProperty("x")
	private float x;
	
	@JsonProperty("y")
	private float y;
	
	public UbicacionDto() {
		super();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
