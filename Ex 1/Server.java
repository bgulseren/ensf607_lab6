/* 
 * Implementation of Server class
 * Creates ServerSocket object, establishes connection with client side, 
 * reads input from client side, writes output to client side, closes connection when no longer needed
 * contains method to check if a word is a palindrome and returns proper output
 * 
 * Created by:  Khaled Behairy, Burak Gulseren
 * @since 11/07/2020
*/

package Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	
	/**
	 * Construct a Server with Port 8099
	 */
	public Server() {
		//create ServerSocket object
		try {
			serverSocket = new ServerSocket(8099);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get user input
	 * Check if input from client is a palindrome 
	 * 
	 * @throws IOException
	 */
	public void checkPalindrome() throws IOException{
		String word = "";
		
		// use this variable to print to socket
		String output ="";
	
		// read from client as long as needed
		while(true) {
			try {
				//read word from client (socketIn)
				word = socketIn.readLine();
				String reverseWord = "";
				// exit if user enters QUIT
				if(word.equals("QUIT")) {
					output = "Good Bye";
					socketOut.println(output);
					break;
				}
				// check if word is palindrome
				for (int i = word.length() - 1; i >= 0; i--) {
					reverseWord = reverseWord + word.charAt(i);
				}
				if(word.contentEquals(reverseWord)) {
					output = word + " is a palindrome!";
				}
				else {
					output = word + " is NOT a palindrome!";
				}
				// send output to socketOut to print to console
				socketOut.println(output);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * Run the Server.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main (String[] args) throws IOException{
		
		Server myServer = new Server();
		
		// establish connection with client
		try {
			myServer.aSocket = myServer.serverSocket.accept();
			System.out.println("Console at Server side says: Connect accepted by the server");
			myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.aSocket.getInputStream()));
			myServer.socketOut = new PrintWriter(myServer.aSocket.getOutputStream(), true);
			
			// check if word is palindrome
			myServer.checkPalindrome();
			
			// close connections 
			myServer.socketIn.close();
			myServer.socketOut.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
