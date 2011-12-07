package unittest;

import junit.framework.TestCase;

import game.Player;
import game.Turn;
import game.card.Card;
import game.enumeration.Suit;
import game.enumeration.CardValue;

public class TurnTest extends TestCase {
	
	static public int MAX_PLAYERS = 4;

	private Player p;
	
	private Card[] diamondCards;
	private Card[] heartCards;
	private Card[] spadeCards;
	private Card[] clubCards;
	
	private Card colorJoker;
	private Card blackJoker;
	
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
		
		this.colorJoker = 
		
		this.colorJoker = TestCardFactory.createCard( Suit.COLOR, CardValue.JOKER );
		this.blackJoker = TestCardFactory.createCard( Suit.BLACK, CardValue.JOKER );
		
		this.diamondCards = new Card[]{
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.FOUR ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.FIVE ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.SIX ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.SEVEN ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.EIGHT ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.NINE ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.TEN ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.JACK ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.QUEEN ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.KING ),
				TestCardFactory.createCard( Suit.DIAMONDS, CardValue.ACE )
		};
		
		this.heartCards = new Card[]{
				TestCardFactory.createCard( Suit.HEARTS, CardValue.FOUR ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.FIVE ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.SIX ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.SEVEN ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.EIGHT ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.NINE ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.TEN ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.JACK ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.QUEEN ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.KING ),
				TestCardFactory.createCard( Suit.HEARTS, CardValue.ACE )
		};
		
		this.spadeCards = new Card[]{
				TestCardFactory.createCard( Suit.SPADES, CardValue.FOUR ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.FIVE ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.SIX ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.SEVEN ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.EIGHT ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.NINE ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.TEN ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.JACK ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.QUEEN ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.KING ),
				TestCardFactory.createCard( Suit.SPADES, CardValue.ACE )
		};
		
		this.clubCards = new Card[]{
				TestCardFactory.createCard( Suit.CLUBS, CardValue.FOUR ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.FIVE ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.SIX ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.SEVEN ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.EIGHT ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.NINE ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.TEN ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.JACK ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.QUEEN ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.KING ),
				TestCardFactory.createCard( Suit.CLUBS, CardValue.ACE )
		};
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void fillTurn( Turn t, Card[] cards, Player[] players ) {
		
		for( int i = 0; i < TurnTest.MAX_PLAYERS; i++ ) {
			
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
		
		for( int i = 0; i < TurnTest.MAX_PLAYERS; i++ ) {
			
			t.addCard( this.dummyPlayers[i], this.diamondCards[i] );
			assertEquals( i + 1, t.nbCards() );
			
		}
	}
	
	public void testMaxAddCard() {
		
		Turn t = new Turn( Suit.NONE );
		
		Card extraCard = null;
		try {
			extraCard = TestCardFactory.createCard( Suit.DIAMONDS, CardValue.EIGHT );
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
		
		fail("double player: did not return exception after same player played twice");		
		
	}
	
	public void testLatestPlayer() {
		
		Turn t = new Turn( Suit.NONE );
		
		try{
			t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		} catch (Exception e) {
			fail("Latest player: error adding first player");
		}
		
		try{
			t.addCard( this.dummyPlayers[1], this.diamondCards[1] );
		} catch (Exception e) {
			fail("Latest player: error adding second player");
		}
		
		assertEquals( this.dummyPlayers[1], t.getLatestPlayer() );
		
	}
	
	public void testLatestCard() {
		
		Turn t = new Turn( Suit.NONE );
		
		try{
			t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		} catch (Exception e) {
			fail("Latest player: error adding first player");
		}
		
		try{
			t.addCard( this.dummyPlayers[1], this.diamondCards[1] );
		} catch (Exception e) {
			fail("Latest player: error adding second player");
		}
		
		assertEquals( this.diamondCards[1], t.getLatestCard() );
		
	}
	
	//======================================
	//Jokers
	//======================================
	
	public void testWinnerColorJoker() throws Exception {
		
		Turn t = new Turn( Suit.HEARTS );
		
		t.addCard( this.dummyPlayers[0], this.heartCards[2] );
		t.addCard( this.dummyPlayers[1], this.colorJoker );
		t.addCard( this.dummyPlayers[2], this.heartCards[0] );
		t.addCard( this.dummyPlayers[3], this.heartCards[1] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	public void testWinnerBlackJoker() throws Exception {
		
		Turn t = new Turn( Suit.CLUBS );
		
		t.addCard( this.dummyPlayers[0], this.clubCards[2] );
		t.addCard( this.dummyPlayers[1], this.blackJoker );
		t.addCard( this.dummyPlayers[2], this.clubCards[0] );
		t.addCard( this.dummyPlayers[3], this.clubCards[1] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	
	public void testWinnerBlackJokerNoSuit() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.clubCards[2] );
		t.addCard( this.dummyPlayers[1], this.blackJoker );
		t.addCard( this.dummyPlayers[2], this.clubCards[0] );
		t.addCard( this.dummyPlayers[3], this.clubCards[1] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	
	public void testWinnerBothJokers() throws Exception {
		
		Turn t = new Turn( Suit.DIAMONDS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[2] );
		t.addCard( this.dummyPlayers[1], this.blackJoker );
		t.addCard( this.dummyPlayers[2], this.colorJoker );
		t.addCard( this.dummyPlayers[3], this.diamondCards[1] );
		
		assertEquals( this.dummyPlayers[2], t.getWinner() );
		
	}
	
	//======================================
	//Jacks
	//======================================
	
	public void testWinnerJackAllSameSuit() throws Exception {
		
		Turn t = new Turn( Suit.DIAMONDS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[8] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[9] );
		t.addCard( this.dummyPlayers[2], this.diamondCards[7] ); //Jack
		t.addCard( this.dummyPlayers[3], this.diamondCards[1] );
		
		assertEquals( this.dummyPlayers[2], t.getWinner() );	
		
	}
	
	public void testWinnerJackGameSuit() throws Exception {
		
		Turn t = new Turn( Suit.CLUBS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[8] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[9] );
		t.addCard( this.dummyPlayers[2], this.clubCards[7] ); //Jack
		t.addCard( this.dummyPlayers[3], this.diamondCards[1] );
		
		assertEquals( this.dummyPlayers[2], t.getWinner() );
		
	}
	
	public void testWinnerJackNotGameSuit() throws Exception {
		
		Turn t = new Turn( Suit.CLUBS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[7] ); //Jack
		t.addCard( this.dummyPlayers[1], this.clubCards[6] ); //Winner
		t.addCard( this.dummyPlayers[2], this.clubCards[5] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[4] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	public void testWinnerBothJackSameColor() throws Exception {
		
		Turn t = new Turn( Suit.SPADES );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[3] );
		t.addCard( this.dummyPlayers[1], this.spadeCards[7] ); //Jack major
		t.addCard( this.dummyPlayers[2], this.clubCards[7] ); //Jack minor
		t.addCard( this.dummyPlayers[3], this.diamondCards[4] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	public void testWinnerBothJackSameColorNotGameSuit() throws Exception {
		
		Turn t = new Turn( Suit.DIAMONDS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[3] );
		t.addCard( this.dummyPlayers[1], this.spadeCards[7] ); //Jack major
		t.addCard( this.dummyPlayers[2], this.clubCards[7] ); //Jack minor
		t.addCard( this.dummyPlayers[3], this.diamondCards[4] );
		
		assertEquals( this.dummyPlayers[3], t.getWinner() );
		
	}
	
	public void testWinnerJackMinorGameSuit() throws Exception {
		
		Turn t = new Turn( Suit.HEARTS );
		
		t.addCard( this.dummyPlayers[0], this.heartCards[3] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[7] ); //Jack minor
		t.addCard( this.dummyPlayers[2], this.clubCards[8] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[4] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
			
	}
	
	public void testWinnerAllJacks() throws Exception {
		
		Turn t = new Turn( Suit.DIAMONDS );
		
		t.addCard( this.dummyPlayers[0], this.heartCards[7] );
		t.addCard( this.dummyPlayers[1], this.clubCards[7] );
		t.addCard( this.dummyPlayers[2], this.spadeCards[7] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[7] );
		
		assertEquals( this.dummyPlayers[3], t.getWinner() );
		
	}
	
	//======================================
	//Game suit
	//======================================
	
	public void testWinnerGameSuitMixedCards() throws Exception {
		
		Turn t = new Turn( Suit.SPADES );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		t.addCard( this.dummyPlayers[1], this.heartCards[0] );
		t.addCard( this.dummyPlayers[2], this.clubCards[0] );
		t.addCard( this.dummyPlayers[3], this.spadeCards[0] );
		
		assertEquals( this.dummyPlayers[3], t.getWinner() );
		
	}
	
	public void testWinnerGameSuitAllSameSuit() throws Exception {
		
		Turn t = new Turn( Suit.DIAMONDS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[2] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[3] );
		t.addCard( this.dummyPlayers[2], this.diamondCards[0] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[1] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	public void testWinnerGameSuitNoSuit() throws Exception {
		
		Turn t = new Turn( Suit.HEARTS );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[2] );
		t.addCard( this.dummyPlayers[2], this.diamondCards[1] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[3] );
		
		assertEquals( this.dummyPlayers[3], t.getWinner() );
		
	}
	
	//======================================
	//Turn suit
	//======================================
	
	public void testWinnerTurnSuitUniqueSuits() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		t.addCard( this.dummyPlayers[1], this.heartCards[0] );
		t.addCard( this.dummyPlayers[2], this.clubCards[0] );
		t.addCard( this.dummyPlayers[3], this.spadeCards[0] );
		
		assertEquals( this.dummyPlayers[0], t.getWinner() );
		
	}
	
	public void testWinnerTurnSuitTwoSuits() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[1] );
		t.addCard( this.dummyPlayers[2], this.spadeCards[0] );
		t.addCard( this.dummyPlayers[3], this.spadeCards[1] );
		
		assertEquals( this.dummyPlayers[1], t.getWinner() );
		
	}
	
	public void testWinnerTurnSuitSameSuit() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.diamondCards[0] );
		t.addCard( this.dummyPlayers[1], this.diamondCards[1] );
		t.addCard( this.dummyPlayers[2], this.diamondCards[3] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[2] );
		
		assertEquals( this.dummyPlayers[2], t.getWinner() );
		
	}
	
	public void testGetTurnSuitJokerOnly() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.colorJoker );
		
		assertEquals( null, t.getTurnSuit() );		
		
	}
	
	public void testGetTurnSuitCardAfterJoker() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.colorJoker );
		t.addCard( this.dummyPlayers[1], this.diamondCards[0] );
		
		assertEquals( Suit.DIAMONDS, t.getTurnSuit() );
		
	}
	
	public void testGetTurnSuitCardsAfterJoker() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.colorJoker );
		t.addCard( this.dummyPlayers[1], this.heartCards[0] );
		t.addCard( this.dummyPlayers[2], this.diamondCards[1] );
		
		assertEquals( Suit.HEARTS, t.getTurnSuit() );
		
	}
	
	public void testGetTurnSuitBothJokers() throws Exception {
		
		Turn t = new Turn( Suit.NONE );
		
		t.addCard( this.dummyPlayers[0], this.colorJoker );
		t.addCard( this.dummyPlayers[1], this.blackJoker );
		t.addCard( this.dummyPlayers[2], this.spadeCards[0] );
		t.addCard( this.dummyPlayers[3], this.diamondCards[0] );
		
		assertEquals( Suit.SPADES, t.getTurnSuit() );
		
	}
;
}
