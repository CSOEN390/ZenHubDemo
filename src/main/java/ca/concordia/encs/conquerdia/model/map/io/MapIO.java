package ca.concordia.encs.conquerdia.model.map.io;

/**
 * Represents the common properties and functionalities of map files
 *
 * @author Mosabbir
 */
public abstract class MapIO {
    /**
     * Represents the start of a comment in the map file
     */
    public static final String COMMENT_SYMBOL = ";";

    /**
     * Represents the start of the Continent section in the file
     */
    public static final String CONTINENTS_SECTION_IDENTIFIER = "[continents]";

    /**
     * Represents the start of the Countries section in the file
     */
    public static final String COUNTRIES_SECTION_IDENTIFIER = "[countries]";

    /**
     * Represents the start of the Borders section in the file
     */
    public static final String BORDERS_SECTION_IDENTIFIER = "[borders]";

    /**
     * Represents the start of the files section in the map file
     */
    public static final String FILES_SECTION_IDENTIFIER = "[files]";

    /**
     * Represents delimiter for each tokens in the map file. e.g. Each country's
     * properties are decimated by {@value #TOKENS_DELIMETER} in the file
     */
    public static final String TOKENS_DELIMETER = " ";

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
     * false
     */
    protected static boolean isContientsIdentifier(String line) {
        return line.equalsIgnoreCase(CONTINENTS_SECTION_IDENTIFIER);
    }

    /**
     * Checks if the line in file matches the countries tag
     *
     * @param line A line from the map file
     * @return true if line is {@value #COUNTRIES_SECTION_IDENTIFIER} otherwise
     * false
     */
    protected static boolean isCountriesIdentifier(String line) {
        return line.equalsIgnoreCase(COUNTRIES_SECTION_IDENTIFIER);
    }

    /**
     * Checks if the line in file matches the borders tag
     *
     * @param line A line from the map file
     * @return true if line is {@value #BORDERS_SECTION_IDENTIFIER} otherwise false
     */
    protected static boolean isBordersIdentifier(String line) {
        return line.equalsIgnoreCase(BORDERS_SECTION_IDENTIFIER);
    }
}
