package ca.concordia.encs.conquerdia.model.map.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.model.map.Card;

/**
 * Provide methods to read cards
 * 
 * @author Mosabbir
 *
 */
public class CardReader implements ICardReader {
	/**
	 * Comment symbol
	 */
	public static final String COMMENT_SYMBOL = ";";
	/**
	 * Card section
	 */
	public static final String CARDS_SECTION_IDENTIFIER = "[cards]";
	/**
	 * Tokens separator
	 */
	public static final String TOKENS_DELIMETER = " ";

	/**
	 * Folder name
	 */
	public static final String CARDS_FOLDER = "cards";
	/**
	 * File extensions
	 */
	public static final String CARDS_FILE_EXTENSION = ".cards";
	/**
	 * Types
	 */
	public static final String SUPPORTED_TYPES = "INFANTRY,CAVALRY,CANNON";

	/**
	 * Reads cards from the card file and returns all the cards
	 * 
	 * @param filename Card File name
	 * @return list of cards
	 */
	public ArrayList<Card> loadCards(String filename) throws IOException {
		final String path = getCardsFilePath(filename);

		final File file = new File(path);
		final BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;

		ArrayList<Card> cards = null;

		try {
			while ((line = reader.readLine()) != null) {
				if (isCardsIdentifier(line)) {
					cards = readCards(reader);
					break;
				}
			}
		} finally {
			reader.close();
		}

		return cards;
	}

	/**
	 * Reads the cards in the map.
	 * 
	 * @param reader the BufferReaser for the cards
	 * @return List of cards read.
	 * @throws IOException
	 */
	private ArrayList<Card> readCards(BufferedReader reader) throws IOException {

		ArrayList<Card> cardList = new ArrayList<>();
		Card card;
		String line;
		String tokens[];
		String cardType;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			cardType = tokens[0];

			if (isSupprotedCardType(cardType)) {
				card = new Card(cardType);
				cardList.add(card);
			}
		}

		return cardList;
	}

	/**
	 * CHecks if the CardType is supported.
	 * 
	 * @param cardType the card Type that is being checked.
	 * @return true if the Card Type is supported; false otherwise.
	 */
	private boolean isSupprotedCardType(String cardType) {
		return SUPPORTED_TYPES.contains(cardType.toUpperCase());
	}

	/**
	 * CHecks the line to see if it is a Card identifier.
	 * 
	 * @param line line of the map.
	 * @return true if it is a card identifier; otherwise false.
	 */
	private static boolean isCardsIdentifier(String line) {
		return line.equalsIgnoreCase(CARDS_SECTION_IDENTIFIER);
	}

	/**
	 * gets the cards file path
	 * 
	 * @param filename the file name
	 * @return The path to card files
	 */
	private static String getCardsFilePath(String filename) {
		return FileHelper.getResourcePath(FileHelper.combinePath(CARDS_FOLDER,
				FileHelper.getFileNameWithoutExtension(filename) + CARDS_FILE_EXTENSION));
	}
}
