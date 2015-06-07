package ua.edu.sumdu.greenberg.server;

import ua.edu.sumdu.greenberg.server.controller.ServerController;
import ua.edu.sumdu.greenberg.server.model.ServerModel;
import ua.edu.sumdu.greenberg.server.view.ServerView;

/**
 * This class started server.
 */
public class ServerChat {
    /**
     * This is constructor a ServerChat.
     */
    public ServerChat() {
        new ServerController(new ServerView(), new ServerModel());
    }
}