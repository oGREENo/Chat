package ua.edu.sumdu.greenberg;

import ua.edu.sumdu.greenberg.client.ClientChat;
import ua.edu.sumdu.greenberg.server.ServerChat;

/**
 * This is main class.
 */
public class Main {
    /**
     * This method started program.
     * @param arg - parameter.
     */
    public static void main(String[] arg) {
        if(arg.length > 0) {
            if (arg[0].equals("startServer")) {
                new ServerChat();
            } else if (arg[0].equals("startClient")) {
                new ClientChat();
            } else {
                System.out.println("This jar file start with parameter <startServer> or <startClient>");
                System.exit(0);
            }
        } else {
            System.out.println("This jar file start with parameter <startServer> or <startClient>");
            System.exit(0);
        }
    }
}