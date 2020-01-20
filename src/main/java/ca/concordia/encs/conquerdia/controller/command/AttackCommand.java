package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PlayersModel;

import java.util.List;

/**
 * Class for attack command
 */
public class AttackCommand extends AbstractCommand {
	/**
	 * Helper message for command
	 */
    public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice\", or \"attack fromcountry tocountry -allout\" or \"attack -noattack\".";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runCommand(List<String> inputCommandParts) throws ValidationException {
        boolean isNoAttack = false;
        boolean isAllOut = false;
        String countryNameFrom = "";
        String countyNameTo = "";
        int numberOfDices = -1;
        if ("-noattack".equals(inputCommandParts.get(1))) {
            isNoAttack = true;
        } else {
            countryNameFrom = inputCommandParts.get(1);
            countyNameTo = inputCommandParts.get(2);
            if ("-allout".equals(inputCommandParts.get(3))) {
                isAllOut = true;
            } else {
                try {
                    numberOfDices = Integer.valueOf(inputCommandParts.get(3));
                    if (numberOfDices < 1) {
                        throw new ValidationException("Number of dices (3rd parameter) must be a positive integer number.");
                    }
                } catch (NumberFormatException ex) {
                    throw new ValidationException("Number of dices (3rd parameter) must be a positive integer number.");
                }
            }
        }
        phaseLogList.addAll(PlayersModel.getInstance().getCurrentPlayer().attack(countryNameFrom, countyNameTo, numberOfDices, isAllOut, isNoAttack));
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
        return CommandType.ATTACK;
    }

}
