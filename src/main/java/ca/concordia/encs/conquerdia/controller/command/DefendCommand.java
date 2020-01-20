package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.player.Player;

import java.util.List;

/**
 * Represents a defend move during the attack phase.
 */
public class DefendCommand extends AbstractCommand {
	/**
	 * Helper message for the user
	 */
	public static final String COMMAND_HELP_MSG = "A valid \"defend\" command format is \"defend numdice\".";

	/**
	 * Validates and executes the defend command with the input params.
	 */
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
		if (inputCommandParts.size() == getCommandType().getMinNumberOfParts()) {
			try {
				int numDice = Integer.parseInt(inputCommandParts.get(1));
				if (numDice < 0) {
					throw new NumberFormatException();
				}

				Player defender = PhaseModel.getInstance().getCurrentPlayer();
				phaseLogList.addAll(defender.defend(numDice));

			} catch (NumberFormatException ex) {
				throw new ValidationException("Number of dices must be a positive integer number.");
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
		return CommandType.DEFEND;
	}
}
