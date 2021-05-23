package com.fuego.quasar.service.interfaces;

import java.util.List;

import com.fuego.quasar.exceptions.NotFoundException;

public interface IMensajeService {

	public String getMessage(List<String[]> messages) throws NotFoundException;
}
