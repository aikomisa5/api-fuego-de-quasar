package com.fuego.quasar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuego.quasar.dto.request.NaveRequest;
import com.fuego.quasar.dto.request.PedidoAuxilioCompletoRequest;
import com.fuego.quasar.dto.request.PedidoAuxilioUnitarioRequest;
import com.fuego.quasar.dto.response.NaveResponse;
import com.fuego.quasar.dto.response.PedidoAuxilioResponse;
import com.fuego.quasar.exceptions.ConflictException;
import com.fuego.quasar.exceptions.NoContentException;
import com.fuego.quasar.exceptions.NotFoundException;
import com.fuego.quasar.service.NaveService;
import com.fuego.quasar.service.PedidoAuxilioService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("naves")
public class Controller {

	@Autowired
	private PedidoAuxilioService pedidoAuxilioService;

	@Autowired
	private NaveService naveService;
	
	@GetMapping("/{idNave}")
	@ApiOperation("Recurso para obtener los datos de una nave")
	public ResponseEntity<NaveResponse> getNave(
			@ApiIgnore @RequestHeader Map<String, String> header,
			@PathVariable Long idNave) throws NoContentException{
		
		NaveResponse response = naveService.getNaveResponse(idNave);
		
		return new ResponseEntity<NaveResponse>(response, HttpStatus.OK);
	}

	@PostMapping()
	@ApiOperation("Recurso para guardar los datos de una nave")
	public ResponseEntity<NaveResponse> postNave(
			@ApiIgnore @RequestHeader Map<String, String> header,
			@RequestBody NaveRequest request){

		NaveResponse response = naveService.saveNaveRequest(request);

		return new ResponseEntity<NaveResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/{idNave}/topsecret")
	@ApiOperation("Recurso para guardar los datos del pedido de auxilio y obtener la ubicacion de la nave y el mensaje secreto")
	public ResponseEntity<PedidoAuxilioResponse> postPedidoAuxilioCompleto(
			@ApiIgnore @RequestHeader Map<String, String> header,
			@PathVariable Long idNave,
			@RequestBody PedidoAuxilioCompletoRequest request) throws ConflictException, NotFoundException{

		PedidoAuxilioResponse response = pedidoAuxilioService.savePedidoAuxilioCompleto(request, idNave);

		return new ResponseEntity<PedidoAuxilioResponse>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{idNave}/topsecret-split")
	@ApiOperation("Recurso para obtener la ubicacion de la nave y el mensaje secreto a partir de pedidos de auxilio unitarios")
	public ResponseEntity<PedidoAuxilioResponse> getPedidoAuxilioUnitario(
			@ApiIgnore @RequestHeader Map<String, String> header,
			@PathVariable Long idNave) throws ConflictException, NotFoundException{

		PedidoAuxilioResponse response = pedidoAuxilioService.getPedidoAuxilioUnitarioDecodificado(idNave);

		return new ResponseEntity<PedidoAuxilioResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/{idNave}/topsecret-split/{satelliteName}")
	@ApiOperation("Recurso para guardar los datos de un pedido de auxilio unitario")
	public ResponseEntity<Void> postPedidoAuxilioUnitario(
			@ApiIgnore @RequestHeader Map<String, String> header,
			@PathVariable Long idNave,
			@PathVariable String satelliteName,
			@RequestBody PedidoAuxilioUnitarioRequest request) throws NotFoundException{

		pedidoAuxilioService.savePedidoAuxilioUnitario(request, idNave, satelliteName);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
