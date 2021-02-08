package com.fuego.quasar.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fuego.quasar.dto.SateliteDto;

public class PedidoAuxilioCompletoRequest implements Serializable{
	
	private static final long serialVersionUID = 5958330426962284400L;
	
	@JsonProperty("satellites")
	List<SateliteDto> satelites = new ArrayList<SateliteDto>();

	public PedidoAuxilioCompletoRequest() {
		super();
	}

	public List<SateliteDto> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<SateliteDto> satelites) {
		this.satelites = satelites;
	}
}
