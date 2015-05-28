package ua.edu.sumdu.greenberg.client;

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
		ClientViewLogin clientViewLogin = new ClientViewLogin();
		ClientViewChat clientViewChat = new ClientViewChat();
		ClientModel clientModel = new ClientModel();
		new ClientController(clientViewLogin, clientViewChat, clientModel);
		clientViewLogin.setVisible(true);
	}
}