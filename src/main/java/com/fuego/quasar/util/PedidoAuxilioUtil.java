package com.fuego.quasar.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.exceptions.NotFoundException;

@Component
public class PedidoAuxilioUtil {
	
	public float[] getDistanciasOrdenadas(List<Satelite> satellites) throws NotFoundException {

		float distanciaKenobi = getDistancia(satellites, Satelite.KENOBI);
		float distanciaSkywalker = getDistancia(satellites, Satelite.SKYWALKER);
		float distanciaSato = getDistancia(satellites, Satelite.SATO);

		float[] distancias = {distanciaKenobi, distanciaSkywalker, distanciaSato};

		return distancias;
	}

	public float getDistancia(List<Satelite> satellites, String sateliteName) throws NotFoundException {
		return satellites.stream().filter(s -> s.getNombre().toUpperCase().equals(sateliteName))
				.map(s -> s.getDistanciaANave()).findFirst()
				.orElseThrow(() -> new NotFoundException("Error. Falta informacion sobre el mensaje y las distancias del satelite: " + sateliteName));
	}

	public List<String[]> getMensajes(List<Satelite> satellites){
		return satellites.stream().map(s -> s.getMensaje()).collect(Collectors.toList());
	}
}
