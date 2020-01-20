import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.concordia.encs.conquerdia.controller.command.CommandTestSuite;
import ca.concordia.encs.conquerdia.model.AbstractPlayerTestSuite;
import ca.concordia.encs.conquerdia.model.map.MapTestSuite;
import ca.concordia.encs.conquerdia.model.map.io.MapIOTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ CommandTestSuite.class,
		AbstractPlayerTestSuite.class,
		MapTestSuite.class,
		MapIOTestSuite.class })

/**
 * Test suite for all the packages test suites classes
 */
public class AllTests {

}
