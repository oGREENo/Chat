package ua.edu.sumdu.greenberg.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.sumdu.greenberg.client.model.ClientModel;
import ua.edu.sumdu.greenberg.client.view.ClientView;
import ua.edu.sumdu.greenberg.client.view.ClientViewChat;
import ua.edu.sumdu.greenberg.client.view.ClientViewLogin;
import ua.edu.sumdu.greenberg.client.model.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ClientController {
	private static final Logger log = Logger.getLogger(ClientController.class);
	private ClientView clientView;
	private ClientViewLogin clientViewLogin;
	private ClientViewChat clientViewChat;
	private ClientModel clientModel;
	private User user;
	private String name;
	private String url;
	private int port;
	private Socket socket;
	
	public ClientController(ClientView clientView, ClientViewLogin clientViewLogin, 
			ClientViewChat clientViewChat, ClientModel clientModel) {
		this.clientView = clientView;
		this.clientModel = clientModel;
		this.clientViewLogin = clientViewLogin;
		this.clientViewLogin.clickLogin(new ClickLogin());
	}
	
	class ClickLogin implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			log.info("Click Login");
			if (clientModel.validData(clientViewLogin.getName(), clientViewLogin.getUrl(), clientViewLogin.getPort())) {
				name = clientViewLogin.getName();
				url = clientViewLogin.getUrl();
				InetAddress addr = null;
				try {
					addr = InetAddress.getByName(url);
				} catch (UnknownHostException e) {
					log.error(e);
				}
				port = clientViewLogin.getPort();
				if (pingServer(addr, port, 25)) {
					log.info("Server is running");
					user = new User(name, url, port);
					createConnection();
					try {
						writeInSocket(name, null, "ADD_USER", "Hello server");
					} catch (IOException e) {
						log.error(e);
					}
					clientViewLogin.setVisible(false);
//					clientViewChat.setVisible(true);
					
//					try {
//						writeInSocket("#GET_USER_LIST" + "[" + clientName.getName() + "]");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				}
			}
		}
	}
	
	/**
	 * This method create a connection.
	 */
	public void createConnection() {
		log.info("ClientUser Name - " + name);
		try {
			socket = new Socket(clientViewLogin.getUrl() , clientViewLogin.getPort());
		} catch (IOException e) {
			log.error(e);
		}
		Runnable runnable = new ClientMessageThread(socket);
		Thread thread = new Thread(runnable);
		thread.start();
		log.info("Connect to URL - " + clientViewLogin.getUrl() + " and PORT - " + clientViewLogin.getPort());
	}
	
	/**
	 * This method checks the server is started.
	 * @param addr - address server.
	 * @param port - port server.
	 * @param timeout -
	 * @return
	 */
	public boolean pingServer(InetAddress addr, int port, int timeout) {
		log.info("Ping Server.");
		Socket socket = new Socket();
		Exception exception = null;
		try {
			socket.connect(new InetSocketAddress(addr, port), timeout);
		} catch (IOException e) {
			log.error("IOException." + e);
			exception = e;
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				log.error("IOException socket.close." + e);
			}
		}
		return exception == null ? true : false;
	}

	/**
	 * This method writes in socket a message.
	 * @param nick - name
	 * @param to_nick - name
	 * @param action - command
	 * @param text - message
	 * @throws IOException
	 */
	public void writeInSocket(String nick, String to_nick, String action, String text) throws IOException {
		Document doc = clientModel.createXML(nick, to_nick, action, text);
		try {
			Transformer t= TransformerFactory.newInstance().newTransformer();
			Source source = new DOMSource(doc);
			Result output = new StreamResult(socket.getOutputStream());
			t.transform(source, output);
			socket.getOutputStream().write('\n');
		} catch (TransformerException e) {
			log.error(e);
		}
	}
}