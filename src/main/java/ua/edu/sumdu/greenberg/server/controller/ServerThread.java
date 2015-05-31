package ua.edu.sumdu.greenberg.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.edu.sumdu.greenberg.server.model.ServerModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class wait messages.
 */
public class ServerThread implements Runnable {
	private static final Logger log = Logger.getLogger(ServerThread.class);
	private Socket socket;
	private ServerModel serverModel;

	/**
	 * This is the constructor a class.
	 * @param socket - socket.
	 */
	public ServerThread(Socket socket, ServerModel serverModel) {
		this.socket = socket;
		this.serverModel = serverModel;
	}

	/**
	 * This method starts a thread.
	 */
	public void run() {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			String message;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				is.setCharacterStream(new StringReader(message));
				Document doc = db.parse(is);
				serverModel.readMessage(doc, this);
		}
			socket.close();
		} catch (IOException e) {
			log.error(e);
		} catch (ParserConfigurationException e) {
			log.error(e);
		} catch (SAXException e) {
			log.error(e);
		}
	}

	/**
	 * This method returned a socket.
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}
}