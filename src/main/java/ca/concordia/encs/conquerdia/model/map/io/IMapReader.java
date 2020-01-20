package ca.concordia.encs.conquerdia.model.map.io;

/**
 * Map Reader interface
 */
public interface IMapReader {

	/**
	 * @param filename filename
	 * @return result
	 */
	boolean readMap(String filename);
}
