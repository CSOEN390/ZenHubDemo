package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides a set of methods to read a risk map file. It also
 * implements the {@link IMapReader nan} interface to expose the map reading method
 *
 * @author Mosabbir
 */
class MapReader extends MapIO implements IMapReader {

    private final WorldMap worldMap;
    private ArrayList<String> continentList;
    private ArrayList<String> countryList;
    private BufferedReader reader;

    /**
     * Constructor takes a worldMap instance to populate it from the file
     *
     * @param worldMap
     */
    public MapReader(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Parses the .map file and load the continents, countries and borders from it
     *
     * @param filename Name of the map file with or without the extension eg. uk,
     *                 risk.map etc.
     * @return true if map file reading is successful, false if an exception occured
     * during reading
     */
    public boolean readMap(String filename) {
        try {
            final String path = getMapFilePath(filename);
            final File file = new File(path);
            String line;

            reader = new BufferedReader(new FileReader(file));
            countryList = new ArrayList<String>();
            continentList = new ArrayList<String>();

            while ((line = reader.readLine()) != null) {
                if (isContientsIdentifier(line)) {
                    readContinents();
                } else if (isCountriesIdentifier(line)) {
                    readCountries();
                } else if (isBordersIdentifier(line)) {
                    readBorders();
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Reads all continents which are under the continent section in file add them
     * to the map instance
     */
    protected void readContinents() throws IOException {
        String line;
        String[] tokens;
        while ((line = reader.readLine()) != null && line.length() > 0) {
            tokens = line.split(TOKENS_DELIMETER);
            String continentName = tokens[0];
            int continentValue = Integer.parseInt(tokens[1]);
            try {
                worldMap.addContinent(continentName, continentValue);
                continentList.add(continentName);
            } catch (ValidationException ex) {

            }
        }
    }

    /**
     * Reads all countries which are under the country section in file add them to
     * the map instance
     */
    protected void readCountries()
            throws IOException {
        String line;
        String[] tokens;
        while ((line = reader.readLine()) != null && line.length() > 0) {
            tokens = line.split(TOKENS_DELIMETER);
            String countryName = tokens[1];
            int continentIndex = Integer.parseInt(tokens[2]) - 1;
            try {
                worldMap.addCountry(countryName, continentList.get(continentIndex));
                countryList.add(countryName);
            } catch (ValidationException ex) {

            }
        }
    }

    /**
     * Reads all borders and add them as neighbors to the country
     */
    protected void readBorders() throws IOException {
        String line;
        String[] tokens;
        int countryIndex;

        while ((line = reader.readLine()) != null && line.length() > 0) {
            tokens = line.split(TOKENS_DELIMETER);
            countryIndex = Integer.parseInt(tokens[0]) - 1;
            addNeighbours(countryList.get(countryIndex), tokens);
        }
    }

    /**
     * Add the neighbor countries to the specified country
     *
     * @param countryName    The name of the country which neighbor has to added
     * @param countryBorders The name of the neighbors of the country which to be
     *                       added
     */
    private void addNeighbours(String countryName, String[] countryBorders) {
        int neighborIndex;
        for (int i = 1; i < countryBorders.length; i++) {
            neighborIndex = Integer.parseInt(countryBorders[i]) - 1;
            try {
                worldMap.addNeighbour(countryName, countryList.get(neighborIndex));
            } catch (ValidationException ex) {
            }
        }
    }
}
