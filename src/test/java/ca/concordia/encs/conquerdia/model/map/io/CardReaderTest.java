package ca.concordia.encs.conquerdia.model.map.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.model.map.io.CardReader;
import ca.concordia.encs.conquerdia.model.map.io.ICardReader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.concordia.encs.conquerdia.model.map.Card;

/**
 * Test class for card reader
 */
@Ignore
public class CardReaderTest {

	/**
	 * Reader instance
	 */
	private ICardReader reader;

	/**
	 * Setup All tests
	 */
	@Before
	public void beforeTests() {
		reader = new CardReader();
	}

	/**
	 * Test the loadcards method
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLoadCard() throws IOException {
		String path = "lotr.cards";

		ArrayList<Card> cardList = null;
		cardList = reader.loadCards(path);

		Card lastValidCard = cardList.get(63);
		assertTrue(lastValidCard.isTypeOf("Infantry"));
	}
}
