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
import ua.edu.sumdu.greenberg.server.model.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ServerThread implements Runnable {
	private static final Logger log = Logger.getLogger(ServerThread.class);
	User user;
	Socket socket;

	/**
	 * This is the constructor a class.
	 * @param socket - socket.
	 */
	ServerThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * This method starts a thread.
	 */
	public void run() {

		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();


			String message = null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				log.info("Message - " + message);
				is.setCharacterStream(new StringReader(message));
				Document doc = db.parse(is);
				System.out.println(doc.getElementsByTagName("text").item(0).getTextContent());
				message = null;
			}
			socket.close();
		} catch (IOException e) {
			log.error(e);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}
