package ca.concordia.encs.conquerdia.model.map.io;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents the common properties and functionalities of Conquest map files
 *
 * @author Mosabbir
 */

public class ConquestMapIO {

	/**
	 * Represents the start of a comment in the map file
	 */
	public static final String COMMENT_SYMBOL = ";";

	/**
	 * Represents the Map section in the file
	 */
	public static final String MAP_SECTION_IDENTIFIER = "[Map]";

	/**
	 * Represents the start of the Continent section in the file
	 */
	public static final String CONTINENTS_SECTION_IDENTIFIER = "[Continents]";

	/**
	 * Represents the start of the Territories section in the file
	 */
	public static final String TERRITORIES_SECTION_IDENTIFIER = "[Territories]";

	/**
	 * Represents delimiter between each values of each row of Continent in the map
	 * file. e.g. Each
	 * Continent's
	 * properties are delimited by {@value #CONTINENTS_DELIMETER} in the file
	 */
	public static final String CONTINENTS_DELIMETER = "=";

	/**
	 * Represents delimiter for each entry of Territories the map file. e.g. Each
	 * Territory's
	 * properties are delimited by {@value #TERRITORIES_DELIMETER} in the file
	 */
	public static final String TERRITORIES_DELIMETER = ",";

	/**
	 * Represents the folder in which all the map file resides
	 */
	public static final String MAPS_FOLDER = "maps";

	/**
	 * Represents the absolute path to the map folder
	 */
	public static final String MAPS_FOLDER_PATH = "C:\\" + MAPS_FOLDER + "\\";

	/**
	 * Represents the extension for each map file;
	 */
	public static final String MAP_FILE_EXTENSION = ".map";

	/**
	 * Returns the absolute path to the map file residing in the
	 * {@value #MAPS_FOLDER}
	 *
	 * @param filename The name of the map file with or without extension
	 * @return The absolute path to the file
	 */
	protected static String getMapFilePath(String filename) {
		return MAPS_FOLDER_PATH + FileHelper.getFileNameWithoutExtension(filename) + MAP_FILE_EXTENSION;
	}

	/**
	 * Checks if the line in file starts the comment symbol
	 *
	 * @param line A line from the map file
	 * @return true if line starts with {@value #COMMENT_SYMBOL} otherwise false
	 */
	protected static boolean isComment(String line) {
		return line.startsWith(COMMENT_SYMBOL);
	}

	/**
	 * Checks if the line in file matches the continent tag
	 *
	 * @param line A line from the map file
	 * @return true if line is {@value #CONTINENTS_SECTION_IDENTIFIER} otherwise
	 *         false
	 */
	protected static boolean isContientsIdentifier(String line) {
		return line.equalsIgnoreCase(CONTINENTS_SECTION_IDENTIFIER);
	}

	/**
	 * Checks if the line in file matches the Territories tag
	 *
	 * @param line A line from the map file
	 * @return true if line is {@value #TERRITORIES_SECTION_IDENTIFIER} otherwise
	 *         false
	 */
	protected static boolean isTerritoriesIdentifier(String line) {
		return line.equalsIgnoreCase(TERRITORIES_SECTION_IDENTIFIER);
	}

	/**
	 * Capitalize the first character of the string if not all uppercase
	 * 
	 * @param word The word which may or may not have spaces
	 * @return if all uppercase the same string
	 */
	protected static String formatNames(String word) {
		if (StringUtils.isAllUpperCase(word))
			return word;
		return WordUtils.capitalizeFully(word, new char[] { ' ', '-' })
				.replaceAll(" ", "_");
	}

	/**
	 * Checks if the supplied file is a conquest map or not
	 * 
	 * @param mapName Name of the map file
	 * @return true if the map is a conquest map (i.e. contains
	 *         [Territories]
	 */
	public static boolean isConquestMap(String mapName) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(getMapFilePath(mapName)));
			return lines.contains(TERRITORIES_SECTION_IDENTIFIER);
		} catch (Exception ex) {
			return false;
		}
	}
}
