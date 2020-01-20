package ca.concordia.encs.conquerdia.model.map.io;

/**
 * A helper class to read and write to the map file
 * 
 * @author Mosabbir
 *
 */
class CountryRow {
	/**
	 * Delimiter for the tokens
	 */
	private static final String delimeter = MapIO.TOKENS_DELIMETER;

	/**
	 * Number of the country
	 */
	private int number;

	/**
	 * Name of the country
	 */
	private String name;

	/**
	 * Continent number
	 */
	private int continentNumber;

	/**
	 * Position in x coord
	 */
	private int xPosition;

	/**
	 * Position in y coord
	 */
	private int yPosition;

	/**
	 * Adjacent countrynames
	 */
	private String[] adjacentCountryNames;

	/**
	 * Constructor sets the number and continent name
	 * 
	 * @param number          The number of the country
	 * @param name            The name of the country
	 * @param continentNumber The number of the continent the country belongs to
	 */
	public CountryRow(int number, String name, int continentNumber) {
		this.number = number;
		this.name = name;
		this.continentNumber = continentNumber;
	}

	/**
	 * Returns the country no
	 * 
	 * @return The number of the country
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Returns the country name
	 * 
	 * @return The name of the country
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number of the continent it belongs to
	 * 
	 * @return The number of the continent
	 */
	public int getContinentNumber() {
		return continentNumber;
	}

	/**
	 * Sets the continent number of this country
	 * 
	 * @param continentNumber The continent number
	 */
	public void setContinentNumber(int continentNumber) {
		this.continentNumber = continentNumber;
	}

	/**
	 * Returns the map file representation of this country
	 * 
	 * @return A country representation similar to the map
	 */
	@Override
	public String toString() {

		return number + delimeter + name + delimeter + continentNumber + delimeter + xPosition
				+ delimeter + yPosition;
	}

	/**
	 * Gets the y coordinate
	 * 
	 * @return The y position
	 */
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * Sets the y position
	 * 
	 * @param yPosition The y position
	 */
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * Gets the x coordinate
	 * 
	 * @return The x position
	 */
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * Sets the x position
	 * 
	 * @param xPosition The x position
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Returns the name of the neighbor countries of this country
	 * 
	 * @return The name of the neighbor countries of this country
	 */
	public String[] getAdjacentCountryNames() {
		return adjacentCountryNames;
	}

	/**
	 * Sets the name of the adjacent country names
	 * 
	 * @param adjacentCountryNames The name of the adjacent countries of this
	 *                             country
	 */
	public void setAdjacentCountryNames(String[] adjacentCountryNames) {
		this.adjacentCountryNames = adjacentCountryNames;
	}
}
