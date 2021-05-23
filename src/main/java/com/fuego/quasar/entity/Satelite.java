package com.fuego.quasar.entity;

import java.util.Arrays;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Satelite")
public class Satelite {
	
	public static final String KENOBI = "KENOBI";
	public static final String SKYWALKER = "SKYWALKER";
	public static final String SATO = "SATO";

	private UUID uuid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;

	private String nombre;

	private float distanciaANave;

	private String[] mensaje;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private PedidoAuxilio pedido;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ubicacionsatelite_id", referencedColumnName = "id")
	private UbicacionSatelite ubicacion;
	
	public Satelite() {
		super();
		this.uuid = UUID.randomUUID();
	}
	
	public Satelite(String nombre) {
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

	public PedidoAuxilio getPedido() {
		return pedido;
	}

	public void setPedido(PedidoAuxilio pedido) {
		this.pedido = pedido;
	}

	public UbicacionSatelite getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionSatelite ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(distanciaANave);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(mensaje);
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
		Satelite other = (Satelite) obj;
		if (Float.floatToIntBits(distanciaANave) != Float.floatToIntBits(other.distanciaANave))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(mensaje, other.mensaje))
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
		return "Satelite [uuid=" + uuid + ", id=" + id + ", nombre=" + nombre + ", distanciaANave=" + distanciaANave
				+ ", mensaje=" + Arrays.toString(mensaje) + "]";
	}
}
