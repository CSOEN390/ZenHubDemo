package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

/**
 * Validate map command
 */
public class ValidateMapCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	private static final String COMMAND_HELP_MSG = "";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.VALIDATE_MAP;
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
	public void runCommand(List<String> inputCommandParts) {
		phaseLogList.add(WorldMap.getInstance().validateMap());
	}

}
