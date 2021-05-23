package com.fuego.quasar.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Nave")
public class Nave {
	
	private UUID uuid;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "nave")
	private List<PedidoAuxilio> pedidosAuxilio = new ArrayList<PedidoAuxilio>();
	
	public Nave() {
		super();
		this.uuid = UUID.randomUUID();
	}
	
	public Nave(String nombre) {
		super();
		this.nombre = nombre;
		this.uuid = UUID.randomUUID();
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
	
	public List<PedidoAuxilio> getPedidosAuxilio() {
		return pedidosAuxilio;
	}

	public void setPedidosAuxilio(List<PedidoAuxilio> pedidosAuxilio) {
		this.pedidosAuxilio = pedidosAuxilio;
	}

	public void addPedidoAuxilio(PedidoAuxilio pedidoAuxilio) {
		this.pedidosAuxilio.add(pedidoAuxilio);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nave other = (Nave) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nave [uuid=" + uuid + ", id=" + id + ", nombre=" + nombre + "]";
	}
}
