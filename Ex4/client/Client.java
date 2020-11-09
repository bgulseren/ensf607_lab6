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
		System.out.println("Enter port number to connect : ");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String portNum = stdin.readLine();
		
		Client aClient = new Client ("localhost", Integer.parseInt(portNum));
		
		aClient.communicate();
	}

}
