import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

/**
 * Implementation of the Game Class for the Tic-Tac-Toe Game.
 * Has the main method which instantiates the board, the players and the referee.
 * Prompts the user from the CLI to enter each players' name.
 * Then appoints the referee which will invoke referee methods to get the game going.
 * 
 * @author M.Morshirpour (modified by B.Gulseren, K.Behairy)
 * @version 1.0
 * @since October 19th, 2020
 */


public class Game implements Constants {

	private ExecutorService pool;
	
	//private Socket [] aSocket;
	private ServerSocket serverSocket;
	private PrintWriter [] socketOut;
	private BufferedReader [] socketIn;
	
	/** the referee object which will conduct (consist the backend) the game */
	private Referee theRef;
	
	/**
	 * Game Class Constructor, instantiates a new board to be played on.
	 *
	 */
	public Game() {
		try {
			//aSocket = new Socket[2];
			serverSocket = new ServerSocket(9898);
			
			socketIn = new BufferedReader[2];
			socketOut = new PrintWriter[2];
			
			pool = Executors.newFixedThreadPool(10);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	
	/**
	 * Run method, which instantiates the board, the players and the referee.
	 * Prompts the user from the CLI to enter each players' name.
	 * Then appoints the referee which will invoke referee methods to get the game going.
	 * 
	 * @throws IOException uses standard input to read lines from the CLI, which can throw an exception.
	 */
	public void runServer () {
		
		//Establishing the connection 

			try {
				while (true) {
					Socket xSocket = new Socket();
					try {
						xSocket = serverSocket.accept();
					} catch (IOException e) {
						System.out.println("Server: x socket failed");
					}
					System.out.println("Server: New session started!");
					System.out.println("Server: First player joined!");
					
					if (xSocket.isConnected()) {
						socketIn[0] = new BufferedReader (new InputStreamReader(xSocket.getInputStream()));
						socketOut[0] = new PrintWriter (xSocket.getOutputStream(), true);
					}
					socketOut[0].println("Waiting for the second player...");
					
					
					Socket oSocket = new Socket();
					try {
						oSocket = serverSocket.accept();
					} catch (IOException e) {
						System.out.println("Server: o socket failed");
					}
					System.out.println("Server: Second player joined!");
					socketOut[0].println("Second player joined.");
					
					if (oSocket.isConnected()) {
						socketIn[1] = new BufferedReader (new InputStreamReader(oSocket.getInputStream()));
						socketOut[1] = new PrintWriter (oSocket.getOutputStream(), true);
					}
					
					System.out.println("Server: Both players joined, requesting names...");
					
					HumanPlayer [] player = new HumanPlayer[2];
					
					String [] name = new String[2];
					char [] pChar = new char[2];
					pChar[0] = LETTER_O;
					pChar[1] = LETTER_X;
					
					for (int i = 0; i < 2; i++) {
						
						int j = 0;
						if (i == 0)
							j = 1;
						
						socketOut[j].println("Waiting for other player to enter their name...");
						
						socketOut[i].println("Please enter your name: ");
						System.out.println("Server: Awaiting player " + (i + 1) + " to enter a name.");
						name[i] = socketIn[i].readLine();
						socketOut[i].println("Welcome " + name[i] + "! Your mark is : " + pChar[i]);
						player[i] = new HumanPlayer(name[i], pChar[i], socketIn[i], socketOut[i]);
					}
					System.out.println("Server: Both players entered their names, starting game");
					
					Referee theRef = new Referee(player[0], player[1]);
					pool.execute(theRef);
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			pool.shutdown();
			
			System.out.println("Server: Server closed");
		
	}
	
	/**
	 * Main method, which instantiates the board, the players and the referee.
	 * Prompts the user from the CLI to enter each players' name.
	 * Then appoints the referee which will invoke referee methods to get the game going.
	 * 
	 * @param args system arguments
	 * @throws IOException uses standard input to read lines from the CLI, which can throw an exception.
	 */
	public static void main(String[] args) throws IOException {

		Game myServer = new Game ();
		System.out.println("Server: Ready, awaiting client connections...");
		myServer.runServer();
	}
	
}
