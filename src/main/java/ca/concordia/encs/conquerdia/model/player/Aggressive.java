package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An aggressive computer player strategy that focuses on attack (reinforces its strongest country, then always attack
 * with it until it cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country).
 */
class Aggressive extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Aggressive(String name) {
        super(name);
    }

    /**
     * @return the Strategy of this player
     */
    @Override
    public String getStrategy() {
        return "aggressive";
    }

    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @return
     * @throws ValidationException
     */
    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        return super.reinforce(findMyStrongestCountry().getName(), unplacedArmies);
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     * @return result of command
     * @throws ValidationException when a validation exception happen
     */
    @Override
    public String placeArmy(String countryName) throws ValidationException {
        return super.placeArmy(findMyStrongestCountry().getName());
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
            Set<String> exclude = new HashSet<>();
            Country myStrongestCountry = null;
            while (myStrongestCountry == null) {
                myStrongestCountry = findMyStrongestCountry(exclude);
                if (!myStrongestCountry.isAdjacentToOtherPlayerCountries()) {
                    exclude.add(myStrongestCountry.getName());
                    myStrongestCountry = null;
                }
            }
            Country weakestAdjacent = null;
            for (Country adjacent : myStrongestCountry.getAdjacentCountries()) {
                if ((weakestAdjacent == null || adjacent.getNumberOfArmies() < weakestAdjacent.getNumberOfArmies()) && !adjacent.getOwner().equals(this)) {
                    weakestAdjacent = adjacent;
                }
            }
            fromCountryName = myStrongestCountry.getName();
            toCountryName = weakestAdjacent.getName();
            numdice = -1;
            isAllOut = true;
            noAttack = false;
        }
        return super.attack(fromCountryName, toCountryName, numdice, isAllOut, noAttack);
    }

    @Override
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy, boolean noneFortify) throws ValidationException {
        Country strongest = findMyStrongestCountry();
        if (strongest.isAdjacentToOtherPlayerCountries()) {
            if (!strongest.isSurroundedByEnemies()) {
                Country secondStrongest = null;
                HashSet<String> exclude = new HashSet<>();
                exclude.add(strongest.getName());
                while (secondStrongest == null) {
                    secondStrongest = findMyStrongestCountry(exclude);
                    if (!WorldMap.isTherePath(strongest, secondStrongest)) {
                        exclude.add(secondStrongest.getName());
                        secondStrongest = null;
                    }
                }
                numberOfArmy = secondStrongest.getNumberOfArmies() - 1;
                return super.fortify(secondStrongest.getName(), strongest.getName(), numberOfArmy, numberOfArmy <= 0);
            }
        } else {
            HashSet<Country> visited = new HashSet<>();
            Set<Country> collect = visitCountries(strongest, visited);
            if (collect.size() > 0) {
                int max = collect.stream().mapToInt(Country::getNumberOfArmies).max().orElse(-1);
                Country toCountry = null;
                for (Country country : collect) {
                    if (country.getNumberOfArmies() >= max) {
                        toCountry = country;
                        break;
                    }
                }
                numberOfArmy = strongest.getNumberOfArmies() - 1;
                return super.fortify(strongest.getName(), toCountry.getName(), numberOfArmy, numberOfArmy <= 0);
            }
        }
        return super.fortify(null, null, -1, true);

    }

    private Set<Country> visitCountries(Country from, Set<Country> visited) {
        HashSet<Country> countries = new HashSet<>();
        for (Country country : from.getAdjacentCountries().stream().filter(country -> !visited.contains(country)).filter(this::isMine).collect(Collectors.toSet())) {
            visited.add(country);
            if (country.isAdjacentToOtherPlayerCountries()) {
                countries.add(country);
            }
            countries.addAll(visitCountries(country, visited));
        }
        return countries;
    }

    private boolean isMine(Country country) {
        return country.getOwner().equals(this);
    }
}

