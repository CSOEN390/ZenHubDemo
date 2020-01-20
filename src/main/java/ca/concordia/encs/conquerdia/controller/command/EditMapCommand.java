package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

/**
 * Edit map command handler
 */
public class EditMapCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	private static final String COMMAND_HELP_MSG = "A valid \"editmap\" command is something like \"editmap filename\".";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.EDIT_MAP;
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
		WorldMap.getInstance().editMap(fileName);
		if (!WorldMap.getInstance().isReadyForEdit()) {
			throw new ValidationException("Map is not loaded successfully!");
		}
		if (WorldMap.getInstance().isNewMapFromScratch()) {
			phaseLogList.add("A new map from scratch is loaded to edit.");
		} else {
			phaseLogList.add(String.format("Map with file name \"%s\" is loaded to edit", fileName));
		}
	}
}
