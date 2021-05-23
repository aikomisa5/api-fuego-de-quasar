package com.fuego.quasar.service.interfaces;

import com.fuego.quasar.dto.request.NaveRequest;
import com.fuego.quasar.dto.response.NaveResponse;
import com.fuego.quasar.entity.Nave;
import com.fuego.quasar.exceptions.NoContentException;

public interface INaveService {

	public NaveResponse getNaveResponse(Long id) throws NoContentException;
	public NaveResponse saveNaveRequest(NaveRequest request);	
	public Nave getNave(Long id) throws NoContentException;
	public Nave saveNave(Nave nave);
	public Nave updateNave(Nave nave);
	public void deleteNave(Nave nave);
	public void deleteNave(Long id);
}
