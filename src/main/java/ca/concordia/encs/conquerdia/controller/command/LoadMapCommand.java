package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

/**
 * Implementation of loadmap command
 */
public class LoadMapCommand extends AbstractCommand {

    private static final String COMMAND_HELP_MSG = "Invalid loadmap command. a valid loadmap command is something like \"loadmap filename\".";

    /**
     * @return
     */
    @Override
    protected CommandType getCommandType() {
        return CommandType.LOAD_MAP;
    }

    /**
     * @return
     */
    @Override
    protected String getCommandHelpMessage() {
        return COMMAND_HELP_MSG;
    }

    /**
     * @param inputCommandParts the command line parameters.
     */
    @Override
    public void runCommand(List<String> inputCommandParts) {
        phaseLogList.add(WorldMap.getInstance().loadMap(inputCommandParts.get(1)));
    }
}