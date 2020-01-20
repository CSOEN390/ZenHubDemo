package ca.concordia.encs.conquerdia.controller.command;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ca.concordia.encs.conquerdia.exception.ValidationException;

/**
 * Test for attack move command
 */
public class AttackMoveCommandTest extends AttackPhaseCommandTest {
	/**
	 * Tests the AttackMoveCommand validity
	 */
	@Test
	public void testCommandValidity() {

		DefendCommand defendCommand = new DefendCommand();
		List<String> list = new ArrayList<String>();
		list.add("attackmove");
		list.add("-3");
		String message = null;
		try {
			defendCommand.runCommand(list);
		} catch (ValidationException ex) {
			message = ex.getMessage();
		}
		assertTrue(message.contains("must be a positive integer"));
	}
}
