package ca.concordia.encs.conquerdia.model;


/**
 * Card Types
 */
public enum CardType {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery");
    private final String name;
    /**
     * Card Type constructor
     * @param name Name of the Card Type
     */
    CardType(String name) {
        this.name = name;
    }
    /**
     * 
     * @return The name of the card
     */
    public String getName() {
        return name;
    }
}
