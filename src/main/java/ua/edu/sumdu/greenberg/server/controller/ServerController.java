package ua.edu.sumdu.greenberg.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ua.edu.sumdu.greenberg.server.model.ServerModel;
import ua.edu.sumdu.greenberg.server.view.ServerView;

public class ServerController {
	public static final int PORT = 12345;
	private ServerView serverView;
	private ServerModel serverModel;
	
	public ServerController(ServerView serverView, ServerModel serverModel) {
		this.serverView = serverView;
		this.serverModel = serverModel;
		
		try {
			runServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * This method running a server.
     * @throws IOException
     */
    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        serverView.serverUP();
        while (true) {
            Socket socket = serverSocket.accept();
            serverView.connectUser();
            new ServerThread(socket).start();
        }
    }
}
