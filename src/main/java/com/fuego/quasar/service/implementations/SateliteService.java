package com.fuego.quasar.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.exceptions.NoContentException;
import com.fuego.quasar.repository.SateliteRepository;
import com.fuego.quasar.service.interfaces.ISateliteService;

@Service
public class SateliteService implements ISateliteService{

	@Autowired
	private SateliteRepository repository;

	public Satelite getSatelite(Long id) throws NoContentException{
		return repository.findById(id).orElseThrow(() -> new NoContentException("No se ha encontrado un satelite con id: " + id));
	}

	public Satelite saveSatelite(Satelite satelite){
		return repository.save(satelite);
	}

	public Satelite updateSatelite(Satelite satelite){
		return repository.save(satelite);
	}

	public void deleteSatelite(Satelite satelite){
		repository.delete(satelite);
	}

	public void deleteSatelite(Long id){
		repository.deleteById(id);
	}
}
