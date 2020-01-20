package ca.concordia.encs.conquerdia.model.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import com.google.common.base.CharMatcher;
import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Battle;
import ca.concordia.encs.conquerdia.model.Battle.BattleState;
import ca.concordia.encs.conquerdia.model.PhaseModel.PhaseTypes;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.model.player.Player;
/**
 * GameLoaderBuilder builds the gamestate from the saved gamestate file
 * @author Sadegh Aalizadeh
 *
 */
public class GameLoaderBuilder extends GameStateBuilder {
	private String gameStateFileName;
	private Queue<Player> players = new LinkedList<>();
	private Player firstPlayer;
	private Player currentPlayer;
	private Battle battle;
	private PhaseTypes currentPhase;
	private String phaseStatus = new String("");
	private List<String> phaseLog = new ArrayList<String>();
    private int numberOfInitialArmies = -1;
    private boolean allCountriesArePopulated;
    private String mapFileName;
    private Map<String , List<String>> countriesProperties = new HashMap<String, List<String>>();
	/**
	 * 
	 * @param fileName the file name to load the gamestate
	 * @throws ValidationException
	 */
    public GameLoaderBuilder(String fileName) throws ValidationException {
	    this.gameStateFileName = fileName; 
        parseGameStateFile();
	}
    /**
     * build Playersmodel in game after loading the playermodel attributes from gamestate file
     */
	@Override
	void buildPlayersModel() {
		
		//1.Initial instantiation of state product with PlayersModel object after loading map
		stateProduct.setPlayersModel(PlayersModel.getInstance());
		//2.Building the state product saved properties of the game state.
		for(Player player: players) {
			stateProduct.getPlayersModel().getPlayers().add(player);
		}
		stateProduct.getPlayersModel().setFirstPlayer(firstPlayer);
		stateProduct.getPlayersModel().getCurrentPlayer().setBattle(battle);
		//3.Updating the PlayersModel with the built state
		PlayersModel.getInstance().setFirstPlayer(stateProduct.getPlayersModel().getFirstPlayer());
		PlayersModel.getInstance().setPlayers(stateProduct.getPlayersModel().getPlayers());
	}
	/**
	 * build PhaseModel in game after loading the phasemodel attributes from gamestate file
	 */
	@Override
	void buildPhaseModel() {
		//1.Initial instantiation of state product with PhaseModel object after loading map
		stateProduct.setPhaseModel(PhaseModel.getInstance());
		//2.Building the state product saved properties of the game state.
		stateProduct.getPhaseModel().setCurrentPhase(currentPhase);
		stateProduct.getPhaseModel().addPhaseLogs(phaseLog);
		stateProduct.getPhaseModel().setNumberOfInitialArmies(numberOfInitialArmies);
		stateProduct.getPhaseModel().setAllCountriesArePopulated(allCountriesArePopulated);
		//3.Updating the PlayersModel with the built state
		PhaseModel.getInstance().setCurrentPhase(stateProduct.getPhaseModel().getCurrentPhase());
		PhaseModel.getInstance().getPhaseLog().clear();
		PhaseModel.getInstance().getPhaseLog().addAll(stateProduct.getPhaseModel().getPhaseLog());
	}
	/**
	 * build Countries in game after loading the countries properties from gamestate file
	 */
	@Override
	void buildCountries() {	
		//1.Initial instantiation of state product with Countries object after loading map
		stateProduct.setCountries(WorldMap.getInstance().getCountries());
		//2.Building the state product saved properties of the game state.
		for(Country country :stateProduct.getCountries()) {
			if(countriesProperties.keySet().contains(country.getName())) {
				List<String> properties = countriesProperties.get(country.getName());
				country.setNumberOfArmies(Integer.parseInt(properties.get(0)));
				for(Player player: PlayersModel.getInstance().getPlayers()) {
					if(player.getName().equals(properties.get(1))) {
						country.setOwner(player); 
						break;
					}
				}
				boolean attackDeclared = properties.get(2).equals("true")?true:false;
				country.setAttackDeclared(attackDeclared);
				}
			}
		//3.Updating the Countries with the built state
		for(Country country :stateProduct.getCountries()) {
			
			WorldMap.getInstance().getCountriesHashMap().get(country.getName()).setNumberOfArmies(country.getNumberOfArmies());
			WorldMap.getInstance().getCountriesHashMap().get(country.getName()).setOwner(country.getOwner());
			WorldMap.getInstance().getCountriesHashMap().get(country.getName()).setAttackDeclared(country.isAttackDeclared());
			}
		}
	/**
	 * Parse game state file to load attributes of the game PlayerModel,PhaseModel and countries
	 * @throws ValidationException
	 */
	private void parseGameStateFile() throws ValidationException {
		BufferedReader reader;
        try {
        	reader = new BufferedReader(new FileReader(gameStateFileName + ".state"));
        	String line = reader.readLine();
        	while(!line.equals("$$End") ) {
    			if(line.startsWith("$$MapFileName")) {
        			line = reader.readLine();
     				while (line != null && !line.startsWith("$$")) {
    	  				mapFileName = line;
            			line = reader.readLine();
    				}
    			}
    			WorldMap.getInstance().loadMap(mapFileName);
    			if(line.startsWith("$$CountryProperties")) {
    				line = reader.readLine();
    				if(line.startsWith("[")) {
    					line = reader.readLine();
    				}
    				String[] csvCountryProperties = {};
    				while (line != null && !line.startsWith("$$")) {
    					csvCountryProperties = line.split("\\|");
    					List<String> properties = new ArrayList<String>();
    					properties.add(csvCountryProperties[1]);
    					properties.add(csvCountryProperties[2]);
    					properties.add(csvCountryProperties[3]);
    					countriesProperties.put(csvCountryProperties[0], properties);
    					line = reader.readLine();
    				}
    			}
        		if(line.equals("$$PlayersModel")) {
            		line = reader.readLine();
            		if(line.startsWith("$$Players")) {
                		line = reader.readLine();
                		if(line.startsWith("[")) {
                			line = reader.readLine();
                		}
                		String[] csvPlayer = {};
                		while (line != null && !line.startsWith("$$")) {		
                			 csvPlayer = line.split("\\|");
                			 String playerName = csvPlayer[0];
                			 String strategy = csvPlayer[1];
                			 Player player = Player.factory(playerName, strategy);
                			 
                			 boolean fortificatiionFinished = csvPlayer[6].equals("true") ? true: false;
                			 boolean attackFinished = csvPlayer[7].equals("true") ? true: false;
                			 boolean hasSuccessfulAttack = csvPlayer[8].equals("true") ? true: false;
                			 player.setFortificationFinished(fortificatiionFinished);
                			 player.setAttackFinished(attackFinished);
                			 player.setSuccessfulAttack(hasSuccessfulAttack);
                			 
                			 String[] continents = csvPlayer[9].replace("[","").replace("]", "").split(",");
                			 HashMap<String, Continent> playerContinents = new HashMap<>();
                			 if(!continents.toString().isEmpty()) {
                    			 for(String continentName : continents) {
                    				 continentName = CharMatcher.anyOf("\r\n\t \u00A0").trimFrom(continentName);
                    				 if(!continentName.trim().isEmpty()) {
                    					 Continent continent =  WorldMap.getInstance().getContinent(continentName);
                        				 playerContinents.put(continentName,continent); 
                    				 }        				 
                    			 } 
                			 }

                			 //player.setContinents(playerContinents);
                			 
                			 String[] countries = csvPlayer[10].replace("[","").replace("]", "").split(",");
                			 if(!countries.toString().isEmpty()) {
                    			 for(String countryName : countries) {
                    				 countryName = CharMatcher.anyOf("\r\n\t \u00A0").trimFrom(countryName);
                    				 if(!countryName.trim().isEmpty()) {
                    					 player.addCountry(WorldMap.getInstance().getCountry(countryName)); 
                    				 }        				 
                    			 }                			  
                			 }
                			 
                			 player.setUnplacedArmies(Integer.parseInt(csvPlayer[11]));
                			 player.setTotalNumberOfArmies(Integer.parseInt(csvPlayer[12]));
                			 players.add(player);
                			 line = reader.readLine();
                		}
                		currentPlayer = players.peek();
                		if(line.startsWith("$$FirstPlayer")) {
                			line = reader.readLine();
                			while (line != null && !line.startsWith("$$")) {
                				for(Player player : players) {
                					if(player.getName().equals(line)) {
                						firstPlayer = player;
                						break;
                					}
                				}
                    			line = reader.readLine();
                			}
                		}
                		if(line.startsWith("$$Battle")) {
                    		line = reader.readLine();
                    		if(line.startsWith("[")) {
                    			line = reader.readLine();
                    		}
                    		String[] csvBattle = {};
                    		while (line != null && !line.startsWith("$$")) {		
                    			csvBattle = line.split("\\|");
                    			if(currentPlayer != null) {
                    				WorldMap worldMap = WorldMap.getInstance();
                    				battle = currentPlayer.getBattle();
                    				if(battle != null && battle.getToCountry() != null && battle.getFromCountry() != null) {
                        				battle.setToCountry(worldMap.getCountry(csvBattle[0]));
                        				battle.setFromCountry(worldMap.getCountry(csvBattle[1]));
                        				battle.setWinner(worldMap.getCountry(csvBattle[2]));
                        				battle.setNumberOfAttackerDices(Integer.parseInt(csvBattle[3]));
                        				battle.setNumberOfDefenderDices(Integer.parseInt(csvBattle[4]));
                        				if(csvBattle[5].equals("Attacked")){
                        					battle.setState(BattleState.Attacked);
                        				}else if(csvBattle[5].equals("Defended")){
                        					battle.setState(BattleState.Defended);
                        				}else if(csvBattle[5].equals("Conquered")){
                         					battle.setState(BattleState.Conquered);
                        				}
                    				}
                    			}
                				line = reader.readLine();
                    		}
                		}
                		if(line.startsWith("$$PhaseModel")) {
                			line = reader.readLine();
                			if(line.equals("$$CurrentPhase")) {
                    			line = reader.readLine();
                				switch(line) {
                				case "START_UP":
                					currentPhase = PhaseTypes.START_UP;
                					break;
                				case "REINFORCEMENT":
                					currentPhase = PhaseTypes.REINFORCEMENT;
                					break;
                				case "ATTACK":
                					currentPhase = PhaseTypes.ATTACK;
                					break;
                				case "FORTIFICATION":
                					currentPhase = PhaseTypes.FORTIFICATION;
                					break;
                				default:
                					break;		
                				}
                    			line = reader.readLine();
                			}
                		}
            			if(line.startsWith("$$PhaseStatus")) {
                			line = reader.readLine();
            				while (line != null && !line.startsWith("$$")) {
            	  				if(phaseStatus != null) {
            	  					phaseStatus = phaseStatus.concat(line).concat("\n");
            	  				}
                    			line = reader.readLine();
            				}
            			}
            			if(line.startsWith("$$PhaseLog")) {
                			line = reader.readLine();
            				while (line != null && !line.startsWith("$$")) {
            					phaseLog.add(line);
                    			line = reader.readLine();
            				}
            			}
            			if(line.startsWith("$$NumberOfInitialArmies")) {
                			line = reader.readLine();
             				while (line != null && !line.startsWith("$$")) {
            	  				numberOfInitialArmies = Integer.parseInt(line);
                    			line = reader.readLine();
            				}
            			}
            			if(line.startsWith("$$AllCountriesArePopulated")) {
                			line = reader.readLine();
             				while (line != null && !line.startsWith("$$")) {
            	  				allCountriesArePopulated = line.equals("true") ? true : false;
                    			line = reader.readLine();
            				}
            			}
            		}
        		}       		
        	}
        	reader.close();
        } catch(IOException ex) {
        	throw new ValidationException(gameStateFileName + ex.getMessage());
        }
	}

}
