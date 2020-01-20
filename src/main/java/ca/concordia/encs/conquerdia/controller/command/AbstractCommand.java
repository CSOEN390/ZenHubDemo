package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;

import java.util.ArrayList;
import java.util.List;

/**
 * All Commands must extend this class.
 * This class implement all common things that is needed and is important to run a command
 */
public abstract class AbstractCommand implements Command {

    protected final List<String> phaseLogList = new ArrayList<>();
    protected final List<String> resultList = new ArrayList<>();

    /**
     * Clear all things
     */
    private void clear() {
        phaseLogList.clear();
        resultList.clear();
    }
    /**
     * Get resultlist
     * @return resultlist
     */
    public List<String> getResultList() {
		return resultList;
	}

	/**
     * @param inputCommandParts the command line parameters.
     */
    @Override
    public final void execute(List<String> inputCommandParts) {
        clear();
        if (!PhaseModel.getInstance().isValidCommand(getCommandType())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Command! Only below mentioned commands are valid at this phase.").append(System.getProperty("line.separator"));
            sb.append("[").append(PhaseModel.getInstance().getValidCommands()).append("]");
            CommandResultModel.getInstance().addResult(sb.toString());
            return;
        } else if (inputCommandParts.size() < getCommandType().getMinNumberOfParts()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid \"").append(getCommandType().getName()).append("\" Command! ").append(getCommandHelpMessage());
            CommandResultModel.getInstance().addResult(sb.toString());
            return;
        } else {
            try {
                runCommand(inputCommandParts);
                PlayersModel.getInstance().update();
                PhaseModel.getInstance().addPhaseLogs(phaseLogList);
                resultList.addAll(PhaseModel.getInstance().changePhase());
                CommandResultModel.getInstance().addResultList(resultList);
            } catch (ValidationException ex) {
                CommandResultModel.getInstance().addResultList(ex.getValidationErrors());
            } catch (Exception ex) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid Command! ").append(getCommandHelpMessage());
                CommandResultModel.getInstance().addResult(sb.toString());
                return;
            }
        }
    }

    /**
     * @param inputCommandParts all input parameters parts
     * @throws ValidationException validation Exception
     */
    protected abstract void runCommand(List<String> inputCommandParts) throws ValidationException;

    /**
     * @return the help message for the command
     */
    protected abstract String getCommandHelpMessage();

    /**
     * @return the type of the command {@link CommandType}
     */
    protected abstract CommandType getCommandType();

    /**
     * @return true if the command has minimum number of parameters defined
     */
    protected boolean hasMinimumNumberofParameters(List<String> inputCommandParts) {
        return inputCommandParts.size() >= getCommandType().getMinNumberOfParts();
    }
}
