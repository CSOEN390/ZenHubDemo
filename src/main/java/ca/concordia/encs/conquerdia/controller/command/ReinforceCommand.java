package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.player.Player;

import java.util.List;

/**
 * Reinforce command
 */
public class ReinforceCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	public final static String COMMAND_HELP_MSG = "The \"reinforce\" command must has at least two parameter, first one is countryname and second one is number of armies.";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.REINFORCE;
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
		try {
			String countryName = inputCommandParts.get(1);
			int numberOfArmy = Integer.valueOf(inputCommandParts.get(2));
			Player currentPlayer = PhaseModel.getInstance().getCurrentPlayer();
			phaseLogList.add(currentPlayer.reinforce(countryName, numberOfArmy));
		} catch (NumberFormatException ex) {
			throw new ValidationException("Number of armies(latest parameter) must be an integer number.");
		}

	}
}
