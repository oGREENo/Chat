package ua.edu.sumdu.greenberg.server.view;

import org.apache.log4j.Logger;

/**
 * This class displays messages to the console.
 */
public class ServerView {
	private static final Logger log = Logger.getLogger(ServerView.class);
	/**
	 * This method displays a message to the console.
	 * @param mes - message.
	 */
	public void consoleMessage(String mes) {
		log.info(mes);
	}
}