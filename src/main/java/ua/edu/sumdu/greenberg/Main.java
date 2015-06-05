package ua.edu.sumdu.greenberg;

import org.apache.log4j.Logger;
import ua.edu.sumdu.greenberg.client.ClientChat;
import ua.edu.sumdu.greenberg.server.ServerChat;

/**
 * This is main class.
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    /**
     * This is the class constructor.
     */
    public Main() {
        printCloseMessage();
    }

    /**
     * This is the class constructor with parameter.
     * @param parameter - parameter.
     */
    public Main(String parameter) {
        readParameter(parameter);
    }

    /**
     * This method started program.
     * @param arg - parameter.
     */
    public static void main(String[] arg) {
        if(arg.length > 0) {
            new Main(arg[0]);
        }
        else new Main();
    }

    /**
     * This method is read parameter.
     * @param parameter - parameter.
     */
    private void readParameter(String parameter) {
        if (parameter.equals("server")) {
            new ServerChat();
        } else if (parameter.equals("client")) {
            new ClientChat();
        } else printCloseMessage();
    }

    /**
     * This method printing on display a error message and closing program.
     */
    private void printCloseMessage() {
        log.info("This jar file start with parameter <server> or <client>.");
        System.exit(0);
    }
}
