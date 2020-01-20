package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A random computer player strategy that reinforces random a random country, attacks a random number of times a random
 * country, and fortifies a random country, all following the standard rules for each phase
 */
class Random extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Random(String name) {
        super(name);
    }


    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @return
     * @throws ValidationException
     */
    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        String[] countyNames = new String[countries.keySet().size()];
        countyNames = countries.keySet().toArray(countyNames);

        return super.reinforce(countyNames[random.nextInt(countyNames.length)], random.nextInt(unplacedArmies) + 1);
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     */
    @Override
    public String placeArmy(String countryName) throws ValidationException {
        String[] countyNames = new String[countries.keySet().size()];
        countyNames = countries.keySet().toArray(countyNames);
        return super.placeArmy(countyNames[random.nextInt(countyNames.length)]);
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
        if (!hasAttackOpportunities()) {
            noAttack = true;
        } else {
            Country fromCountry = getOneCountry();

            List<Country> dest = fromCountry.getAdjacentCountries().stream().filter(adjacent -> !adjacent.getOwner().equals(this)).collect(Collectors.toList());
            Country[] toCounties = new Country[dest.size()];
            toCounties = dest.toArray(toCounties);
            Country toCountry = toCounties[random.nextInt(toCounties.length)];

            numdice = -1;
            isAllOut = true;
            noAttack = random.nextBoolean();
            fromCountryName = fromCountry.getName();
            toCountryName = toCountry.getName();
        }
        return super.attack(fromCountryName, toCountryName, numdice, isAllOut, noAttack);
    }

    /**
     * @param fromCountryName source country
     * @param toCountryName   destination country
     * @param numberOfArmy    number of army
     * @throws ValidationException
     */
    @Override
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy, boolean noneFortify) throws ValidationException {
        Country fromCountry;
        Country toCountry;
        noneFortify = random.nextBoolean();
        if (!noneFortify) {
            do {
                fromCountry = getOneCountry();
                toCountry = getOneCountry();
            } while (!fromCountry.equals(toCountry) && !WorldMap.isTherePath(fromCountry, toCountry));
            fromCountryName = fromCountry.getName();
            toCountryName = toCountry.getName();
            numberOfArmy = random.nextInt(fromCountry.getNumberOfArmies() - 1);
            noneFortify = numberOfArmy == 0;
        }
        return super.fortify(fromCountryName, toCountryName, numberOfArmy, noneFortify);
    }

    private Country getOneCountry() {
        List<Country> source = countries.entrySet().stream().filter(entry -> entry.getValue().getNumberOfArmies() > 1).map(entry -> entry.getValue()).collect(Collectors.toList());
        Country[] fromCounties = new Country[source.size()];
        fromCounties = source.toArray(fromCounties);
        Country fromCountry = fromCounties[random.nextInt(fromCounties.length)];
        return fromCountry;
    }

    /**
     * @return the Strategy of this player
     */
    @Override
    public String getStrategy() {
        return "random";
    }

}
