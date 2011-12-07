package unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BetTest.class, CardComparatorTest.class, CardTest.class,
		PlayerTest.class })
public class AllTests {

}
