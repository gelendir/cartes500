package game;

import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.ArrayList;
import java.util.Iterator;

public class Hand {
	private transient ArrayList<Card> hand = new ArrayList<Card>(10);
	private int numberOfCard = 0;
	private Suit suit = null;
	
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
	
	public Suit getGameSuit() {
		return this.suit;
	}
	
	public boolean setGameSuit(Suit suit) {
		if(this.suit ==  Suit.BLACK || this.suit == Suit.COLOR)
			return false;
		
		this.suit = suit;
		return true;
	}
	
	public Card[] getCards() {
		if(this.hand == null) {
			return null;
		} else {
			Card cards[] = new Card[ this.hand.size() ];
			this.hand.toArray( cards );
			return cards;
			//return (Card[])this.hand.toArray();
		}
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.hand = (ArrayList<Card>) cards.clone();
	}
	
	public boolean playCard(Card card) {
		if(this.hand != null && this.hand.contains(card)) {
			this.hand.remove(card);
			--this.numberOfCard;
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Card> getPlayableCard() {
		if(this.suit !=  null && this.hand != null) {
			ArrayList<Card> ret = new ArrayList<Card>();
			Iterator<Card> itr = this.hand.iterator(); 
			Card add = null;
			
			while(itr.hasNext()) {
				add = itr.next();
				if(add.getSuit().equals(this.suit)) {
					ret.add(add);
				}
			}
			
			if(this.suit == Suit.SPADES) {
				add = new Card(Suit.CLUBS, CardValue.JACK);
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			else if(this.suit == Suit.CLUBS) {
				add = new Card(Suit.SPADES, CardValue.JACK);
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			else if(this.suit == Suit.DIAMONDS) {
				add = new Card(Suit.HEARTS, CardValue.JACK);
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			else if(this.suit == Suit.HEARTS) {
				add = new Card(Suit.DIAMONDS, CardValue.JACK);
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			
			add = new Card(Suit.COLOR, CardValue.JOKER);
			if(this.hand.contains(add)) {
				ret.add(add);
			}
			
			add = new Card(Suit.BLACK, CardValue.JOKER);
			if(this.hand.contains(add)) {
				ret.add(add);
			}
			
			if(ret.size() != 0) {
				return ret;
			}		
		}
		
		return this.hand;
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
