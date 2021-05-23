package com.fuego.quasar.service.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.entity.Ubicacion;
import com.fuego.quasar.entity.UbicacionSatelite;
import com.fuego.quasar.exceptions.ConflictException;
import com.fuego.quasar.service.interfaces.IUbicacionService;
import com.fuego.quasar.util.UbicacionUtil;

@Service
public class UbicacionService implements IUbicacionService{

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

	@Autowired
	private UbicacionUtil ubicacionUtil;
	
	public Ubicacion getLocation(float[] distances) throws ConflictException {
		List<Satelite> satelites = getSatelites();
		float xPositionNave = getXPositionDeNave(satelites, distances);
		float yPositionNave = getYPositionDeNave(satelites, distances);

		return new Ubicacion(xPositionNave, yPositionNave);
	}

	public float getXPositionSateliteKenobi() {
		return this.xPositionSateliteKenobi;
	}

	public float getYPositionSateliteKenobi() {
		return this.yPositionSateliteKenobi;
	}

	public float getXPositionSateliteSkywalker() {
		return this.xPositionSateliteSkywalker;
	}

	public float getYPositionSateliteSkywalker() {
		return this.yPositionSateliteSkywalker;
	}

	public float getXPositionSateliteSato() {
		return this.xPositionSateliteSato;
	}

	public float getYPositionSateliteSato() {
		return this.yPositionSateliteSato;
	}

	private List<Satelite> getSatelites(){

		Satelite sateliteKenobi = new Satelite(Satelite.KENOBI);
		Satelite sateliteSkywalker = new Satelite(Satelite.SKYWALKER);
		Satelite sateliteSato = new Satelite(Satelite.SATO);

		UbicacionSatelite ubicacionSateliteKenobi = new UbicacionSatelite(xPositionSateliteKenobi, yPositionSateliteKenobi);
		UbicacionSatelite ubicacionSateliteSkywalker = new UbicacionSatelite(xPositionSateliteSkywalker, yPositionSateliteSkywalker);
		UbicacionSatelite ubicacionSateliteSato = new UbicacionSatelite(xPositionSateliteSato, yPositionSateliteSato);

		sateliteKenobi.setUbicacion(ubicacionSateliteKenobi);
		sateliteSkywalker.setUbicacion(ubicacionSateliteSkywalker);
		sateliteSato.setUbicacion(ubicacionSateliteSato);
		
		return new ArrayList<Satelite>(Arrays.asList(sateliteKenobi, sateliteSkywalker, sateliteSato));
	}

	private float getXPositionDeNave(List<Satelite> satelites, float[] distances) throws ConflictException {

		ubicacionUtil.validateSatelites(satelites);
		ubicacionUtil.validateDistances(distances);

		Satelite sateliteKenobi = getSatelite(satelites, Satelite.KENOBI);
		Satelite sateliteSkywalker = getSatelite(satelites, Satelite.SKYWALKER);
		Satelite sateliteSato = getSatelite(satelites, Satelite.SATO);

		float distanciaSateliteKenobi = distances[0];
		float distanciaSateliteSkywalker = distances[1];
		float distanciaSateliteSato = distances[2];

		float[] datosKenobi = {sateliteKenobi.getUbicacion().getX(), sateliteKenobi.getUbicacion().getY(), distanciaSateliteKenobi};
		float[] datosSkywalker = {sateliteSkywalker.getUbicacion().getX(), sateliteSkywalker.getUbicacion().getY(), distanciaSateliteSkywalker};
		float[] datosSato = {sateliteSato.getUbicacion().getX(), sateliteSato.getUbicacion().getY(), distanciaSateliteSato};

		return calculateXPositionUsingTrilateration(datosKenobi, datosSkywalker, datosSato);
	}

	private float getYPositionDeNave(List<Satelite> satelites, float[] distances) throws ConflictException {

		ubicacionUtil.validateSatelites(satelites);
		ubicacionUtil.validateDistances(distances);

		Satelite sateliteKenobi = getSatelite(satelites, Satelite.KENOBI);
		Satelite sateliteSkywalker = getSatelite(satelites, Satelite.SKYWALKER);
		Satelite sateliteSato = getSatelite(satelites, Satelite.SATO);

		float distanciaSateliteKenobi = distances[0];
		float distanciaSateliteSkywalker = distances[1];
		float distanciaSateliteSato = distances[2];

		float[] datosSateliteKenobi = {sateliteKenobi.getUbicacion().getX(), sateliteKenobi.getUbicacion().getY(), distanciaSateliteKenobi};
		float[] datosSaliteSkywalker = {sateliteSkywalker.getUbicacion().getX(), sateliteSkywalker.getUbicacion().getY(), distanciaSateliteSkywalker};
		float[] datosSateliteSato = {sateliteSato.getUbicacion().getX(), sateliteSato.getUbicacion().getY(), distanciaSateliteSato};

		float x = calculateXPositionUsingTrilateration(datosSateliteKenobi, datosSaliteSkywalker, datosSateliteSato);
		
		return calculateYPositionUsingTrilateration(datosSateliteKenobi, datosSaliteSkywalker, x);
	}

	private float calculateXPositionUsingTrilateration(float[] datosSateliteKenobi, float[] datosSateliteSkywalker, float[] datosSateliteSato) {

		float x = (float) ( ( ( (Math.pow(datosSateliteKenobi[2], 2) - Math.pow(datosSateliteSkywalker[2], 2)) + (Math.pow(datosSateliteSkywalker[0],2) - Math.pow(datosSateliteKenobi[0],2)) + (Math.pow(datosSateliteSkywalker[1],2) - Math.pow(datosSateliteKenobi[1],2)) ) * 
				(2*datosSateliteSato[1]-2*datosSateliteSkywalker[1]) - ( (Math.pow(datosSateliteSkywalker[2],2)-Math.pow(datosSateliteSato[2],2)) + (Math.pow(datosSateliteSato[0],2)-Math.pow(datosSateliteSkywalker[0],2)) + (Math.pow(datosSateliteSato[1],2)-Math.pow(datosSateliteSkywalker[1],2)) ) * 
				(2*datosSateliteSkywalker[1]-2*datosSateliteKenobi[1]) ) / ( (2*datosSateliteSkywalker[0]-2*datosSateliteSato[0]) * (2*datosSateliteSkywalker[1]-2*datosSateliteKenobi[1]) - (2*datosSateliteKenobi[0]-2*datosSateliteSkywalker[0]) * (2*datosSateliteSato[1]-2*datosSateliteSkywalker[1] ) ) );
		return x;
	}

	private float calculateYPositionUsingTrilateration(float[] datosSateliteKenobi, float[] datosSateliteSkywalker, float x) {

		float y = (float) (( (Math.pow(datosSateliteKenobi[2],2)-Math.pow(datosSateliteSkywalker[2],2)) + (Math.pow(datosSateliteSkywalker[0],2)-Math.pow(datosSateliteKenobi[0],2)) + 
				(Math.pow(datosSateliteSkywalker[1],2)-Math.pow(datosSateliteKenobi[1],2)) + x*(2*datosSateliteKenobi[0]-2*datosSateliteSkywalker[0])) / (2*datosSateliteSkywalker[1]-2*datosSateliteKenobi[1]));
		return y;
	}

	private Satelite getSatelite(List<Satelite> satelites, String name) {
		return satelites.stream().filter(s -> s.getNombre().equals(name)).findFirst().orElse(null);
	}
}
