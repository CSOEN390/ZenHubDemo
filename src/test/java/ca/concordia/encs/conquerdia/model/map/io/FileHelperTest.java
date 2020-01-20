package ca.concordia.encs.conquerdia.model.map.io;

import static org.junit.Assert.assertEquals;

import ca.concordia.encs.conquerdia.model.map.io.FileHelper;
import org.junit.Test;

/**
 * Test class to test the functionalities of {@link FileHelper} class
 */
public class FileHelperTest {

	/**
	 * Checks the extraction of filename method
	 */
	@Test
	public void testFileNameWithoutExtension() {
		assertEquals(FileHelper.getFileNameWithoutExtension("uk.mp"), "uk");
	}
}
