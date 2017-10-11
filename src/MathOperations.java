import java.util.*;
/**
 * Operations class, creates the math operations used in the game
 * @author Mehdi Himmiche
 * @date Apr 25, 2016
 */
public class MathOperations {
	private int number1;
	private int number2;
	private int lowerBound;
	private int upperBound;
	private int correctAnswer;
	private ArrayList<Integer> otherAnswers;
	private Integer[] answers;
	private int interval;
	private int other;
	
	public MathOperations() {
		answers = new Integer[8];
		lowerBound = 25;
		upperBound = 75;
		interval = 15;
		other = 0;
	}
	
	/**
	 * Method that starts the operations, creating random numbers and handling the operations. 
	 */
	public void startOperations() {
		answers = new Integer[8];
		otherAnswers = new ArrayList<Integer>();
		Random r = new Random();
		number1 = r.nextInt(upperBound - lowerBound + 1) + lowerBound;
		number2 = r.nextInt(upperBound - lowerBound + 1) + lowerBound;
		correctAnswer = number1 + number2;
		otherAnswers.add(correctAnswer);
		int answerIndex = (int) (Math.random() * answers.length);
		answers[answerIndex] = correctAnswer;
		boolean isUnique = false;
		Iterator<Integer> iter = otherAnswers.iterator();
		for (int i = 0; i < answers.length; i++) {
			isUnique = false;
			if (answers[i] == null) {
				while (!isUnique) {
					isUnique = true;
					other = randomAnswers(correctAnswer);
					iter = otherAnswers.iterator();
					while (iter.hasNext()) {
						if (other == iter.next()) {
							isUnique = false;
						}
					}
				}
				answers[i] = other;
				otherAnswers.add(other);
			}			
		}
		// Great cheating tool right here :D
		//System.out.println(correctAnswer);
	}
	
	/**
	 * Return the lower bound of the random number
	 * @return lower bound
	 */
	public int getLowBound() {
		return lowerBound;
	}
	
	/**
	 * Changes the lower bound to one the user decides
	 * @param new Lower bound
	 */
	public void setLowBount(int newLow) {
		lowerBound = newLow;
	}
	
	/**
	 * Return the upper bound of the random number
	 * @return upper bound
	 */
	public int getUpperBound() {
		return upperBound;
	}
	
	/**
	 * Changes the upper bound to one the user decides
	 * @param new upper bound
	 */
	public void setHighBound (int newHigh) {
		upperBound = newHigh;
	}
	
	/**
	 * Returns the string of the operation displayed on the screen
	 * @return the operation
	 */
	public String getOperation() {
		return number1 + " + " + number2;
	}
	
	/**
	 * Creates random numbers close to the correct answer
	 * @param realAnswer
	 * @return a random number close to the real answer
	 */
	public int randomAnswers(int realAnswer) {
		int max = realAnswer + interval;
		int min = realAnswer - interval;
		int rand = 0;
		Random r = new Random();
		rand = r.nextInt(max - min + 1) + min;
		return rand;
	}
	
	/**
	 * Returns the first number
	 * @return first num
	 */
	public int getFirstNumber() {
		return number1;
	}
	
	/**
	 * Returns the second number
	 * @return second num
	 */
	public int getSecondNumber() {
		return number2;
	}
	
	/**
	 * Returns the correct answer to the operation.
	 * @return correct
	 */
	public int getAnswer() {
		return correctAnswer;
	}
	
	/**
	 * Return an array containing all the answers - correct one and the fake ones
	 * @return answers array
	 */
	public Integer[] getAllAnswers() {
		return answers;
	}
}
