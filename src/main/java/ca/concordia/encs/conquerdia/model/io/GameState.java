package ca.concordia.encs.conquerdia.model.io;

import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.Country;
import java.util.Set;
/**
 * GameState keeps the built state of the game for loading and saving 
 * @author Sadegh Aalizadeh
 *
 */
public class GameState {
    private PhaseModel phaseModel;
    private PlayersModel playersModel;
    private Set<Country> countries;
    public Set<Country> getCountries() {
		return countries;
	}
    /**
     * Setter for the GameState countries
     * @param countries
     */
	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}
	/**
	 * gets the PlayerModel in the GameState
	 * @return
	 */
	public PlayersModel getPlayersModel() {
        return playersModel;
    }
	/**
	 * sets the playerdModel in the GameState
	 * @param playersModel
	 */
    public void setPlayersModel(PlayersModel playersModel) {
        this.playersModel = playersModel;
    }
    /**
     * sets the phaseModel in the GameState
     * @return
     */
    public PhaseModel getPhaseModel() {
        return phaseModel;
    }
    /**
     * sets the PhaseModel in the GameState
     * @param phaseModel
     */
    public void setPhaseModel(PhaseModel phaseModel) {
        this.phaseModel = phaseModel;
    }

}
