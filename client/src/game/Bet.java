package game;

import game.enumeration.Suit;

public class Bet {
	
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
