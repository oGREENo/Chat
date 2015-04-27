package ua.edu.sumdu.greenberg.server;

import ua.edu.sumdu.greenberg.server.controller.ServerController;
import ua.edu.sumdu.greenberg.server.model.ServerModel;
import ua.edu.sumdu.greenberg.server.view.ServerView;

public class ServerChat {
	public static void main(String[] args) {
		ServerView serverView = new ServerView();
		ServerModel serverModel = new ServerModel();
		ServerController serverController = new ServerController(serverView, serverModel);
		
	}
}
