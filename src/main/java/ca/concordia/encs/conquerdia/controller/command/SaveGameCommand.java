package ca.concordia.encs.conquerdia.controller.command;

import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.io.GameIO;

/**
 * LoadGame Command Interpreter class
 */
public class SaveGameCommand extends AbstractCommand {
	/**
	 * Helper message for the user
	 */
	public static final String COMMAND_HELP_MSG = "A valid \"savegame\" command format is \"savegame filename\".";

	/**
	 * Validates and executes the savegame command with the input params.
	 * @throws ValidationException 
	 */
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException{
		if ( inputCommandParts.size() == 2) {
			String fileName = inputCommandParts.get(1);
			try {
				GameIO gameIO = new GameIO();
				resultList.add(gameIO.saveGame(fileName));
			} catch (Exception e) {
				throw new ValidationException(e.getMessage());
			}
		} else {
			throw new ValidationException("Invalid input! " + getCommandHelpMessage());
		}
	}


	/**
	 * @return Returns the helper message
	 */
	@Override
	protected String getCommandHelpMessage() {
		return COMMAND_HELP_MSG;
	}

	/**
	 * @return Returns the command type that is associated with this class
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.SAVE_GAME;
	}
}
