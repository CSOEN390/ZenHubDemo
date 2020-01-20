package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.model.player.Player;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Abstraction for attack phase commands tests
 */
@Ignore
public abstract class AttackPhaseCommandTest {

	/**
	 * Worldmap
	 */
	protected static WorldMap map;

	/**
	 * Phase
	 */
	protected static PhaseModel phaseModel;

	/**
	 * This method setups the Test class
	 *
	 * @throws ValidationException
	 */
	@BeforeClass
	public static void onStart() throws ValidationException {
		map = createMapForTest();
		phaseModel = PhaseModel.getInstance();
	}

	/**
	 * Creates a Map for test
	 *
	 * @return the WOrldMap created for the test
	 * @throws ValidationException
	 */
	public static WorldMap createMapForTest() throws ValidationException {
		WorldMap.clear();
		WorldMap map = WorldMap.getInstance();
		map.addContinent("Africa", 5);
		map.addCountry("Greece", "Africa");
		map.addCountry("Germany", "Africa");
		map.addCountry("Italy", "Africa");
		map.addCountry("France", "Africa");

		map.addNeighbour("Greece", "Germany");
		map.addNeighbour("Greece", "Italy");
		map.addNeighbour("Greece", "France");

		map.getCountry("Greece").placeArmy(5);
		map.getCountry("Germany").placeArmy(1);
		map.getCountry("Italy").placeArmy(4);
		map.getCountry("France").placeArmy(15);

		Player p1 = Player.factory("John", "human");
		map.getCountry("Greece").setOwner(p1);
		map.getCountry("France").setOwner(p1);

		map.getCountry("Italy").setOwner(Player.factory("Robert", "human"));
		map.getCountry("Germany").setOwner(Player.factory("Doe", "human"));

		return map;
	}

	/**
	 * Free resources after the test.
	 */
	@AfterClass
	public static void onFinish() {
		WorldMap.clear();
	}
}
