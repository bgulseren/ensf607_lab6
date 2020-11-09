import java.io.*;

/**
 * Implementation of the Player Class for the Tic-Tac-Toe Game.
 * 
 * @author B.Gulseren, K.Behairy
 * @version 1.0
 * @since October 19th, 2020
 */

public abstract class Player {
	/** name of the player */
	private String name;
	/** the board object where the player is playing the game */
	private Board board;
	/** the opponent assigned to this player */
	private Player opponent;
	/** the mark character assigned to this player */
	private char mark;
	
	protected PrintWriter socketOut;
	protected BufferedReader socketIn;
	
	/**
	 * Player Class Constructor
	 *
	 * @param name the name of the player to be constructed
	 * @param mark the mark of the player (either X or O) to be constructed
	 */
	public Player(String name, char mark, BufferedReader socketIn, PrintWriter socketOut) {
		this.name = name;
		this.mark = mark;
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	/**
	 * Abstract makeMove implementation
	 *
	 * @throws IOException sub-class human player has methods which can throw IOException
	 */
	protected void makeMove() throws IOException {
		return; 
	}
	
	/**
	 * Sets the player's opponent
	 *
	 * @param p the player to be set as the opponent of this player
	 */
	public void setOpponent(Player p) {
		this.opponent = p;
	}
	
	/**
	 * Sets the board on which player will play 
	 *
	 * @param theBoard the board specified for player to play on
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}
	
	/**
	 * Returns the name of the player
	 *
	 * @return the name of the player
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the mark of the player
	 *
	 * @return the mark of the player, either X or O
	 */
	public char getMark() {
		return this.mark;
	}
	
	/**
	 * Returns the board of the player
	 *
	 * @return the board of the player
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the opponent of the player
	 *
	 * @return the opponent of the player
	 */
	public Player getOpponent() {
		return opponent;
	}
	
	/**
	 * Returns the socket out for this player
	 *
	 * @return the socket out for this player
	 */
	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	/**
	 * Returns the socket in for this player
	 *
	 * @return the socket in for this player
	 */
	public BufferedReader getSocketIn() {
		return socketIn;
	}
	
}
