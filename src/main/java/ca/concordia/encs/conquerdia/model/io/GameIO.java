package ca.concordia.encs.conquerdia.model.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Battle;
import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PhaseModel.PhaseTypes;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.model.player.Player;
/**
 * This class has methods to save or load the state of the game  
 * 
 */
public class GameIO {
	private GameStateIO gameStateIO;
	/**
	 * The Game IO constructor
	 */
	public GameIO() {
		this.gameStateIO = new GameStateIO();
	}
	/**
	 * 
	 * @param fileName the filename to save the game state 
	 * @return Returns the result of the game state save
	 * @throws ValidationException
	 */
	public String saveGame(String fileName) throws ValidationException {
		gameStateIO.setBuilder(new GameSaverBuilder(fileName));
		gameStateIO.constructGameState();
		GameState gameSaveState = gameStateIO.getGameState();
		String saveGameLog = writeGameState(gameSaveState , fileName);	
		return saveGameLog;
	}
	/**
	 * 
	 * @param fileName the filename to load the game
	 * @return message that shows the game load state.
	 * @throws Exception
	 */
	public List<String> loadGame(String fileName) throws Exception {
		gameStateIO.setBuilder(new GameLoaderBuilder(fileName));
		gameStateIO.constructGameState();
		List<String> message = new ArrayList<String>();
		message.add(("Game is Loaded Successfuly!"));
		return message;
	}
	/**
	 * 
	 * @param gameSaveState the GameState for the savegame
	 * @param fileName fileName to save the gamestate
	 * @return savegame log
	 * @throws ValidationException
	 */
	private String  writeGameState(GameState gameSaveState, String fileName) throws ValidationException {
		String saveGameLog = "";
		PlayersModel playersModel = gameSaveState.getPlayersModel();  
		Queue<Player> players = playersModel.getPlayers();
		Player firstPlayers = playersModel.getFirstPlayer();
		PhaseModel phaseModel = gameSaveState.getPhaseModel();
		PhaseTypes currentPhase = phaseModel.getCurrentPhase();
		String phaseStatus = phaseModel.getPhaseStatus();
		List<String> phaseLog = phaseModel.getPhaseLog();
		Set<Country> countries = gameSaveState.getCountries();
		File file = new File(fileName +".state");
		StringBuilder sb = new StringBuilder();
		sb.append("$$MapFileName").append("\n");
		sb.append(WorldMap.getInstance().getFileName()).append("\n");
		sb.append("$$CountryProperties").append("\n");
		sb.append("[CountryName,numberOfArmies,owner,attackDeclared]").append("\n");
		for(Country country :countries) {
			sb.append(country.getName()).append("|");
			sb.append(country.getNumberOfArmies()).append("|");
			sb.append(country.getOwner().getName()).append("|");
			sb.append(country.isAttackDeclared()).append("\n");
		}
		sb.append("$$PlayersModel").append("\n");
		sb.append("$$Players").append("\n");
		sb.append("[PlayerName,strategy,#Continents,#Countries,#TotalArmies,Cards,FortificationFinished, AttackFinished, SuccessfulAttack , ContinentNames,CountryNames, #unplacedArmies, #totalNumberoFArmies]").append("\n");
		for(Player player: players ) {
			sb.append(player.getName()).append("|");
			sb.append(player.getStrategy()).append("|");
			sb.append(player.getNumberOfContinents()).append("|");
			sb.append(player.getNumberOfCountries()).append("|");
			sb.append(player.getTotalNumberOfArmies()).append("|");
			sb.append(player.getUnplacedArmies()).append("|");
			List<CardType> playerCards = player.getCards();
			for(CardType cardType : playerCards) {
				if(playerCards.isEmpty()) {
					sb.append("|");
					break;
				}
				sb.append(cardType.getName()).append("|");
			}
			sb.append(player.isFortificationFinished()).append("|");
			sb.append(player.isAttackFinished()).append("|");
			sb.append(player.hasSuccessfulAttack()).append("|");
			sb.append(player.getContinentNames()).append("|");
			sb.append(player.getCountryNames()).append("|");
			sb.append(player.getUnplacedArmies()).append("|");
			sb.append(player.getTotalNumberOfArmies()).append("\n");
		}
		sb.append("$$FirstPlayer").append("\n");
		if(firstPlayers != null) {
			sb.append(firstPlayers.getName()).append("\n"); 
		}else {
			sb.append("").append("\n"); 
		}
		sb.append("$$Battle").append("\n");
		Battle currentPlayerBattle = playersModel.getCurrentPlayer().getBattle();
		sb.append("[FromCountry,ToCountry,winner, #AttackerDice,#DefenderDice,state]").append("\n");
		if(currentPlayerBattle != null && currentPlayerBattle.getFromCountry() != null && currentPlayerBattle.getToCountry()!= null) {
			sb.append(currentPlayerBattle.getFromCountry().getName()).append("|");
			sb.append(currentPlayerBattle.getToCountry().getName()).append("|");
			Country winner = currentPlayerBattle.getWinner();
			if(winner != null) {
				sb.append( winner.getName()).append("|");
			}else {
				sb.append("").append("|");
			}
			sb.append(currentPlayerBattle.getNumberOfAttackerDices()).append("|");
			sb.append(currentPlayerBattle.getNumberOfDefenderDices()).append("|");

			if(currentPlayerBattle.getState() != null) {
				sb.append( currentPlayerBattle.getState()).append("\n");	
			}else {
				sb.append("").append("\n");	

			}
		}else {
			sb.append("\n");
		}
		sb.append("$$PhaseModel").append("\n");
		sb.append("$$CurrentPhase").append("\n");
		sb.append(currentPhase).append("\n");
		sb.append("$$PhaseStatus").append("\n");
		sb.append(phaseStatus).append("\n");
		sb.append("$$PhaseLog").append("\n");
		for(String log : phaseLog) {
			sb.append(log).append("\n");		
		}
		sb.append("$$NumberOfInitialArmies").append("\n");
		sb.append(phaseModel.getNumberOfInitialArmies()).append("\n");
		sb.append("$$AllCountriesArePopulated").append("\n");
		sb.append(phaseModel.isAllCountriesArePopulated()).append("\n");
		sb.append("$$End");

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(sb.toString());
		} catch (IOException e) {
			throw new ValidationException(e.getMessage());
		} finally {
			if (writer != null)
				try {
					writer.close();
					saveGameLog = "Game state was saved in the "+ fileName+ ".state file" +" successfuly! You can Exit the game.";
				} catch (IOException e) {
					throw new ValidationException(e.getMessage());
				}
		}
		return saveGameLog;
	}
}
