package unittest;

import exception.GameException;
import game.Bet;
import game.Game;
import game.Player;
import game.card.Deck;
import game.enumeration.Suit;

import java.util.ArrayList;

import junit.framework.TestCase;
import org.junit.Assert;

public class GameTest extends TestCase {

	public void testGetSetPlayers() throws GameException {

		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred" + i);
		}
		Game game = new Game( players, new Deck() );
		
		Player gamePlayers[] = game.getPlayers();
		for(int i = 0; i < players.length; ++i) {
			Assert.assertTrue(gamePlayers[i].equals(players[i]));
		}
	}

	public void testGetGameSuit() throws Exception {

		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred" + i);
		}
		Game game = new Game( players, new Deck() );
		
		game.setBet(new Bet(6, Suit.HEARTS), players[0]);
		game.setBet(new Bet(7, Suit.SPADES), players[1]);
		game.setBet(null, players[2]);
		game.setBet(new Bet(7, Suit.DIAMONDS), players[3]);
		
		Assert.assertTrue(game.getGameSuit().equals(Suit.DIAMONDS));
	}

	public void testGetBestPlayerBet() throws Exception {

		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred" + i);
		}
		Game game = new Game( players, new Deck() );
		
		game.setBet(new Bet(6, Suit.HEARTS), players[0]);
		game.setBet(new Bet(7, Suit.SPADES), players[1]);
		game.setBet(new Bet(7, Suit.DIAMONDS), players[2]);
		game.setBet(null, players[3]);
				
		Assert.assertTrue(game.getBestPlayerBet().equals(players[2]));
	}

	public void testGetPlayableBets() throws Exception {
		ArrayList<Bet> bets;

		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred" + i);
		}
		Game game = new Game( players, new Deck() );
		
		bets = game.getPlayableBets(players[0]);
		Assert.assertEquals(bets.size(), 25);
		Assert.assertTrue(bets.get(0).equals(new Bet(6, Suit.SPADES)));
		Assert.assertTrue(bets.get(bets.size() - 1).equals(new Bet(10, Suit.NONE)));
		game.setBet(new Bet(6, Suit.HEARTS), players[0]);
		
		bets = game.getPlayableBets(players[1]);
		Assert.assertEquals(bets.size(), 21);
		Assert.assertTrue(bets.get(0).equals(new Bet(6, Suit.NONE)));
		Assert.assertTrue(bets.get(bets.size() - 1).equals(new Bet(10, Suit.NONE)));
		game.setBet(new Bet(7, Suit.SPADES), players[1]);
		
		bets = game.getPlayableBets(players[2]);
		Assert.assertEquals(bets.size(), 19);
		Assert.assertTrue(bets.get(0).equals(new Bet(7, Suit.CLUBS)));
		Assert.assertTrue(bets.get(bets.size() - 1).equals(new Bet(10, Suit.NONE)));
		game.setBet(new Bet(10, Suit.NONE), players[2]);
		
		bets = game.getPlayableBets(players[3]);
		Assert.assertEquals(bets.size(), 0);
		game.setBet(null, players[3]);
	}

	public void testSetBet() throws Exception {

		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred" + i);
		}
		Game game = new Game( players, new Deck() );
		
		Assert.assertTrue(game.setBet(new Bet(6, Suit.HEARTS), players[0]));
		Assert.assertFalse(game.setBet(new Bet(6, Suit.SPADES), players[1]));
		Assert.assertTrue(game.setBet(new Bet(7, Suit.SPADES), players[1]));
		Assert.assertTrue(game.setBet(new Bet(10, Suit.NONE), players[2]));
		Assert.assertTrue(game.setBet(null, players[3]));
	}

	public void testIsValidBet() throws Exception {

		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred" + i);
		}
		Game game = new Game( players, new Deck() );
		
		Assert.assertTrue(game.isValidBet(new Bet(6, Suit.HEARTS), players[0]));
		game.setBet(new Bet(6, Suit.HEARTS), players[0]);
		Assert.assertFalse(game.isValidBet(new Bet(6, Suit.SPADES), players[1]));
		Assert.assertTrue(game.isValidBet(new Bet(7, Suit.SPADES), players[1]));
		game.setBet(new Bet(7, Suit.SPADES), players[1]);
		Assert.assertTrue(game.isValidBet(new Bet(10, Suit.NONE), players[2]));
		game.setBet(new Bet(10, Suit.NONE), players[2]);
		Assert.assertTrue(game.isValidBet(null, players[3]));
		game.setBet(null, players[3]);
	}

}
