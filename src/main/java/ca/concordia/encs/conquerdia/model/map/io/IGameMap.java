package ca.concordia.encs.conquerdia.model.map.io;

/**
 * Abstraction for file operation on game map
 * @author K
 *
 */
public interface IGameMap {
	/**
	 * @param filename name of the map file
	 * @return result
	 */
	boolean loadFrom(String filename);

	/**
	 * write map to a file
	 *
	 * @param filename filename
	 * @return true if success
	 */
	boolean saveTo(String filename);
}
