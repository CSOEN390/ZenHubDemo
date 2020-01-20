package ca.concordia.encs.conquerdia.model.map;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dnl.utils.text.table.TextTable;

public class MapFormattor {

	private WorldMap worldMap;
	/**
	 * The constructor for mapFormattor
	 * @param worldMap
	 */
	public MapFormattor(WorldMap worldMap) {
		this.worldMap = worldMap;
	}
	/**
	 * Default Map format 
	 * @return default Map format
	 */
	public String format() {
		return this.format(FormatType.Default);
	}
	/**
	 * Format the Map
	 * @param type type of the format
	 * @return the formatted Map
	 */
	public String format(FormatType type) {
		Set<Country> countries = worldMap.getCountries();

		String[] columnNames = getColumnNames(type);
		Object[][] data = new Object[countries.size()][columnNames.length];

		int count = 0;
		for (Country country : countries) {
			data[count] = formatCountry(country, type);
			count++;
		}
		String[][] emptyContinentRows = getRowsForEmptyContinents(worldMap.getContinents(), columnNames);

		Object[][] result = new Object[data.length + emptyContinentRows.length][];
		System.arraycopy(data, 0, result, 0, data.length);
		System.arraycopy(emptyContinentRows, 0, result, data.length, emptyContinentRows.length);
		return format(columnNames, result);
	}
	/**
	 * Format a country in the map
	 * @param country the Country to be formatted.
	 * @param type the type of the format.
	 * @return
	 */
	private String[] formatCountry(Country country, FormatType type) {
		ArrayList<String> values = new ArrayList<>();

		String neighborsAsCsv = String.join(",", country.getAdjacentCountries()
				.stream()
				.map(Country::getName)
				.collect(Collectors.toList()));

		values.add(country.getName());
		if (type == FormatType.Detail) {
			values.add(country.getNumberOfArmies() + "");
			values.add(country.getOwner() != null ? country.getOwner().getName() : "");
		}
		values.add(formatContinent(country.getContinent()));
		values.add(neighborsAsCsv);
		return values.toArray(new String[0]);
	}
	/**
	 * Gets rows for empty continents
	 * @param continents the continent
	 * @param columnNames the columnNames 
	 * @return rows for Empty Continent
	 */
	private String[][] getRowsForEmptyContinents(Set<Continent> continents, String[] columnNames) {
		int continentIndex = 0;
		for (int i = 0; i < columnNames.length; i++) {
			if (columnNames[i].toLowerCase().contains("continent")) {
				continentIndex = i;
				break;
			}
		}

		List<Continent> emptyContinents = continents.stream()
				.filter(continent -> continent.isEmpty())
				.collect(Collectors.toList());

		String[][] rows = new String[emptyContinents.size()][columnNames.length];
		for (int i = 0; i < rows.length; i++) {
			rows[i][continentIndex] = formatContinent(emptyContinents.get(i));
		}
		return rows;
	}
	/**
	 * Formats the continent.
	 * @param continent Continent to be formatted.
	 * @return formatted continent.
	 */
	private String formatContinent(Continent continent) {
		return continent.getName() + " (" + continent.getValue() + ")";
	}
	/**
	 * Get Column Names with a certain format
	 * @param type formatting type
	 * @return the formatted continent.
	 */
	private String[] getColumnNames(FormatType type) {
		ArrayList<String> columns = new ArrayList<>();
		columns.add("Country");

		if (type == FormatType.Detail) {
			columns.add("Armies");
			columns.add("Owner");
		}
		columns.add("Continent (ControlValue)");
		columns.add("Adjacent Countries");
		return columns.toArray(new String[0]);
	}
	/**
	 * @param columnNames columnNames
	 * @param data
	 * @return formatted string
	 */
	private String format(String[] columnNames, Object[][] data) {
		TextTable tt = new TextTable(columnNames, data);
		tt.setAddRowNumbering(false);
		// sort by the first column
		tt.setSort(0);
		return getTextTableString(tt, 0);
	}
	/**
	 * Gets the TextTables string
	 * @param tt Text Table
	 * @param Table print indentation
	 * @return
	 */
	private String getTextTableString(TextTable tt, int indent) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintStream ps = new PrintStream(baos, true, "UTF-8")) {
			tt.printTable(ps, indent);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		return data;
	}
	public enum FormatType {
		Default,
		Detail
	}
}
