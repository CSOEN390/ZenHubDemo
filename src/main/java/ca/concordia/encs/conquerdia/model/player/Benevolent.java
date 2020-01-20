package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.HashSet;
import java.util.List;

/**
 * A benevolent computer player strategy that focuses on protecting its weak countries (reinforces its weakest
 * countries, never attacks, then fortifies in order to move armies to weaker countries).
 */
class Benevolent extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Benevolent(String name) {
        super(name);
    }

    /**
     * @return the Strategy of this player
     */
    @Override
    public String getStrategy() {
        return "benevolent";
    }

    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @return
     * @throws ValidationException
     */
    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        return super.reinforce(findMyWeakestCountry().getName(), 1);
    }

    /**
     * @param fromCountryName source country
     * @param toCountryName   destination country
     * @param numberOfArmy    number of army
     * @param noneFortify     true if you want to skip fortification phase
     * @return
     * @throws ValidationException
     */
    @Override
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy, boolean noneFortify) throws ValidationException {
        numberOfArmy = -1;
        Country weakest = null;
        {
            HashSet<String> exclude = new HashSet<>();
            while (weakest == null && exclude.size() < countries.size()) {
                weakest = findMyWeakestCountry(exclude);
                boolean adjacentWithFriend = false;
                for (Country adjacent : weakest.getAdjacentCountries()) {
                    if (adjacent.getOwner().getName().equals(name)) {
                        adjacentWithFriend = true;
                        break;
                    }
                }
                if (adjacentWithFriend) {
                    break;
                } else {
                    exclude.add(weakest.getName());
                    weakest = null;
                }
            }
        }
        if (weakest != null) {
            Country strongest = null;
            {
                HashSet<String> exclude = new HashSet<>();
                exclude.add(weakest.getName());
                while (strongest == null && exclude.size() < countries.size()) {
                    strongest = findMyStrongestCountry(exclude);
                    if (!WorldMap.isTherePath(strongest, weakest)) {
                        exclude.add(strongest.getName());
                        strongest = null;
                    }
                }
            }
            if (strongest != null) {
                numberOfArmy = (strongest.getNumberOfArmies() - weakest.getNumberOfArmies()) / 2;
                fromCountryName = strongest.getName();
                toCountryName = weakest.getName();
            }
        }
        return super.fortify(fromCountryName, toCountryName, numberOfArmy, numberOfArmy <= 0);
    }

    /**
     * @param fromCountryName The attacker country
     * @param toCountryName   That country that has been attacked
     * @param numdice         Number of dices used to perform attack.
     * @param isAllOut        The option to attack with all possible armies
     * @param noAttack
     * @return
     * @throws ValidationException
     */
    @Override
    public List<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut, boolean noAttack) throws ValidationException {
        return super.attack(fromCountryName, toCountryName, numdice, isAllOut, true);
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     * @return result of command
     * @throws ValidationException when a validation exception happen
     */
    @Override
    public String placeArmy(String countryName) throws ValidationException {
        return super.placeArmy(findMyWeakestCountry().getName());
    }

}
