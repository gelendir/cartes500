package unittest;

import junit.framework.TestCase;

import game.card.Card;
import game.card.CardComparator;
import game.enumeration.Suit;
import game.enumeration.CardValue;

public class CardComparatorTest extends TestCase {

	private CardComparator c;

	protected void setUp() throws Exception {
		super.setUp();
		this.c = new CardComparator();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSmallCardHighCard() throws Exception  {
		
		Card low = new Card( Suit.NONE, CardValue.FOUR );
		Card high = new Card( Suit.NONE, CardValue.EIGHT );
		
		assertEquals( this.c.compare( low, high ), -1 );
		
	}
	
	public void testHighCardSmallCard() throws Exception {
		
		Card low = new Card( Suit.NONE, CardValue.FOUR );
		Card high = new Card( Suit.NONE, CardValue.NINE );
		
		assertEquals( this.c.compare( high, low ), 1 );
		
	}
	
	public void testSameCardDifferentSuit() throws Exception {
		
		Card card1 = new Card( Suit.CLUBS, CardValue.SIX );
		Card card2 = new Card( Suit.HEARTS, CardValue.SIX );
		
		assertEquals( this.c.compare( card1, card2 ), 0 );
		
	}
	
	public void testAceHigherThanFour() throws Exception {
		
		Card ace = new Card( Suit.DIAMONDS, CardValue.ACE );
		Card four = new Card( Suit.SPADES, CardValue.FOUR );
		
		assertEquals( this.c.compare( ace, four ), 1 );
	}
	
	public void testJokerHigherThanFour() throws Exception {
		
		Card joker = new Card( Suit.COLOR, CardValue.JOKER );
		Card four = new Card( Suit.SPADES, CardValue.FOUR );
		
		assertEquals( this.c.compare( joker, four ), 1 );
		
	}
	
	public void testJokerHigherThanAce() throws Exception {
		
		Card joker = new Card( Suit.COLOR, CardValue.JOKER );
		Card ace = new Card( Suit.SPADES, CardValue.ACE );
		
		assertEquals( this.c.compare( joker, ace ), 1 );
		
	}


}
