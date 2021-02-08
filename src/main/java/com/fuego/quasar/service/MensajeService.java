package com.fuego.quasar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fuego.quasar.exceptions.NotFoundException;

@Service
public class MensajeService {
	
	private static final String ESPACIO = " ";

	/**
	 * Public 
	 **/
	
	public String getMessage(List<String[]> messages) throws NotFoundException {
		return decodeMessage(messages);
	}

	/**
	 * Private
	 **/
	
	private String decodeMessage(List<String[]> messages) throws NotFoundException {

		StringBuilder msg = new StringBuilder();

		if (messages == null || messages.size() == 0) {
			throw new NotFoundException("Error. La cantidad de mensajes es 0");
		}
		if (messages.size() <3) {
			throw new NotFoundException("Error. La cantidad de mensajes es menor a 3, por lo tanto no se podra decodificar el mensaje");
		}

		int messageLength = 0;
		int counter = 0;

		for(String[] message : messages) {
			if (message == null || message.length == 0) {
				throw new NotFoundException("Error. Uno o mas mensajes estan vacios");
			}
			if(counter == 0) {
				messageLength = message.length;
				counter++;
			}
			if (message.length != messageLength) {
				throw new NotFoundException("Error. Los mensajes no tienen la misma longitud, por lo tanto no se podra decodificar el mensaje");
			}
		}

		String[] message1 = messages.get(0);
		String[] message2 = messages.get(1);
		String[] message3 = messages.get(2);

		for(int i = 0; i<messageLength; i++) {
			
			if(message1[i] != null && message1[i].isEmpty() == false && message1[i].equals(ESPACIO) == false) {
				if (i == messageLength-1) {
					msg.append(message1[i]);
				}else {
					msg.append(message1[i]).append(ESPACIO);
				}
			}else if (message2[i] != null && message2[i].isEmpty() == false && message2[i].equals(ESPACIO) == false) {
				if (i == messageLength-1) {
					msg.append(message2[i]);
				}else {
					msg.append(message2[i]).append(ESPACIO);
				}
			}else if (message3[i] != null && message3[i].isEmpty() == false && message3[i].equals(ESPACIO) == false) {
				if (i == messageLength-1) {
					msg.append(message3[i]);
				}else {
					msg.append(message3[i]).append(ESPACIO);
				}
			}else {
				throw new NotFoundException("Error. No se ha podido decodificar el mensaje porque una parte del mensaje llego vacia a todos los satelites");
			}
		}
		
		return msg.toString();
	}
}
