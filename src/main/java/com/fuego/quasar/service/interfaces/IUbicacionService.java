package com.fuego.quasar.service.interfaces;

import com.fuego.quasar.entity.Ubicacion;
import com.fuego.quasar.exceptions.ConflictException;

public interface IUbicacionService {

	public Ubicacion getLocation(float[] distances) throws ConflictException;
	public float getXPositionSateliteKenobi();
	public float getYPositionSateliteKenobi();
	public float getXPositionSateliteSkywalker();
	public float getYPositionSateliteSkywalker();
	public float getXPositionSateliteSato();
	public float getYPositionSateliteSato();
}
