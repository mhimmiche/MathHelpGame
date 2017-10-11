import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 * Class containing the player's history 
 * @author Mehdi Himmiche
 * @date Apr 25, 2016
 */
public class HistoryPanel extends JFrame {
	private JTable historyTable;
	private JButton quitButton;
	private JButton saveButton;
	private JButton backToMenu;
	private JLabel endLabel;
	private int endStreak;
	
	/**
	 * Constructor for the history panel
	 */
	public HistoryPanel() {
		endStreak = 0;
		quitButton = new JButton("Exit");
		ActionListener exitList = new ExitListener();
		quitButton.addActionListener(exitList);
		saveButton = new JButton("Save History");
		ActionListener saveList = new SaveListener();
		saveButton.addActionListener(saveList);
		backToMenu = new JButton("Main Menu");
		endLabel = new JLabel("");
		createPanel();
	}
	
	/**
	 * Returns the back to menu button from the history panel
	 * @return
	 */
	public JButton getBackToMenuButton() {
		return backToMenu;
	}
	
	/**
	 * Sets the value of the streak to be displayed in the end
	 * @param newVal
	 */
	public void setStreakVal(int newVal) {
		endStreak = newVal;
	}
	
	/**
	 * Update the label to the correct streak value. 
	 */
	public void updateLabel() {
		endLabel.setText("<html>I hope you enjoyed this game! Hopefully it wasn't too challenging for you? <br>"
				+ "Your highest streak was: " + endStreak + ". Hopefully next time you can beat that? :) <br>"
						+ "Here's a history of all the questions you were asked, with what you selected and what the correct answer was.</html>");
	}
	
	/**
	 * Load the palyer's history into the table to be displayed at the end
	 * @param history queue
	 */
	public void loadHistory(LinkedList<OperationHistory> hist) {
		DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
		model.setRowCount(0);
		while (hist.peek() != null) {
			OperationHistory op = hist.poll();
			String operation = op.getOperation();
			String correct = op.getCorrectAnswer();
			String selected = op.getSelectedAnswer();
			model.addRow(new Object[] {operation, correct, selected});
		}
	}
	
	/**
	 * Creates a scorllpane containing the history table 
	 * @return history table
	 */
	private JScrollPane createHistTable() {
		String columnNames[] = { "Operation", "Correct Answer", "Selected Answer"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		tableModel.setColumnIdentifiers(columnNames);
		historyTable = new JTable(tableModel);

		JScrollPane resultPanel = new JScrollPane(historyTable);
		historyTable.setFillsViewportHeight(true);
		
		return resultPanel;
	}
	
	/**
	 * Creates the panel containing the buttons at the bottom of the history panel
	 * @return button panel
	 */
	private JPanel buttonPanel() {
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 3, 10, 10));
		btnPanel.add(backToMenu);
		btnPanel.add(saveButton);
		btnPanel.add(quitButton);
		return btnPanel;
	}
	
	/**
	 * Creates the panel containing all the components in the history panel
	 * @return history panel
	 */
	private JPanel histPanel() {
		JPanel hist = new JPanel();
		hist.setLayout(new BorderLayout());
		JScrollPane table = createHistTable();
		hist.add(table, BorderLayout.CENTER);
		JPanel btns = buttonPanel();
		hist.add(btns, BorderLayout.SOUTH);
		hist.add(endLabel, BorderLayout.NORTH);
		return hist;
	}
	
	/**
	 * Creates the full panel, and adds a border around it for some padding
	 * @return full history panel
	 */
	public JPanel fullPanel() {
		JPanel full = new JPanel();
		full.setLayout(new BorderLayout());
		full.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
		full.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
		full.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
		full.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
		JPanel hist = histPanel();
		full.add(hist, BorderLayout.CENTER);
		return full;
	}
	
	/**
	 * create the panel, called in the constructor.
	 */
	private void createPanel() {
		JPanel panel = new JPanel();
		add(panel);
		JPanel full = fullPanel();
		panel.add(full);
	}

	/**
	 * Get the values in the table, used to save the player's history
	 * @return a 2D array of the history table
	 */
	public String[][] tableValues() {
		DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
	    int rows = model.getRowCount();
	    int cols = model.getColumnCount();
	    String[][] tableVals = new String[rows][cols];
	    for (int i = 0; i < rows; i++) {
	    	for (int j = 0; j < cols; j++) {
	    		tableVals[i][j] = (String) model.getValueAt(i, j);
	    	}
	    }	    
	    return tableVals;
	}
	
	/**
	 * Saves the player's history as .txt file for them to view it later
	 * @author Mehdi Himmiche
	 * @date Apr 25, 2016
	 */
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String date = "" + (new Date());
			String fileName = "History/hist.txt";
			String[][] tableVals = tableValues();
			try {
				FileWriter fw = new FileWriter(fileName);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("History for: " + date);
				bw.newLine();
				bw.write("Your streak was " + endStreak);
				bw.newLine();
				bw.newLine();
				for (int i = 0; i < tableVals.length; i++) {
					bw.write("Operation: " + tableVals[i][0] + ". Correct Answer: " + tableVals[i][1] + ". Selected Answer: " + tableVals[i][2] + ".");
					bw.newLine();
					bw.newLine();
				}				
				bw.close();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(null, "There was an error attempting to save your history.",
						"Error saving file", JOptionPane.ERROR_MESSAGE);
			}
		}
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
	
}
