package ca.concordia.encs.conquerdia.model.io;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
/**
 * GameLoaderBuilder builds the gamestate from the game model
 * @author Sadegh Aalizadeh
 *
 */
public class GameSaverBuilder extends GameStateBuilder {
	private String gameStateFileName;
	/**
	 * 
	 * @param filenName fileName to save the game with
	 * @throws ValidationException
	 */
	public GameSaverBuilder(String filenName) throws ValidationException {
	    this.gameStateFileName = filenName; 
	}
	/**
	 * build the PhaseModel from the game PhaseModel instance
	 */
	@Override
	void buildPhaseModel() {
		stateProduct.setPhaseModel(PhaseModel.getInstance());
	}
	/**
	 * build the Countries from the current WorldNap instance
	 */
	@Override
	void buildCountries() {
		stateProduct.setCountries(WorldMap.getInstance().getCountries());;
	}
	/**
	 * build playersModel from the current game PlayerModel instance
	 */
	@Override
	void buildPlayersModel() {
		stateProduct.setPlayersModel(PlayersModel.getInstance());
		
	}
}
