import java.io.IOException;

/**
 * Implementation of the Referee Class for the Tic-Tac-Toe Game
 * 
 * @author B.Gulseren, K.Behairy
 * @version 1.0
 * @since October 19th, 2020
 */

public class Referee implements Runnable {
	/** the player object which will place the X marks */
	private Player xPlayer;
	/** the player object which will place the O marks */
	private Player oPlayer;
	/** the board object assigned to this referee */
	private Board board;
	
	/** game still in progress */
	private boolean inProgress;
	
	/**
	 * Default Referee constructor
	 *
	 */
	public Referee(Player oPlayer, Player xPlayer) {
		board = new Board();
		setoPlayer(oPlayer);
		setxPlayer(xPlayer);
		inProgress = true;
	}

	/**
	 * This method sets the players and opponents of the X- and O- players.
	 * Then, initiates the game by displaying the board, and calling the play
	 * method for the X-Player as the first player.
	 *
	 * @throws IOException Downstream Player makeMove method uses standard input to read lines
	 * from the CLI, which can throw an exception.
	 */
	@Override
	public void run() {
		this.xPlayer.setBoard(board);
		this.oPlayer.setBoard(board);
		
		this.xPlayer.setOpponent(this.oPlayer); //set the opponents of the X-Player
		this.oPlayer.setOpponent(this.xPlayer); //set the opponents of the O-Player
		
		
		while (inProgress) {
			try {
				this.board.display(this.xPlayer.getSocketOut());
				this.xPlayer.makeMove();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (this.board.xWins()) {
				
				xPlayer.getSocketOut().println("You won!");
				oPlayer.getSocketOut().println("You lost! " + xPlayer.getName() + " won!");
				endGame();
				
			} else if (this.board.isFull()) {
				xPlayer.getSocketOut().println("Game is tie...");
				oPlayer.getSocketOut().println("Game is tie...");
				endGame();
				
			} else {
			
				try {
					this.board.display(this.oPlayer.getSocketOut());
					this.oPlayer.makeMove();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (this.board.oWins()) {
					
					oPlayer.getSocketOut().println("You won!");
					xPlayer.getSocketOut().println("You lost! " + oPlayer.getName() + " won!");
					endGame();
					
				} else if (this.board.isFull()) {
					xPlayer.getSocketOut().println("Game is tie...");
					oPlayer.getSocketOut().println("Game is tie...");
					endGame();
				}
			}
		}
			
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
	
	public void endGame() {
		inProgress = false;
		xPlayer.getSocketOut().println("GAME OVER");
		oPlayer.getSocketOut().println("GAME OVER");
		
		try {
			oPlayer.getSocketIn().close();
			oPlayer.getSocketIn().close();
			xPlayer.getSocketOut().close();
			xPlayer.getSocketOut().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
