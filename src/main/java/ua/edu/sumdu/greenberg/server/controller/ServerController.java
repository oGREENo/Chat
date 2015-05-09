package ua.edu.sumdu.greenberg.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import ua.edu.sumdu.greenberg.server.model.ServerModel;
import ua.edu.sumdu.greenberg.server.view.ServerView;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class is the controller.
 */
public class ServerController {
	private static final Logger log = Logger.getLogger(ServerController.class);
	public static final int PORT = 12345;
	private ServerView serverView;
	private ServerModel serverModel;

	/**
	 * This is the class constructor.
	 * @param serverView - serverView.
	 * @param serverModel - serverModel.
	 */
	public ServerController(ServerView serverView, ServerModel serverModel) {
		this.serverView = serverView;
		this.serverModel = serverModel;
		serverModel.addControllerToModel(this);
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

	/**
	 * This method send xml to Socket.
	 * @param doc - Document.
	 * @param st - serverThread
	 * @throws IOException
	 */
	public void writeInSocket(Document doc, ServerThread st) throws IOException {
		try {
			Transformer t= TransformerFactory.newInstance().newTransformer();
			Source source = new DOMSource(doc);
			Result output = new StreamResult(st.getSocket().getOutputStream());
			t.transform(source, output);
			st.getSocket().getOutputStream().write('\n');
		} catch (TransformerException e) {
			log.error(e);
		}
	}
}