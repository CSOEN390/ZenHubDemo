package ca.concordia.encs.conquerdia.view;

import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.model.player.Player;
import ca.concordia.encs.conquerdia.util.Observable;
import ca.concordia.encs.conquerdia.util.Observer;

import java.io.PrintStream;
import java.util.Queue;
import java.util.stream.Collectors;

public class PlayersWorldDominationView implements Observer {
    private final PrintStream output;

    public PlayersWorldDominationView(PrintStream output) {
        this.output = output;
    }

    @Override
    public void update(Observable o, Object arg) {
        Queue<Player> players = ((PlayersModel) o).getPlayers();
        if (!players.isEmpty()) {
            StringBuilder mapPercentage = new StringBuilder();
            StringBuilder continentControlled = new StringBuilder();
            StringBuilder totalNumberOfArmies = new StringBuilder();
            int numberOfAllCountries = WorldMap.getInstance().getCountries().size();
            for (Player player : players) {
                mapPercentage.append(player.getName() + ":" + (player.getNumberOfCountries() * 100) / numberOfAllCountries + "%, ");
                if (player.getNumberOfContinents() > 0) {
                    continentControlled.append(player.getName()).append(": ").append(player.getContinentNames().stream().collect(Collectors.joining(","))).append(", ");
                }
                totalNumberOfArmies.append(player.getName()).append(": ").append(player.getTotalNumberOfArmies()).append(", ");
            }
            output.println("****    Players World Domination View   ********************************************************");
            output.println("The percentage of the map controlled by every player:");
            output.println(mapPercentage.toString());
            output.println("The continents controlled by every player:");
            output.println(continentControlled.toString());
            output.println("the total number of armies owned by every player:");
            output.println(totalNumberOfArmies.toString());
            output.println("****____________________________________________________________________________________________");
        }
    }
}
