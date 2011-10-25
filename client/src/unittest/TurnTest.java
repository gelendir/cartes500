package unittest;

import junit.framework.TestCase;

import game.Card;
import game.Player;
import game.Turn;
import game.enumeration.Suit;
import game.enumeration.CardValue;

public class TurnTest extends TestCase {

	private Player p;
	private Card[] diamondCards;
	private Player[] dummyPlayers;

	protected void setUp() throws Exception {
		super.setUp();
		
		this.p = new Player("MockPlayer");
		
		this.dummyPlayers = new Player[]{
				new Player("DummyPlayer1"),
				new Player("DummyPlayer2"),
				new Player("DummyPlayer3"),
				new Player("DummyPlayer4")
		};
		
		this.diamondCards = new Card[]{
				new Card( Suit.DIAMONDS, CardValue.FOUR ),
				new Card( Suit.DIAMONDS, CardValue.FIVE ),
				new Card( Suit.DIAMONDS, CardValue.SIX ),
				new Card( Suit.DIAMONDS, CardValue.SEVEN )
		};
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void fillTurn( Turn t, Card[] cards, Player[] players ) {
		
		for( int i = 0; i < cards.length; i++ ) {
			
			try {
				t.addCard( players[i], cards[i] );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Exception launched when filling up turn");
			}
			
		}
				
	}
	
	public void testNbCards() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		assertEquals( 0, t.nbCards() );
		
		for( int i = 0; i < this.diamondCards.length; i++ ) {
			
			t.addCard( this.dummyPlayers[i], this.diamondCards[i] );
			assertEquals( i + 1, t.nbCards() );
			
		}
	}
	
	public void testMaxAddCard() {
		
		Turn t = new Turn( Suit.NONE );
		
		Card extraCard = null;
		try {
			extraCard = new Card( Suit.DIAMONDS, CardValue.EIGHT );
		} catch (Exception e1) {
			fail("add card: error generating extra card");
		}
		
		this.fillTurn( t , this.diamondCards, this.dummyPlayers );
		
		try {
			t.addCard( this.p, extraCard );
		} catch (Exception e) {
			return;
		}
		
		fail("maximum cards: did not return exception after 5 cards");
		
	}
	
	public void testSamePlayerTwice() {
		
		Turn t = new Turn( Suit.NONE );
		
		try {
			t.addCard( this.p, this.diamondCards[0] );
		} catch (Exception e) {
			fail("add card: error adding card before double player test");
		}
		
		try {
			t.addCard( this.p, this.diamondCards[1] );
		} catch (Exception e) {
			return;
		}
		
		fail("double plauer: did not return exception after same player played twice");		
		
	}

}
