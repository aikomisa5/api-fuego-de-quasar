package com.fuego.quasar.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UbicacionPedidoAuxilio")
public class UbicacionPedidoAuxilio extends Ubicacion{
	
	@OneToOne(cascade = {CascadeType.ALL}, mappedBy = "ubicacion")
	private PedidoAuxilio pedidoAuxilio;
	
	public UbicacionPedidoAuxilio() {
		super();
	}

	public PedidoAuxilio getPedidoAuxilio() {
		return pedidoAuxilio;
	}

	public void setPedidoAuxilio(PedidoAuxilio pedidoAuxilio) {
		this.pedidoAuxilio = pedidoAuxilio;
	}
}
