package ua.edu.sumdu.greenberg.server;

import org.apache.log4j.*;
import ua.edu.sumdu.greenberg.server.controller.ServerController;
import ua.edu.sumdu.greenberg.server.model.ServerModel;
import ua.edu.sumdu.greenberg.server.view.ServerView;

public class ServerChat {
	public static void main(String[] args) {
		PropertyConfigurator.configure("./src/resources/log4j.properties");
		ServerView serverView = new ServerView();
		ServerModel serverModel = new ServerModel();
		ServerController serverController = new ServerController(serverView, serverModel);
		
	}
}
