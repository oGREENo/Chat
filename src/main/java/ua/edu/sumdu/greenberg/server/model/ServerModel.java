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

public class ServerModel {
    private static final Logger log = Logger.getLogger(ServerModel.class);
    private static Map<User, ServerThread> userMap = new HashMap<User, ServerThread>();
    private User user;
    private DocumentBuilder builder;
    private ServerController serverController;
    private ArrayList arrUsers = new ArrayList<String>();

    public void addControllerToModel(ServerController serverController) {
        this.serverController = serverController;
    }

    public void addUser(User user, ServerThread serverThread) {
        userMap.put(user,serverThread);
    }

    public void removeUser(User user) {
        userMap.remove(user);
    }

    public ArrayList getNameUsers() {
        ArrayList users = new ArrayList<String>();
        for (Map.Entry entry : userMap.entrySet()) {
            users.add(entry.getKey());
        }
        return users;
    }

    public void readMessage(Document doc, ServerThread serverThread) throws IOException {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String toNick = doc.getElementsByTagName("to_nick").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        System.out.println("Server Message : nick - " + nick + ", toNick - " + toNick + ", action - " + action +", text - " + text + ".");
        if (action.equals("ADD_USER")) {
            user = new User(nick);
            addUser(user, serverThread);
        } else if (action.equals("REMOVE_USER")) {
            user = new User(nick);
            removeUser(user);
        } else if (action.equals("GET_USER_LIST")) {
            ServerThread st;
            arrUsers = null;
            arrUsers = getNameUsers();
            for (Map.Entry entry : userMap.entrySet()) {
                st = (ServerThread) entry.getValue();
                for (int i = 0; i < arrUsers.size(); i++) {
                    String name = arrUsers.get(i).toString();
                    serverController.writeInSocket(createXML(nick, toNick, action, name), st);
                }
            }


        } else if (action.equals("") && toNick.equals("")) {
            System.out.println("Action = null. Message ; " + text);
//            User users;
            ServerThread st;
            for (Map.Entry entry : userMap.entrySet()) {
//                users = (User) entry.getKey();
                st = (ServerThread) entry.getValue();
                serverController.writeInSocket(createXML(nick, toNick, action, text), st);
            }
        }
    }

    public Document createXML(String nick, String to_nick, String action, String text) {
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
        if (to_nick != null) {
            Element NameElementToNick = doc.createElement("to_nick");
            NameElementToNick.appendChild(doc.createTextNode(to_nick));
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
