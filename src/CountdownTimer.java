import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 * Timer class. This creates the timer for the game and handles changing the timer values
 * @author Mehdi Himmiche
 * @date Apr 24, 2016
 */
class CountdownTimer {
	private static final int TIMER_PERIOD = 1000;
	private int maxTime = 10;
	private GamePanel game;
	private int count;
	private int life;
	private Timer tim;
	private ActionListener list;

	public CountdownTimer(final GamePanel game) {
		this.game = game;
		String text = "" + (maxTime - count);
		game.setTimerText(text);
		list = new TimerDecision();
		tim = new Timer(TIMER_PERIOD, list);
	}
	
	/**
	 * Starts the timer. Pretty self explanatory. 
	 */
	public void start() {
		tim.start();
	}

	/**
	 * Method resets the counter. It also checks if the player streak is within certain values to change the timer/life. 
	 * This will serve as a reward/challenge for the player to keep things rough. 
	 */
	public void resetCounter() {
		if (game.getStreak() == 10 && maxTime >= 3) {
			maxTime--;
			game.resetStreak();
			life = game.getLives();
			life++;
			game.setLife(life);
			game.setLivesText(life);
		}
		
		if (game.getStreak() == 5) {
			life = game.getLives();
			life++;
			game.setLife(life);
			game.setLivesText(life);
		}
		
		if (game.getWrongs() > 5) {
			maxTime++;
			game.resetWrongs();
		}
		tim.stop();
		count = 0;
		String text = "" + (maxTime - count);
		game.setTimerText(text);
		start();
	}

	/**
	 * Action Listener class for the timer. This counts down and removes the player's life points if the timer runs out. 
	 * @author Mehdi Himmiche
	 * @date Apr 24, 2016
	 */
	class TimerDecision implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (game.getGameState()) {
				if (count < maxTime) {
					count++;
					String text = "" + (maxTime - count);
					game.setTimerText(text);
				} else {
					life = game.getLives();
					if (life > 0) {
						life = life - 1;
						game.setLife(life);
						game.setLivesText(life);
						game.newOp();
						resetCounter();
					}					
				}
			}			
		}
	}

	/**
	 * Getter for the current max time value
	 * @return the max time for the player
	 */
	public int getMaxTime() {
		return maxTime;
	}
	
	/**
	 * Changes the value of the max time
	 * @param newMax new max time to set
	 */
	public void setMaxTime(int newMax) {
		maxTime = newMax;
	}

}