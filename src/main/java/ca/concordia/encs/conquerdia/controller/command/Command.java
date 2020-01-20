package ca.concordia.encs.conquerdia.controller.command;

import java.util.List;

/**
 * Command Interface
 */
public interface Command {
    /**
     * Factory method for the commands based on user input
     *
     * @param inputCommandParts user input
     * @return the command type
     */
    static CommandType createCommand(String[] inputCommandParts) {
        CommandType commandType = CommandType.findCommandTypeByName(inputCommandParts[0]);
        return commandType;
    }


    /**
     * @param inputCommandParts the command line parameters.
     */
    void execute(List<String> inputCommandParts);

}
