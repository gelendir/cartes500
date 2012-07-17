package unittest;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import game.card.Card;
import game.card.Deck;
import junit.framework.TestCase;
import org.junit.Assert;

public class DeckTest extends TestCase {

	public void testConstructor() {
		Deck d = new Deck();
		Stack<Card> cards = d.getDeck();
		Assert.assertEquals(cards.size(), 46);

		Set<Card> set = new HashSet<Card>();
		for(int i = 0; i < 46; ++i) {
			Assert.assertTrue(set.add(cards.pop()));
		}
	}

	public void testMixCards() {
		Deck d = new Deck();
		d.mixCards();
		Stack<Card> cards = d.getDeck();
		Assert.assertEquals(cards.size(), 46);

		Set<Card> set = new HashSet<Card>();
		for(int i = 0; i < 46; ++i) {
			Assert.assertTrue(set.add(cards.pop()));
		}
	}
	
	public void testTakeCard() {
		Deck d = new Deck();
		Set<Card> set = new HashSet<Card>();
		for(int i = 0; i < 46; ++i) {
			Assert.assertTrue(set.add(d.takeCard()));
		}
	}
	
	public void testIsEmpty() {
		Deck d = new Deck();
		for(int i = 0; i < 45; ++i) {
			d.takeCard();
			Assert.assertFalse(d.isEmpty());
		}
		
		d.takeCard();
		Assert.assertTrue(d.isEmpty());
	}
}
