package _02_Chat_Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {

	Server server;
	Client client;

	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	
	public ChatApp() {
	
		server = new Server(8080);
		String ipAddress = server.getIPAddress();
		int port = server.getPort();
		client = new Client(ipAddress, port);
try {
	Socket socket = new Socket(ipAddress, port);
} catch (UnknownHostException e) {
	// TODO Auto-generated catch block
	System.out.println("Unknown host...");
	e.printStackTrace();
} catch (IOException e) {
	System.out.println("IOException...");
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	ServerSocket ss = new ServerSocket(8080, 100);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


	}
}
