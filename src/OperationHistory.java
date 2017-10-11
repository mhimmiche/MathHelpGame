/**
 * Creates an object with the player's history
 * @author Mehdi Himmiche
 * @date Apr 25, 2016
 */
public class OperationHistory {
	private String operation;
	private String correctAnswer;
	private String selectAnswer;
	
	public OperationHistory(String operation, String correct, String selected) {
		this.operation = operation;
		this.correctAnswer = correct;
		this.selectAnswer = selected;
	}

	/**
	 * Returns the operation saved
	 * @return operation
	 */
	public String getOperation() {
		return operation;
	}
	
	/**
	 * Returns the correct answer
	 * @return correct answer
	 */
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	/**
	 * Returns the selected answer
	 * @return selected answer
	 */
	public String getSelectedAnswer() {
		return selectAnswer;
	}
}
