package ua.edu.sumdu.greenberg.client;

import ua.edu.sumdu.greenberg.client.controller.ClientController;
import ua.edu.sumdu.greenberg.client.model.ClientModel;
import ua.edu.sumdu.greenberg.client.view.ClientView;
import ua.edu.sumdu.greenberg.client.view.ClientViewChat;
import ua.edu.sumdu.greenberg.client.view.ClientViewLogin;

public class ClientChat {
	public static void main(String[] args) {
		ClientView clientView= new ClientView();
		ClientViewLogin clientViewLogin = new ClientViewLogin();
		ClientViewChat clientViewChat = new ClientViewChat();
		ClientModel clientModel = new ClientModel();
		ClientController clientController = new ClientController(clientView, clientViewLogin, 
				clientViewChat, clientModel);
		
		clientViewLogin.setVisible(true);
	}
}
