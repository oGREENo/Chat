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
    private static Map<User, ServerThread> userMap = new HashMap<User, ServerThread>();
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
     * @throws IOException
     */
    public void readMessage(Document doc, ServerThread serverThread) throws IOException {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String toNick = doc.getElementsByTagName("to_nick").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        if (action.equals("ADD_USER")) {
            user = new User(nick);
            addUser(user, serverThread);
        } else if (action.equals("REMOVE_USER")) {
            user = new User(nick);
            removeUser(user);
            ServerThread st;
            arrUsers.clear();
            arrUsers = getNameUsers();
            for (Map.Entry entry : userMap.entrySet()) {
                st = (ServerThread) entry.getValue();
                for (int i = 0; i < arrUsers.size(); i++) {
                    String name = arrUsers.get(i).toString();
                    serverController.writeInSocket(createXML(name, toNick, "REMOVE_USER", nick), st);
                }
            }
        } else if (action.equals("GET_USER_LIST")) {
            ServerThread st;
            arrUsers.clear();
            arrUsers = getNameUsers();
            for (Map.Entry entry : userMap.entrySet()) {
                if (entry.getKey().toString().equals(nick)) {
                    st = (ServerThread) entry.getValue();
                    for (int i = 0; i < arrUsers.size(); i++) {
                        String name = arrUsers.get(i).toString();
                        serverController.writeInSocket(createXML(nick, toNick, action, name), st);
                    }
                } else {
                    st = (ServerThread) entry.getValue();
                    serverController.writeInSocket(createXML(entry.getKey().toString(), toNick, "ADDED_USER", nick), st);
                }
            }
        } else if (action.equals("") && toNick.equals("")) {
            ServerThread st;
            for (Map.Entry entry : userMap.entrySet()) {
                st = (ServerThread) entry.getValue();
                serverController.writeInSocket(createXML(nick, toNick, action, "[" + nick + "] : " + text), st);
            }
        } else if (action.equals("") && !toNick.equals("")) {
            ServerThread st;
            for (Map.Entry entry : userMap.entrySet()) {
                if (entry.getKey().toString().equals(toNick)) {
                    st = (ServerThread) entry.getValue();
                    serverController.writeInSocket(createXML(nick, toNick, action, "[" + nick + "] : " + text), st);
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
        Element RootElement=doc.createElement("message");
        if (nick != null) {
            Element NameElementNick = doc.createElement("nick");
            NameElementNick.appendChild(doc.createTextNode(nick));
            RootElement.appendChild(NameElementNick);
        } else {
            Element NameElementNick = doc.createElement("nick");
            NameElementNick.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementNick);
        }
        if (toNick != null) {
            Element NameElementToNick = doc.createElement("to_nick");
            NameElementToNick.appendChild(doc.createTextNode(toNick));
            RootElement.appendChild(NameElementToNick);
        } else {
            Element NameElementToNick = doc.createElement("to_nick");
            NameElementToNick.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementToNick);
        }
        if (action != null) {
            Element NameElementAction = doc.createElement("action");
            NameElementAction.appendChild(doc.createTextNode(action));
            RootElement.appendChild(NameElementAction);
        } else {
            Element NameElementAction = doc.createElement("action");
            NameElementAction.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementAction);
        }
        if (text != null) {
            Element NameElementText = doc.createElement("text");
            NameElementText.appendChild(doc.createTextNode(text));
            RootElement.appendChild(NameElementText);
        } else {
            Element NameElementText = doc.createElement("text");
            NameElementText.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementText);
        }
        doc.appendChild(RootElement);
        return doc;
    }
}