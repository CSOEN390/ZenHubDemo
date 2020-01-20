package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Map Writer class
 */
class MapWriter extends MapIO implements IMapWriter {

	static final String CONTINENT_ROW_FORMAT = "%s" + TOKENS_DELIMETER + "%s" + TOKENS_DELIMETER + "%s";
	protected final WorldMap worldMap;
	protected BufferedWriter writer;

	/**
	 * Constructor takes the world map to be written
	 * 
	 * @param worldMap WorldMap to be written
	 */
	public MapWriter(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	/**
	 * Writes the world map to the specified file
	 * 
	 * @param filename of the map in which the world map has to be saved
	 * @return True if save is successful otherwise false
	 */
	public boolean writeMap(String filename) {
		String[] borderRows;
		ArrayList<CountryRow> countryRows;

		try {
			final String mapName = FileHelper.getFileNameWithoutExtension(filename);
			final String path = MapIO.getMapFilePath(filename);

			writer = new BufferedWriter(new FileWriter(path));

			writeComments(new String[] { "RISK MAP", "Conquerdia Map Editor" });
			writer.newLine();

			writeResourceFiles(new String[] { "pic " + mapName + "_pic.png",
					"map " + mapName + "_map.gif",
					"crd card.cards", "prv " + mapName + ".jpg" });
			writer.newLine();

			writeMapName(mapName.toUpperCase());
			writer.newLine();

			writeContinents();
			writer.newLine();

			countryRows = getAllCountryRows(new ArrayList<Continent>(worldMap.getContinents()));
			writeCountries(countryRows);
			writer.newLine();

			borderRows = getBorders(countryRows);
			writeBorders(borderRows);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
				}
			}
		}

		return true;
	}

	/**
	 * Writes borders to the file
	 * 
	 * @param borderRows the borders
	 */
	private void writeBorders(String[] borderRows) throws IOException {
		writeSection(BORDERS_SECTION_IDENTIFIER, borderRows);
	}

	/**
	 * Writes country rows to the file
	 * 
	 * @param countryRows
	 */
	private void writeCountries(ArrayList<CountryRow> countryRows) throws IOException {
		String[] rows = countryRows.stream()
				.map(CountryRow::toString)
				.toArray(String[]::new);
		writeSection(COUNTRIES_SECTION_IDENTIFIER, rows);
	}

	/**
	 * Writes the continents to the file
	 */
	private void writeContinents() throws IOException {
		Set<Continent> continents = worldMap.getContinents();
		String[] rows = new String[continents.size()];
		int i = 0;
		for (Continent continent : continents) {
			rows[i++] = String.format(CONTINENT_ROW_FORMAT, continent.getName(), continent.getValue(), "Black");
		}
		writeSection(CONTINENTS_SECTION_IDENTIFIER, rows);
	}

	/**
	 * Writes resource sections
	 * 
	 * @param files the name of the resource files
	 */
	private void writeResourceFiles(String[] files) throws IOException {
		writeSection(FILES_SECTION_IDENTIFIER, files);
	}

	/**
	 * Writes the name of the map to the file in name {@code mapName} Map
	 *
	 * @param mapName The name of the map
	 * @throws IOException
	 */
	private void writeMapName(String mapName) throws IOException {
		writeLine("name " + mapName + " map");
	}

	/**
	 * Write comments to the map file
	 * 
	 * @param comments Comments of the map
	 */
	private void writeComments(String[] comments) throws IOException {
		for (String comment : comments) {
			writeLine(COMMENT_SYMBOL + " " + comment);
		}
	}

	/**
	 * Writes a section like countries and its rows
	 * 
	 * @param sectionIdentifier The section identifier
	 * @param rows              The rows for the sections
	 */
	private void writeSection(String sectionIdentifier, String[] rows) throws IOException {
		writeLine(sectionIdentifier);
		for (String row : rows) {
			writeLine(row);
		}
	}

	/**
	 * Helper method to write a single line and then providing a new line
	 * 
	 * @param line The line
	 */
	private void writeLine(String line) throws IOException {
		writer.write(line);
		writer.newLine();
	}

	/**
	 * Returns all the countries
	 * 
	 * @param continents The continents
	 * @return Collection of CountryRows
	 */
	private ArrayList<CountryRow> getAllCountryRows(ArrayList<Continent> continents) {
		ArrayList<CountryRow> rows = new ArrayList<CountryRow>();
		Set<Country> countries;
		CountryRow countryRow;
		int countryNumber = 0;

		for (int i = 0; i < continents.size(); i++) {
			countries = continents.get(i).getCountries();

			for (Country country : countries) {
				countryNumber++;
				countryRow = countryRowFromCountry(country, countryNumber, i + 1);
				rows.add(countryRow);
			}
		}
		return rows;
	}

	/**
	 * Get all the borders
	 * 
	 * @param countryRows The country rows list
	 * @return Collection of borders
	 */
	private String[] getBorders(ArrayList<CountryRow> countryRows) {
		String[] borders = new String[countryRows.size()];
		CountryRow countryRow;
		HashMap<String, Integer> countryNumbers = new HashMap<>();
		StringBuilder builder;
		countryNumbers = getCountryNumbers(countryRows);

		for (int i = 0; i < countryRows.size(); i++) {
			builder = new StringBuilder();
			countryRow = countryRows.get(i);
			builder.append(countryRow.getNumber());

			for (String countryName : countryRow.getAdjacentCountryNames()) {
				builder.append(TOKENS_DELIMETER + countryNumbers.get(countryName));
			}
			borders[i] = builder.toString();
		}
		return borders;
	}

	/**
	 * Generates country number for each country
	 * 
	 * @param countryRows Country rows
	 * @return country number for each country name in a hashmap
	 */
	private HashMap<String, Integer> getCountryNumbers(ArrayList<CountryRow> countryRows) {
		HashMap<String, Integer> countryNumbers = new HashMap<>();
		CountryRow countryRow;
		for (int i = 0; i < countryRows.size(); i++) {
			countryRow = countryRows.get(i);
			countryNumbers.put(countryRow.getName(), countryRow.getNumber());
		}
		return countryNumbers;
	}

	/**
	 * Convert the country game object to the map file country format by storing it
	 * to CountryRow object
	 * 
	 * @param country         The Country object
	 * @param number          The number of the country
	 * @param continentNumber The number of the continent
	 * @return CountryRow object created from the country object
	 */
	private CountryRow countryRowFromCountry(Country country, int number, int continentNumber) {
		CountryRow countryRow = new CountryRow(number, country.getName(), continentNumber);
		countryRow.setAdjacentCountryNames(country.getAdjacentCountries()
				.stream()
				.map(Country::getName)
				.toArray(String[]::new));

		return countryRow;
	}
}
