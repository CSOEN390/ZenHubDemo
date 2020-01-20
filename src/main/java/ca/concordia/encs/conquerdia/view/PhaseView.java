package ca.concordia.encs.conquerdia.view;


import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.util.Observable;
import ca.concordia.encs.conquerdia.util.Observer;

import java.io.PrintStream;

public class PhaseView implements Observer {

    private final PrintStream output;

    public PhaseView(PrintStream output) {
        this.output = output;
    }

    @Override
    public void update(Observable o, Object arg) {
        output.println("****    Phase View      ************************************************************************");
        output.println(((PhaseModel) o).getPhaseStatus());
        output.println("****____________________________________________________________________________________________");
    }
}
