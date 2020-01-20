package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

/**
 * This class implements the Populate Country Command ("populatecountries")
 */
public class PopulateCountriesCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	private static final String COMMAND_HELP_MSG = "";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.POPULATE_COUNTRIES;
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
		phaseModel.populateCountries();
		phaseLogList.add(
				String.format("All %d countries are populated and each of %d players are allocated %d initial armies.",
						WorldMap.getInstance().getCountries().size(), PlayersModel.getInstance().getNumberOfPlayers(),
						phaseModel.getNumberOfInitialArmies()));
	}
}