package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.player.Player;

import java.util.List;

/**
 * Command for place army
 */
public class PlaceArmyCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	private static final String COMMAND_HELP_MSG = "A valid \"placearmy\" command is something like \"placearmy countryname\".";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.PLACE_ARMY;
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
		PhaseModel phaseModel = PhaseModel.getInstance();
		if (!phaseModel.isAllCountriesArePopulated()) {
			throw new ValidationException("Before this command you must run \"populatecountries\" command!");
		}
		String countryName = inputCommandParts.get(1);
		Player currentPlayer = phaseModel.getCurrentPlayer();

		phaseLogList.add(currentPlayer.placeArmy(countryName));
		PlayersModel.getInstance().giveTurnToAnotherPlayer();
		while (PlayersModel.getInstance().isThereAnyUnplacedArmy()
				&& PlayersModel.getInstance().getCurrentPlayer().getUnplacedArmies() <= 0) {
			PlayersModel.getInstance().giveTurnToAnotherPlayer();
		}
	}
}