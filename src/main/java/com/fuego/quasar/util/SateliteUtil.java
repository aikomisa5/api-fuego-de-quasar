package com.fuego.quasar.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuego.quasar.dto.SateliteDto;
import com.fuego.quasar.dto.request.PedidoAuxilioCompletoRequest;
import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.entity.UbicacionSatelite;
import com.fuego.quasar.exceptions.NotFoundException;
import com.fuego.quasar.service.implementations.UbicacionService;

@Component
public class SateliteUtil {

	@Autowired
	private UbicacionService ubicacionService;

	public Satelite getSateliteFromSateliteDto(SateliteDto sateliteDto) {

		Satelite satelite = new Satelite();

		satelite.setNombre(sateliteDto.getNombre().toUpperCase());
		satelite.setDistanciaANave(sateliteDto.getDistanciaANave());
		satelite.setMensaje(sateliteDto.getMensaje());

		return satelite;
	}

	public void validateNombreSatelite(String nombreSatelite) throws NotFoundException {

		nombreSatelite = nombreSatelite.toUpperCase();

		if (nombreSatelite.equals(Satelite.KENOBI) == false && nombreSatelite.equals(Satelite.SKYWALKER) == false && nombreSatelite.equals(Satelite.SATO) == false) {
			throw new NotFoundException("Error. El nombre del satelite no es valido: " + nombreSatelite);
		}
	}

	public void validateNombreSatelites(List<Satelite> satelites) throws NotFoundException {

		boolean sateliteKenobi = false;
		boolean sateliteSkywalker = false;
		boolean sateliteSato = false;

		for (Satelite satelite : satelites) {
			if (satelite.getNombre().equals(Satelite.KENOBI)) {
				sateliteKenobi = true;
			}else if(satelite.getNombre().equals(Satelite.SKYWALKER)){
				sateliteSkywalker = true;
			}else if(satelite.getNombre().equals(Satelite.SATO)){
				sateliteSato = true;
			}else {
				throw new NotFoundException("Error. Uno de los nombres de satelite no es valido: " + satelite.getNombre());
			}
		}

		if (sateliteKenobi == false || sateliteSkywalker == false || sateliteSato == false) {
			throw new NotFoundException("Error. No se envio la informacion de uno o mas de los 3 satelites");
		}
	}

	public void validateCantidadSatelites(PedidoAuxilioCompletoRequest request) throws NotFoundException {

		if (request.getSatelites() == null || request.getSatelites().size() == 0) {
			throw new NotFoundException("Error. La informacion enviada al satelite sobre el mensaje y distancias es vacia");
		}
		if (request.getSatelites().size() < 3) {
			throw new NotFoundException("Error. Falta informacion sobre el mensaje y las distancias a los satelites");
		}
	}

	public void fillSateliteUbicacion(Satelite satelite) throws NotFoundException {
		fillSateliteUbicacionByName(satelite);
	}

	public void fillSatelitesUbicaciones(List<Satelite> satelites) throws NotFoundException {
		for (Satelite satelite : satelites) {
			fillSateliteUbicacionByName(satelite);
		}
	}

	private void fillSateliteUbicacionByName(Satelite satelite) throws NotFoundException {
		if (satelite.getNombre().equals(Satelite.KENOBI)) {
			UbicacionSatelite ubicacionSateliteKenobi = new UbicacionSatelite(ubicacionService.getXPositionSateliteKenobi(), ubicacionService.getYPositionSateliteKenobi());
			ubicacionSateliteKenobi.setSatelite(satelite);
			satelite.setUbicacion(ubicacionSateliteKenobi);

		}else if(satelite.getNombre().equals(Satelite.SKYWALKER)){
			UbicacionSatelite ubicacionSateliteSkywalker = new UbicacionSatelite(ubicacionService.getXPositionSateliteSkywalker(), ubicacionService.getYPositionSateliteSkywalker());
			ubicacionSateliteSkywalker.setSatelite(satelite);
			satelite.setUbicacion(ubicacionSateliteSkywalker);

		}else if(satelite.getNombre().equals(Satelite.SATO)){
			UbicacionSatelite ubicacionSateliteSato = new UbicacionSatelite(ubicacionService.getXPositionSateliteSato(), ubicacionService.getYPositionSateliteSato());
			ubicacionSateliteSato.setSatelite(satelite);
			satelite.setUbicacion(ubicacionSateliteSato);

		}else {
			throw new NotFoundException("Error. Uno de los nombres de satelite no es valido: " + satelite.getNombre());
		}
	}
}
