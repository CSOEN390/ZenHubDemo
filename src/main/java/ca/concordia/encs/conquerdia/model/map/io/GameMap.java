package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Concrete implementation of game map operations
 */
public class GameMap implements IGameMap {
	/**
	 * The worldmap that needs to be populated
	 */
	private final WorldMap worldMap;

	/**
	 * Constructor
	 * 
	 * @param worldMap The worldmap that needs to be populated
	 */
	public GameMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	/**
	 * Loads the map from the file and populates the worldmap object
	 */
	@Override
	public boolean loadFrom(String filename) {
		IMapReader reader;
		if (isConquestMapFile(filename)) {
			reader = new DominationToConquestMapReaderAdapter(worldMap,
					new ConquestMapReader(worldMap));
		} else {
			reader = new MapReader(worldMap);
		}

		worldMap.clearData();
		return reader.readMap(filename);
	}

	/**
	 * Save the map to the specified file
	 */
	@Override
	public boolean saveTo(String filename) {
		IMapWriter writer;

		if (isConquestMapFile(filename)) {
			writer = new DominationToConquestMapWriterAdapter(worldMap,
					new ConquestMapWriter());
		} else {
			writer = new MapWriter(worldMap);
		}

		return writer.writeMap(filename);
	}

	/**
	 * Checks if the map is a conquest file or not
	 * 
	 * @param filename
	 * @return
	 */
	private boolean isConquestMapFile(String filename) {
		return ConquestMapIO.isConquestMap(filename);
	}
}
