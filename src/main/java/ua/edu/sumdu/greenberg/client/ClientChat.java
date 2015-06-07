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
        new ClientController(new ClientViewLogin(),
                new ClientViewChat(),
                new ClientModel());
    }
}