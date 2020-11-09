import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	
	public Client (String serverName, int portNumber) {
		
		try {
			aSocket = new Socket (serverName, portNumber);
			//keyboard input stream
			stdIn = new BufferedReader (new InputStreamReader (System.in));
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter (aSocket.getOutputStream(), true);
		}catch (UnknownHostException e) {
			e.printStackTrace();
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
				System.out.println(response);
			}
			
			line = stdIn.readLine();
			while (line.equals("") || line == null) {
				System.out.println("Name cannot be empty!");
				System.out.println("Please enter your name: ");
				line = stdIn.readLine();
			}
			socketOut.println(line);
			
			
			while (!finished) {
				
				while (!response.equals("...")) {
					response = socketIn.readLine();  //read response form the socket
					System.out.println(response);
					
					if (response.equals("GAME OVER")) {
						finished = true;
						break;
					}
				}
				
				if (!finished) {
					response = "";
					line = stdIn.readLine();
					if (line.equals("QUIT")) {
						finished = true;
					} else {
						socketOut.println(line);
					}
						
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} //reading the input from the user (i.e. the keyboard);
			

		closeSocket ();
		
	}
	
	private void closeSocket () {
		
		try {
			stdIn.close();
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
		
		Client aClient = new Client ("localhost", 9898);
		
		aClient.communicate();
	}

}
