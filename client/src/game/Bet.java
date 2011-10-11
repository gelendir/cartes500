package game;

import game.enumeration.Suit;

public class Bet {
	
	final static public int MIN_BET = 6;
	final static public int MAX_BET = 10;
	final static public  Suit[] SUITS = { 
		Suit.SPADES, 
		Suit.CLUBS, 
		Suit.DIAMONDS, 
		Suit.HEARTS, 
		Suit.NONE 
	};
	
	private int nbRounds;
	private Suit suit;
	
	public Bet( int nbRounds, Suit suit ) {
		
		this.nbRounds = nbRounds;
		this.suit = suit;
		
	}
	
	public int getNbRounds() {
		return this.nbRounds;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public String toString() {
		return "Bet[" + this.suit.toString() + "," + Integer.toString( this.nbRounds ) + "]";
	}

}
