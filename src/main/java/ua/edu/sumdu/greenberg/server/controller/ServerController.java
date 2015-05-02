package ua.edu.sumdu.greenberg.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import ua.edu.sumdu.greenberg.server.model.ServerModel;
import ua.edu.sumdu.greenberg.server.view.ServerView;

public class ServerController {
	private static final Logger log = Logger.getLogger(ServerController.class);
	public static final int PORT = 12345;
	private ServerView serverView;
	private ServerModel serverModel;
	
	public ServerController(ServerView serverView, ServerModel serverModel) {
		this.serverView = serverView;
		this.serverModel = serverModel;
		
		try {
			runServer();
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
     * This method running a server.
     * @throws IOException
     */
    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        serverView.consoleMessage("Server UP $ ready connection.");
        while (true) {
            Socket socket = serverSocket.accept();
            serverView.consoleMessage("Connection user.");
			Runnable runnable = new ServerThread(socket, serverModel);
			Thread thread = new Thread(runnable);
			thread.start();
		}
    }
}
