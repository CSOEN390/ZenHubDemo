package ca.concordia.encs.conquerdia.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PlayersModel;

@Ignore
/**
 * Test for load game
 */
public class LoadGameCommandTest {

	/**
	 * Runs before each tests
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test LoadGame command
	 */
	@Test
	public void testLoadGameCommand() {
		LoadGameCommand loadgameCommand = new LoadGameCommand();
		List<String> list = new ArrayList<String>();
		list.add("loadgame");
		list.add("game");
		try {
			loadgameCommand.runCommand(list);
		} catch (ValidationException ex) {
		}
		assertTrue(loadgameCommand.getResultList().contains("Game is Loaded Successfuly!"));
		int numberOfPlayers = PlayersModel.getInstance().getPlayers().size();
		assertEquals(numberOfPlayers, 3);
	}
}
