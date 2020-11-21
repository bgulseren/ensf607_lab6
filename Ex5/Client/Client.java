import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author B.Gulseren, K.Behairy
 * @version 1.0
 * @since October 27th, 2020
 */

public class Client {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ClientGUI theGui;
	
	/**
	 * Default constructor
	 */
	public Client () {
		theGui = new ClientGUI();
	}
	
	/**
	 * Starts the connection with the server
	 * @param serverName ip address of the server
	 * @param portNumber portnumber to connect
	 */
	public void connect (String serverName, int portNumber) {
		try {
			aSocket = new Socket (serverName, portNumber);
			
			if (aSocket.isConnected()) {
				socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
				socketOut = new PrintWriter (aSocket.getOutputStream(), true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Starts the communication with the server
	 */
	public void communicate () {
		String response = "";
		boolean finished = false;

		try {
			while (!response.equals("Please enter your name: ")) {
				response = socketIn.readLine();  //read response form the socket
				theGui.updateMessage("\n" + response);
				System.out.println(response);
				theGui.lockButtons(true);
			}
			
			while (true) {
				System.out.print("");
				//wait here until name is entered by user
				if (theGui.isPlayerStringReady()) {
					socketOut.println(theGui.getPlayerString());
					System.out.println("name entry sent");
					break;
				}
			}
			
			boolean markReceived = false;
			
			while (!markReceived) {
				//wait here until mark is received from server
				response = socketIn.readLine();
				markReceived = response.equals("Your mark is : ");
				theGui.updateMessage("\n" + response);
			}
			
			response = socketIn.readLine();
			theGui.updateMark(response);
			System.out.println("mark received");
		
			while (!finished) {
				
				response = socketIn.readLine();  //read response form the socket
				theGui.updateMessage("\n" + response);
				
				if (response.equals("GAME OVER")) {
					finished = true;
					//break;
					
				} else if (response.equals("...updating board...")) {
					response = socketIn.readLine();  //read response form the socket
					theGui.updateGrids(response);
					theGui.updateMessage("\n" + response);
					
				} else if (response.equals("...waiting user input...")) {
					
					boolean clicked = false;
					
					while (!clicked) {
						if (theGui.isCellClicked()) {
							theGui.resetCellClick();
							clicked = true;
							
							socketOut.println(theGui.getRow());
							socketOut.println(theGui.getCol());
							theGui.lockButtons(true);
							break;
						}
					}
				}
				
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		closeSocket ();
		
	}
	
	/**
	 * Closes all the sockets when game terminates
	 */
	private void closeSocket () {
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Main
	 */
	public static void main (String [] args) throws IOException {
		System.out.println("Press ENTER to connect to the game server.");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		stdin.readLine();
		
		Client aClient = new Client();
		
		aClient.connect("localhost", 9898);
		
		aClient.communicate();
	}

}
