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

public class MapReaderTest {

	private static WorldMap worldMap;
	private static IMapReader reader;
	private static boolean isReadSuccessful;

	/**
	 * Uses {@link WorldMap} object with the help of a new {@link MapReader}
	 * instance. This method is run once before tests
	 */
	@BeforeClass
	public static void setup() {
		WorldMap.clear();
		worldMap = WorldMap.getInstance();
		reader = new MapReader(worldMap);
		String map = "uk.map";
		assumeTrue(FileHelper.exists(MapIO.getMapFilePath(map)));
		isReadSuccessful = reader.readMap(map);
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
		assertEquals(worldMap.getContinents().size(), 6);
	}

	/**
	 * Tests if the loaded map contains the same number of countries in the map file
	 */
	@Test
	public void testCountryCount() {
		// uk map has 75 countries,
		assertEquals(worldMap.getCountries().size(), 75);
	}

	/**
	 * Tests if a country holds the correct adjacent neighbors as in the map
	 */
	@Test
	public void testNeighbors() {
		// uk map's last border row is 75 73 74 71
		// i.e. country kent is adjacent to Surrey, Sussex, Essex
		Country kent = worldMap.getCountry("Kent");
		assertTrue(kent.isAdjacentTo("Surrey"));
		assertTrue(kent.isAdjacentTo("Sussex"));
		assertTrue(kent.isAdjacentTo("Essex"));
	}

	/**
	 * Tests if a Continent is loaded and have the same control value as in the file
	 */
	@Test
	public void testContinentDetail() {
		// Uk map has a continent row like: North-Scotland 2 blue
		Continent continent = worldMap.getContinent("North-Scotland");
		assertNotNull(continent);
		assertEquals(continent.getValue(), 2);
	}

	/**
	 * Tests if a Country is loaded and is associated with the correct continent as
	 * in the file
	 */
	@Test
	public void testCountryDetail() {
		// Uk map has a country row like: 18 Ayrshire 2 423 136
		Country country = worldMap.getCountry("Ayrshire");
		assertNotNull(country);

		// Ayshire belongs to continent 2 South-Scotland
		assertEquals(country.getContinent().getName(), "South-Scotland");
	}
}
