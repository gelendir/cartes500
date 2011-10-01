package game;

import java.util.ArrayList;

public class Hand {
	private transient ArrayList<Card> hand = new ArrayList<Card>(10);
	private int numberOfCard = 0;
	
	public Hand(Deck deck) throws Exception {
		for(int i = 0; i < 10; ++i) {
			if(deck.isEmpty()) {
				throw new Exception("The deck is empty.");
			}
			else {
				this.hand.add(deck.takeCard());
				++this.numberOfCard;
			}
		}
	}
	
	public Card[] getCards() {
		if(this.hand == null)
			return null;
		else
			return (Card[])this.hand.toArray();
	}
	
	public boolean playCard(Card card) {
		if(this.hand != null && this.hand.contains(card)) {
			this.hand.remove(card);
			--this.numberOfCard;
			return true;
		}
		
		return false;
	}
	
	public int getNumberOfCard() {
		return this.numberOfCard;
	}
	
	public String toString() {
		
		String hand = "";
	
		for( Card card : this.getCards() ) {
			hand += card.toString() + "\n";
		}
		
		return hand;
		
	}
}
