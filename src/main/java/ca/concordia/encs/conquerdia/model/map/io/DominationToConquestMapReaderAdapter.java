package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Adapter for conquest map
 * 
 * @author Mosabbir
 *
 */
public class DominationToConquestMapReaderAdapter extends MapReader {

	/**
	 * Reader for conquest
	 */
	private final ConquestMapReader conquestMapReader;

	/**
	 * Constructor Takes a world map and the conquest reader which alllows the
	 * interface to adapt readMap() to call conquestMapReader.read()
	 * 
	 * @param worldMap          The world map object to be populated
	 * @param conquestMapReader The reader to read conquest map files
	 */
	public DominationToConquestMapReaderAdapter(WorldMap worldMap,
			ConquestMapReader conquestMapReader) {
		super(worldMap);
		this.conquestMapReader = conquestMapReader;
	}

	/**
	 * This adapts the MapReader.readMap() interface to call
	 * ConquestMapReader.read() method
	 */
	@Override
	public boolean readMap(String filename) {
		return conquestMapReader.read(filename);
	}
}
