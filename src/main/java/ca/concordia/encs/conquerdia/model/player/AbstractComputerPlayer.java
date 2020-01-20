package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.model.map.Country;

import java.security.SecureRandom;
import java.util.*;

/**
 * The player that is a computer
 */
abstract class AbstractComputerPlayer extends AbstractPlayer {

    /**
     * random generator
     */
    protected SecureRandom random = new SecureRandom();

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public AbstractComputerPlayer(String name) {
        super(name);
    }


    /**
     * @return true if this player is a computer
     */
    @Override
    public boolean isComputer() {
        return true;
    }


    protected Country findMyStrongestCountry() {
        return findMyCountry(false, null);
    }

    protected Country findMyWeakestCountry() {
        return findMyCountry(true, null);
    }

    protected Country findMyStrongestCountry(Set<String> exclude) {
        return findMyCountry(false, exclude);
    }

    protected Country findMyWeakestCountry(Set<String> exclude) {
        return findMyCountry(true, exclude);
    }

    protected Country findMyCountry(boolean weakest, Set<String> exclude) {
        Map.Entry<String, Country> foundCountry = null;
        for (Map.Entry<String, Country> entry : countries.entrySet()) {
            if ((exclude != null && exclude.contains(entry.getKey()))) {
                continue;
            }
            if (foundCountry == null) {
                foundCountry = entry;
            } else {
                if (weakest ? foundCountry.getValue().getNumberOfArmies() > entry.getValue().getNumberOfArmies() : foundCountry.getValue().getNumberOfArmies() < entry.getValue().getNumberOfArmies()) {
                    foundCountry = entry;
                }
            }

        }
        return foundCountry != null ? foundCountry.getValue() : null;
    }

    @Override
    public String exchangeCard(int first, int second, int third) throws ValidationException {
        boolean same = false;
        boolean different = false;
        CardType sameType = null;
        {
            HashMap<CardType, Integer> cardTypes = new HashMap<>();
            cards.stream().forEach(cardType -> cardTypes.merge(cardType, 1, Integer::sum));
            if (cardTypes.keySet().size() == 3) {
                different = true;
            } else {
                if (cardTypes.values().stream().anyMatch(numberOfCard -> numberOfCard >= 3)) {
                    same = true;

                    for (Map.Entry<CardType, Integer> entry : cardTypes.entrySet()) {
                        if (entry.getValue() >= 3) {
                            sameType = entry.getKey();
                        }
                    }
                }
            }
        }
        if (same || different) {
            List<Integer> indexes = new ArrayList<>();
            if (same) {
                for (int i = 0; i < cards.size(); i++) {
                    CardType cardType = cards.get(i);
                    if (sameType.equals(cardType)) {
                        indexes.add(i + 1);
                    }
                }
            }
            if (different) {
                HashSet<CardType> cardTypes = new HashSet<>();
                cardTypes.add(cards.get(0));
                indexes.add(1);
                for (int i = 1; i < cards.size(); i++) {
                    CardType cardType = cards.get(i);
                    if (!cardTypes.contains(cardType)) {
                        indexes.add(i + 1);
                        cardTypes.add(cards.get(i));
                    }
                }
            }
            return super.exchangeCard(indexes.get(0), indexes.get(1), indexes.get(2));
        }
        return String.format("%s chooses to not exchange cards.", name);

    }
}
