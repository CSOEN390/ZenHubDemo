package ca.concordia.encs.conquerdia.model.map.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Conquest Map Writer
 */
class ConquestMapWriter extends ConquestMapIO {

	/**
	 * Format of each continent
	 */
	static final String CONTINENT_ROW_FORMAT = "%s" + CONTINENTS_DELIMETER + "%d";

	/**
	 * Writer for the file
	 */
	protected BufferedWriter writer;

	/**
	 * Writes the world map to the specified file
	 * 
	 * @param filename of the map in which the world map has to be saved
	 * @param worldMap Map to be saved
	 * @return True if save is successful otherwise false
	 */
	public boolean write(String filename, WorldMap worldMap) {
		ArrayList<Country> countries;

		try {
			final String mapName = FileHelper.getFileNameWithoutExtension(filename);
			final String path = ConquestMapIO.getMapFilePath(filename);

			writer = new BufferedWriter(new FileWriter(path));

			// Write [Map]
			writeSection(MAP_SECTION_IDENTIFIER, null);
			writer.newLine();

			writeContinents(worldMap.getContinents());
			writer.newLine();

			countries = getAllCountries(worldMap.getContinents());
			writeTerritories(countries);
			writer.newLine();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	/**
	 * Writes Territories
	 * 
	 * @param row for Territory
	 */
	private void writeTerritories(ArrayList<Country> countries) throws IOException {
		ArrayList<String> rows = new ArrayList<>();
		StringBuilder rowBuilder = new StringBuilder();

		// Each Territory has this format Kittson,67,77,Minnesota,Roseau,Marshall
		for (Country country : countries) {
			rowBuilder.setLength(0);
			rowBuilder.append(country.getName());

			// Save the unused coordinates
			rowBuilder.append(TERRITORIES_DELIMETER);
			rowBuilder.append("0");
			rowBuilder.append(TERRITORIES_DELIMETER);
			rowBuilder.append("0");

			// Append the continent
			rowBuilder.append(TERRITORIES_DELIMETER);
			rowBuilder.append(country.getContinent().getName());

			// Append the adjacency
			for (Country neighbor : country.getAdjacentCountries()) {
				rowBuilder.append(TERRITORIES_DELIMETER);
				rowBuilder.append(neighbor.getName());
			}

			rows.add(rowBuilder.toString());
		}

		writeSection(TERRITORIES_SECTION_IDENTIFIER, rows.stream().toArray(String[]::new));
	}

	/**
	 * Writes the continents to the file
	 */
	private void writeContinents(Set<Continent> continents) throws IOException {
		String[] rows = new String[continents.size()];
		int i = 0;
		for (Continent continent : continents) {
			rows[i++] = String.format(CONTINENT_ROW_FORMAT,
					continent.getName(),
					continent.getValue());
		}
		writeSection(CONTINENTS_SECTION_IDENTIFIER, rows);
	}

	/**
	 * Writes a section like countries and its rows
	 * 
	 * @param sectionIdentifier The section identifier
	 * @param rows              The rows for the sections
	 */
	private void writeSection(String sectionIdentifier, String[] rows) throws IOException {
		writeLine(sectionIdentifier);
		if (rows != null) {
			for (String row : rows) {
				writeLine(row);
			}
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
	 * @param continent The continents
	 * @return Collection of Unique Countries
	 */
	private ArrayList<Country> getAllCountries(Set<Continent> continents) {
		ArrayList<Country> allCountries = new ArrayList<Country>();

		for (Continent continent : continents) {
			for (Country country : continent.getCountries()) {
				allCountries.add(country);
			}
		}
		return allCountries;
	}
}
