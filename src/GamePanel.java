import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
/**
 * Game Panel. This class creates the panel in which the main game occurs. 
 * @author Mehdi Himmiche
 * @date Apr 25, 2016
 */
public class GamePanel extends JPanel {
	private MathOperations mathOp;
	private JButton[] answers;
	private JLabel operation;
	private JLabel timer;
	private JLabel streakLabel;
	private int lives;
	private JLabel livesLabel;
	private int firstNum;
	private int secondNum;
	private int streak;
	private int highStreak;
	private int currStreak;
	private int wrongs;
	private boolean gameOn;
	private CountdownTimer countdown;
	private JButton backToMenu;
	private JButton quit;
	private JButton pause;
	private JButton moveToEnd;
	private JButton abort;
	private int pausePane;
	private Queue<OperationHistory> history;
	private Integer[] operationAnswers;
	private boolean checkQueue;

	/**
	 * Constructor for the game panel.
	 */
	public GamePanel() {
		checkQueue = true;
		highStreak = 0;
		moveToEnd = new JButton("Abort");
		abort = new JButton("Abort");
		history = new LinkedList<OperationHistory>();
		backToMenu = new JButton("Return to Main Menu");
		quit = new JButton("Exit Game");
		pause = new JButton("Pause");
		ActionListener pauseList = new PauseListener();
		pause.addActionListener(pauseList);
		gameOn = false;
		lives = 3;
		streak = 0;
		currStreak = 0;
		wrongs = 0;
		livesLabel = new JLabel("Lives: " + lives);
		timer = new JLabel("Time goes here");
		timer.setFont(new Font("Serif", Font.BOLD, 25));
		streakLabel = new JLabel("Streak : " + streak);
		answers = new JButton[8];
		ActionListener buttonList = new SelectionListener();
		for (int i = 0; i < answers.length; i++) {
			answers[i] = new JButton("");
			answers[i].addActionListener(buttonList);
		}
		operation = new JLabel("", JLabel.CENTER);
		newOp();
		createPanel();
		countdown = new CountdownTimer(this);
		countdown.start();
	}

	/**
	 * Resets the value for a new operations. 
	 * Also checks if the user can have another round, i.e. they have no more lives to spend. 
	 */
	public void newOp() {
		if (lives > 0) {
			mathOp = new MathOperations();
			mathOp.startOperations();
			operationAnswers = mathOp.getAllAnswers();
			for (int i = 0; i < answers.length; i++) {
				String buttonText = "" + operationAnswers[i];
				answers[i].setText(buttonText);
			}
			firstNum = mathOp.getFirstNumber();
			secondNum = mathOp.getSecondNumber();
			operation.setText(firstNum + " + " + secondNum);
			operation.setFont(new Font("Serif", Font.BOLD, 65));
		}
		else {
			gameOn = false;			
			JPanel endPanel = endGamePanel();
			int selection = JOptionPane.showOptionDialog(null, endPanel, "Game Over", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			if (selection == JOptionPane.CLOSED_OPTION && checkQueue) {
				moveToEnd.doClick();
			}
			else {
				checkQueue = true;
			}
		}
	}
	
	/**
	 * Sets up a new game - Resets all values and the counter
	 */
	public void newGame() {
		newOp();
		countdown.resetCounter();
	}
	
	/**
	 * Creates the bottom panel containing the buttons with answers
	 * @return answer buttons
	 */
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2, 4, 10, 10));
		for (int i = 0; i < answers.length; i++) {
			bottomPanel.add(answers[i]);
		}
		return bottomPanel;
	}

	/**
	 * Creates the top panel with streak value, the life value, the timer, and the pause button. 
	 * @return top panel with game info
	 */
	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 3));
		streakLabel.setHorizontalAlignment(JLabel.CENTER);
		timer.setHorizontalAlignment(JLabel.CENTER);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(streakLabel);
		topPanel.add(timer);
		topPanel.add(livesLabel);
		topPanel.add(pause);
		topPanel.add(new JLabel(""));
		topPanel.add(new JLabel(""));
		return topPanel;
	}

	/**
	 * Creates the full game panel. 
	 */
	private void createPanel() {
		JPanel panel = new JPanel();
		add(panel);
		JPanel game = gamePanel();
		panel.add(game);
	}

	/**
	 * Creates a JPanel containing all the pieces for the game panel. 
	 * This is passed to the createPanel() method to be called in the constructor.
	 * @return the full game panel
	 */
	public JPanel gamePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel top = createTopPanel();
		panel.add(top, BorderLayout.NORTH);
		panel.add(operation, BorderLayout.CENTER);
		JPanel fullBottomPanel = new JPanel();
		fullBottomPanel.setLayout(new BorderLayout());
		JPanel bottomPanel = createBottomPanel();
		fullBottomPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
		fullBottomPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
		fullBottomPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
		fullBottomPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
		fullBottomPanel.add(bottomPanel, BorderLayout.CENTER);
		panel.add(fullBottomPanel, BorderLayout.SOUTH);
		return panel;
	}
	
	/**
	 * Changes the value of the timer. 
	 * @param the new timer text
	 */
	public void setTimerText(String text) {
		timer.setText(text);
	}
	
	/**
	 * Changes the text of the player's life
	 * @param newLives to be set
	 */
	public void setLivesText(int newLives) {
		livesLabel.setText("Lives: " + newLives);
	}
	
	/**
	 * sets the life value of the player to a new value
	 * @param newLife
	 */
	public void setLife(int newLife) {
		lives = newLife;
	}
	
	/**
	 * Changes the value of the timer displayer
	 * @param newMax
	 */
	public void changeTimerVal(int newMax) {
		countdown.setMaxTime(newMax);
	}
	
	/**
	 * Gets the current value of the max number of seconds. 
	 * @return max seconds
	 */
	public int getTimerVal() {
		return countdown.getMaxTime();
	}
	
	/**
	 * Returns the player's life value
	 * @return player's lives
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Returns player's streak.
	 * @return player's streak.
	 */
	public int getStreak() {
		return streak;
	}
	
	/**
	 * Returns the number of wrong answers in a row that the player selected
	 * @return number of wrongs.
	 */
	public int getWrongs() {
		return wrongs;
	}
	
	/**
	 * Returns the game state. If the game is currently paused or not. 
	 * @return
	 */
	public boolean getGameState() {
		return gameOn;
	}
	
	/**
	 * Changes the game state to its negation. 
	 */
	public void changeGameState() {
		gameOn = !gameOn;
	}
	
	/**
	 * Resets the player's streak value to 0. 
	 */
	public void resetStreak() {
		streak = 0;
	}
	
	/**
	 * Resets the player's wrongs value to 0. 
	 */
	public void resetWrongs() {
		wrongs = 0;
	}
	
	/**
	 * Returns the back to menu button, it's in the pause panel. 
	 * @return back to menu button
	 */
	public JButton getBackToMenuButton() {
		return backToMenu;
	}
	
	/**
	 * Returns the end of game button, in the end game panel.
	 * @return end of game button
	 */
	public JButton getEndButton() {
		return moveToEnd;
	}
	
	/**
	 * Returns the abort button, in the pause panel.
	 * @return abort button
	 */
	public JButton getAbortButton() {
		return abort;
	}
	
	/**
	 * Returns the player's highest streak (correct answers in a row)
	 * @return high streak
	 */
	public int getHighStreak() {
		return highStreak;
	}
	
	/**
	 * The queue containing the player's history
	 * @return history queue
	 */
	public Queue<OperationHistory> getHistQueue() {
		return history;
	}
	
	/**
	 * Resets the values of the streaks, the lives, and the history queue
	 */
	public void resetVals() {
		currStreak = 0;
		streak = 0;
		highStreak = 0;
		lives = 3;
		setLivesText(lives);
		history = new LinkedList<OperationHistory>();
		streakLabel.setText("Streak : " + streak);
	}
	
	/**
	 * Creates the panel that is displayed when the player loses - game ends. 
	 * @return end of game panel
	 */
	public JPanel endGamePanel() {
		JPanel end = new JPanel();
		end.setLayout(new BorderLayout());
		JLabel endLabel = new JLabel("Game Over. You gave it a good shot though!", JLabel.CENTER);
		moveToEnd.setText("Continue");
		moveToEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkQueue = false;			
			}			
		});
		end.add(endLabel);
		end.add(moveToEnd, BorderLayout.SOUTH);
		return end;
	}
	
	/**
	 * Creates the panel displayed when the user pauses the game. 
	 * @return the pause panel
	 */
	public JPanel pausePanel() {
		JPanel pause = new JPanel();
		pause.setLayout(new GridLayout(3, 1, 10, 10));		
		ActionListener quitList = new ExitListener();
		quit.addActionListener(quitList);
		pause.add(abort);
		pause.add(backToMenu);
		pause.add(quit);
		JPanel pausePane = new JPanel();
		pausePane.setLayout(new BorderLayout());
		pausePane.add(pause);
		pausePane.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
		pausePane.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
		pausePane.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
		pausePane.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
		return pausePane;
	}

	/**
	 * Class to handle the user's selection of answers
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class SelectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String correctAnswer = "" + mathOp.getAnswer();
			String selected = ((JButton) e.getSource()).getText();
			String op = mathOp.getOperation();
			OperationHistory temp = new OperationHistory(op, correctAnswer, selected);
			history.add(temp);
			if (selected.equals(correctAnswer)) {
				wrongs = 0;
				streak++;
				currStreak++;
				streakLabel.setText("Streak : " + currStreak);
			} else {
				if (currStreak > highStreak) highStreak = currStreak;
				streak = 0;
				currStreak = 0;
				wrongs++;
				lives--;
				streakLabel.setText("Streak : " + currStreak);
				livesLabel.setText("Lives: " + lives);
			}
			newOp();
			countdown.resetCounter();
		}
	}
	
	/**
	 * Action listener for the pause button
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class PauseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currStreak > highStreak) {
				highStreak = currStreak;
			}
			changeGameState();
			JPanel pause = pausePanel();
			pausePane = JOptionPane.showOptionDialog(null, pause, "Game Paused", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			if (pausePane == JOptionPane.CLOSED_OPTION) {
				changeGameState();
			}
		}		
	}
	
	/**
	 * Action listener for the exit button
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}		
	}
}
