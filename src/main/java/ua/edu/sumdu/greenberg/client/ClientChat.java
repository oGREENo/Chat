package ua.edu.sumdu.greenberg.client;

import org.apache.log4j.PropertyConfigurator;
import ua.edu.sumdu.greenberg.client.controller.ClientController;
import ua.edu.sumdu.greenberg.client.model.ClientModel;
import ua.edu.sumdu.greenberg.client.view.ClientViewChat;
import ua.edu.sumdu.greenberg.client.view.ClientViewLogin;

/**
 * This class started client.
 */
public class ClientChat {
	/**
	 * This is constructor a ClientChat.
	 */
	public ClientChat() {
		PropertyConfigurator.configure("./src/resources/log4j.properties");
		ClientViewLogin clientViewLogin = new ClientViewLogin();
		ClientViewChat clientViewChat = new ClientViewChat();
		ClientModel clientModel = new ClientModel();
		ClientController clientController = new ClientController(clientViewLogin,
				clientViewChat, clientModel);
		clientViewLogin.setVisible(true);
	}
}