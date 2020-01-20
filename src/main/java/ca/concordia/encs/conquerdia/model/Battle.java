package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a battle from the attacker to the defending country.
 * Holds states during battle. It also provides information on battle winner,
 * Last dices rolled to conquer etc.
 */
public class Battle implements Serializable {
	/**
	 * For serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Winner country if the defending country is conquered
	 */
	private Country winner;

	/**
	 * 
	 * @param winner Winner Country
	 */
	public void setWinner(Country winner) {
		this.winner = winner;
	}

	/**
	 * The country from which the attack is performed
	 */
	private Country fromCountry;

	/**
	 * 
	 * @param fromCountry fromCountry
	 */
	public void setFromCountry(Country fromCountry) {
		this.fromCountry = fromCountry;
	}

	/**
	 * Country where the defending will be performed
	 */
	private Country toCountry;

	/**
	 * 
	 * @param toCountry toCountry
	 */
	public void setToCountry(Country toCountry) {
		this.toCountry = toCountry;
	}

	/**
	 * Dices used by the attacker
	 */
	private int numberOfAttackerDices;

	/**
	 * Dices used by the defender
	 */
	private int numberOfDefenderDices;

	/**
	 * A util dice roller which simulates dice rolling
	 */
	private DiceRoller diceRoller;

	/**
	 * Holds internal state management for battle such as whether it is the time for
	 * defend or attack
	 */
	private BattleState state;

	/**
	 * 
	 * @param state BattleState
	 */
	public void setState(BattleState state) {
		this.state = state;
	}

	/**
	 * The constructor of the battle class. It is assumed that a battle is created
	 * when
	 * an attacked command has performed
	 *
	 * @param attackingCountry The attacker country
	 * @param defendingCountry The country that is being attacked.
	 */
	public Battle(Country attackingCountry, Country defendingCountry) {
		state = BattleState.Attacked;
		this.fromCountry = attackingCountry;
		this.toCountry = defendingCountry;
		diceRoller = DiceRoller.getInstance();
	}

	/**
	 * Performs an allOut attack by simulating both attackers and defenders best
	 * dice roll count
	 *
	 * @return the result of the allOut Attack in messages
	 */
	public ArrayList<String> allOutAttack() {
		ArrayList<String> log = new ArrayList<String>();
		boolean continueAttack = true;

		do {
			numberOfAttackerDices = getMaxDiceCountForAttacker();
			if (numberOfAttackerDices < 1) {
				continueAttack = false;
				log.add(String.format("%s does not have anymore army to attack!!!", fromCountry.getOwner().getName()));
			} else {
				numberOfDefenderDices = getMaxDiceCountForDefender();
				state = BattleState.Attacked;
				log.addAll(simulateBattle());
				continueAttack = (winner == null);
			}
		} while (continueAttack);

		return log;
	}

	/**
	 * This method simulates the battle for attack commands
	 *
	 * @return An String of arrays demonstrating the result of simulating the
	 *         attacks.
	 */
	public ArrayList<String> simulateBattle() {
		state = BattleState.Defended;
		ArrayList<String> log = new ArrayList<>();

		int[] attackerDiceRolled = diceRoller.generateSortedDices(numberOfAttackerDices);
		int[] defenderDiceRolled = diceRoller.generateSortedDices(numberOfDefenderDices);

		int killedByDefender = 0;
		int killedByAttacker = 0;
		int minDiceRolled = Math.min(numberOfAttackerDices, numberOfDefenderDices);

		for (int i = 0; i < minDiceRolled; i++) {
			if (defenderDiceRolled[i] >= attackerDiceRolled[i]) {
				killedByDefender++;
			} else {
				killedByAttacker++;
			}
		}

		fromCountry.removeArmy(killedByDefender);
		toCountry.removeArmy(killedByAttacker);

		log.add(String.format("Attacker Rolled:  %s & Defender rolled %s."
				+ " Attacker killed: %s & Defender killed: %s."
				+ " Army Count: %s (%d) & %s (%d)",
				Arrays.toString(attackerDiceRolled),
				Arrays.toString(defenderDiceRolled),
				killedByAttacker,
				killedByDefender,
				fromCountry.getName(), fromCountry.getNumberOfArmies(),
				toCountry.getName(), toCountry.getNumberOfArmies()));

		// Check if toCuntry is Conquered
		if (toCountry.hasNoArmy()) {
			log.addAll(conquer());
			log.add(String.format(
					"Congrats! %s has conquered %s. Please move atleast %s of your armies from %s to the conquered country %s.",
					fromCountry.getOwner().getName(),
					toCountry.getName(),
					numberOfAttackerDices,
					fromCountry.getName(),
					toCountry.getName()));
		}

		return log;
	}

	/**
	 * @return The maximum dice count of attacker for the all out phase
	 */
	private int getMaxDiceCountForAttacker() {
		int armies = fromCountry.getNumberOfArmies();
		if (armies < 2) {
			return 0;
		}
		return Math.min(armies - 1, 3);
	}

	/**
	 * @return The maximum dice count of defender for the all out phase
	 */
	private int getMaxDiceCountForDefender() {
		int armies = toCountry.getNumberOfArmies();
		return Math.min(armies, 2);
	}

	/**
	 * When the attack is done and the country is conquered this method is performed
	 * to apply attack related rules
	 */
	private List<String> conquer() {
		List<String> result = new ArrayList<>();
		state = BattleState.Conquered;
		Player defender = toCountry.getOwner();
		defender.removeCountry(toCountry.getName());

		Player attacker = fromCountry.getOwner();
		int numberOfContinents = attacker.getNumberOfContinents();
		attacker.addCountry(toCountry);
		boolean isAttackerOwnedAContinent = attacker.getNumberOfContinents() > numberOfContinents;
		if (isAttackerOwnedAContinent) {
			result.add(String.format("Attacker (%s) conquered a continent as well.", attacker.getName(),
					defender.getCards().size(), defender.getName()));
		}

		winner = fromCountry;
		// fromCountry.getOwner().setAttackFinished();
		attacker.setSuccessfulAttack(true);
		/// Check Number of countries owned by the defender, if 0 gives all cards to
		/// attacker and remove player from model player queue
		result.addAll(getCardsOfDefenderIfLost(defender, attacker));

		return result;
	}

	/**
	 * Get cards of defender if they lost all of their countries
	 * 
	 * @param defender The defending player
	 * @param attacker The attacking player
	 * @return the result from the getCards
	 */
	private List<String> getCardsOfDefenderIfLost(Player defender, Player attacker) {
		List<String> result = new ArrayList<String>();
		if (defender.getNumberOfCountries() == 0) {
			result.add(String.format("%s is kicked out from game.", defender.getName()));
			PlayersModel.getInstance().getPlayers().remove(defender);
			if (defender.getCards().size() > 0) {
				result.add(String.format("Attacker (%s) wins all %d cards of kicked out player (%s).",
						attacker.getName(), defender.getCards().size(), defender.getName()));
				attacker.getCards().addAll(defender.getCards());
			}
		}
		return result;
	}

	/**
	 * @return true if the result of an attack is to conquer a country; otherwise
	 *         return false.
	 */
	public boolean isConquered() {
		return winner != null;
	}

	/**
	 * @return true if more attack is possible
	 */
	public boolean isMoreAttackPossible() {
		return fromCountry.getNumberOfArmies() > 1;
	}

	/**
	 * @return the Attacking country
	 */
	public Country getFromCountry() {
		return fromCountry;
	}

	/**
	 * @return the country that is being attacked.
	 */
	public Country getToCountry() {
		return toCountry;
	}

	/**
	 * @return the number of Attacker dices.
	 */
	public int getNumberOfAttackerDices() {
		return numberOfAttackerDices;
	}

	/**
	 * Set the number of attacker dices
	 *
	 * @param numberOfAttackerDices number of attacker dices
	 */
	public void setNumberOfAttackerDices(int numberOfAttackerDices) {
		this.numberOfAttackerDices = numberOfAttackerDices;
	}

	/**
	 * @return the number of defender dices
	 */
	public int getNumberOfDefenderDices() {
		return numberOfDefenderDices;
	}

	/**
	 * Sets the number of defender dices
	 *
	 * @param numberOfDefenderDices number of defender dices
	 */
	public void setNumberOfDefenderDices(int numberOfDefenderDices) {
		this.numberOfDefenderDices = numberOfDefenderDices;
	}

	/**
	 * @return The winner country
	 */
	public Country getWinner() {
		return winner;
	}

	/**
	 * @return get the current state of the battle
	 */
	public BattleState getState() {
		return state;
	}

	/**
	 * @return true if the attack is possible; false otherwise.
	 */
	public boolean isAttackPossible() {
		return state == BattleState.Defended;
	}

	/**
	 * @return true if the defend is possible; otherwise false.
	 */
	public boolean isDefendPossible() {
		return state == BattleState.Attacked;
	}

	/**
	 * Internal states
	 */
	public enum BattleState {
		Attacked,
		Defended,
		Conquered,
	}
}
