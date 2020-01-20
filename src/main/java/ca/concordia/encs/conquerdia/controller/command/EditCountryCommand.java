package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.Iterator;
import java.util.List;

/**
 * Edit country command
 */
public class EditCountryCommand extends AbstractCommand {
	/**
	 * Helper message for the command
	 */
	public final static String COMMAND_HELP_MSG = "Invalid input! The \"editcountry\" command must has at least one option like \"-add\" or \"-remove\".";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.EDIT_COUNTRY;
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
		Iterator<String> iterator = inputCommandParts.iterator();
		iterator.next();

		while (iterator.hasNext()) {
			switch (iterator.next()) {
			case ("-add"): {
				String countryName = iterator.next();
				String continentName = iterator.next();
				try {
					WorldMap.getInstance().addCountry(countryName, continentName);
					phaseLogList.add(
							String.format("Country with name \"%s\" is added to \"%s\"", countryName, continentName));
				} catch (ValidationException ex) {
					resultList.addAll(ex.getValidationErrors());
				}
				break;
			}
			case "-remove": {
				try {
					String countryName = iterator.next();
					WorldMap.getInstance().removeCountry(countryName);
					phaseLogList.add(String.format("Country with name \"%s\" is successfully removed from World Map",
							countryName));
				} catch (ValidationException ex) {
					resultList.addAll(ex.getValidationErrors());
				}
				break;
			}
			default: {
				throw new ValidationException(COMMAND_HELP_MSG);
			}
			}
		}
	}
}
