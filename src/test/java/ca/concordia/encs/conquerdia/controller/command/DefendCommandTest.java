package ca.concordia.encs.conquerdia.controller.command;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.concordia.encs.conquerdia.exception.ValidationException;

/**
 * Test for defend
 */
public class DefendCommandTest extends AttackPhaseCommandTest {
	/**
	 * TestsDefend command
	 */
	@Test
	public void testCommandValidity() {

		DefendCommand defendCommand = new DefendCommand();
		List<String> list = new ArrayList<String>();
		list.add("defend");
		list.add("-5");
		String message = null;
		try {
			defendCommand.runCommand(list);
		} catch (ValidationException ex) {
			message = ex.getMessage();
		}
		assertTrue(message.contains("must be a positive integer"));
	}
}
