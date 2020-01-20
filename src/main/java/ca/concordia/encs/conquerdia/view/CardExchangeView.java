package ca.concordia.encs.conquerdia.view;

import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.util.Observable;
import ca.concordia.encs.conquerdia.util.Observer;

import java.io.PrintStream;

/**
 * The card exchange view is created only during the reinforcement phase. It display all the cards owned by the current
 * player, then allow the player to select some cards to exchange. If the player selects cards, they are given the
 * appropriate number of armies as reinforcement. The player can choose not to exchange cards and exit the card exchange
 * view. If the player own 5 cards or more, they must exchange cards. The cards exchange view should cease to exist
 * after the cards exchange.
 */
public class CardExchangeView implements Observer {

    private final PrintStream output;

    public CardExchangeView(PrintStream output) {
        this.output = output;
    }

    @Override
    public void update(Observable o, Object arg) {
        CardExchangeModel cardExchangeModel = (CardExchangeModel) o;
        if (cardExchangeModel.isReinforcementPhaseActive() && !cardExchangeModel.getCards().isEmpty()) {
            output.println("****    Card Exchange View     ****************************************************************");
            int i = 0;
            for (CardType cardType : cardExchangeModel.getCards()) {
                output.print(++i + "-" + cardType.getName() + ", ");
            }
            output.println();
            output.println("****____________________________________________________________________________________________");
        }
    }
}
