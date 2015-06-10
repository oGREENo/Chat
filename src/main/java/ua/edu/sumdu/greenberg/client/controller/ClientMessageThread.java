package ua.edu.sumdu.greenberg.client.controller;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.edu.sumdu.greenberg.client.model.ClientModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;

/**
 * This class wait messages.
 */
public class ClientMessageThread implements Runnable {
	private static final Logger log = Logger.getLogger(ClientMessageThread.class);
	private Socket socket;
	private ClientModel clientModel;
	
	/**
	 * This is the constructor.
	 * @param socket - socket.
	 */
	public ClientMessageThread(Socket socket, ClientModel clientModel) {
		this.socket = socket;
		this.clientModel = clientModel;
	}

	/**
	 * This method run thread.
	 */
	public void run() {
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message;
			while ((message = bufferedReader.readLine()) != null) {
				is.setCharacterStream(new StringReader(message));
				Document doc = db.parse(is);
				clientModel.readMessage(doc);
			}
		} catch (ParserConfigurationException e) {
			log.error(e);
		} catch (SAXException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}
}