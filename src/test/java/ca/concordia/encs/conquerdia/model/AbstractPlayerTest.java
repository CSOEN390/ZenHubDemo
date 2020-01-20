package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for players
 */
public class AbstractPlayerTest {

	/**
	 * Name of the player
	 */
	private final static String PLAYER_NAME = "testPlayer";

	/**
	 * Player object
	 */
	private Player player;

	/**
	 * Continent to test
	 */
	private Continent testContinent;

	/**
	 * Country to test
	 */
	private Country country1;

	/**
	 * Country2
	 */
	private Country country2;

	/**
	 * Country3
	 */
	private Country country3;

	/**
	 * All common activities are placed here
	 */
	@Before
	public void setUp() {

		try {
			player = Player.factory(PLAYER_NAME, "human");
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		testContinent = new Continent.Builder("testContinent").setValue(8).build();
		country1 = new Country.Builder("one", testContinent).build();
		country2 = new Country.Builder("two", testContinent).build();
		country3 = new Country.Builder("tree", testContinent).build();

		player.addCountry(country1);
		player.addCountry(country2);
		player.addCountry(country3);

	}

	/**
	 * Test case for {@link Player#getName() getName} method
	 */
	@Test
	public void getName() {
		assertEquals(player.getName(), PLAYER_NAME);
	}

	/**
	 * Test case for {@link Player#addUnplacedArmies(int)} () addUnplacedArmies}
	 * method
	 */
	@Test
	public void addUnplacedArmies() {
		player.addUnplacedArmies(100);
		assertEquals(100, player.getUnplacedArmies());
	}

	/**
	 * Test case for {@link Player#minusUnplacedArmies(int)} method
	 */
	@Test
	public void minusUnplacedArmies() {
		player.addUnplacedArmies(100);
		player.minusUnplacedArmies(50);
		assertEquals(50, player.getUnplacedArmies());
	}

	/**
	 * Test case for {@link Player#addCountry(Country)} method
	 */
	@Test
	public void addCountry() {
		Country country4 = new Country.Builder("four", testContinent).build();
		player.addCountry(country4);
		assertTrue(player.getCountryNames().contains("four"));
	}

	/**
	 * Test case for {@link Player#removeCountry(String)} method
	 */
	@Test
	public void removeCountry() {
		player.removeCountry(country3.getName());
		assertFalse(player.getCountryNames().contains("tree"));
	}

	/**
	 * Test case for {@link Player#getNumberOfCountries()} method
	 */
	@Test
	public void getNumberOfCountries() {
		assertEquals(3, player.getNumberOfCountries());
	}

	/**
	 * Test case for {@link Player#getCountryNames()} addUnplacedArmies} method
	 */
	@Test
	public void getCountryNames() {
		assertTrue(player.getCountryNames().containsAll(Arrays.asList("one", "two", "tree")));
	}

	/**
	 * Test case for {@link Player#getNumberOfContinents()} ()} method
	 */
	@Test
	public void getNumberOfContinent() {
		assertEquals(1, player.getNumberOfContinents());
	}

	/**
	 * Test case for {@link Player#calculateNumberOfReinforcementArmies()
	 * calculateNumberOfReinforcementArmies} method
	 */
	@Test
	public void calculateNumberOfReinforcementArmies() {
		assertEquals(9, player.calculateNumberOfReinforcementArmies());
		for (int i = 0; i < 10; i++) {
			Country country = new Country.Builder("country" + i, testContinent).build();
			player.addCountry(country);
		}
		assertEquals(12, player.calculateNumberOfReinforcementArmies());
	}

	/**
	 * Test case for {@link Player#getUnplacedArmies() addUnplacedArmies} method
	 */
	@Test
	public void getUnplacedArmies() {
		player.addUnplacedArmies(100);
		assertEquals(100, player.getUnplacedArmies());
	}
}
