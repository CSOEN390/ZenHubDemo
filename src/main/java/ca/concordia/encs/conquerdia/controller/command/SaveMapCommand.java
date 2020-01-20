package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

/**
 * Save map command handler
 */
public class SaveMapCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	private static final String COMMAND_HELP_MSG = "Invalid \"savemap\" command. a valid \"savemap\" command is something like \"savemap filename\".";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.SAVE_MAP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCommandHelpMessage() {
		return COMMAND_HELP_MSG;
	}

	/**
	 * @param inputCommandParts the command line parameters.
	 */
	@Override
	public void runCommand(List<String> inputCommandParts) throws ValidationException {
		String fileName = inputCommandParts.get(1);
		WorldMap.getInstance().saveMap(fileName);
		phaseLogList.add(String.format("Map with file name \"%s\" has been saved successfully", fileName));
	}
}