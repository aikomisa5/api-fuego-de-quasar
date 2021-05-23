package com.fuego.quasar.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuego.quasar.dto.request.PedidoAuxilioCompletoRequest;
import com.fuego.quasar.dto.request.PedidoAuxilioUnitarioRequest;
import com.fuego.quasar.dto.response.PedidoAuxilioResponse;
import com.fuego.quasar.entity.Nave;
import com.fuego.quasar.entity.PedidoAuxilio;
import com.fuego.quasar.entity.Satelite;
import com.fuego.quasar.entity.UbicacionPedidoAuxilio;
import com.fuego.quasar.exceptions.ConflictException;
import com.fuego.quasar.exceptions.NoContentException;
import com.fuego.quasar.exceptions.NotFoundException;
import com.fuego.quasar.repository.PedidoAuxilioRepository;
import com.fuego.quasar.util.PedidoAuxilioUtil;
import com.fuego.quasar.util.SateliteUtil;
import com.fuego.quasar.util.UbicacionUtil;

@Service
public class PedidoAuxilioService {
	
	@Autowired
	private PedidoAuxilioRepository repository;

	@Autowired
	private UbicacionService ubicacionService;

	@Autowired
	private MensajeService mensajeService;

	@Autowired
	private NaveService naveService;

	@Autowired
	private PedidoAuxilioUtil pedidoAuxilioUtil;
	
	@Autowired
	private SateliteUtil sateliteUtil;

	@Autowired
	private UbicacionUtil ubicacionUtil;

	public PedidoAuxilioResponse getPedidoAuxilioUnitarioDecodificado(Long idNave) throws NotFoundException, ConflictException {
		
		Nave nave = getNavePedido(idNave);
		pedidoAuxilioUtil.validatePedidosAuxilioFromNave(idNave, nave);
		PedidoAuxilio pedidoAuxilio = getUltimoPedidoAuxilio(nave);
		pedidoAuxilioUtil.validateSatelitesFromPedidoAuxilio(idNave, pedidoAuxilio);
		sateliteUtil.validateNombreSatelites(pedidoAuxilio.getSatelites());
		
		return getDecodedMessageAndLocation(pedidoAuxilio.getSatelites());
	}

	public void savePedidoAuxilioUnitario(PedidoAuxilioUnitarioRequest pedidoAuxilioUnitario, Long idNave, String nombreSatelite) throws NotFoundException {
		
		sateliteUtil.validateNombreSatelite(nombreSatelite);
		nombreSatelite = nombreSatelite.toUpperCase();

		Nave nave = getNavePedido(idNave);

		if (nave.getPedidosAuxilio() == null || nave.getPedidosAuxilio().isEmpty()) {

			PedidoAuxilio pedido = new PedidoAuxilio(nave);

			Satelite satelite = new Satelite();
			satelite.setNombre(nombreSatelite);
			satelite.setMensaje(pedidoAuxilioUnitario.getMensaje());
			satelite.setDistanciaANave(pedidoAuxilioUnitario.getDistanciaANave());
			satelite.setPedido(pedido);
			sateliteUtil.fillSateliteUbicacion(satelite);
			pedido.addSatelite(satelite);
			
			savePedidoAuxilio(pedido);
		}else {

			PedidoAuxilio pedidoAuxilio = getUltimoPedidoAuxilio(nave);
			Satelite sateliteBBDD = getSateliteFromPedidoAuxilio(nombreSatelite, pedidoAuxilio);

			if (sateliteBBDD == null) {
				Satelite satelite = new Satelite();
				satelite.setNombre(nombreSatelite);
				satelite.setMensaje(pedidoAuxilioUnitario.getMensaje());
				satelite.setDistanciaANave(pedidoAuxilioUnitario.getDistanciaANave());
				satelite.setPedido(pedidoAuxilio);
				sateliteUtil.fillSateliteUbicacion(satelite);

				pedidoAuxilio.addSatelite(satelite);
			}else {
				sateliteBBDD.setMensaje(pedidoAuxilioUnitario.getMensaje());
				sateliteBBDD.setDistanciaANave(pedidoAuxilioUnitario.getDistanciaANave());
				sateliteUtil.fillSateliteUbicacion(sateliteBBDD);
			}

			savePedidoAuxilio(pedidoAuxilio);
		}
	}

	public PedidoAuxilioResponse savePedidoAuxilioCompleto(PedidoAuxilioCompletoRequest request, Long idNave) throws NotFoundException, ConflictException {

		PedidoAuxilioResponse response = new PedidoAuxilioResponse();

		Nave naveBBDD = getNavePedido(idNave);
		sateliteUtil.validateCantidadSatelites(request);
		List<Satelite> satelites = request.getSatelites().stream().map(s -> sateliteUtil.getSateliteFromSateliteDto(s)).collect(Collectors.toList());
		sateliteUtil.validateNombreSatelites(satelites);

		response = getDecodedMessageAndLocation(satelites);

		PedidoAuxilio pedidoAuxilio = new PedidoAuxilio();
		UbicacionPedidoAuxilio ubicacionPedidoAuxilio = new UbicacionPedidoAuxilio();
		
		ubicacionPedidoAuxilio = ubicacionUtil.getUbicacionPedidoAuxilioFromUbicacionDto(response.getUbicacion());
		pedidoAuxilio.setUbicacion(ubicacionPedidoAuxilio);
		pedidoAuxilio.setNave(naveBBDD);
		
		sateliteUtil.fillSatelitesUbicaciones(satelites);

		satelites.forEach(s ->{
			pedidoAuxilio.addSatelite(s);
			s.setPedido(pedidoAuxilio);
		});

		naveBBDD.addPedidoAuxilio(pedidoAuxilio);
		savePedidoAuxilio(pedidoAuxilio);

		return response;
	}

	public PedidoAuxilio getPedidoAuxilio(Long id) throws NoContentException{
		return repository.findById(id).orElseThrow(() -> new NoContentException("No se ha encontrado un pedido de auxilio con id: " + id));
	}

	public PedidoAuxilio savePedidoAuxilio(PedidoAuxilio pedidoAuxilio){
		return repository.save(pedidoAuxilio);
	}

	public PedidoAuxilio updatePedidoAuxilio(PedidoAuxilio pedidoAuxilio){
		return repository.save(pedidoAuxilio);
	}

	public void deletePedidoAuxilio(PedidoAuxilio pedidoAuxilio){
		repository.delete(pedidoAuxilio);
	}

	public void deletePedidoAuxilio(Long id){
		repository.deleteById(id);	
	}
	
	private Nave getNavePedido(Long idNave) throws NotFoundException {
		Nave naveBBDD;
		try {
			naveBBDD = naveService.getNave(idNave);
		}catch(NoContentException e) {
			throw new NotFoundException("Error. No existe una nave con id: " + idNave);
		}
		return naveBBDD;
	}
	
	private Satelite getSateliteFromPedidoAuxilio(String nombreSatelite, PedidoAuxilio pedidoAuxilio) {
		Satelite sateliteBBDD = pedidoAuxilio.getSatelites().stream().filter(p -> p.getNombre().equals(nombreSatelite)).findFirst().orElse(null);
		return sateliteBBDD;
	}

	private PedidoAuxilioResponse getDecodedMessageAndLocation(List<Satelite> satelites)
			throws NotFoundException, ConflictException {
		
		PedidoAuxilioResponse response = new PedidoAuxilioResponse();
		
		float[] distancias = pedidoAuxilioUtil.getDistanciasOrdenadas(satelites);
		response.setUbicacion(ubicacionUtil.getUbicacionDto(ubicacionService.getLocation(distancias)));

		List<String[]> mensajes = pedidoAuxilioUtil.getMensajes(satelites);
		response.setMensaje(mensajeService.getMessage(mensajes));
		
		return response;
	}

	private PedidoAuxilio getUltimoPedidoAuxilio(Nave naveBBDD) throws NotFoundException {
		
		List<Long> ids = naveBBDD.getPedidosAuxilio().stream().map(n -> n.getId()).collect(Collectors.toList());
		Long idMaximo = ids.stream().max(Long::compare).get();
		PedidoAuxilio pedidoAuxilio = naveBBDD.getPedidosAuxilio().stream().filter(n -> n.getId().equals(idMaximo)).findFirst().orElseThrow(() -> new NotFoundException("Error. No se ha encontrado un pedido de auxilio con id: " + idMaximo));
		return pedidoAuxilio;
	}
}
