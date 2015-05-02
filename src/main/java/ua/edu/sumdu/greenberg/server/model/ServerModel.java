package ua.edu.sumdu.greenberg.server.model;

import ua.edu.sumdu.greenberg.server.controller.ServerThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerModel {
    private static Map<User, ServerThread> userMap = new HashMap<User, ServerThread>();

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
	
}
