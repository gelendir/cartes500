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
		
		result = JUnitCore.runClasses(PlayerTest.class);
		System.out.println("Classe Player: " + result.getRunCount() + " tests réussis.");
		for (Failure failure : result.getFailures())
		{
			System.out.println("Classe Player: " + failure.toString());
		}
		
	}

}
