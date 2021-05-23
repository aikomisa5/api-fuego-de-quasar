package com.fuego.quasar.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fuego.quasar.dto.UbicacionDto;
import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.entity.Ubicacion;
import com.fuego.quasar.entity.UbicacionPedidoAuxilio;
import com.fuego.quasar.exceptions.ConflictException;

@Component
public class UbicacionUtil {
	
	public UbicacionDto getUbicacionDto(Ubicacion ubicacion) {
		
		UbicacionDto response = new UbicacionDto();
		
		response.setX(ubicacion.getX());
		response.setY(ubicacion.getY());
		
		return response;
	}
	
	public UbicacionPedidoAuxilio getUbicacionPedidoAuxilioFromUbicacionDto(UbicacionDto ubicacion) {
		
		UbicacionPedidoAuxilio response = new UbicacionPedidoAuxilio();
		
		response.setX(ubicacion.getX());
		response.setY(ubicacion.getY());
		
		return response;
	}
	
	public void validateDistances(float[] distances) throws ConflictException {
		if (distances == null) {
			throw new ConflictException("Error. La cantidad de distancias es vacia");
		}
		if (distances.length < 3) {
			throw new ConflictException("Error. La cantidad de distancias es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}
	}

	public void validateSatelites(List<Satelite> satelites) throws ConflictException {
		if (satelites == null) {
			throw new ConflictException("Error. La cantidad de satelites es vacia");
		}
		if (satelites.size() < 3) {
			throw new ConflictException("Error. La cantidad de satelites es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}
	}
}
