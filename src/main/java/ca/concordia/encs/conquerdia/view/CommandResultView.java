package ca.concordia.encs.conquerdia.view;

import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.util.Observable;
import ca.concordia.encs.conquerdia.util.Observer;

import java.io.PrintStream;

public class CommandResultView implements Observer {
    private final PrintStream output;

    public CommandResultView(PrintStream output) {
        this.output = output;
    }

    @Override
    public void update(Observable o, Object arg) {
        output.println("****    Command Result View     ****************************************************************");
        for (String result : ((CommandResultModel) o).getResultList()) {
            output.println(result);
        }
        output.println("****____________________________________________________________________________________________");
    }
}
