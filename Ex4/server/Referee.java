import java.io.IOException;

/**
 * Implementation of the Referee Class for the Tic-Tac-Toe Game
 * 
 * @author B.Gulseren, K.Behairy
 * @version 1.0
 * @since October 19th, 2020
 */

public class Referee {
	/** the player object which will place the X marks */
	private Player xPlayer;
	/** the player object which will place the O marks */
	private Player oPlayer;
	/** the board object assigned to this referee */
	private Board board;
	
	/**
	 * Default Referee constructor
	 *
	 */
	public Referee() {
		
	}

	/**
	 * This method sets the players and opponents of the X- and O- players.
	 * Then, initiates the game by displaying the board, and calling the play
	 * method for the X-Player as the first player.
	 *
	 * @throws IOException Downstream Player makeMove method uses standard input to read lines
	 * from the CLI, which can throw an exception.
	 */
	public void runTheGame() throws IOException {
		this.xPlayer.setOpponent(this.oPlayer); //set the opponents of the X-Player
		this.oPlayer.setOpponent(this.xPlayer); //set the opponents of the O-Player
		this.board.display(this.xPlayer.getSocketOut()); //initiates the game by displaying the board
		this.xPlayer.play(); //Start with X-Player who is always the first player
	}
	
	/**
	 * Sets the board for the referee
	 *
	 * @param theBoard the board to be set for this referee
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}
	
	/**
	 * Sets the player with O mark
	 *
	 * @param p the player to be set as O-player
	 */
	public void setoPlayer(Player p) {
		this.oPlayer = p;
	}
	
	/**
	 * Sets the player with X mark
	 *
	 * @param p the player to be set as X-player
	 */
	public void setxPlayer(Player p) {
		this.xPlayer = p;
	}
	
}
