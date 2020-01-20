package ca.concordia.encs.conquerdia.model.map.io;

/**
 * Map Writer interface
 */
public interface IMapWriter {

	/**
	 * write map to a file
	 *
	 * @param filename filename
	 * @return true if success
	 */
	boolean writeMap(String filename);
}
