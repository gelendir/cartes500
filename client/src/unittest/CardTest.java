package unittest;

import junit.framework.TestCase;
import org.junit.Assert;

import game.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

public class CardTest extends TestCase {
	
	public void testConstructor() {
		Card card;
		
		try {
			card = new Card(Suit.HEARTS, CardValue.FOUR);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.DIAMONDS, CardValue.ACE);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.SPADES, CardValue.KING);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.CLUBS, CardValue.JACK);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.COLOR, CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.BLACK, CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.BLACK, CardValue.NINE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.BLACK, CardValue.EIGHT);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.BLACK, CardValue.ACE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.COLOR, CardValue.NINE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.COLOR, CardValue.EIGHT);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.COLOR, CardValue.ACE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.SPADES, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.CLUBS, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.DIAMONDS, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = new Card(Suit.HEARTS, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
	}
	
	public void testGetSuit() {
		Card card;
		
		try {
			card = new Card(Suit.HEARTS, CardValue.FOUR);
			Assert.assertEquals(card.getSuit(), Suit.HEARTS);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.DIAMONDS, CardValue.ACE);
			Assert.assertEquals(card.getSuit(), Suit.DIAMONDS);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.SPADES, CardValue.KING);
			Assert.assertEquals(card.getSuit(), Suit.SPADES);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.CLUBS, CardValue.JACK);
			Assert.assertEquals(card.getSuit(), Suit.CLUBS);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.COLOR, CardValue.JOKER);
			Assert.assertEquals(card.getSuit(), Suit.COLOR);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.BLACK, CardValue.JOKER);
			Assert.assertEquals(card.getSuit(), Suit.BLACK);
		} catch(Exception e) {
			Assert.fail();
		}
	}
	
	public void testGetCardValue() {
		Card card;
		
		try {
			card = new Card(Suit.HEARTS, CardValue.FOUR);
			Assert.assertEquals(card.getCardValue(), CardValue.FOUR);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.DIAMONDS, CardValue.ACE);
			Assert.assertEquals(card.getCardValue(), CardValue.ACE);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.SPADES, CardValue.KING);
			Assert.assertEquals(card.getCardValue(), CardValue.KING);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.CLUBS, CardValue.JACK);
			Assert.assertEquals(card.getCardValue(), CardValue.JACK);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.COLOR, CardValue.JOKER);
			Assert.assertEquals(card.getCardValue(), CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = new Card(Suit.BLACK, CardValue.JOKER);
			Assert.assertEquals(card.getCardValue(), CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
	}
}
