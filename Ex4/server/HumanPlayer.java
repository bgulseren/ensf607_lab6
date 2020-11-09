import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Implementation of the HumanPlayer Class for the Tic-Tac-Toe Game
 * 
 * @author B.Gulseren, K. Behairy
 * @version 1.0
 * @since October 19th, 2020
 */

public class HumanPlayer extends Player {
	
	/**
	 * Call's the Player Class Constructor
	 *
	 * @param name the name of the player to be constructed
	 * @param mark the mark of the player (either X or O) to be constructed
	 */
	public HumanPlayer(String name, char mark, BufferedReader socketIn, PrintWriter socketOut) {
		super(name, mark, socketIn, socketOut);
	}
	
	/**
	 * Checks whether player can play or not. If player can play, calls the makeMove method
	 * and then passes the turn to the next player. Otherwise, it outputs the result of the
	 * game (winner or tie) to the console.
	 *
	 * @throws IOException Downstream Player makeMove method uses standard input to read lines
	 * from the CLI, which can throw an exception.
	 */
	protected void makeMove() throws IOException {
		int row, col;
		
		while (true) {
			socketOut.println(this.getName() + ", what row should your next " + this.getMark() + " be placed in?");
			socketOut.println("...");
			String entryRow = socketIn.readLine();
			
			while (entryRow == null) {
				socketOut.println("Please try again: ");
				socketOut.println("...");
				entryRow = socketIn.readLine();
			}
			
			socketOut.println(this.getName() + ", what column should your next " + this.getMark() + " be placed in?");
			socketOut.println("...");
			String entryCol = socketIn.readLine();
			
			while (entryCol == null) {
				socketOut.println("Please try again: ");
				socketOut.println("...");
				entryCol = socketIn.readLine();
			}
			
			try {
				row = Integer.parseInt(entryRow); //can throw a number format exception if entry was not a valid integer
				col = Integer.parseInt(entryCol);
				
				if (row > 2 || row < 0 || col > 2 || col < 0) {
					socketOut.println("Row/Column must be between 0 and 2, please try again!");
				} else if (getBoard().isMarked(row, col)) { //check if the coordinates are already marked
					socketOut.println("Tile already marked, please try again!");
				} else {
					break;
				}
			} catch (Exception e) {
				socketOut.println("Row and column need to be entered as valid integers, please try again!");
			}
			
		}

		getBoard().addMark(row, col, getMark());
	}

}
