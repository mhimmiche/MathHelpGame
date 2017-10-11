import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
/**
 * Final panel class. This class manages all other panels of the game and handles the interaciton between them. 
 * Transitions between panels as needed.
 * @author Mehdi Himmiche
 * @date Apr 24, 2016
 */
public class FinalPanel extends JFrame{
	private MenuPanel menu;
	private GamePanel game;
	private CardLayout layoutMan;
	private MathOperations mathVals;
	private HistoryPanel history;
	private String upper;
	private String lower;
	private String time;
	private int newUp;
	private int newLow;
	private int newTime;
	
	/**
	 * Constructor for the final panel class
	 */
	public FinalPanel() {
		layoutMan = new CardLayout();
		menu = new MenuPanel();
		game = new GamePanel();
		history = new HistoryPanel();
		createPanel();
		initializeVals();
	}
	
	/**
	 * Creates the final panel and assigns it the card layout manager.
	 * It creates the appropriate action listeners for each necessary button. 
	 * I tried making it prettier but I couldn't. I know :(
	 */
	private void createPanel() {
		final JPanel gamePanels = new JPanel();
		add(gamePanels);
		gamePanels.setLayout(layoutMan);
		JPanel menuPanel = menu.fullPanel();
		gamePanels.add(menuPanel, "1");
		JPanel gamePanel = game.gamePanel();
		gamePanels.add(gamePanel, "2");
		JPanel endPanel = history.fullPanel();
		gamePanels.add(endPanel, "3");
		menu.getStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startOperation();
				layoutMan.show(gamePanels, "2");
			}});	
		game.getBackToMenuButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenuOperations();
				layoutMan.show(gamePanels, "1");
			}});
		game.getEndButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateHistory();
				layoutMan.show(gamePanels, "3");
			}});
		game.getAbortButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abortOperation();
				layoutMan.show(gamePanels, "3");
			}});
		history.getBackToMenuButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layoutMan.show(gamePanels, "1");
				game.resetVals();
			}});
	}

	/**
	 * Calls the appropriate method for when the user starts the game and transitions from menu to game.
	 */
	private void startOperation() {
		game.resetVals();
		adjustVals();
		checkVals();
		validateVals();
		updateVals();
		game.changeGameState();
		game.newGame();
	}
	
	/**
	 * Calls the appropriate methods when the player decides to stop playing and abort their current game session.
	 */
	private void abortOperation() {
		int newStreak = game.getHighStreak();
		history.setStreakVal(newStreak);
		history.updateLabel();
		LinkedList<OperationHistory> hist = (LinkedList<OperationHistory>) game.getHistQueue();
		history.loadHistory(hist);
		Window win = SwingUtilities.getWindowAncestor(game.getAbortButton());
		win.setVisible(false);
	}
	
	/**
	 * Updates the player's history. It allows to display the player's highest streak and pulls all the history of all operations they made a choice in.
	 */
	private void updateHistory() {
		int newStreak = game.getHighStreak();
		history.setStreakVal(newStreak);
		history.updateLabel();
		LinkedList<OperationHistory> hist = (LinkedList<OperationHistory>) game.getHistQueue();
		history.loadHistory(hist);
		Window win = SwingUtilities.getWindowAncestor(game.getEndButton());
		win.setVisible(false);
	}
	
	/**
	 * Sets up the appropriate methods for when the user decides to return to the main menu. 
	 * This resets all values. It does not allow the user to save current progress. 
	 */
	private void backToMenuOperations() {
		Window win = SwingUtilities.getWindowAncestor(game.getBackToMenuButton());
		win.setVisible(false);
		game.changeGameState();
		game.setLife(3);
		game.setLivesText(3);
	}
	
	/**
	 * Initializes the values displayed in the settings tab. It gives the user an idea of what they're up against. 
	 */
	private void initializeVals() {
		int lives = game.getLives();
		game.setLivesText(lives);
		mathVals = new MathOperations();
		upper = menu.getUpper();
		upper = "" + mathVals.getUpperBound();
		lower = menu.getLower();
		lower = "" + mathVals.getLowBound();
		time = menu.getTime();
		time = "" + game.getTimerVal();
		menu.setVals(lower, upper, time);
	}
	
	/**
	 * Readjusts the initial values to what the user entered in the settings panel. 	
	 */
	private void adjustVals() {
		upper = menu.getUpper();
		lower = menu.getLower();
		time = menu.getTime();
		if (upper.equals("")) {
			upper = "" + mathVals.getUpperBound();
		}
		if (lower.equals("")) {
			lower = "" + mathVals.getLowBound();
		}
		if (time.equals("")) {
			time = "" + game.getTimerVal();
		}
	}
	
	/**
	 * Checks if the user's input in the settings panel are valid numbers, otherwise reassign them to the original. 
	 */
	private void checkVals() {
		try {
			newUp = Integer.parseInt(upper);
			newLow = Integer.parseInt(lower);			
		} catch (NumberFormatException e) {
			upper = "" + mathVals.getUpperBound();
			newUp = Integer.parseInt(upper);
		}
		try {
			newLow = Integer.parseInt(lower);			
		} catch (NumberFormatException e) {
			lower = "" + mathVals.getLowBound();
			newLow = Integer.parseInt(lower);
		}
		try {
			newTime = Integer.parseInt(time);			
		} catch (NumberFormatException e) {
			time = "" + game.getTimerVal();
			newTime = Integer.parseInt(time);
		}
	}
	
	/**
	 * Checks if the user is trying to cheat and make it easy. 
	 * If the user enters the same values for max and min (or max is less than min), the biggest becomes min, and max is 3 times min;
	 */
	private void validateVals() {
		if (newUp <= newLow) {
			newUp = 3 * newLow;
		}
		if (newTime < 3) {
			newTime = 3;
		}
		else if (newTime > 30) {
			newTime = 30;
		}
	}
	
	/**
	 * Updates the new min and max values to apply to the operations. 
	 */
	private void updateVals() {
		mathVals.setHighBound(newUp);
		mathVals.setLowBount(newLow);
		game.changeTimerVal(newTime);
	}
}
