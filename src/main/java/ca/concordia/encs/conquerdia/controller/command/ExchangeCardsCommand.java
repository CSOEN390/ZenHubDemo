package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.List;

/**
 * Exchange cards Command
 */
public class ExchangeCardsCommand extends AbstractCommand {

	/**
	 * Helper message for the command
	 */
	public final static String COMMAND_HELP_MSG = "exchangecards num num num –none (exchange three cards from the hand, as specified by three numbers that represent the position of the exchanged cards in the player’s hand. If –none is specified, choose to not exchange cards.)";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
		if ("–none".equals(inputCommandParts.get(1))) {
			return;
		} else {
			try {
				phaseLogList.add(PhaseModel.getInstance().getCurrentPlayer().exchangeCard(
						Integer.valueOf(inputCommandParts.get(1)), Integer.valueOf(inputCommandParts.get(2)),
						Integer.valueOf(inputCommandParts.get(3))));
			} catch (NumberFormatException ex) {
				throw new ValidationException("Card number must be an integer.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCommandHelpMessage() {
		return COMMAND_HELP_MSG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.EXCHANGE_CARDS;
	}
}
