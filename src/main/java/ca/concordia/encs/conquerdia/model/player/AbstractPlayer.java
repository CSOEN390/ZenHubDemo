package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Battle;
import ca.concordia.encs.conquerdia.model.Battle.BattleState;
import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.security.SecureRandom;
import java.util.*;

/**
 * Represents a player in the Game
 */
abstract class AbstractPlayer implements Player {
    /**
     * The number of armies a player will get for cards is first 5, then increases
     * by 5 every time any player does so (i.e. 5, 10, 15, …).
     */
    private static int NUMBER_OF_ARMIES_FOR_EXCHANGE_CARD = 0;

    /**
     * Player Name
     */
    protected final String name;
    /**
     * Countries owned by this player
     */
    protected final HashMap<String, Country> countries = new HashMap<>();
    /**
     * List of the card that this player has
     */
    protected final List<CardType> cards = new ArrayList<>();
    /**
     * Continents owned by this player
     */
    protected HashMap<String, Continent> continents = new HashMap<>();
    /**
     * The number of armies that belong to this player and are not placed on any
     * country.
     */
    protected int unplacedArmies = 0;
    /**
     * true if fortification phase for the current turn has down by player
     */
    protected boolean fortificationFinished;
    /**
     * This Attribute shows the Attack is finished.
     */
    protected boolean attackFinished;
    /**
     * This Class performs the simulation for Attack
     */
    private Battle battle;
    /**
     * This Attribute shows that during the attack phase of the current player there
     * has been a successful attack
     */
    private boolean successfulAttack;

    /**
     * This attribute is the total number of armies of the player in the countries
     * he owns;
     */
    private int totalNumberOfArmies = 0;

    /**
     * @param name The name of a player that must be determined when you want to
     *             create a player
     */
    public AbstractPlayer(String name) {
        this.name = name;
        this.battle = null;
    }

    private static int getNumberOfArmiesForExchangeCard() {
        NUMBER_OF_ARMIES_FOR_EXCHANGE_CARD += 5;
        return NUMBER_OF_ARMIES_FOR_EXCHANGE_CARD;
    }

    /**
     * Player's continents.
     *
     * @param continents
     */
    public void setContinents(HashMap<String, Continent> continents) {
        this.continents = continents;
    }

    /**
     * @param successfulAttack
     */
    public void setSuccessfulAttack(boolean successfulAttack) {
        this.successfulAttack = successfulAttack;
    }

    /**
     * @return true if the player has successful attack
     */
    public boolean hasSuccessfulAttack() {
        return successfulAttack;
    }

    /**
     * Gets the battle of the player
     */
    public Battle getBattle() {
        if (battle != null) {
            return battle;
        } else {
            return new Battle(null, null);
        }
    }

    /**
     * Sets the battle of the current player for loadgame
     */
    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    /**
     * @return true if fortification is down
     */
    public boolean isFortificationFinished() {
        return fortificationFinished;
    }

    /**
     * boolean value of ffortificationFinished.
     */
    public void setFortificationFinished(boolean isFortificationFinished) {
        fortificationFinished = isFortificationFinished;
    }

    /**
     * @return true if attack is down
     */
    public boolean isAttackFinished() {
        return attackFinished;
    }

    /**
     * Sets the attackFinished
     *
     * @param isAttackFinished attackFinished boolean value
     */
    public void setAttackFinished(boolean isAttackFinished) {
        attackFinished = isAttackFinished;
    }

    /**
     * @return the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * @param add The number of armies that you want to add to unplaced armies of
     *            this player
     */
    public void addUnplacedArmies(int add) {
        if (add > 0)
            unplacedArmies += add;
    }

    /**
     * @param minus the number of armies that you want to minus from unplaced armies
     *              of this player
     */
    public void minusUnplacedArmies(int minus) {
        unplacedArmies -= minus;
        if (unplacedArmies < 0)
            unplacedArmies = 0;
    }

    /**
     * Add a country to the list of the countries that owned by this player
     *
     * @param country The country to add
     */
    public void addCountry(Country country) {
        this.countries.put(country.getName(), country);
        country.setOwner(this);
        if (ownedAll(country.getContinent().getCountriesName())) {
            continents.put(country.getContinent().getName(), country.getContinent());
        }
    }

    /**
     * Remove a country from the list of the countries that owned by this player
     *
     * @param countryName countryName
     */
    public void removeCountry(String countryName) {
        if (countries.containsKey(countryName)) {
            Country country = countries.get(countryName);
            country.setOwner(null);
            continents.remove(country.getContinent().getName());
            countries.remove(countryName);
        }
    }

    /**
     * @return the number of countries this player owns
     */
    public int getNumberOfCountries() {
        return countries.size();
    }

    /**
     * @return name of the countries this player owns
     */
    public Set<String> getCountryNames() {
        return countries.keySet();
    }

    /**
     * @return name of the continents this player owns
     */
    public Set<String> getContinentNames() {
        return continents.keySet();
    }

    /**
     * @return the total number of armies owned by this player
     */
    public int getTotalNumberOfArmies() {
        this.totalNumberOfArmies = countries.values().stream().mapToInt(Country::getNumberOfArmies).sum();
        return totalNumberOfArmies;
    }

    /**
     * Set totalNumber of Armies
     *
     * @param totalNumberOfArmies total number of armies
     */
    public void setTotalNumberOfArmies(int totalNumberOfArmies) {
        this.totalNumberOfArmies = totalNumberOfArmies;
    }

    /**
     * @return the number of continents this player owns
     */
    public int getNumberOfContinent() {
        return continents.size();
    }

//    /**
//     * Add a continent to the list of the continents that owned by this player
//     *
//     * @param continent The continent to be added to the list of continents that
//     *                  owned by this player
//     */
//    public void addContinent(Continent continent) {
//        continents.put(continent.getName(), continent);
//    }

    /**
     * Returns <tt>true</tt> if this player own all of the countries of the
     * specified collection.
     *
     * @param countries countries to be checked for owned by this player
     * @return <tt>true</tt> if this player own all of the countries of the
     * specified collection
     */
    public boolean ownedAll(Set<String> countries) {
        return this.countries.keySet().containsAll(countries);
    }

    /**
     * This Method return checks if the player owns the specified country.
     *
     * @param country the specified country
     * @return returns true if the player owns the specified country.
     */
    public boolean owns(String country) {
        return this.countries.containsKey(country);
    }

    /**
     * @return number of reinforcement armies according to the Risk rules.
     */
    public int calculateNumberOfReinforcementArmies() {
        int numberOfReinforcementArmies = 0;
        numberOfReinforcementArmies += countries.size() / 3;
        for (Map.Entry<String, Continent> entry : continents.entrySet()) {
            numberOfReinforcementArmies += entry.getValue().getValue();
        }
        if (numberOfReinforcementArmies < 3) {
            numberOfReinforcementArmies = 3;
        }
        unplacedArmies = numberOfReinforcementArmies;
        return numberOfReinforcementArmies;
    }

    /**
     * @return The number of armies that belong to this player and are not placed on
     * any country.
     */
    public int getUnplacedArmies() {
        return unplacedArmies;
    }

    /**
     * get Unplaced armies
     *
     * @param unplacedArmies number of unplaced armies
     */
    public void setUnplacedArmies(int unplacedArmies) {
        this.unplacedArmies = unplacedArmies;
    }

    /**
     * @param fromCountryName source country
     * @param toCountryName   destination country
     * @param numberOfArmy    number of army
     * @param noneFortify     true if you want to skip fortification phase
     * @throws ValidationException validation exception
     */
    @Override
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy, boolean noneFortify)
            throws ValidationException {
        if (noneFortify) {
            fortificationFinished = true;
            return String.format("%s choose to not do a move during the fortification phase.", name);
        }
        Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
        if (fromCountry == null) {
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", fromCountryName));
        }
        Country toCountry = WorldMap.getInstance().getCountry(toCountryName);
        if (toCountry == null) {
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", toCountryName));
        }
        if (numberOfArmy < 1) {
            throw new ValidationException("Invalid number! Number of armies must be greater than 0.");
        }
        if (fromCountry.getOwner() == null || !fromCountry.getOwner().getName().equals(name)) {
            throw new ValidationException(
                    String.format("Country with name \"%s\" does not belong to you!", fromCountryName));
        }
        if (toCountry.getOwner() == null || !toCountry.getOwner().getName().equals(name))
            throw new ValidationException(
                    String.format("Country with name \"%s\" does not belong to you!", toCountryName));
        if (fromCountry.equals(toCountry)) {
            throw new ValidationException("From and To countries are the same!!");
        }
        if (!WorldMap.isTherePath(fromCountry, toCountry))
            throw new ValidationException(
                    "There is no path between these two countries that is composed of countries that you owns.");

        int realNumberOfArmies = numberOfArmy > fromCountry.getNumberOfArmies() - 1
                ? fromCountry.getNumberOfArmies() - 1
                : numberOfArmy;
        fromCountry.removeArmy(realNumberOfArmies);
        toCountry.placeArmy(realNumberOfArmies);
        fortificationFinished = true;
        return String.format("%d army/armies was/were moved from %s to %s.", realNumberOfArmies, fromCountryName,
                toCountryName);
    }

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
    public List<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut,
                               boolean noAttack)
            throws ValidationException {
        if (isInBattle()) {
            throw new ValidationException("Current Attack has not finished yet.");
        }
        ArrayList<String> result = new ArrayList<>();
        if (noAttack) {
            result.add(String.format("\"-noattack\" is selected by %s.", name));
            this.attackFinished = true;
            return result;
        }
        Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
        Country toCountry = WorldMap.getInstance().getCountry(toCountryName);
        if (fromCountry == null) {
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", fromCountryName));
        }
        if (!this.owns(fromCountryName)) {
            throw new ValidationException(String.format("Country with name \"%s\" is not owned by the player \"%s\"!",
                    fromCountry.getName(), getName()));
        }
        if (toCountry == null) {
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", toCountryName));
        }
        if (this.owns(toCountryName)) {
            throw new ValidationException("You cannot attack to your countries.");
        }
        if (!fromCountry.isAdjacentTo(toCountryName)) {
            throw new ValidationException(String.format("Country with name \"%s\" is not adjacent to \"%s\"!",
                    fromCountryName, toCountryName));
        }
        if (fromCountry.getNumberOfArmies() <= 1) {
            throw new ValidationException("You have not enough army in source country.");
        }

        if (!isAllOut) {
            if (numdice > 3) {
                throw new ValidationException(
                        String.format("The Attacker \"%s\" can not roll more than 3 dices. (\"%d\" dice rolled)",
                                getName(), numdice));
            }
            if (numdice >= fromCountry.getNumberOfArmies()) {
                throw new ValidationException(String.format(
                        "Number of dice rolled (%d) should be less than the number of armies (%d) in \"%s\")", numdice,
                        fromCountry.getNumberOfArmies(), fromCountry.getName()));
            }
            battle = new Battle(fromCountry, toCountry);
            battle.setNumberOfAttackerDices(numdice);
            result.add(String.format("%s has attacked %s with %s number of dice(s).", fromCountryName, toCountryName,
                    numdice));
        } else {
            battle = new Battle(fromCountry, toCountry);
            result.add(String.format("%s(%d) has started an all out attack on %s(%d).", fromCountryName,
                    fromCountry.getNumberOfArmies(), toCountryName, toCountry.getNumberOfArmies()));
            result.addAll(battle.allOutAttack());
            if (!battle.isConquered()) {
                battle = null;
            }
        }
        return result;
    }

    /**
     * Performs a defend action of attack phase. It also checks if this player is in
     * battle and has a country to defend.
     *
     * @param numDice Number of dice rolled by the defending player
     * @return messages for the view
     */
    public ArrayList<String> defend(int numDice) throws ValidationException {
        if (!isInBattle()) {
            throw new ValidationException("There is no attack to defend!");
        }
        if (battle.isDefendPossible()) {
            throw new ValidationException("Defend Command is not valid at this phase!");
        }
        if (numDice > 2) {
            throw new ValidationException(
                    "Defender cannot roll more than 2 dices and not more than the number of armies contained defending country");
        }
        if (numDice > battle.getToCountry().getNumberOfArmies()) {
            throw new ValidationException(
                    "The number of dice cannot be more the number of armies in the defending country.");
        }
        ArrayList<String> results = new ArrayList<>();
        results.add(String.format("Player %s defended with %d dice(s).", this.name, numDice));
        battle.setNumberOfDefenderDices(numDice);
        results.addAll(battle.simulateBattle());
        if (!battle.isConquered()) {
            battle = null;
            if (!hasAttackOpportunities()) {
                this.attackFinished = true;
            }
        }
        return results;
    }

    /**
     * Move armies after conquering another country during a battle
     *
     * @param armiesToMove
     */
    public String attackMove(int armiesToMove) throws ValidationException {
        if (!isInBattle()) {
            throw new ValidationException("There is no battle!");
        }
        if (!canMoveAttack()) {
            throw new ValidationException("AttackMove Command is not valid at this phase!");
        }
        if (armiesToMove + 1 > battle.getFromCountry().getNumberOfArmies()) {
            throw new ValidationException("You must move less armies than what you have in your attacking country.");
        }

        if (armiesToMove < battle.getNumberOfAttackerDices()) {
            throw new ValidationException(String.format(
                    "You must move atleast %d armies since you rolled %d dices to conquer %s",
                    battle.getNumberOfAttackerDices(),
                    battle.getNumberOfAttackerDices(),
                    battle.getToCountry().getName()));
        }

        String fromCountryName = battle.getFromCountry().getName();
        String toCountryName = battle.getToCountry().getName();
        battle.getFromCountry().removeArmy(armiesToMove);
        battle.getToCountry().placeArmy(armiesToMove);
        successfulAttack = battle.isConquered();
        battle = null;

        if (!hasAttackOpportunities()) {
            attackFinished = true;
        }

        return String.format("%s has moved %d armies from %s to %s.", name, armiesToMove, fromCountryName,
                toCountryName);
    }

    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @throws ValidationException
     */
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        Country country = WorldMap.getInstance().getCountry(countryName);
        if (country == null)
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", countryName));
        if (numberOfArmy < 1)
            throw new ValidationException("Invalid number! Number of armies must be greater than zero.");
        if (country.getOwner() == null || !country.getOwner().getName().equals(name))
            throw new ValidationException(
                    String.format("Country with name \"%s\" does not belong to you!", countryName));
        int realNumberOfArmies = numberOfArmy > unplacedArmies ? unplacedArmies : numberOfArmy;
        country.placeArmy(realNumberOfArmies);
        minusUnplacedArmies(realNumberOfArmies);
        return String.format("%s placed %d army/armies to %s.", name, realNumberOfArmies, countryName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof AbstractPlayer))
            return false;

        AbstractPlayer player = (AbstractPlayer) o;

        return new EqualsBuilder()
                .append(getName(), player.getName())
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .toHashCode();
    }

    /**
     * Add a card randomly to the list of the cards that this player have
     */
    public void winCard() {
        cards.add(CardType.values()[new SecureRandom().nextInt(CardType.values().length)]);
    }

    /**
     * Exchange Card
     *
     * @param first  position of first card
     * @param second position of second card
     * @param third  position of third card
     * @throws ValidationException validation exception
     */
    public String exchangeCard(int first, int second, int third) throws ValidationException {
        if (cards.size() < 3) {
            throw new ValidationException(String.format("Dear %s, you need at least three cards to exchange.", name));
        }
        if (first <= 0 || first > cards.size() || second <= 0 || second > cards.size() || third <= 0
                || third > cards.size() || first == second || first == third || second == third) {
            throw new ValidationException(
                    String.format("Card numbers must be unique and positive numbers between 1 and %d", cards.size()));
        }
        if (!canExchangeCard()) {
            throw new ValidationException(
                    "A player can exchange a set of three cards of the same kind, or a set of three cards of all different kinds");
        }
        CardType firstCard = cards.get(first - 1);
        CardType secondCard = cards.get(second - 1);
        CardType thirdCard = cards.get(third - 1);
        if ((!firstCard.equals(secondCard) || !firstCard.equals(thirdCard) || !secondCard.equals(thirdCard))
                && (firstCard.equals(secondCard) || firstCard.equals(thirdCard) || secondCard.equals(thirdCard))) {
            throw new ValidationException(
                    "A player can exchange a set of three cards of the same kind, or a set of three cards of all different kinds");
        }
        Integer[] indexes = {first - 1, second - 1, third - 1};
        Arrays.sort(indexes, Collections.reverseOrder());
        for (int i = 0; i < indexes.length; i++) {
            cards.remove(indexes[i].intValue());
        }
        int numberOfArmiesForExchangeCard = getNumberOfArmiesForExchangeCard();
        unplacedArmies += numberOfArmiesForExchangeCard;
        return String.format("Player %s exchanges %s, %s, %s cards with %d armies.", name, firstCard.getName(),
                secondCard.getName(), thirdCard.getName(), numberOfArmiesForExchangeCard);
    }

    /**
     * Reset status of various phases
     */
    public void cleanPlayerStatus() {
        fortificationFinished = false;
        attackFinished = false;
    }

    /**
     * Get current cards of this player
     *
     * @return list of cards
     */
    public List<CardType> getCards() {
        return cards;
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     */
    public String placeArmy(String countryName) throws ValidationException {
        if (StringUtils.isBlank(countryName))
            throw new ValidationException("Country name is not valid!");
        Country country = WorldMap.getInstance().getCountry(countryName);
        if (country == null)
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", countryName));
        if (!this.equals(country.getOwner())) {
            throw new ValidationException(
                    String.format("Dear %s, Country with name \"%s\" does not belong to you!", name, countryName));
        }
        if (this.unplacedArmies < 1) {
            throw new ValidationException("You Don't have any unplaced army!");
        }
        minusUnplacedArmies(1);
        country.placeOneArmy();
        return String.format("%s placed one army to %s", name, countryName);
    }

    /**
     * Determines if the player can do a attack
     *
     * @return true if player can perform attack
     */
    public boolean canPerformAttack() {
        return battle == null && hasAttackOpportunities();
    }

    /**
     * Determines if the player can do a defend command
     *
     * @return true if the player can do a defend command
     */
    public boolean canPerformDefend() {
        return battle != null && (battle.getState() == BattleState.Attacked);
    }

    /**
     * @return true if this player can move armies to the country has been conquered
     * after attack
     */
    public boolean canMoveAttack() {
        return battle != null && (battle.isConquered());
    }

    /**
     * @return true if the battle is not Null; otherwise false;
     */
    private boolean isInBattle() {
        return battle != null;
    }

    /**
     * Checks if the player can attack a country or can re-attack the same country
     *
     * @return true if user can attack
     */
    public boolean hasAttackOpportunities() {
        return countries.values()
                .stream()
                .anyMatch(country -> country.getNumberOfArmies() > 1 && country.isAdjacentToOtherPlayerCountries());
    }

    /**
     * @return number of continents owned by this player
     */
    @Override
    public int getNumberOfContinents() {
        return continents.size();
    }

    /**
     * @return true if this player can exchange cards
     */
    public boolean canExchangeCard() {
        HashMap<CardType, Integer> cardTypes = new HashMap<>();
        cards.stream().forEach(cardType -> cardTypes.merge(cardType, 1, Integer::sum));
        if (cardTypes.keySet().size() == 3) {
            return true;
        }
        return cardTypes.values().stream().anyMatch(numberOfCard -> numberOfCard >= 3);
    }
}
