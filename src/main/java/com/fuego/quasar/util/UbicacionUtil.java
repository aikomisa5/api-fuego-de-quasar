package com.fuego.quasar.util;

import org.springframework.stereotype.Component;

import com.fuego.quasar.dto.UbicacionDto;
import com.fuego.quasar.entity.Ubicacion;
import com.fuego.quasar.entity.UbicacionPedidoAuxilio;

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
}
