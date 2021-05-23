package com.fuego.quasar.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UbicacionSatelite")
public class UbicacionSatelite extends Ubicacion{
	
	@OneToOne(cascade = {CascadeType.ALL}, mappedBy = "ubicacion")
	private Satelite satelite;
	
	public UbicacionSatelite() {
		super();
	}
	
	public UbicacionSatelite(float x, float y) {
		super();
		this.setX(x);
		this.setY(y);
	}

	public Satelite getSatelite() {
		return satelite;
	}

	public void setSatelite(Satelite satelite) {
		this.satelite = satelite;
	}
}
