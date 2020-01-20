package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.Card;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface to provide card file reading functionalities
 *
 * @author Mosabbir
 */
public interface ICardReader {
    /**
     * Loads the cards defined in the filename
     *
     * @param filename The name of the card file
     * @return An ArrayList containing the card objects read from the file
     * @throws IOException IOException
     */
    ArrayList<Card> loadCards(String filename) throws IOException;
}
