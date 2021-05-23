package com.fuego.quasar.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuego.quasar.exceptions.NotFoundException;
import com.fuego.quasar.service.interfaces.IMensajeService;
import com.fuego.quasar.util.MensajeUtil;

@Service
public class MensajeService implements IMensajeService{
	
	private static final String ESPACIO = " ";
	
	@Autowired
	private MensajeUtil mensajeUtil;

	public String getMessage(List<String[]> messages) throws NotFoundException {
		return decodeMessage(messages);
	}

	private String decodeMessage(List<String[]> sateliteMessages) throws NotFoundException {

		mensajeUtil.validateSateliteMessages(sateliteMessages);
		int messageLength = mensajeUtil.getMessageLength(sateliteMessages);

		String[] message1 = sateliteMessages.get(0);
		String[] message2 = sateliteMessages.get(1);
		String[] message3 = sateliteMessages.get(2);

		StringBuilder decodedMessage = new StringBuilder();
		
		for(int i = 0; i<messageLength; i++) {
			
			if(message1[i] != null && message1[i].isEmpty() == false && message1[i].equals(ESPACIO) == false) {
				if (isLastPartOfMessage(messageLength, i)) {
					decodedMessage.append(message1[i]);
				}else {
					decodedMessage.append(message1[i]).append(ESPACIO);
				}
			}else if (message2[i] != null && message2[i].isEmpty() == false && message2[i].equals(ESPACIO) == false) {
				if (isLastPartOfMessage(messageLength, i)) {
					decodedMessage.append(message2[i]);
				}else {
					decodedMessage.append(message2[i]).append(ESPACIO);
				}
			}else if (message3[i] != null && message3[i].isEmpty() == false && message3[i].equals(ESPACIO) == false) {
				if (isLastPartOfMessage(messageLength, i)) {
					decodedMessage.append(message3[i]);
				}else {
					decodedMessage.append(message3[i]).append(ESPACIO);
				}
			}else {
				throw new NotFoundException("Error. No se ha podido decodificar el mensaje porque una parte del mensaje llego vacia a todos los satelites");
			}
		}
		
		return decodedMessage.toString();
	}

	private boolean isLastPartOfMessage(int messageLength, int i) {
		return i == messageLength-1;
	}
}
