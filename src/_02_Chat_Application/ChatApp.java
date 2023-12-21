package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp extends JFrame {
	JButton button = new JButton("CLICK");
	Server server;
	Client client;
	Socket socket;
	Socket sockIt;
	 ServerSocket serverSocket;
	JPanel panel = new JPanel();
	JTextArea ta = new JTextArea();
	boolean connected  = true;
	boolean clientConnect = true;

	
	public static void main(String[] args){
		ChatApp ca = new ChatApp();
	}
	
	
	public ChatApp() {
		
	 int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			this.server = new Server(8080);
		    setTitle("SERVER");
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			System.out.println(server.getIPAddress());
			button.addActionListener((e)->{
			    boolean connected = true;
				while( connected ) {
				    try {	
				    	System.out.println("Try...");
				    	this.serverSocket = new ServerSocket(8080,100);
				        System.out.println( "Waiting to connect..." );
				        sockIt = serverSocket.accept();
				        System.out.println( "Connected!" );
				        DataInputStream dis = new DataInputStream( sockIt.getInputStream() );
						System.out.println( dis.readUTF() );
						DataOutputStream dos = new DataOutputStream( sockIt.getOutputStream() );
						dos.writeUTF( "Hello, I'm the server. Nice to meet you!" );				
				    } catch( SocketTimeoutException e1 ) {
				        System.out.println( "ERROR: Socket timeout!" );
				        connected = false;
				    } catch( IOException e1 ){
				        System.out.println( "ERROR: I/O Exception!" );
				        connected = false;
				    } finally {
				        if( socket != null ) {
				            try {
		                        socket.close();
		                    } catch( IOException e1 ) {
		                        e1.printStackTrace();
		                        System.out.println( "ERROR: server socket could not be closed!" );
		                    }
				        }
				    }
				}
			
				System.out.println("Server sent message.");
			});
			panel.add(button);
			panel.add(ta);
			add(panel);
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			server.start();
			
		}else{
			setTitle("CLIENT");
			String ipStr = JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			client = new Client(ipStr, port);
				button.addActionListener((e)->{
				      try {
				           socket = new Socket( server.getIPAddress(), server.getPort() );
				          DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
				          dos.writeUTF( "Hello, I'm the client. Nice to meet you!" );
				          DataInputStream dis = new DataInputStream( socket.getInputStream() );
				          System.out.println( dis.readUTF() );		          
				       } catch( Exception e1 ) {
				           e1.printStackTrace();
				       } finally {
				           if( socket != null ) {
				               try {
				                 socket.close();
				             } catch( IOException e1 ) {
				                 e1.printStackTrace();
				                 System.out.println( "ERROR: client socket could not be closed!" );
				             }
				           }
				       }
				System.out.println("Client sent message.");
			});
			panel.add(button);
			panel.add(ta);
			add(panel);
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.start();
		}
	}
}
