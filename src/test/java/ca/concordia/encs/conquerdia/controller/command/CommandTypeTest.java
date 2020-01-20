package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.controller.command.EditContinentCommand;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for command types
 */
public class CommandTypeTest {
	/**
	 * Test findCommandTypeByName
	 */
	@Test
	public void findCommandTypeByName() {
		assertEquals(CommandType.findCommandTypeByName("editcontinent"), CommandType.EDIT_CONTINENT);
	}

	/**
	 * Test Get factory method
	 */
	@Test
	public void getFactory() {
		assertTrue(CommandType.EDIT_CONTINENT.getCommand() instanceof EditContinentCommand);
	}
}