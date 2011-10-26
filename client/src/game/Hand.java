package game;

import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.ArrayList;
import java.util.Iterator;

public class Hand {
	
	final static public int MAX_CARDS = 10;
	
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
		this.numberOfCard = this.hand.size();
	}
	
	public boolean playCard(Card card) {
		if(this.hand != null && this.hand.contains(card)) {
			this.hand.remove(card);
			--this.numberOfCard;
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Card> getPlayableCard(Suit turn) {
		if(this.suit !=  null && !this.suit.equals(Suit.NONE) && this.hand != null && turn != null) {
			ArrayList<Card> ret = new ArrayList<Card>();
			Iterator<Card> itr = this.hand.iterator(); 
			Card add = null;
			
			while(itr.hasNext()) {
				add = itr.next();
				if(add.getSuit().equals(turn)) {
					ret.add(add);
				}
			}
			
			if(ret.size() != 0) {
				/*try {
					add = new Card(Suit.COLOR, CardValue.JOKER);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
				
				try {
					add = new Card(Suit.BLACK, CardValue.JOKER);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}*/
				
				return ret;
			}
			
			
			
			/*//Si on n'a pas de carte avec l'atout du tour...
			itr = this.hand.iterator(); 
			while(itr.hasNext()) {
				add = itr.next();
				if(add.getSuit().equals(this.suit)) {
					ret.add(add);
				}
			}
			
			if(this.suit == Suit.SPADES) {
				try {
					add = new Card(Suit.CLUBS, CardValue.JACK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			else if(this.suit == Suit.CLUBS) {
				try {
					add = new Card(Suit.SPADES, CardValue.JACK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			else if(this.suit == Suit.DIAMONDS) {
				try {
					add = new Card(Suit.HEARTS, CardValue.JACK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			else if(this.suit == Suit.HEARTS) {
				try {
					add = new Card(Suit.DIAMONDS, CardValue.JACK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
			}
			
			if(ret.size() != 0) {
				try {
					add = new Card(Suit.COLOR, CardValue.JOKER);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
				
				try {
					add = new Card(Suit.BLACK, CardValue.JOKER);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.hand.contains(add)) {
					ret.add(add);
				}
				
				return ret;
			}*/
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
	
	public boolean equals(Object obj) {
		if(obj instanceof Hand) {
			Hand hand = (Hand)obj;
			if(hand.hand.size() == this.hand.size() &&
					hand.numberOfCard == this.numberOfCard &&
					hand.suit == this.suit) {
				for(int i = 0; i < this.hand.size(); ++i) {
					if(!hand.hand.get(i).equals(this.hand.get(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}
