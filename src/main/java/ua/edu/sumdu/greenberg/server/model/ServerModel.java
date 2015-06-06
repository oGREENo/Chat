package ua.edu.sumdu.greenberg.server.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.sumdu.greenberg.server.controller.ServerController;
import ua.edu.sumdu.greenberg.server.controller.ServerThread;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is class a model.
 */
public class ServerModel {
    private static final Logger log = Logger.getLogger(ServerModel.class);
    private Map<User, ServerThread> userMap = new HashMap<User, ServerThread>();
    private User user;
    private DocumentBuilder builder;
    private ServerController serverController;
    private ArrayList arrUsers = new ArrayList<String>();

    /**
     * This method added a ServerController.
     * @param serverController - ServerController
     */
    public void addControllerToModel(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * This method adding user and ServerTread to array.
     * @param user - user.
     * @param serverThread - ServerThread.
     */
    public void addUser(User user, ServerThread serverThread) {
        userMap.put(user,serverThread);
    }

    /**
     * This method removing user from array.
     * @param user - user.
     */
    public void removeUser(User user) {
        log.info("The user has left.");
        userMap.remove(user);
    }

    /**
     * This method return from array a user name.
     * @return array.
     */
    public ArrayList getNameUsers() {
        ArrayList users = new ArrayList<String>();
        for (Map.Entry entry : userMap.entrySet()) {
            users.add(entry.getKey());
        }
        return users;
    }

    /**
     * This method read a MXL.
     * @param doc - Document.
     * @param serverThread - ServerThread.
     */
    public void readMessage(Document doc, ServerThread serverThread) {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String toNick = doc.getElementsByTagName("to_nick").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();

        if (action.equals("ADD_USER")) actionAddUser(nick, serverThread);
        if (action.equals("REMOVE_USER")) actionRemoveUser(nick, toNick);
        if (action.equals("GET_USER_LIST")) actionGetUserList(nick, toNick, action);
        if (action.equals("") && toNick.isEmpty()) actionSendMessage(nick, toNick, action, text);
        if (action.equals("") && !toNick.isEmpty()) actionSendMessagePrivate(nick, toNick, action, text);
    }

    /**
     * This method receives a command to add a user.
     * @param nick - nick.
     * @param serverThread - Server Thread.
     */
    private void actionAddUser(String nick, ServerThread serverThread) {
        user = new User(nick);
        addUser(user, serverThread);
    }

    /**
     * This method receives a command to remove a user.
     * @param nick - nick.
     * @param toNick - toNick.
     */
    private void actionRemoveUser(String nick, String toNick) {
        commandRemoveUser(nick);
        ServerThread st;
        arrUsers.clear();
        arrUsers = getNameUsers();
        for (Map.Entry entry : userMap.entrySet()) {
            st = (ServerThread) entry.getValue();
            for (int i = 0; i < arrUsers.size(); i++) {
                String name = arrUsers.get(i).toString();
                try {
                    serverController.writeInSocket(createXML(name, toNick, "REMOVE_USER", nick), st);
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * This method gives a command to delete the user.
     * @param nick - nick.
     */
    private void commandRemoveUser(String nick) {
        user = new User(nick);
        removeUser(user);
    }

    /**
     * This method receives a command to get a user list.
     * @param nick - nick.
     * @param toNick - toNick.
     * @param action - action.
     */
    private void actionGetUserList(String nick, String toNick, String action) {
        ServerThread st;
        arrUsers.clear();
        arrUsers = getNameUsers();
        for (Map.Entry entry : userMap.entrySet()) {
            if (entry.getKey().toString().equals(nick)) {
                st = (ServerThread) entry.getValue();
                for (int i = 0; i < arrUsers.size(); i++) {
                    String name = arrUsers.get(i).toString();
                    try {
                        serverController.writeInSocket(createXML(nick, toNick, action, name), st);
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
            } else {
                st = (ServerThread) entry.getValue();
                try {
                    serverController.writeInSocket(createXML(entry.getKey().toString(), toNick, "ADDED_USER", nick), st);
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * This method receives a command to send private message a user.
     * @param nick - nick.
     * @param toNick - toNick.
     * @param action - action.
     * @param text - text.
     */
    private void actionSendMessage(String nick, String toNick, String action, String text) {
        ServerThread st;
        for (Map.Entry entry : userMap.entrySet()) {
            st = (ServerThread) entry.getValue();
            try {
                serverController.writeInSocket(createXML(nick, toNick, action, "[" + nick + "] : " + text), st);
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    /**
     * This method receives a command to send  message all users.
     * @param nick - nick.
     * @param toNick - toNick.
     * @param action - action.
     * @param text - text.
     */
    private void actionSendMessagePrivate(String nick, String toNick, String action, String text) {
        ServerThread st;
        for (Map.Entry entry : userMap.entrySet()) {
            if (entry.getKey().toString().equals(toNick)) {
                st = (ServerThread) entry.getValue();
                try {
                    serverController.writeInSocket(createXML(nick, toNick, action, "[" + nick + " ==> " + toNick + "] : " + text), st);
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * This method is created a XML.
     * @param nick - nick.
     * @param toNick - toNick.
     * @param action - action.
     * @param text - text.
     * @return Document.
     */
    public Document createXML(String nick, String toNick, String action, String text) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e);
        }
        Document doc=builder.newDocument();
        Element rootElement=doc.createElement("message");

        Element nameElementNick = doc.createElement("nick");
        if (nick != null) nameElementNick.appendChild(doc.createTextNode(nick));
        else nameElementNick.appendChild(doc.createTextNode(""));
        rootElement.appendChild(nameElementNick);

        Element nameElementToNick = doc.createElement("to_nick");
        if (toNick != null) nameElementToNick.appendChild(doc.createTextNode(toNick));
        else nameElementToNick.appendChild(doc.createTextNode(""));
        rootElement.appendChild(nameElementToNick);

        Element nameElementAction = doc.createElement("action");
        if (action != null) nameElementAction.appendChild(doc.createTextNode(action));
        else nameElementAction.appendChild(doc.createTextNode(""));
        rootElement.appendChild(nameElementAction);

        Element nameElementText = doc.createElement("text");
        if (text != null) nameElementText.appendChild(doc.createTextNode(text));
        else nameElementText.appendChild(doc.createTextNode(""));
        rootElement.appendChild(nameElementText);

        doc.appendChild(rootElement);
        return doc;
    }
}