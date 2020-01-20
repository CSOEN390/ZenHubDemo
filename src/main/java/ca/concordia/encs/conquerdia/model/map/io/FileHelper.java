package ca.concordia.encs.conquerdia.model.map.io;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

/**
 * Provides various helper method for storage system
 *
 * @author Mosabbir
 */
public class FileHelper {

	/**
	 * Check if the file exists
	 * 
	 * @param path The path to the file
	 * @return true if exits otherwise false
	 */
	public static boolean exists(String path) {
		try {
			return new File(path).exists();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Returns the absolute path of the resource
	 *
	 * @param resource The resource file name
	 * @return The full path to the resource
	 */
	public static String getResourcePath(String resource) {
		return ClassLoader.getSystemClassLoader().getResource(resource).getPath();
	}

	/**
	 * Combines the specified path and returns the absolute path to the file
	 *
	 * @param path A file or a relative path
	 * @return Absolute path to the path
	 */
	public static String getAbsolutePath(String path) {
		return Paths.get(getRootPath(), path).toString();
	}

	/**
	 * Returns the AbsolutePath to the root folder
	 *
	 * @return AbsolutePath to the root folder
	 */
	public static String getRootPath() {
		return Paths.get("").toAbsolutePath().toString();
	}

	/**
	 * Combines two strings with a separator
	 *
	 * @param parent The name of the parent folder
	 * @param child  the name of the folder or file which is under the parent
	 * @return The combination of parent and child forming a path
	 */
	public static String combinePath(String parent, String child) {
		return parent + "/" + child;
	}

	/**
	 * Gets the base name, minus the full path and extension, from a full filename.
	 * <p>
	 * This method will handle a file in either Unix or Windows format.The text
	 * after the last forward or backslash and before the last dot is returned.
	 * <p>
	 * The output will be the same irrespective of the machine that the code is
	 * running on.
	 *
	 * @param filename the filename to query, null returns null
	 * @return the name of the file without the path, or an empty string if none
	 *         exists
	 */
	public static String getFileNameWithoutExtension(String filename) {
		return FilenameUtils.getBaseName(filename);
	}

	/**
	 * Creates a directory if not exists
	 *
	 * @param directoryPath The path to the directory
	 */
	public static void CreateDirectoryIfNotExists(String directoryPath) {
		try {
			File dir = new File(directoryPath);
			if (!dir.exists())
				dir.mkdirs();
		} catch (Exception ex) {
		}
	}
}
