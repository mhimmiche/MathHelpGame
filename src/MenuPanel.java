import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 * Class creating the main menu of the game
 * @author Mehdi Himmiche
 * @date Apr 25, 2016
 */
public class MenuPanel extends JFrame {
	private JButton startButton;
	private JButton helpButton;
	private JButton exitButton;
	private JButton settingsButton;
	private JLabel versionLabel;
	private JLabel title;
	private String upper;
	private String lower;
	private String timerMax;
	private JLabel upperLabel;
	private JLabel lowerLabel;
	private JLabel timerLabel;
	private JTextField upperField;
	private JTextField lowerField;
	private JTextField timerField;
	private boolean startPressed;

	public MenuPanel() {
		upperLabel = new JLabel("Max number: ");
		lowerLabel = new JLabel("Min number: ");
		timerLabel = new JLabel("Starting time: ");
		upperField = new JTextField();
		lowerField = new JTextField();
		timerField = new JTextField();
		startPressed = false;
		startButton = new JButton("Start");
		helpButton = new JButton("Help");
		ActionListener helpList = new HelpListener();
		helpButton.addActionListener(helpList);
		exitButton = new JButton("Exit");
		settingsButton = new JButton("Settings");
		ActionListener settingsList = new SettingsListener();
		settingsButton.addActionListener(settingsList);
		ActionListener exit = new ExitListener();
		exitButton.addActionListener(exit);
		versionLabel = new JLabel("<html>Version 1.0. <br>By Mehdi Himmiche</html>");
		title = new JLabel("MATHLETICS", JLabel.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 85));
		createPanel();
	}

	/**
	 * Creates the panel containing the buttons displayed on the menu 
	 * @return button panel
	 */
	public JPanel menuButtons() {
		JPanel menuButtons = new JPanel();
		menuButtons.setLayout(new GridLayout(4, 1, 10, 10));
		menuButtons.setAlignmentX(CENTER_ALIGNMENT);
		menuButtons.add(startButton);
		menuButtons.add(settingsButton);
		menuButtons.add(helpButton);
		menuButtons.add(exitButton);
		return menuButtons;
	}

	/**
	 * Creates a general info button at the bottom of the menu
	 * @return general info panel
	 */
	public JPanel bottomInfoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(versionLabel, BorderLayout.EAST);
		return infoPanel;
	}

	/**
	 * Creates the full menu panel containing all components
	 * @return full menu panel
	 */
	public JPanel fullPanel() {
		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new BorderLayout());
		JPanel menubutton = menuButtons();
		finalPanel.add(menubutton, BorderLayout.CENTER);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(title);
		titlePanel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.NORTH);
		titlePanel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.SOUTH);
		titlePanel.add(Box.createRigidArea(new Dimension(30, 0)), BorderLayout.EAST);
		titlePanel.add(Box.createRigidArea(new Dimension(30, 0)), BorderLayout.WEST);
		JPanel info = bottomInfoPanel();
		JPanel subInfo = new JPanel();
		subInfo.setLayout(new BorderLayout());
		subInfo.add(info, BorderLayout.CENTER);
		subInfo.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
		finalPanel.add(subInfo, BorderLayout.SOUTH);
		finalPanel.add(titlePanel, BorderLayout.NORTH);
		finalPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
		finalPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
		return finalPanel;
	}

	/**
	 * Create the panel displayed when settings button is pressed
	 * @return settings panel
	 */
	public JPanel settingsPanel() {
		JPanel settings = new JPanel();
		settings.setLayout(new GridLayout(3, 2, 10, 10));
		settings.add(lowerLabel);
		settings.add(lowerField);
		settings.add(upperLabel);
		settings.add(upperField);
		settings.add(timerLabel);
		settings.add(timerField);
		return settings;
	}

	/**
	 * Sets the values displayed in the text fields of the settings panel.
	 * This helps the user see what they're up against!
	 * @param orgLower lower value
	 * @param orgUpper upper value
	 * @param orgTime time value
	 */
	public void setVals(String orgLower, String orgUpper, String orgTime) {
		lower = orgLower;
		upper = orgUpper;
		timerMax = orgTime;
		lowerField.setText(orgLower);
		upperField.setText(orgUpper);
		timerField.setText(orgTime);
	}

	/**
	 * method that creates and instantiates the panel
	 */
	private void createPanel() {
		JPanel panel = new JPanel();
		add(panel);
		JPanel full = fullPanel();
		panel.add(full);
	}

	/**
	 * Action Listener for the exit button
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Action Listener for the settings button
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class SettingsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JPanel set = settingsPanel();
			int option = JOptionPane.showConfirmDialog(null, set, "Settings", JOptionPane.PLAIN_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				upper = upperField.getText();
				lower = lowerField.getText();
				timerMax = timerField.getText();
			}
		}
	}
	
	/**
	 * Action Listener for the help button
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class HelpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String help = "Hi! Thank you for trying out the new Mathletics game! \n"
					+ "This game is about helping you get accustomed to quick mental math. If you can add numbers together in your head, the world is your oyster!!\n"
					+ "You can make the experience as hard or easy as you want - Although not too easy; the max time you're allowed is 30 seconds. That's plenty of time.\n"
					+ "If you feel like the numbers are too big, go ahead and change them in settings window. Make sure you increase your challenge though."
					+ "Once you're done, you can view a table of all the operations you've encountered, as well as the correct answer and the answer you chose.\n"
					+ "If you feel like it, you can save that information onto a text file and look over it later. It'll help you improve I bet!";
			JOptionPane.showMessageDialog(null, help, "Help & tips", JOptionPane.INFORMATION_MESSAGE);
		}		
	}

	/**
	 * Get the value for low that the user input
	 * @return user low
	 */
	public String getLower() {
		return lower;
	}

	/**
	 * Get the value for upper that the user input
	 * @return user upper
	 */
	public String getUpper() {
		return upper;
	}

	/**
	 * Get the value for time that the user input
	 * @return user time
	 */
	public String getTime() {
		return timerMax;
	}

	/**
	 * Returns the start button displayed in the menu
	 * @return start game button
	 */
	public JButton getStartButton() {
		return startButton;
	}
}
