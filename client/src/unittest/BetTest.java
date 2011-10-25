package unittest;

import junit.framework.TestCase;
import junit.framework.Assert;

import game.Bet;
import game.enumeration.Suit;

public class BetTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testConstructorUnderMinBet() {
				
		try {
			Bet b = new Bet( Bet.MIN_BET - 1, Suit.DIAMONDS );
		} catch ( Exception e ) {
			return;
		}
		
		fail("Constructor under minimum bet");
		
	}
	
	public void testConstructorOverMaxBet() {
		
		try {
			Bet b = new Bet( Bet.MAX_BET + 1, Suit.DIAMONDS ); 
		} catch( Exception e ) {
			return;
		}
		
		fail("Constructor over maximum bet");
		
	}
	
	public void testConstructorBlackJoker() {
		
		try {
			Bet b = new Bet( Bet.MIN_BET, Suit.COLOR );
		} catch ( Exception e ) {
			return;
		}
		
		fail("Constructor with black joker");
		
	}
	
	public void testConstructorColorJoker() {
		
		try {
			Bet b = new Bet( Bet.MIN_BET, Suit.BLACK );
		} catch( Exception e ) {
			return;
		}
		
		fail("Constructor with color joker");
		
	}
	
	public void testMinimumBet() {
		
		Bet b = null;
		try {
			b = new Bet( Bet.MIN_BET, Suit.CLUBS );
		} catch( Exception e ) {
			fail("Bet constructor with minimum bet");
		}
		
		assertEquals( Bet.MIN_BET , b.getNbRounds() );
		assertEquals( Suit.CLUBS, b.getSuit() );
		
	}
	
	public void testMaximumBet() {
		
		Bet b = null;
		try { 
			b = new Bet( Bet.MAX_BET, Suit.DIAMONDS );
		} catch( Exception e ) {
			fail("Bet constructor with maximum bet");
		}
		
		assertEquals( Bet.MAX_BET , b.getNbRounds() );
		assertEquals( Suit.DIAMONDS, b.getSuit() );
		
	}
	
	public void testNormalBet() {
		
		int nbRounds = 7;
		Suit suit = Suit.HEARTS;
		
		Bet b = null;
		try { 
			b = new Bet( nbRounds, suit );
		} catch( Exception e ) {
			fail("Bet with normal number of bets");
		}
		
		assertEquals( nbRounds, b.getNbRounds() );
		assertEquals( suit , b.getSuit() );
		
	}

	public void testGetNbRounds() {
		
		int nbRounds = 8;
		Suit suit = Suit.SPADES;
		
		Bet b = null;
		try { 
			b = new Bet( nbRounds, suit );
		} catch( Exception e ) {
			fail("Bet constructor while testing getNbRounds()");
		}
		
		assertEquals( nbRounds, b.getNbRounds() );
		
	}

	public void testGetSuit() {
		
		int nbRounds = 9;
		Suit suit = Suit.NONE;
		
		Bet b = null;
		try { 
			b = new Bet( nbRounds, suit );
		} catch( Exception e ) {
			fail("Bet constructor while testing getSuit()");
		}
		
		assertEquals( suit , b.getSuit() );
		
	}

	public void testToString() {
		
		int nbRounds = 7;
		Suit suit = Suit.DIAMONDS;
		
		Bet b = null;
		try { 
			b = new Bet( nbRounds, suit );
		} catch( Exception e ) {
			fail("Bet constructor while testing toString()");
		}
		
		assertTrue( b.toString().indexOf( Integer.toString( nbRounds ) ) > -1 );
		assertTrue( b.toString().indexOf( suit.toString() ) > -1 );

	}

}
