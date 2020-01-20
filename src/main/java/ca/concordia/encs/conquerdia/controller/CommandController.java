package ca.concordia.encs.conquerdia.controller;

import ca.concordia.encs.conquerdia.controller.command.Command;
import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.player.Player;

import java.util.Arrays;

/**
 * Handles commands
 */
public class CommandController {

    /**
     * @param commandStr user command
     */
    public void executeCommand(String commandStr) {
        do {
            CommandResultModel.getInstance().clear();
            if (PhaseModel.getInstance().getCurrentPlayer() != null && PhaseModel.getInstance().getCurrentPlayer().isComputer() && PhaseModel.getInstance().isAllCountriesArePopulated()) {
                switch (PhaseModel.getInstance().getCurrentPhase()) {
                    case START_UP:
                        if (PhaseModel.getInstance().isAllCountriesArePopulated()) {
                            if (PlayersModel.getInstance().isThereAnyUnplacedArmy()) {
                                commandStr = "placearmy countryname";
                            }
                        }
                        break;
                    case REINFORCEMENT:
                        if (PhaseModel.getInstance().getCurrentPlayer().canExchangeCard()) {
                            commandStr = "exchangecards 1 2 3";
                        } else {
                            commandStr = "reinforce countryname 1";
                        }
                        break;
                    case FORTIFICATION:
                        commandStr = "fortify fromcountry tocountry 1";
                        break;
                    case ATTACK:
                        if (PhaseModel.getInstance().getCurrentPlayer().getBattle() != null && PhaseModel.getInstance().getCurrentPlayer().getBattle().isConquered()) {
                            commandStr = "attackmove " + PhaseModel.getInstance().getCurrentPlayer().getBattle().getNumberOfAttackerDices();
                        } else if (PhaseModel.getInstance().getCurrentPlayer().canPerformAttack()) {
                            commandStr = "attack countrynamefrom countynameto 1";
                        } else {
                            commandStr = "attack -noattack";
                        }
                        break;
                }
            }

            String[] inputCommandParts = commandStr.trim().split(" ");
            if (inputCommandParts.length <= 0) {
                CommandResultModel.getInstance().addResult("Invalid Command! A valid command must have at least one part.");
                return;
            }
            CommandType commandType = Command.createCommand(inputCommandParts);
            if (commandType == null) {
                CommandResultModel.getInstance().addResult("Command not found.");
                return;
            }
            commandType.getCommand().execute(Arrays.asList(inputCommandParts));

            Player currentPlayer = PhaseModel.getInstance().getCurrentPlayer();
            if (currentPlayer != null) {
                CardExchangeModel.getInstance().addCards(currentPlayer.getCards());
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        } while (PhaseModel.getInstance().getCurrentPlayer() != null &&
                PhaseModel.getInstance().isAllCountriesArePopulated() &&
                PhaseModel.getInstance().getCurrentPlayer().isComputer() &&
                !PhaseModel.getInstance().isFinished()
        );
    }

}
