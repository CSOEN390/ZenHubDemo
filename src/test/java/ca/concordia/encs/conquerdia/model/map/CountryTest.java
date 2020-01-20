package ca.concordia.encs.conquerdia.model.map;

import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * This class is design to test all different functionality of {@link Country}
 * and {@link Country.Builder}.
 */
public class CountryTest {

	/**
	 * Country to test
	 */
	private Country country;

	/**
	 * Test continent
	 */
	private Continent testContinent;

	/**
	 * C1
	 */
	private Country country1;

	/**
	 * C2
	 */
	private Country country2;

	/**
	 * C3
	 */
	private Country country3;

	/**
	 * All common activities are placed here
	 */
	@Before
	public void setUp() {
		testContinent = new Continent.Builder("testContinent").build();
		country1 = new Country.Builder("one", testContinent).build();
		country2 = new Country.Builder("two", testContinent).build();
		country3 = new Country.Builder("tree", testContinent).build();

		country = new Country.Builder("test", testContinent).build();
	}

	/**
	 * This test case is designed to check {@link Country.Builder#build() build}
	 * method of {@link Country.Builder builder} by using the fact that the
	 * {@link Country} object that is built by calling this method must be not null.
	 */
	@Test
	public void buildTestCase() {
		assertNotNull(country);
	}

	/**
	 * This test case is designed to check {@link Country#getContinent()}
	 * getContinent} method of {@link Country country}. Continent object of a
	 * country that is build by {@link Country.Builder} class must be same object
	 * that is passed to constructor.
	 */
	@Test
	public void addContinentTestCase() {
		assertSame(country.getContinent(), testContinent);
	}

}
