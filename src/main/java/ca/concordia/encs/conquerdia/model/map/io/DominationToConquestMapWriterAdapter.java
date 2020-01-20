package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Adapter for conquest map which has a different write map signature than the
 * MapWriter
 */
class DominationToConquestMapWriterAdapter extends MapWriter {

	/**
	 * Writer for conquest map
	 */
	private final ConquestMapWriter conquestMapWriter;

	/**
	 * Constructor takes the worldmap and the writer
	 * 
	 * @param worldMap
	 * @param conquestMapWriter
	 */
	DominationToConquestMapWriterAdapter(WorldMap worldMap, ConquestMapWriter conquestMapWriter) {
		super(worldMap);
		this.conquestMapWriter = conquestMapWriter;
	}

	/**
	 * This adapts the MapWriter.write() method to call the
	 * conquestMapWriter.write() method
	 */
	@Override
	public boolean writeMap(String filename) {
		return conquestMapWriter.write(filename, worldMap);
	}
}
