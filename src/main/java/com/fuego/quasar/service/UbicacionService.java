package com.fuego.quasar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.entity.Ubicacion;
import com.fuego.quasar.entity.UbicacionSatelite;
import com.fuego.quasar.exceptions.ConflictException;

@Service
public class UbicacionService {

	@Value("${x.position.satelite.kenobi:-500}")
	private float xPositionSateliteKenobi;

	@Value("${y.position.satelite.kenobi:-200}")
	private float yPositionSateliteKenobi;

	@Value("${x.position.satelite.skywalker:100}")
	private float xPositionSateliteSkywalker;

	@Value("${y.position.satelite.skywalker:-100}")
	private float yPositionSateliteSkywalker;

	@Value("${x.position.satelite.sato:500}")
	private float xPositionSateliteSato;

	@Value("${y.position.satelite.sato:100}")
	private float yPositionSateliteSato;

	/**
	 * Public
	 **/

	public Ubicacion getLocation(float[] distances) throws ConflictException {

		List<Satelite> satelites = getSatelites();

		float xPositionNave = getXPositionDeNave(satelites, distances);
		float yPositionNave = getYPositionDeNave(satelites, distances);

		Ubicacion location = new Ubicacion();

		location.setX(xPositionNave);
		location.setY(yPositionNave);

		return location;
	}

	public float getXPositionSateliteKenobi() {
		return xPositionSateliteKenobi;
	}

	public float getYPositionSateliteKenobi() {
		return yPositionSateliteKenobi;
	}

	public float getXPositionSateliteSkywalker() {
		return xPositionSateliteSkywalker;
	}

	public float getYPositionSateliteSkywalker() {
		return yPositionSateliteSkywalker;
	}

	public float getXPositionSateliteSato() {
		return xPositionSateliteSato;
	}

	public float getYPositionSateliteSato() {
		return yPositionSateliteSato;
	}

	/**
	 * Private
	 **/

	private List<Satelite> getSatelites(){

		List<Satelite> satelites = new ArrayList<Satelite>();

		Satelite sateliteKenobi = new Satelite();
		Satelite sateliteSkywalker = new Satelite();
		Satelite sateliteSato = new Satelite();

		UbicacionSatelite locationSateliteKenobi = new UbicacionSatelite();
		locationSateliteKenobi.setX(xPositionSateliteKenobi);
		locationSateliteKenobi.setY(yPositionSateliteKenobi);

		UbicacionSatelite locationSateliteSkywalker = new UbicacionSatelite();
		locationSateliteSkywalker.setX(xPositionSateliteSkywalker);
		locationSateliteSkywalker.setY(yPositionSateliteSkywalker);

		UbicacionSatelite locationSateliteSato = new UbicacionSatelite();
		locationSateliteSato.setX(xPositionSateliteSato);
		locationSateliteSato.setY(yPositionSateliteSato);

		sateliteKenobi.setNombre(Satelite.KENOBI);
		sateliteSkywalker.setNombre(Satelite.SKYWALKER);
		sateliteSato.setNombre(Satelite.SATO);

		sateliteKenobi.setUbicacion(locationSateliteKenobi);
		sateliteSkywalker.setUbicacion(locationSateliteSkywalker);
		sateliteSato.setUbicacion(locationSateliteSato);

		satelites.add(sateliteKenobi);
		satelites.add(sateliteSkywalker);
		satelites.add(sateliteSato);

		return satelites;
	}

	private float getXPositionDeNave(List<Satelite> satelites, float[] distances) throws ConflictException {

		if (satelites == null) {
			throw new ConflictException("Error. La cantidad de satelites es vacia");
		}
		if (distances == null) {
			throw new ConflictException("Error. La cantidad de distancias es vacia");
		}
		if (satelites.size() < 3) {
			throw new ConflictException("Error. La cantidad de satelites es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}
		if (distances.length < 3) {
			throw new ConflictException("Error. La cantidad de distancias es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}

		Satelite sateliteKenobi = getSatelite(satelites, Satelite.KENOBI);
		Satelite sateliteSkywalker = getSatelite(satelites, Satelite.SKYWALKER);
		Satelite sateliteSato = getSatelite(satelites, Satelite.SATO);

		float distanciaSateliteKenobi = distances[0];
		float distanciaSateliteSkywalker = distances[1];
		float distanciaSateliteSato = distances[2];

		float[] datosKenobi = {sateliteKenobi.getUbicacion().getX(), sateliteKenobi.getUbicacion().getY(), distanciaSateliteKenobi};
		float[] datosSkywalker = {sateliteSkywalker.getUbicacion().getX(), sateliteSkywalker.getUbicacion().getY(), distanciaSateliteSkywalker};
		float[] datosSato = {sateliteSato.getUbicacion().getX(), sateliteSato.getUbicacion().getY(), distanciaSateliteSato};

		float x = getXPositionWithTrilaterationAlgorithm(datosKenobi, datosSkywalker, datosSato);

		return x;
	}

	private float getYPositionDeNave(List<Satelite> satelites, float[] distances) throws ConflictException {

		if (satelites == null) {
			throw new ConflictException("Error. La cantidad de satelites es vacia");
		}
		if (distances == null) {
			throw new ConflictException("Error. La cantidad de distancias es vacia");
		}
		if (satelites.size() < 3) {
			throw new ConflictException("Error. La cantidad de satelites es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}
		if (distances.length < 3) {
			throw new ConflictException("Error. La cantidad de distancias es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}

		Satelite sateliteKenobi = getSatelite(satelites, Satelite.KENOBI);
		Satelite sateliteSkywalker = getSatelite(satelites, Satelite.SKYWALKER);
		Satelite sateliteSato = getSatelite(satelites, Satelite.SATO);

		float distanciaSateliteKenobi = distances[0];
		float distanciaSateliteSkywalker = distances[1];
		float distanciaSateliteSato = distances[2];

		float[] datosKenobi = {sateliteKenobi.getUbicacion().getX(), sateliteKenobi.getUbicacion().getY(), distanciaSateliteKenobi};
		float[] datosSkywalker = {sateliteSkywalker.getUbicacion().getX(), sateliteSkywalker.getUbicacion().getY(), distanciaSateliteSkywalker};
		float[] datosSato = {sateliteSato.getUbicacion().getX(), sateliteSato.getUbicacion().getY(), distanciaSateliteSato};

		float x = getXPositionWithTrilaterationAlgorithm(datosKenobi, datosSkywalker, datosSato);

		float y = getYPositionWithTrilaterationAlgorithm(datosKenobi, datosSkywalker, x);

		return y;
	}

	private float getXPositionWithTrilaterationAlgorithm(float[] datosKenobi, float[] datosSkywalker, float[] datosSato) {

		float x = (float) ( ( ( (Math.pow(datosKenobi[2], 2) - Math.pow(datosSkywalker[2], 2)) + (Math.pow(datosSkywalker[0],2) - Math.pow(datosKenobi[0],2)) + (Math.pow(datosSkywalker[1],2) - Math.pow(datosKenobi[1],2)) ) * 
				(2*datosSato[1]-2*datosSkywalker[1]) - ( (Math.pow(datosSkywalker[2],2)-Math.pow(datosSato[2],2)) + (Math.pow(datosSato[0],2)-Math.pow(datosSkywalker[0],2)) + (Math.pow(datosSato[1],2)-Math.pow(datosSkywalker[1],2)) ) * 
				(2*datosSkywalker[1]-2*datosKenobi[1]) ) / ( (2*datosSkywalker[0]-2*datosSato[0]) * (2*datosSkywalker[1]-2*datosKenobi[1]) - (2*datosKenobi[0]-2*datosSkywalker[0]) * (2*datosSato[1]-2*datosSkywalker[1] ) ) );
		return x;
	}

	private float getYPositionWithTrilaterationAlgorithm(float[] datosKenobi, float[] datosSkywalker, float x) {

		float y = (float) (( (Math.pow(datosKenobi[2],2)-Math.pow(datosSkywalker[2],2)) + (Math.pow(datosSkywalker[0],2)-Math.pow(datosKenobi[0],2)) + 
				(Math.pow(datosSkywalker[1],2)-Math.pow(datosKenobi[1],2)) + x*(2*datosKenobi[0]-2*datosSkywalker[0])) / (2*datosSkywalker[1]-2*datosKenobi[1]));
		return y;
	}

	private Satelite getSatelite(List<Satelite> satelites, String name) {
		return satelites.stream().filter(s -> s.getNombre().equals(name)).findFirst().orElse(null);
	}
}
