package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.player.Player;
import ca.concordia.encs.conquerdia.util.Observable;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Players Information
 */
public class PlayersModel extends Observable {
    /**
     * current instance of this model
     */
    private static PlayersModel instance;

    /**
     * Name of the players
     */
    private final Set<String> playerNames = new HashSet<>();

    /**
     * players queue
     */
    private Queue<Player> players = new LinkedList<>();

    /**
     * first player
     */
    private Player firstPlayer;

    /**
     * number of turns
     */
    private int numberOfTurns = -1;

    /**
     * private Constructor to implementation of the Singleton Pattern
     */
    private PlayersModel() {
    }

    /**
     * This method is used for getting a single instance of the {@link PlayersModel}
     *
     * @return single instance of the {@link PlayersModel phase}
     */
    public static PlayersModel getInstance() {
        if (instance == null) {
            synchronized (PlayersModel.class) {
                if (instance == null) {
                    instance = new PlayersModel();
                }
            }
        }
        return instance;
    }

    /**
     * Clear this model
     */
    public static void clear() {
        instance = null;
    }

    /**
     * Get the queue of the game players
     *
     * @return A queue of the game players
     */
    public Queue<Player> getPlayers() {
        return players;
    }

    /**
     * @param players
     */
    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }

    /**
     * Gets the first player of the game.
     *
     * @return the first player of the game.
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Sets the first player of the game.
     *
     * @param firstPlayer the first player of the game
     */
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    /**
     * @return number of turns
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     * @return number of players
     */
    public int getNumberOfPlayers() {
        return playerNames.size();
    }


    /**
     * Add a new player to the game if player name will not found in current player
     *
     * @param playerName name of the player to be added
     * @param strategy   strategy of the player to be added
     * @throws ValidationException when a validation rule is breaking
     */
    public void addPlayer(String playerName, String strategy) throws ValidationException {
        if (StringUtils.isBlank(playerName)) {
            throw new ValidationException("Player name is not valid!");
        }
        if (StringUtils.isBlank(strategy)) {
            throw new ValidationException("Player strategy is not valid!");
        }
        if (playerNames.contains(playerName)) {
            throw new ValidationException(String.format("Player with name \"%s\" is already exist.", playerName));
        }
        Player player = Player.factory(playerName, strategy);
        playerNames.add(playerName);
        players.add(player);
    }

    /**
     * This Method remove a player
     *
     * @param playerName name of the player to remove
     * @throws ValidationException when a validation rule is breaking
     */
    public void removePlayer(String playerName) throws ValidationException {
        if (!playerNames.contains(playerName))
            throw new ValidationException(String.format("Player with name \"%s\" is not found.", playerName));
        playerNames.remove(playerName);
        players.removeIf(player -> player.getName().equals(playerName));
    }

    /**
     * @return current player
     */
    public Player getCurrentPlayer() {
        return players.peek();
    }

    /**
     * give turn to another player based on player positions
     */
    public void giveTurnToAnotherPlayer() {
        Player player = players.poll();
        player.cleanPlayerStatus();
        players.add(player);
        numberOfTurns++;
    }

    /**
     * Give turn to the first player
     */
    public void giveTurnToFirstPlayer() {
        while (!firstPlayer.equals(getCurrentPlayer())) {
            giveTurnToAnotherPlayer();
        }
        numberOfTurns = 0;
    }

    /**
     * @return true if there is any player with unplaced army
     */
    public boolean isThereAnyUnplacedArmy() {
        return PlayersModel.getInstance().getPlayers().stream().filter(player -> player.getUnplacedArmies() > 0).count() > 0;
    }

    //
    public void update() {
        setChanged();
        notifyObservers();
    }
}
