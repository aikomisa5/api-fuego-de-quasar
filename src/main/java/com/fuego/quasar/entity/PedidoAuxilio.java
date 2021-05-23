package com.fuego.quasar.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PedidoAuxilio")
public class PedidoAuxilio {
	
	private UUID uuid;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nave_id")
	private Nave nave;
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pedido")
	private List<Satelite> satelites = new ArrayList<Satelite>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ubicacionpedidoauxilio_id", referencedColumnName = "id")
	private UbicacionPedidoAuxilio ubicacion;

	public PedidoAuxilio() {
		super();
		this.uuid = UUID.randomUUID();
	}
	
	public PedidoAuxilio(Nave nave) {
		super();
		this.uuid = UUID.randomUUID();
		this.nave = nave;
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

	public Nave getNave() {
		return nave;
	}

	public void setNave(Nave nave) {
		this.nave = nave;
	}

	public List<Satelite> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<Satelite> satelites) {
		this.satelites = satelites;
	}

	public void addSatelite(Satelite satelite) {
		this.satelites.add(satelite);
	}

	public UbicacionPedidoAuxilio getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionPedidoAuxilio ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PedidoAuxilio other = (PedidoAuxilio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "PedidoAuxilio [uuid=" + uuid + ", id=" + id + "]";
	}
}
