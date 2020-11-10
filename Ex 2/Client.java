/* 
 * Implementation of Server class
 * Creates ServerSocket object, establishes connection with client side, 
 * reads input from client side, writes output to client side, closes connection when no longer needed
 * contains method to check if a word is a palindrome and returns proper output
 * 
 * Created by:  Khaled Behairy, Burak Gulseren
 * @since 11/07/2020
*/

package Ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private PrintWriter socketOut;
	private Socket palinSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

	/**
	 *  construct Client class
	 *  
	 *  @param serverName - String parameter representing name of server
	 *  @param portNumber - integer representing port number
	 **/
	public Client(String serverName, int portNumber) {
		try {
			palinSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * communication between client and server
	 **/
	public void communicate() {
		String line = "";
		String response = "";
		while (!line.contentEquals("QUIT")) {
			try {
				System.out.println("Please select an option (DATE/TIME)");
				line = stdIn.readLine();
				socketOut.println(line);
				response = socketIn.readLine();
				System.out.println(response);	
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		
		/**
		 * close connections
		 **/
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException  {
		Client aClient = new Client("localhost", 9090);
		aClient.communicate();
	}
	
}