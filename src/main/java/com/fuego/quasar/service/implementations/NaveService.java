package com.fuego.quasar.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuego.quasar.dto.request.NaveRequest;
import com.fuego.quasar.dto.response.NaveResponse;
import com.fuego.quasar.entity.Nave;
import com.fuego.quasar.exceptions.NoContentException;
import com.fuego.quasar.repository.NaveRepository;
import com.fuego.quasar.service.interfaces.INaveService;

@Service
public class NaveService implements INaveService{

	@Autowired
	private NaveRepository repository;
	
	public NaveResponse getNaveResponse(Long id) throws NoContentException{
		Nave nave = getNave(id);
		
		return new NaveResponse(nave.getId(), nave.getNombre());
	}

	public NaveResponse saveNaveRequest(NaveRequest request){
		Nave nave = new Nave(request.getNombre());
		Nave naveBBDD = repository.save(nave);

		return new NaveResponse(naveBBDD.getId(), naveBBDD.getNombre());
	}

	public Nave getNave(Long id) throws NoContentException{
		return repository.findById(id).orElseThrow(() -> new NoContentException("No se ha encontrado una nave con id: " + id));
	}

	public Nave saveNave(Nave nave){
		return repository.save(nave);
	}

	public Nave updateNave(Nave nave){
		return repository.save(nave);
	}

	public void deleteNave(Nave nave){
		repository.delete(nave);
	}

	public void deleteNave(Long id){
		repository.deleteById(id);
	}
}
