package ua.edu.sumdu.greenberg.server.view;

public class ServerView {
	
	public void serverUP() {
		System.out.println("Server up & ready for connection...");
	}
	
	public void connectUser() {
		System.out.println("Connetion user...");
	}
	
	public void addUser(String user) {
		System.out.println("Added user: " + user);
	}
	
	public void exitUser(String user) {
		System.out.println("Exit user: " + user);
	}
	
}
