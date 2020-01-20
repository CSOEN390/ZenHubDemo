package ca.concordia.encs.conquerdia.model.map.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * This class contains junit tests to test the map loading validity of
 * {@link MapReader} class It also checks the validity of countries, borders
 * etc. after the map read. This class is temporarily disabled due to file path
 * issues which are still need to be solved for Travis CI. The test works with
 * local machine.
 *
 * @author Mosabbir
 */

public class ConquestMapReaderTest {

	/**
	 * Worldmap to work on
	 */
	private static WorldMap worldMap;

	/**
	 * Checks if map read is sucessful
	 */
	private static boolean isReadSuccessful;

	/**
	 * Uses {@link WorldMap} object with the help of a new {@link MapReader}
	 * instance. This method is run once before tests
	 */
	@BeforeClass
	public static void setup() {
		WorldMap.clear();
		worldMap = WorldMap.getInstance();
		ConquestMapReader reader = new ConquestMapReader(worldMap);
		String map = "Montreal.map";
		assumeTrue(FileHelper.exists(MapIO.getMapFilePath(map)));
		isReadSuccessful = reader.read(map);
	}

	/**
	 * Clear {@link WorldMap} object with instance. This method is run after all the
	 * tests are run
	 */
	@AfterClass
	public static void end() {
		WorldMap.clear();
	}

	/**
	 * Test to check if the map is read successfully in the setup phase
	 */
	@Test
	public void testReadMap() {
		assertTrue(isReadSuccessful);
	}

	/**
	 * Tests if the loaded map contains the same number of continents in the map
	 * file
	 */
	@Test
	public void testContinentCount() {
		// uk map has 6 continents,
		assertEquals(worldMap.getContinents().size(), 5);
	}

	/**
	 * Tests if the loaded map contains the same number of countries in the map file
	 */
	@Test
	public void testCountryCount() {
		assertEquals(worldMap.getCountries().size(), 28);
	}

	/**
	 * Tests if a country holds the correct adjacent neighbors as in the map
	 */
	@Test
	public void testNeighbors() {
		// Villeray,265,150,Montreal Centre-Est,Ahuntsic,Montreal
		// Nord,St-Leonard,Rosemont,Mont-ROyal,Outremont
		Country country = worldMap.getCountry("Villeray");
		assertTrue(country.getAdjacentCountries().size() == 6);
		assertTrue(country.isAdjacentTo("Ahuntsic"));
		assertTrue(country.isAdjacentTo("Montreal_Nord"));
		assertTrue(country.isAdjacentTo("St-Leonard"));
		assertTrue(country.isAdjacentTo("Rosemont"));
		assertTrue(country.isAdjacentTo("Mont-Royal"));
		assertTrue(country.isAdjacentTo("Outremont"));
	}

	/**
	 * Tests if a Continent is loaded and have the same control value as in the file
	 */
	@Test
	public void testContinentDetail() {
		Continent continent = worldMap.getContinent("Montreal_Centre-Ouest");
		assertNotNull(continent);
		assertEquals(continent.getValue(), 4);
	}

	/**
	 * Tests if a Country is loaded and is associated with the correct continent as
	 * in the file
	 */
	@Test
	public void testCountryDetail() {
		Country country = worldMap.getCountry("Mont-Royal");
		assertNotNull(country);
		assertEquals(country.getContinent().getName(), "Montreal_Centre-Ouest");
	}
}
