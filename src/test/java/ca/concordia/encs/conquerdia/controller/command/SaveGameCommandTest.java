package ca.concordia.encs.conquerdia.controller.command;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Save command
 */

@Ignore
public class SaveGameCommandTest {

	/**
	 * Runs before each test
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test SaveGame command
	 */

	/**
	 * Test save game command
	 */
	@Test
	public void testSaveGameCommand() {
		SaveGameCommand savegameCommand = new SaveGameCommand();
		List<String> list = new ArrayList<String>();
		list.add("savegame");
		list.add("game");
		String message = null;
		try {
			WorldMap.clear();
			WorldMap.getInstance().loadMap("risk");
			PlayersModel.clear();
			PlayersModel.getInstance().addPlayer("p1", "human");
			PlayersModel.getInstance().addPlayer("p2", "human");
			PlayersModel.getInstance().addPlayer("p3", "human");
			PhaseModel.clear();
			PhaseModel.getInstance().populateCountries();
			savegameCommand.runCommand(list);
		} catch (ValidationException ex) {
			message = ex.getMessage();
		}
		assertTrue(savegameCommand.getResultList()
				.contains("Game state was saved in the game.state file successfuly! You can Exit the game."));

	}

}
