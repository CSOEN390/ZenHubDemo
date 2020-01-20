package ca.concordia.encs.conquerdia.model;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Provides methods to simulate dice rolls
 */
public class DiceRoller {

	/**
	 * Randomizer
	 */
	private SecureRandom rand;

	/**
	 * Singleton holder
	 */
	private static DiceRoller instance;

	/**
	 * Returns the singleton instance
	 */
	public static DiceRoller getInstance() {
		if (instance == null) {
			instance = new DiceRoller();
		}
		return instance;
	}

	/**
	 * Clear this model
	 */
	public static void clear() {
		instance = null;
	}

	/**
	 * Private constructor for the singleton
	 */
	private DiceRoller() {
		rand = new SecureRandom();
	}

	/**
	 * this method generates a random number between 1-6
	 * 
	 * @return a random number between 1-6
	 */
	public int rollDice() {
		return rand.nextInt(5) + 1;
	}

	/**
	 * @return An array of DESC sorted random integer each element between 1-6
	 */
	public int[] generateSortedDices(int numDice) {

		ArrayList<Integer> dices = new ArrayList<>(numDice);

		for (int i = 0; i < numDice; i++) {
			dices.add(rollDice());
		}

		dices.sort((a, b) -> b - a);
		return dices.stream().mapToInt(Integer::intValue).toArray();
	}
}
