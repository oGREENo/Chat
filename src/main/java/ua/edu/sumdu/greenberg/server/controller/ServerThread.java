package ua.edu.sumdu.greenberg.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import ua.edu.sumdu.greenberg.server.model.User;

public class ServerThread extends Thread {
//	private static final Logger log = Logger.getLogger(ServerThread.class);
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
			String message = null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				System.out.println("Message - " + message);
			}
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
