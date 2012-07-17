package unittest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AppTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Result result;
		
		result = JUnitCore.runClasses(BetTest.class);
		System.out.println("Classe Bet: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Bet: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(CardComparatorTest.class);
		System.out.println("Classe CardComparator: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe CardComparator: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(CardTest.class);
		System.out.println("Classe Card: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Card: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(DeckTest.class);
		System.out.println("Classe Deck: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Deck: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(GameTest.class);
		System.out.println("Classe Game: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Game: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(HandTest.class);
		System.out.println("Classe Hand: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Hand: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(PlayerTest.class);
		System.out.println("Classe Player: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Player: " + failure.toString());
		}
		
		result = JUnitCore.runClasses(TurnTest.class);
		System.out.println("Classe Turn: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Turn: " + failure.toString());
		}
	}

}
