package unittest;

import junit.framework.TestCase;
import org.junit.Assert;

import game.card.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

public class CardTest extends TestCase {
	
	public void testConstructor() {
		Card card;
		
		try {
			card = TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.SPADES, CardValue.KING);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.CLUBS, CardValue.JACK);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.BLACK, CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.BLACK, CardValue.NINE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.BLACK, CardValue.EIGHT);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.BLACK, CardValue.ACE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.COLOR, CardValue.NINE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.COLOR, CardValue.EIGHT);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.COLOR, CardValue.ACE);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.SPADES, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.CLUBS, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.DIAMONDS, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
		try {
			card = TestCardFactory.createCard(Suit.HEARTS, CardValue.JOKER);
			Assert.fail();
		} catch(Exception e) { }
		
	}
	
	public void testGetSuit() {
		Card card;
		
		try {
			card = TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR);
			Assert.assertEquals(card.getSuit(), Suit.HEARTS);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE);
			Assert.assertEquals(card.getSuit(), Suit.DIAMONDS);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.SPADES, CardValue.KING);
			Assert.assertEquals(card.getSuit(), Suit.SPADES);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.CLUBS, CardValue.JACK);
			Assert.assertEquals(card.getSuit(), Suit.CLUBS);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER);
			Assert.assertEquals(card.getSuit(), Suit.COLOR);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.BLACK, CardValue.JOKER);
			Assert.assertEquals(card.getSuit(), Suit.BLACK);
		} catch(Exception e) {
			Assert.fail();
		}
	}
	
	public void testGetCardValue() {
		Card card;
		
		try {
			card = TestCardFactory.createCard(Suit.HEARTS, CardValue.FOUR);
			Assert.assertEquals(card.getCardValue(), CardValue.FOUR);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.DIAMONDS, CardValue.ACE);
			Assert.assertEquals(card.getCardValue(), CardValue.ACE);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.SPADES, CardValue.KING);
			Assert.assertEquals(card.getCardValue(), CardValue.KING);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.CLUBS, CardValue.JACK);
			Assert.assertEquals(card.getCardValue(), CardValue.JACK);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.COLOR, CardValue.JOKER);
			Assert.assertEquals(card.getCardValue(), CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
		
		try {
			card = TestCardFactory.createCard(Suit.BLACK, CardValue.JOKER);
			Assert.assertEquals(card.getCardValue(), CardValue.JOKER);
		} catch(Exception e) {
			Assert.fail();
		}
	}
}
