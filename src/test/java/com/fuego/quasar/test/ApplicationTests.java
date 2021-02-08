package com.fuego.quasar.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuego.quasar.dto.SateliteDto;
import com.fuego.quasar.dto.request.PedidoAuxilioCompletoRequest;
import com.fuego.quasar.dto.request.PedidoAuxilioUnitarioRequest;
import com.fuego.quasar.dto.response.PedidoAuxilioResponse;
import com.fuego.quasar.entity.Nave;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	@Order(1)
	public void postNaveI() throws Exception {
		
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
	}
	
	@Test
	@Order(2)
	public void postNaveII() throws Exception {
		
		/**
		 * Nave I 
		 **/
		Nave naveI = new Nave();
		naveI.setNombre("Nave rebelde de test I");
		
		String bodyI = mapper.writeValueAsString(naveI);
		assertNotNull(bodyI);
		
		MvcResult resultI = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyI))
				.andExpect(status().isCreated()).andReturn();
		
		String responseI = resultI.getResponse().getContentAsString();
		
		Nave naveIBBDD = mapper.readValue(responseI, Nave.class);
		
		assertNotNull(naveIBBDD);
		assertTrue(naveIBBDD.getId().equals(1L));

		/**
		 * Nave I 
		 **/
		Nave naveII = new Nave();
		naveII.setNombre("Nave rebelde de test I");
		
		String bodyII = mapper.writeValueAsString(naveII);
		assertNotNull(bodyII);

		MvcResult resultII = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyII))
				.andExpect(status().isCreated()).andReturn();

		String responseII = resultII.getResponse().getContentAsString();

		Nave naveIIBBDD = mapper.readValue(responseII, Nave.class);

		assertNotNull(naveIIBBDD);
		assertTrue(naveIIBBDD.getId().equals(2L));
		
	}
	
	@Test
	@Order(3)
	public void getNave() throws Exception {
		
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
		
		MvcResult resultGet = mockMvc.perform(get("/naves/{idNave}", naveBBDD.getId()))
				.andExpect(status().isOk()).andReturn();
		
		String responseGet = resultGet.getResponse().getContentAsString();
		
		Nave naveGet = mapper.readValue(responseGet, Nave.class);
		
		assertTrue(naveBBDD.getId().equals(naveGet.getId()));
	}
	
	@Test
	@Order(4)
	public void getNave204() throws Exception {
		
		mockMvc.perform(get("/naves/{idNave}", 10000)).andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	public void postPedidoAuxilioCompletoI() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
		
		/**
		 * Pedido auxilio
		 **/
		PedidoAuxilioCompletoRequest request = new PedidoAuxilioCompletoRequest();
		
		SateliteDto sateliteKenobi = new SateliteDto();
		sateliteKenobi.setNombre("kenobi");
		sateliteKenobi.setDistanciaANave(696.419414f);
		
		String[] mensajeKenobi = {"este", "", "", "mensaje", ""};
		sateliteKenobi.setMensaje(mensajeKenobi);
		
		SateliteDto sateliteSkywalker = new SateliteDto();
		sateliteSkywalker.setNombre("skywalker");
		sateliteSkywalker.setDistanciaANave(158.113883f);
		
		String[] mensajeSkywalker = {"", "es", "", "", "secreto"};
		sateliteSkywalker.setMensaje(mensajeSkywalker);
		
		SateliteDto sateliteSato = new SateliteDto();
		sateliteSato.setNombre("sato");
		sateliteSato.setDistanciaANave(353.553391f);
		
		String[] mensajeSato = {"este", "", "un", "", ""};
		sateliteSato.setMensaje(mensajeSato);
		
		List<SateliteDto> satelites = new ArrayList<SateliteDto>();
		satelites.add(sateliteKenobi);
		satelites.add(sateliteSkywalker);
		satelites.add(sateliteSato);
		
		request.setSatelites(satelites);
		
		String bodyPedidoAuxilio = mapper.writeValueAsString(request);
		assertNotNull(bodyPedidoAuxilio);
		
		MvcResult resultPedidoAuxilio = mockMvc.perform(post("/naves/1/topsecret")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilio))
				.andExpect(status().isCreated()).andReturn();
		
		String responsePedidoAuxilio = resultPedidoAuxilio.getResponse().getContentAsString();
		
		PedidoAuxilioResponse pedidoAuxilioResponse = mapper.readValue(responsePedidoAuxilio, PedidoAuxilioResponse.class);
		
		assertNotNull(pedidoAuxilioResponse);
		assertTrue(pedidoAuxilioResponse.getMensaje().equals("este es un mensaje secreto"));
		assertTrue(pedidoAuxilioResponse.getUbicacion().getX() > 149f && pedidoAuxilioResponse.getUbicacion().getX() < 151f);
		assertTrue(pedidoAuxilioResponse.getUbicacion().getY() > 49f && pedidoAuxilioResponse.getUbicacion().getY() < 51f);
		
	}

	@Test
	@Order(6)
	public void postPedidoAuxilioCompletoII() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
		
		/**
		 * Pedido auxilio 
		 **/
		PedidoAuxilioCompletoRequest request = new PedidoAuxilioCompletoRequest();
		
		SateliteDto sateliteKenobi = new SateliteDto();
		sateliteKenobi.setNombre("kenobi");
		sateliteKenobi.setDistanciaANave(590.529423f);
		
		String[] mensajeKenobi = {"este", "", "es", "", "", "secreto"};
		sateliteKenobi.setMensaje(mensajeKenobi);
		
		SateliteDto sateliteSkywalker = new SateliteDto();
		sateliteSkywalker.setNombre("skywalker");
		sateliteSkywalker.setDistanciaANave(163.477827f);
		
		String[] mensajeSkywalker = {"", "mensaje", "", "un", "", ""};
		sateliteSkywalker.setMensaje(mensajeSkywalker);
		
		SateliteDto sateliteSato = new SateliteDto();
		sateliteSato.setNombre("sato");
		sateliteSato.setDistanciaANave(467.680446f);
		
		String[] mensajeSato = {"", "", "", "", "mensaje", ""};
		sateliteSato.setMensaje(mensajeSato);
		
		List<SateliteDto> satelites = new ArrayList<SateliteDto>();
		satelites.add(sateliteKenobi);
		satelites.add(sateliteSkywalker);
		satelites.add(sateliteSato);
		
		request.setSatelites(satelites);
		
		String bodyPedidoAuxilio = mapper.writeValueAsString(request);
		assertNotNull(bodyPedidoAuxilio);
		
		MvcResult resultPedidoAuxilio = mockMvc.perform(post("/naves/1/topsecret")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilio))
				.andExpect(status().isCreated()).andReturn();
		
		String responsePedidoAuxilio = resultPedidoAuxilio.getResponse().getContentAsString();
		
		PedidoAuxilioResponse pedidoAuxilioResponse = mapper.readValue(responsePedidoAuxilio, PedidoAuxilioResponse.class);
		
		assertNotNull(pedidoAuxilioResponse);
		assertTrue(pedidoAuxilioResponse.getMensaje().equals("este mensaje es un mensaje secreto"));
		assertTrue(pedidoAuxilioResponse.getUbicacion().getX() > 34f && pedidoAuxilioResponse.getUbicacion().getX() < 36f);
		assertTrue(pedidoAuxilioResponse.getUbicacion().getY() > 49f && pedidoAuxilioResponse.getUbicacion().getY() < 51f);
		
	}
	
	@Test
	@Order(7)
	public void postPedidoAuxilioCompletoIII() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
		
		/**
		 * Pedido auxilio
		 **/
		PedidoAuxilioCompletoRequest request = new PedidoAuxilioCompletoRequest();
		
		SateliteDto sateliteKenobi = new SateliteDto();
		sateliteKenobi.setNombre("kenobi");
		sateliteKenobi.setDistanciaANave(551.543289f);
		
		String[] mensajeKenobi = {"este", "", "es", "", "mensaje", "", "para", "", "satelites"};
		sateliteKenobi.setMensaje(mensajeKenobi);
		
		SateliteDto sateliteSkywalker = new SateliteDto();
		sateliteSkywalker.setNombre("skywalker");
		sateliteSkywalker.setDistanciaANave(142.126704f);
		
		String[] mensajeSkywalker = {"", "mensaje", "", "", "", "secreto", "", "", ""};
		sateliteSkywalker.setMensaje(mensajeSkywalker);
		
		SateliteDto sateliteSato = new SateliteDto();
		sateliteSato.setNombre("sato");
		sateliteSato.setDistanciaANave(498.196748f);
		
		String[] mensajeSato = {"", "", "", "un", "", "", "", "los", ""};
		sateliteSato.setMensaje(mensajeSato);
		
		List<SateliteDto> satelites = new ArrayList<SateliteDto>();
		satelites.add(sateliteKenobi);
		satelites.add(sateliteSkywalker);
		satelites.add(sateliteSato);
		
		request.setSatelites(satelites);
		
		String bodyPedidoAuxilio = mapper.writeValueAsString(request);
		assertNotNull(bodyPedidoAuxilio);
		
		MvcResult resultPedidoAuxilio = mockMvc.perform(post("/naves/1/topsecret")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilio))
				.andExpect(status().isCreated()).andReturn();
		
		String responsePedidoAuxilio = resultPedidoAuxilio.getResponse().getContentAsString();
		
		PedidoAuxilioResponse pedidoAuxilioResponse = mapper.readValue(responsePedidoAuxilio, PedidoAuxilioResponse.class);
		
		assertNotNull(pedidoAuxilioResponse);
		assertTrue(pedidoAuxilioResponse.getMensaje().equals("este mensaje es un mensaje secreto para los satelites"));
		assertTrue(pedidoAuxilioResponse.getUbicacion().getX() > 9f && pedidoAuxilioResponse.getUbicacion().getX() < 11f);
		assertTrue(pedidoAuxilioResponse.getUbicacion().getY() > 9f && pedidoAuxilioResponse.getUbicacion().getY() < 11f);
	}
	
	@Test
	@Order(8)
	public void postPedidoAuxilioCompletoNotFound() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
		
		/**
		 * Pedido auxilio
		 **/
		PedidoAuxilioCompletoRequest request = new PedidoAuxilioCompletoRequest();
		
		SateliteDto sateliteKenobi = new SateliteDto();
		sateliteKenobi.setNombre("kenobi");
		sateliteKenobi.setDistanciaANave(551.543289f);
		
		String[] mensajeKenobi = {"este", "", "es", "", "mensaje", "", "", "", "satelites"};
		sateliteKenobi.setMensaje(mensajeKenobi);
		
		SateliteDto sateliteSkywalker = new SateliteDto();
		sateliteSkywalker.setNombre("skywalker");
		sateliteSkywalker.setDistanciaANave(142.126704f);
		
		String[] mensajeSkywalker = {"", "mensaje", "", "", "", "", "", "", ""};
		sateliteSkywalker.setMensaje(mensajeSkywalker);
		
		SateliteDto sateliteSato = new SateliteDto();
		sateliteSato.setNombre("sato");
		sateliteSato.setDistanciaANave(498.196748f);
		
		String[] mensajeSato = {"", "", "", "un", "", "", "", "", ""};
		sateliteSato.setMensaje(mensajeSato);
		
		List<SateliteDto> satelites = new ArrayList<SateliteDto>();
		satelites.add(sateliteKenobi);
		satelites.add(sateliteSkywalker);
		satelites.add(sateliteSato);
		
		request.setSatelites(satelites);
		
		String bodyPedidoAuxilio = mapper.writeValueAsString(request);
		assertNotNull(bodyPedidoAuxilio);
		
		mockMvc.perform(post("/naves/1/topsecret")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilio))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	@Order(9)
	public void postPedidoAuxilioCompletoNotFoundII() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String body = mapper.writeValueAsString(nave);
		assertNotNull(body);
		
		MvcResult result = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(response, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));

		/**
		 * Pedido auxilio
		 **/
		PedidoAuxilioCompletoRequest request = new PedidoAuxilioCompletoRequest();
		
		SateliteDto sateliteKenobi = new SateliteDto();
		sateliteKenobi.setNombre("kenobi");
		sateliteKenobi.setDistanciaANave(551.543289f);
		
		String[] mensajeKenobi = {"este", "", "es", "", "mensaje", "", "", "", "satelites"};
		sateliteKenobi.setMensaje(mensajeKenobi);
		
		SateliteDto sateliteSkywalker = new SateliteDto();
		sateliteSkywalker.setNombre("skywalker");
		sateliteSkywalker.setDistanciaANave(142.126704f);
		
		String[] mensajeSkywalker = {"", "mensaje", "", "", "", "", "", "", ""};
		sateliteSkywalker.setMensaje(mensajeSkywalker);
		
		SateliteDto sateliteSato = new SateliteDto();
		sateliteSato.setNombre("sato");
		sateliteSato.setDistanciaANave(498.196748f);
		
		List<SateliteDto> satelites = new ArrayList<SateliteDto>();
		satelites.add(sateliteKenobi);
		satelites.add(sateliteSkywalker);
		satelites.add(sateliteSato);
		
		request.setSatelites(satelites);
		
		String bodyPedidoAuxilio = mapper.writeValueAsString(request);
		assertNotNull(bodyPedidoAuxilio);
		
		mockMvc.perform(post("/naves/1/topsecret")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilio))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	@Order(10)
	public void postPedidoAuxilioUnitario() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String bodyNave = mapper.writeValueAsString(nave);
		assertNotNull(bodyNave);
		
		MvcResult resultNave = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyNave))
				.andExpect(status().isCreated()).andReturn();
		
		String responseNave = resultNave.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(responseNave, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));
		
		/**
		 * Pedido auxilio unitario KENOBI
		 **/
		PedidoAuxilioUnitarioRequest requestKenobi = new PedidoAuxilioUnitarioRequest();
		requestKenobi.setDistanciaANave(696.419414f);
		String[] mensajeKenobi = {"", "es", "", "", ""};
		requestKenobi.setMensaje(mensajeKenobi);
		
		String bodyPedidoAuxilioKenobi = mapper.writeValueAsString(requestKenobi);
		assertNotNull(bodyPedidoAuxilioKenobi);
		
		mockMvc.perform(post("/naves/1/topsecret-split/KENOBI")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilioKenobi))
		.andExpect(status().isCreated());
		
		/**
		 * Pedido auxilio unitario SKYWALKER
		 **/
		PedidoAuxilioUnitarioRequest requestSkywalker = new PedidoAuxilioUnitarioRequest();
		requestSkywalker.setDistanciaANave(158.113883f);
		String[] mensajeSkywalker = {"este", "", "", "", "secreto"};
		requestSkywalker.setMensaje(mensajeSkywalker);
		
		String bodyPedidoAuxilioSkywalker = mapper.writeValueAsString(requestSkywalker);
		assertNotNull(bodyPedidoAuxilioSkywalker);
		
		mockMvc.perform(post("/naves/1/topsecret-split/SKYWALKER")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilioSkywalker))
		.andExpect(status().isCreated());
		
		/**
		 * Pedido auxilio unitario SATO
		 **/
		PedidoAuxilioUnitarioRequest requestSato = new PedidoAuxilioUnitarioRequest();
		requestSato.setDistanciaANave(353.553391f);
		String[] mensajeSato = {"", "", "un", "mensaje", ""};
		requestSato.setMensaje(mensajeSato);
		
		String bodyPedidoAuxilioSato = mapper.writeValueAsString(requestSato);
		assertNotNull(bodyPedidoAuxilioSato);
		
		mockMvc.perform(post("/naves/1/topsecret-split/SATO")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilioSato))
		.andExpect(status().isCreated());
		
		MvcResult resultGet = mockMvc.perform(get("/naves/{idNave}/topsecret-split", naveBBDD.getId()))
				.andExpect(status().isOk()).andReturn();
		
		String content = resultGet.getResponse().getContentAsString();
		
		PedidoAuxilioResponse response = mapper.readValue(content, PedidoAuxilioResponse.class);
		
		assertNotNull(response);
		assertTrue(response.getMensaje().equals("este es un mensaje secreto"));
		assertTrue(response.getUbicacion().getX() > 149f && response.getUbicacion().getX() < 151f);
		assertTrue(response.getUbicacion().getY() > 49f && response.getUbicacion().getY() < 51f);
	}
	
	@Test
	@Order(11)
	public void postPedidoAuxilioUnitarioNotFound() throws Exception {
		
		/**
		 * Nave 
		 **/
		Nave nave = new Nave();
		nave.setNombre("Nave rebelde de test");
		
		String bodyNave = mapper.writeValueAsString(nave);
		assertNotNull(bodyNave);
		
		MvcResult resultNave = mockMvc.perform(post("/naves")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyNave))
				.andExpect(status().isCreated()).andReturn();
		
		String responseNave = resultNave.getResponse().getContentAsString();
		
		Nave naveBBDD = mapper.readValue(responseNave, Nave.class);
		
		assertNotNull(naveBBDD);
		assertTrue(naveBBDD.getId().equals(1L));

		/**
		 * Pedido auxilio unitario KENOBI
		 **/
		PedidoAuxilioUnitarioRequest requestKenobi = new PedidoAuxilioUnitarioRequest();
		requestKenobi.setDistanciaANave(696.419414f);
		String[] mensajeKenobi = {"", "es", "", "", ""};
		requestKenobi.setMensaje(mensajeKenobi);
		
		String bodyPedidoAuxilioKenobi = mapper.writeValueAsString(requestKenobi);
		assertNotNull(bodyPedidoAuxilioKenobi);
		
		mockMvc.perform(post("/naves/1/topsecret-split/KENOBI")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilioKenobi))
		.andExpect(status().isCreated());
		
		/**
		 * Pedido auxilio unitario SKYWALKER
		 **/
		PedidoAuxilioUnitarioRequest requestSkywalker = new PedidoAuxilioUnitarioRequest();
		requestSkywalker.setDistanciaANave(158.113883f);
		String[] mensajeSkywalker = {"este", "", "", "", "secreto"};
		requestSkywalker.setMensaje(mensajeSkywalker);
		
		String bodyPedidoAuxilioSkywalker = mapper.writeValueAsString(requestSkywalker);
		assertNotNull(bodyPedidoAuxilioSkywalker);
		
		mockMvc.perform(post("/naves/1/topsecret-split/SKYWALKER")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyPedidoAuxilioSkywalker))
		.andExpect(status().isCreated());
		
		mockMvc.perform(get("/naves/{idNave}/topsecret-split", naveBBDD.getId()))
				.andExpect(status().isNotFound());
	}
}
