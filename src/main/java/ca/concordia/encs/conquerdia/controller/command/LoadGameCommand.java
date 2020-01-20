package ca.concordia.encs.conquerdia.controller.command;

import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.io.GameIO;

/**
 * LoadGame Command Interpreter class
 */
public class LoadGameCommand extends AbstractCommand {
	/**
	 * Helper message for the user
	 */
	public static final String COMMAND_HELP_MSG = "A valid \"loadgame\" command format is \"loadgame filename\".";

	/**
	 * Validates and executes the loadgame command with the input params.
	 * @throws ValidationException 
	 */
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException{
		if ( inputCommandParts.size() == 2) {
			String fileName = inputCommandParts.get(1);
			
			try {
				GameIO gameIO = new GameIO();
				resultList.addAll(gameIO.loadGame(fileName));

			} catch (Exception e) {
				throw new ValidationException("FileName:"+fileName+"does not Exist.");
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
		return CommandType.LOAD_GAME;
	}
}
