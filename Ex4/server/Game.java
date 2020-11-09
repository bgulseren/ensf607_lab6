import java.net.ServerSocket;
import java.net.Socket;
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

	private Socket [] aSocket;
	private ServerSocket [] serverSocket;
	private PrintWriter [] socketOut;
	private BufferedReader [] socketIn;
	
	/** the board object where the game will be played */
	private Board theBoard;
	
	/** the referee object which will conduct (consist the backend) the game */
	private Referee theRef;
	
	/**
	 * Game Class Constructor, instantiates a new board to be played on.
	 *
	 */
	public Game() {
		try {
			aSocket = new Socket[2];
			serverSocket = new ServerSocket[2];
			serverSocket[0] = new ServerSocket(9898);
			serverSocket[1] = new ServerSocket(9899);
			
			socketIn = new BufferedReader[2];
			socketOut = new PrintWriter[2];
			
			theBoard  = new Board(); //todo: create a board array when needed only
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * Appoints the referee, which consist the entry point to the game application.
	 * 
	 * @param r the referee object to be appointed for the game.
	 * @throws IOException Downstream Player makeMove method uses standard input to read lines
	 * from the CLI, which can throw an exception.
	 */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
	
	
	/**
	 * Main method, which instantiates the board, the players and the referee.
	 * Prompts the user from the CLI to enter each players' name.
	 * Then appoints the referee which will invoke referee methods to get the game going.
	 * 
	 * @param args system arguments
	 * @throws IOException uses standard input to read lines from the CLI, which can throw an exception.
	 */
	public static void main (String [] args) throws IOException {
		
		Game myServer = new Game ();
		
		//Establishing the connection 
		try {
			for (int i = 0; i < 2; i++) {
				myServer.aSocket[i] = myServer.serverSocket[i].accept();
				System.out.println("Server console: Connection accepted by the server!");
				myServer.socketIn[i] = new BufferedReader (new InputStreamReader(myServer.aSocket[i].getInputStream()));
				myServer.socketOut[i] = new PrintWriter (myServer.aSocket[i].getOutputStream(), true);
			}
			
			System.out.println("Server console: Both connections done!");
			
			Referee theRef;
			HumanPlayer [] player = new HumanPlayer[2];
			
			String [] name = new String[2];
			char [] pChar = new char[2];
			pChar[0] = LETTER_O;
			pChar[1] = LETTER_X;
			
			for (int i = 0; i < 2; i++) {
				myServer.socketOut[i].println("Please enter your name: ");
				System.out.println("Name request sent");
				name[i] = myServer.socketIn[i].readLine();
				while (name[i] == null) {
					myServer.socketOut[i].println("Please try again: ");
					name[i] = myServer.socketIn[i].readLine();
				}
				myServer.socketOut[i].println("Your sign is : " + pChar[i]);
				player[i] = new HumanPlayer(name[i], pChar[i], myServer.socketIn[i], myServer.socketOut[i]);
				player[i].setBoard(myServer.theBoard);
			}
			System.out.println("Server console: Both players are set up, starting game");
			
			theRef = new Referee();
			theRef.setBoard(myServer.theBoard);
			theRef.setoPlayer(player[0]);
			theRef.setxPlayer(player[1]);
	        
			myServer.appointReferee(theRef);
			
			myServer.socketIn[0].close();
			myServer.socketIn[1].close();
			myServer.socketOut[0].close();
			myServer.socketOut[1].close();
			
			System.out.println("Server console: Game ended, server closed");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}