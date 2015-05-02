package ua.edu.sumdu.greenberg.server.model;

import org.w3c.dom.Document;
import ua.edu.sumdu.greenberg.server.controller.ServerThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerModel {
    private static Map<User, ServerThread> userMap = new HashMap<User, ServerThread>();
    User user;
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

    public void readMesage(Document doc, ServerThread serverThread) {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String t0_nick = doc.getElementsByTagName("to_nick").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        if(action != null) {
            if (action.equals("ADD_USER")) {
                user = new User(nick);
                addUser(user, serverThread);
            } else if (action.equals("REMOVE_USER")) {
                user = new User(nick);
                removeUser(user);
            }
        } else {
            //sending a normal message.
        }
    }
}
