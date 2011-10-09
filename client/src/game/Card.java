package game;

import game.enumeration.CardValue;
import game.enumeration.Suit;

public class Card {
	private final Suit suit;
	private final CardValue value;
	
	public Card(Suit suit, CardValue value) throws Exception {
		if(value.equals(CardValue.JOKER)) {
			if(!suit.equals(Suit.BLACK) && !suit.equals(Suit.COLOR)) {
				//throw new Exception("Playing Card does not exist.");
			}
		}
		else if(suit.equals(Suit.BLACK) || suit.equals(Suit.COLOR)) {
			//throw new Exception("Playing Card does not exist.");
		}
		
		this.suit = suit;
		this.value = value;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public CardValue getCardValue() {
		return this.value;
	}
	
	public String toString() {
		String s = this.value.name().toLowerCase();
		s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
		
		String s2 = this.suit.name().toLowerCase();
		s2 = s2.substring(0,1).toUpperCase() + s2.substring(1).toLowerCase();
		
		return s + " of " + s2;
	}
}
