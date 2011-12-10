package unittest;

import java.util.ArrayList;

import game.Hand;
import game.card.Card;
import game.card.Deck;
import game.enumeration.CardValue;
import game.enumeration.Suit;
import junit.framework.TestCase;
import org.junit.Assert;

public class HandTest extends TestCase {

	public void testHand() {
		Deck d = new Deck();
		for(int i = 0; i < 4; ++i) {
			try {
				Hand h = new Hand(d);
			} catch (Exception e) {
				Assert.fail();
			}
		}
		
		try {
			Hand h = new Hand(d);
			Assert.fail();
		} catch (Exception e) {
			
		}
	}

	public void testGetSetCards() throws Exception {
		Deck d = new Deck();
		Hand h = new Hand(d);
		ArrayList<Card> cards = new ArrayList<Card>(10);
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.NINE));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.SIX));
		cards.add(TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.TWO));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.NINE));
		h.setCards(cards);
		
		Card[] tabCards = h.getCards();
		for(int i = 0; i < tabCards.length; ++i) {
			boolean ret = cards.contains(tabCards[i]);
			Assert.assertTrue(ret);
			if(ret) {
				cards.remove(tabCards[i]);
			}
		}
	}

	public void testPlayCard() throws Exception {
		Deck d = new Deck();
		Hand h = new Hand(d);
		ArrayList<Card> cards = new ArrayList<Card>(10);
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.NINE));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.SIX));
		cards.add(TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.TWO));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.NINE));
		h.setCards(cards);
		
		Assert.assertTrue(h.playCard(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT)));
		Assert.assertFalse(h.playCard(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT)));
		
		Assert.assertTrue(h.playCard(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING)));
		Assert.assertFalse(h.playCard(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING)));
		
		cards.remove(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT));
		cards.remove(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING));
		
		for(int i = 0; i < 8; ++i) {
			Assert.assertTrue(h.playCard(cards.get(i)));
			Assert.assertFalse(h.playCard(cards.get(i)));
		}
	}

	public void testGetPlayableCard() throws Exception {
		Deck d = new Deck();
		Hand h = new Hand(d);
		ArrayList<Card> cards = new ArrayList<Card>(10);
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.NINE));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.SIX));
		cards.add(TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.TWO));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.NINE));
		h.setCards(cards);
		
		ArrayList<Card> playables;
		
		playables = h.getPlayableCard(Suit.NONE);
		Assert.assertEquals(playables.size(), 10);
		
		playables = h.getPlayableCard(Suit.HEARTS);
		Assert.assertEquals(playables.size(), 2);
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR)));
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.HEARTS, CardValue.NINE)));
		
		h.playCard(TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR));
		h.playCard(TestCardFactory.createCard(Suit.HEARTS, CardValue.NINE));
		
		playables = h.getPlayableCard(Suit.HEARTS);
		Assert.assertEquals(playables.size(), 8);
		
		playables = h.getPlayableCard(Suit.SPADES);
		Assert.assertEquals(playables.size(), 3);
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT)));
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.SPADES, CardValue.NINE)));
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.SPADES, CardValue.TWO)));
		
		playables = h.getPlayableCard(Suit.DIAMONDS);
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE)));
		Assert.assertTrue(playables.contains(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING)));
		
		playables = h.getPlayableCard(Suit.HEARTS);
		Assert.assertEquals(playables.size(), 8);
	}

	public void testGetNumberOfCard() throws Exception {
		Card[] playables;
		Deck d = new Deck();
		Hand h = new Hand(d);
				
		Assert.assertEquals(h.getNumberOfCard(), 10);
		for(int i = 9; i >= 0; --i) {
			playables = h.getCards();
			h.playCard(playables[0]);
			Assert.assertEquals(h.getNumberOfCard(), i);
		}
		
		ArrayList<Card> cards = new ArrayList<Card>(10);
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.EIGHT));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.NINE));
		cards.add(TestCardFactory.createCard(Suit.DIAMONDS, CardValue.KING));
		cards.add(TestCardFactory.createCard(Suit.CLUBS, CardValue.SIX));
		cards.add(TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER));
		cards.add(TestCardFactory.createCard(Suit.SPADES, CardValue.TWO));
		cards.add(TestCardFactory.createCard(Suit.HEARTS, CardValue.NINE));
		h.setCards(cards);
		
		Assert.assertEquals(h.getNumberOfCard(), 10);
		for(int i = 9; i >= 0; --i) {
			playables = h.getCards();
			h.playCard(playables[0]);
			Assert.assertEquals(h.getNumberOfCard(), i);
		}
	}

}
