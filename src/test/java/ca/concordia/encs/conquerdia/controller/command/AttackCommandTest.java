package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests for attack commands
 */
public class AttackCommandTest {
	/**
	 * Setup the data for attack validations
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * test Attack command validation rules
	 */
	@Test
	public void testAttackCommand() {
		AttackCommand attackCommand = new AttackCommand();
		List<String> list = new ArrayList<String>();
		list.add("attack");
		list.add("Greece");
		list.add("Iran");
		list.add("-5");
		String message = null;
		try {
			PlayersModel.clear();
			PlayersModel.getInstance().addPlayer("p1", "human");
			attackCommand.runCommand(list);
		} catch (ValidationException ex) {
			message = ex.getMessage();
		} finally {
			PlayersModel.clear();
		}
		assertTrue(message.contains("positive integer"));
	}

	/**
	 * Test validateNoAttack method of AttackCOmmand Class
	 */
	@Test
	public void testValidateNoAttackCommand() {

	}
}
