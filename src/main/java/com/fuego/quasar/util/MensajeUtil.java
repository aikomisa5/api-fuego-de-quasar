package com.fuego.quasar.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fuego.quasar.exceptions.NotFoundException;

@Component
public class MensajeUtil {

	public void validateSateliteMessages(List<String[]> messages) throws NotFoundException {
		
		int messageLength = 0;
		int counter = 0;

		if (messages == null || messages.size() == 0) {
			throw new NotFoundException("Error. La cantidad de mensajes es 0");
		}
		if (messages.size() <3) {
			throw new NotFoundException("Error. La cantidad de mensajes es menor a 3, por lo tanto no se podra decodificar el mensaje");
		}

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
	}
	
	public int getMessageLength(List<String[]> messages) {
		return messages.get(0).length;
	}
}
