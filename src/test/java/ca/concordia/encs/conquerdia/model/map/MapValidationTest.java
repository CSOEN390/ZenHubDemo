package ca.concordia.encs.conquerdia.model.map;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This class Tests the behavior of class.
 */
public class MapValidationTest {

	/**
	 * Worldmap
	 */
	private WorldMap worldMap;

	/**
	 * All common activities are placed here
	 */
	@Before
	public void setUp() throws ValidationException {
		worldMap = WorldMap.getInstance();

		// CONTINENTS
		worldMap.addContinent("Asia", 1);
		worldMap.addContinent("Europe", 3);

		// Build sample countries in Asia and determine their adjacent countries
		worldMap.addCountry("Iran", "Asia");
		worldMap.addCountry("SaudiArabia", "Asia");
		worldMap.addCountry("Armenia", "Asia");
		worldMap.addCountry("Turkey", "Asia");
		worldMap.addCountry("Greece", "Europe");

		worldMap.addNeighbour("Iran", "SaudiArabia");
		worldMap.addNeighbour("Iran", "Armenia");
		worldMap.addNeighbour("Iran", "Turkey");
		worldMap.addNeighbour("SaudiArabia", "Turkey");
		worldMap.addNeighbour("Greece", "Turkey");

	}

	/**
	 * This TestCase is designed to check the result of
	 * {@link WorldMap#checkAllMapValidationRules()} in {@link WorldMap} class.
	 */
	@Test
	public void checkAllMapValidationRulesTestCase() {
		assertTrue(worldMap.checkAllMapValidationRules());
	}
}
