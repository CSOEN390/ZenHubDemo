package ca.concordia.encs.conquerdia.model.map;

/**
 * A player receives a card at the end of his turn if he successfully conquered
 * at least one country during his turn. Each card is either an infantry,
 * cavalry, or artillery card. During a player’s reinforcement phase, a player
 * can exchange a set of three cards of the same kind, or a set of three cards
 * of all different kinds for a number of armies that increases every time any
 * player does so. The number of armies a player will get for cards is first 5,
 * then increases by 5 every time any player does so (i.e. 5, 10, 15, …). A
 * player that conquers the last country owned by another player receives all
 * the cards held by that player. If a player holds five cards during his
 * reinforcement phase, he must exchange three of them for armies.
 *
 * @author Mosabbir
 */
public class Card {

    private String cardType;

    /**
     * @param cardType type of cards
     */
    public Card(String cardType) {
        this.cardType = cardType.toUpperCase();
    }

    /**
     * @return card type
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param card
     * @return
     */
    public boolean equals(Card card) {
        return card.getCardType().equals(cardType);
    }

    /**
     * @param type
     * @return
     */
    public boolean isTypeOf(String type) {
        return type.toUpperCase().equals(cardType);
    }
}
