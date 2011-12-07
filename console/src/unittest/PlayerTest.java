package unittest;

import game.Bet;
import game.Hand;
import game.Player;
import game.enumeration.Suit;
import junit.framework.TestCase;
import org.junit.Assert;

public class PlayerTest extends TestCase {

	public final void testGetName() {
		Player player = new Player("Fred");
		Assert.assertEquals(player.getName(), "Fred");
	}

	public final void testGetSetOriginalBet() throws Exception {
		Bet bet = new Bet(6, Suit.HEARTS);
		Player player = new Player("Fred");
		player.setOriginalBet(bet);
		Assert.assertTrue(player.getOriginalBet().equals(bet));
	}

	public final void testAddGetTurnWin() {
		Player player = new Player("Fred");
		for(int i = 0; i <= 10; ++i) {
			Assert.assertEquals(player.getTurnWin(), i);
			player.addTurnWin();
		}
	}

}
