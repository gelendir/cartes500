package unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BetTest.class, CardComparatorTest.class, CardTest.class,
		DeckTest.class, GameTest.class, HandTest.class, PlayerTest.class,
		TurnTest.class })
public class AllTests {

}
