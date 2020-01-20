package ca.concordia.encs.conquerdia.model.map;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.player.Player;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test for map formattor
 * 
 * @author Mosabbir
 *
 */
public class MapFormattorTest {

	/**
	 * Map to format
	 */
	private static WorldMap map;

	/**
	 * All common activities are placed here
	 */
	@BeforeClass
	public static void setUpOnce() throws ValidationException {
		map = WorldMap.getInstance();

		map.addContinent("South-West-England", 5);
		map.addCountry("Ross-shire", "South-West-England");
		map.addCountry("Ross-shire-and-Chromartyshire", "South-West-England");
		map.addCountry("Kincardine-shire", "South-West-England");
		map.addCountry("Northamptonshire_Northamptonshire", "South-West-England");

		map.addNeighbour("Ross-shire", "Ross-shire-and-Chromartyshire");
		map.addNeighbour("Ross-shire", "Kincardine-shire");
		map.addNeighbour("Ross-shire", "Northamptonshire_Northamptonshire");

		map.getCountry("Ross-shire").placeArmy(5);
		map.getCountry("Ross-shire-and-Chromartyshire").placeArmy(1);
		map.getCountry("Kincardine-shire").placeArmy(4);
		map.getCountry("Northamptonshire_Northamptonshire").placeArmy(15);

		Player p1 = Player.factory("John", "human");
		map.getCountry("Ross-shire").setOwner(p1);
		map.getCountry("Northamptonshire_Northamptonshire").setOwner(p1);

		map.getCountry("Kincardine-shire").setOwner(Player.factory("Robert", "human"));
		map.getCountry("Ross-shire-and-Chromartyshire").setOwner(Player.factory("Doe", "human"));

	}

	/**
	 * Release all objects after All Test cases are performed in the Test Class
	 */
	@AfterClass
	public static void cleanup() {
		WorldMap.getInstance().clear();
	}

	/**
	 * TEst for Default Map formatter
	 */
	@Test
	public void testDefaultMapFormattor() {
		MapFormattor formattor = new MapFormattor(map);
		String res = formattor.format();
		assertTrue(res.contains("Northamptonshire_Northamptonshire"));
	}

	/**
	 * Test for DetailMapFormatter
	 */
	@Test
	public void testDetailMapFormattor() {
		MapFormattor formattor = new MapFormattor(map);
		String res = formattor.format(MapFormattor.FormatType.Detail);
		assertTrue(res.contains("Northamptonshire_Northamptonshire| 15    | John"));
	}

	/**
	 * Tests for PresenseOfEmptyContinents()
	 *
	 * @throws ValidationException
	 */
	@Test
	public void testPresenseOfEmptyContinents() throws ValidationException {
		map.addContinent("Antarctica", 8);
		MapFormattor formattor = new MapFormattor(map);
		String res = formattor.format(MapFormattor.FormatType.Detail);
		assertTrue(res.contains("Antarctica"));
	}
}
