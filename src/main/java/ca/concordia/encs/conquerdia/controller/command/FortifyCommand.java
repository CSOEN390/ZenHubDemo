package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.List;

/**
 * Fortify command
 */
public class FortifyCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	private static final String COMMAND_HELP_MSG = "a valid \"fortify\" command is something like \"fortify fromcountry tocountry num\" or \"fortify none\".";

	/**
	 * Type of the commands
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.FORTIFY;
	}

	/**
	 * Getter for help message
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
		boolean noneFortify = false;
		String fromCountryName = null;
		String toCountryName = null;
		int numberOfArmy = -1;
		if ("-none".equals(inputCommandParts.get(1))) {
			noneFortify = true;
		} else {
			if (inputCommandParts.size() < 4) {
				throw new ValidationException("Invalid input! " + getCommandHelpMessage());
			}
			fromCountryName = inputCommandParts.get(1);
			toCountryName = inputCommandParts.get(2);
			try {
				numberOfArmy = Integer.valueOf(inputCommandParts.get(3));
			} catch (NumberFormatException ex) {
				throw new ValidationException("Number of armies(latest parameter) must be an integer number.");
			}
		}
		phaseLogList.add(PhaseModel.getInstance().getCurrentPlayer().fortify(fromCountryName, toCountryName,
				numberOfArmy, noneFortify));
	}
}
