import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * 
 * @author B.Gulseren, K.Behairy
 * @version 1.0
 * @since October 27th, 2020
 */

// An AWT GUI program inherits the top-level container java.awt.Frame
public class ClientGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//private Client client;
	private JFrame frame;
	private JButton [] btns;
	private JLabel messageLabel, nameLabel, markLabel;
	private JTextArea messageArea;
	private JTextField playerName;
	private JTextField playerMark;
	private String playerString;
	private boolean playerStringReady;
	private boolean clicked;
	private int row, col;
	
	/**
	 * Graphical user interface constructor
	 * 
	 */
	public ClientGUI () {
		row = -1;
		col = -1;
		this.clicked = false;
		
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerString = new String();
		this.playerStringReady = false;
		
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        // Building left panel
		btns = new JButton[9];
				
		leftPanel.setLayout(new GridLayout(3, 3, 12, 12));
		leftPanel.setSize(300, 300);
		// The components are added from left-to-right, top-to-bottom

		for (int i = 0; i < btns.length; i++) {
			btns[i] = new JButton(" ");
			JButton button = btns[i];
			button.setFont(new Font("Arial", Font.PLAIN, 40));
			button.addActionListener(this);
			leftPanel.add(button);
		}
		
		// Building right panel
		rightPanel.setLayout(new GridLayout(7, 1, 12, 12));
		
		
		// Message info area
		messageLabel = new JLabel("Messages");
		messageArea = new JTextArea(5, 20);
		DefaultCaret caret = (DefaultCaret)messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPane = new JScrollPane(messageArea); 
		messageArea.setEditable(false);
		rightPanel.add(messageLabel);
		rightPanel.add(scrollPane);
		
		// Player name entry area
		nameLabel = new JLabel("Your Name");
		playerName = new JTextField();
		playerName.setEditable(true);
		playerName.addActionListener(this);
		rightPanel.add(nameLabel);
		rightPanel.add(playerName);
		
		// Player mark info area
		markLabel = new JLabel("Your Mark");
		playerMark = new JTextField();
		playerMark.setEditable(false);
		rightPanel.add(markLabel);
		rightPanel.add(playerMark);
		
		frame.setLayout(new GridLayout(1, 2, 12, 12));
		frame.add(leftPanel);
		frame.add(rightPanel);
		
		frame.setSize(640, 640);           // "super" Frame sets initial size
		frame.setVisible(true);            // "super" Frame shows
	}

	
	/**
	 * Gets the action performed on the visual objects
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < btns.length; i++) {
			if(e.getSource() == btns[i]){
				row = i / 3;
				col = i % 3;
				clicked = true;
			}
		}
		
		if (e.getSource() == playerName) {
			playerString = playerName.getText();
			this.playerStringReady = true;
			playerName.setEditable(false); //lock it after entered
		}
	}
	
	/**
	 * Updates grids on the client
	 * @param String flattended string representation of grids.
	 */
	public void updateGrids(String gridMarks) {
		for (int i = 0; i < 9; i++) {
			if (gridMarks.charAt(i) == 'X' || gridMarks.charAt(i) == 'O') {
				btns[i].setEnabled(false); //if occupied, lock the button
			}
			btns[i].setText(String.valueOf(gridMarks.charAt(i)));
		}
	}
	
	/**
	 * Adds a message to the client area
	 * @param String message to be added.
	 */
	public void updateMessage(String msg) {
		messageArea.append(msg);
	}
	
	/**
	 * Updates the mark for the client
	 * @param String mark 
	 */
	public void updateMark(String mark) {
		playerMark.setText(mark);
	}
	
	/**
	 * Returns the player string entered from the client area.
	 * @return String
	 */
	public String getPlayerString() {
		return playerString;
	}
	
	/**
	 * Returns true if a player string was entered
	 * @return boolean
	 */
	public boolean isPlayerStringReady() {
		return playerStringReady;
	}
	
	/**
	 * Returns row number of the last clicked cell
	 * @return int
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns col number of the last clicked cell
	 * @return int
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Returns true if a cell was clicked by user.
	 * @return boolean
	 */
	public boolean isCellClicked() {
		return this.clicked;
	}
	
	/**
	 * Resets the clicked flag for the client
	 */
	public void resetCellClick() {
		this.clicked = false;
	}

	
	/**
	 * Locks buttons to disable until turn passes to the other player
	 * @param boolean true, locks, false, unlocks
	 */
	public void lockButtons(boolean lock) {
		for (int i = 0; i < btns.length; i++) {
			btns[i].setEnabled(!lock);
		}
	}
	
}

