package ca.concordia.encs.conquerdia.model.io;

public abstract class GameStateBuilder {
    /**
     * Product to be constructed by the builder
     */
    protected GameState stateProduct;
    
    /**
     * Get the constructed GameState from the Builder
     */
    public GameState getGameState() {
        return stateProduct;
    }

    /**
     * Create a new unspecified GameState that
     * will be eventually built by calling the
     * following abstract methods in a concrete
     * class derived from the GameStateBuilder class
     */
    public void createNewGameState() {
    	stateProduct = new GameState();
    }
    abstract void buildPlayersModel();
	abstract void buildPhaseModel();
	abstract void buildCountries();
}
