package com.fuego.quasar.service.interfaces;

import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.exceptions.NoContentException;

public interface ISateliteService {

	public Satelite getSatelite(Long id) throws NoContentException;
	public Satelite saveSatelite(Satelite satelite);
	public Satelite updateSatelite(Satelite satelite);
	public void deleteSatelite(Satelite satelite);
	public void deleteSatelite(Long id);
}
