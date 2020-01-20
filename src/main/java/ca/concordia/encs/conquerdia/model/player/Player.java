package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Battle;
import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Game Player Interface
 */
public interface Player {

    /**
     * Factory For the Player
     *
     * @param playerName name of the player
     * @param strategy   strategy
     * @return the player object
     * @throws ValidationException
     */
    static Player factory(String playerName, String strategy) throws ValidationException {
        Player player;
        switch (strategy) {
            case "human":
                player = new Human(playerName);
                break;
            case "aggressive":
                player = new Aggressive(playerName);
                break;
            case "benevolent":
                player = new Benevolent(playerName);
                break;
            case "random":
                player = new Random(playerName);
                break;
            case "cheater":
                player = new Cheater(playerName);
                break;
            default:
                throw new ValidationException("Player strategy is not valid!");
        }
        return player;
    }

    /**
     * @return true if the attack is finished
     */
    boolean isAttackFinished();
    
    /**
     * @param isAttackFinished attackfinished boolean value
     */
    void setAttackFinished(boolean isAttackFinished);

    /**
     * @return true if the player has successful attack
     */
    boolean hasSuccessfulAttack();

    /**
     * Gets the battle of the player
     */
    Battle getBattle();
    
    void setBattle(Battle battle);
    /**
     * Add a card randomly to the list of the cards that this player have
     */
    void winCard();

    /**
     * @return the name of the player
     */
    String getName();

    /**
     * Determines if the player can do a defend command
     *
     * @return true when this player can do a defend command
     */
    boolean canPerformDefend();

    /**
     * @return true if this player can move armies to the country has been conquered after attack
     */
    boolean canMoveAttack();

    /**
     * Determines if the player can do a attack
     *
     * @return true when this player can do a attack
     */
    boolean canPerformAttack();

    /**
     * @return true if this player can exchange cards
     */
    boolean canExchangeCard();

    /**
     * @return true if fortification is down
     */
    boolean isFortificationFinished();

    /**
     * set fortificationFinished
     *
     * @param isFortificationFinished boolean value of fortificationFinished
     */
    void setFortificationFinished(boolean isFortificationFinished);

    /**
     * @return number of reinforcement armies according to the Risk rules.
     */
    int calculateNumberOfReinforcementArmies();

    /**
     * @return The number of armies that belong to this player and are not placed on
     * any country.
     */
    int getUnplacedArmies();
    /**
     * Set unplaced Armies
     * @param unplacedArmies number of Unplaced Armies
     */
    void setUnplacedArmies(int unplacedArmies);
    
    /**
     * @return name of the countries this player owns
     */
    
    Set<String> getCountryNames();

    /**
     * Get current cards of this player
     *
     * @return list of cards
     */
    List<CardType> getCards();

    /**
     * Add a country to the list of the countries that owned by this player
     *
     * @param country The country to add
     */
    void addCountry(Country country);

    /**
     * Remove a country from the list of the countries that owned by this player
     *
     * @param countryName countryName
     */
    void removeCountry(String countryName);

    /**
     * Reset status of various phases
     */
    void cleanPlayerStatus();

    /**
     * @param add The number of armies that you want to add to unplaced armies of
     *            this player
     */
    void addUnplacedArmies(int add);

    /**
     * @return the number of countries this player owns
     */
    int getNumberOfCountries();

    /**
     * @return number of continents this player owns
     */
    int getNumberOfContinents();

    /**
     * Player's continents
     *
     * @param continents
     */
    void setContinents(HashMap<String, Continent> continents);

    /**
     * @param successfulAttack
     */
    void setSuccessfulAttack(boolean successfulAttack);

    /**
     * Move armies after conquering another country during a battle
     *
     * @param armiesToMove
     */

    String attackMove(int armiesToMove) throws ValidationException;

    /**
     * Performs a defend action of attack phase. It also checks if this player is in
     * battle and has a country to defend.
     *
     * @param numDice Number of dice rolled by the defending player
     * @return messages for the view
     */
    ArrayList<String> defend(int numDice) throws ValidationException;

    /**
     * @param countryName Name of the country that one army be placed on it
     */
    String placeArmy(String countryName) throws ValidationException;

    /**
     * @return name of the continents this player owns
     */
    Set<String> getContinentNames();

    /**
     * @return the total number of armies owned by this player
     */
    int getTotalNumberOfArmies();
    
    /**
     * Set totalNumber of Armies 
     * @param totalNumberOfArmies total number of armies
     */
    void setTotalNumberOfArmies(int totalNumberOfArmies);
    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @throws ValidationException
     */
    String reinforce(String countryName, int numberOfArmy) throws ValidationException;

    /**
     * Performs an attack in the attack phase.
     *
     * @param fromCountryName The attacker country
     * @param toCountryName   That country that has been attacked
     * @param numdice         Number of dices used to perform attack.
     * @param isAllOut        The option to attack with all possible armies
     * @return Message log returned about the status of the attack.
     * @throws ValidationException
     */
    List<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut, boolean noAttack) throws ValidationException;


    /**
     * @param fromCountryName source country
     * @param toCountryName   destination country
     * @param numberOfArmy    number of army
     * @param noneFortify     true if you want to skip fortification phase
     * @throws ValidationException validation exception
     */
    String fortify(String fromCountryName, String toCountryName, int numberOfArmy, boolean noneFortify) throws ValidationException;

    /**
     * Exchange Card
     *
     * @param first  position of first card
     * @param second position of second card
     * @param third  position of third card
     * @throws ValidationException validation exception
     */
    String exchangeCard(int first, int second, int third) throws ValidationException;

    /**
     * @param minus the number of armies that you want to minus from unplaced armies of this player
     */
    void minusUnplacedArmies(int minus);

    /**
     * @return true if this player is a computer
     */
    boolean isComputer();

    /**
     * @return the Strategy of this player
     */
    String getStrategy();
}
