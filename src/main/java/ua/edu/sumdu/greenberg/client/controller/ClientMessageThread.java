package ua.edu.sumdu.greenberg.client.controller;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMessageThread implements Runnable {
	private static final Logger log = Logger.getLogger(ClientMessageThread.class);
	private Socket socket;
	
	/**
	 * This is the constructor.
	 * @param socket - socket.
	 */
	public ClientMessageThread(Socket socket) {
			this.socket = socket;
	}

	/**
	 * This method run thread.
	 */
	public void run() {
		String message = null;
		BufferedReader bufferedReader;
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				System.out.println(message);
//				if (getSystemMessage(message)) {
//					readSystemMessage(message);
//				} else {
//					ClientChat.addMessageToList("[" + dateFormat.format(date) + "] " + message);
//				}
			}
		} catch (IOException e) {
			log.error(e);
		}
	}
}