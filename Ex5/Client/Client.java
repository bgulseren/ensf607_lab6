import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ClientGUI theGui;
	
	public Client () {
		theGui = new ClientGUI();
	}
	
	
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
	
	
	public void communicate () {
		String line = "";
		String response = "";
		boolean finished = false;

		try {
			while (!response.equals("Please enter your name: ")) {
				response = socketIn.readLine();  //read response form the socket
				theGui.updateMessage("\n" + response);
				System.out.println(response);
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
				theGui.lockButtons(true);
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
					theGui.lockButtons(false);
					
					boolean clicked = false;
					
					while (!clicked) {
						if (theGui.isCellClicked()) {
							theGui.resetCellClick();
							clicked = true;
							
							socketOut.println(theGui.getRow());
							socketOut.println(theGui.getCol());
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
	
	private void closeSocket () {
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main (String [] args) throws IOException {
		System.out.println("Press ENTER to connect to the game server.");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		stdin.readLine();
		
		Client aClient = new Client();
		
		aClient.connect("localhost", 9898);
		
		aClient.communicate();
	}

}