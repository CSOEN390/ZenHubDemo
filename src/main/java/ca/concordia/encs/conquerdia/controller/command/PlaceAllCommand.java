package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.player.Player;

import java.security.SecureRandom;
import java.util.List;
import java.util.Set;

public class PlaceAllCommand extends AbstractCommand {
    /**
     * {@inheritDoc}
     */
    @Override
    protected CommandType getCommandType() {
        return CommandType.PLACE_ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getCommandHelpMessage() {
        return "";
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

        SecureRandom randomNumber = new SecureRandom();

        while (PlayersModel.getInstance().isThereAnyUnplacedArmy()) {
            Player currentPlayer = phaseModel.getCurrentPlayer();
            if (currentPlayer.getUnplacedArmies() > 0) {
                Set<String> countryNames = currentPlayer.getCountryNames();
                String[] countriesArray = new String[countryNames.size()];
                countriesArray = countryNames.toArray(countriesArray);
                String countryName = countriesArray[randomNumber.nextInt(countryNames.size())];
                phaseModel.getCurrentPlayer().placeArmy(countryName);
                phaseLogList.add(String.format("%s placed one army to %s", currentPlayer.getName(), countryName));
            }
            PlayersModel.getInstance().giveTurnToAnotherPlayer();
        }
    }

}
