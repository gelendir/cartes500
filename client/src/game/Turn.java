package game;


import game.enumeration.Suit;

import java.util.HashMap;
import java.util.Map;

public class Turn {
	
	final private static int MAX_CARDS = 4;
	
	private HashMap<Player, Card> cards;
	private Player latestPlayer = null;
	private Card latestCard = null;
	private Suit suit = null;
	private boolean haveSort = false;
	
	public Turn(Suit suit) {
		this.cards = new HashMap<Player, Card>( Turn.MAX_CARDS );
		this.suit = suit;
	}
	
	public void addCard( Player player, Card card ) throws Exception {
		
		if( this.cards.size() >= Turn.MAX_CARDS ) {
			throw new Exception("Cannot add more cards to this turn");
		} else if ( this.cards.containsKey( player ) ) {
			throw new Exception("Player has already played a card");
		}
		
		this.cards.put( player, card );
		this.latestPlayer = player;
		this.latestCard = card;
		
		if(this.suit.equals(Suit.NONE)) {
			this.suit = card.getSuit();
		}
		
		if(this.suit.equals(card.getSuit())) {
			this.haveSort = true;
		}
		
	}
	
	public int nbCards() {
		return this.cards.size();
	}
	
	public HashMap<Player, Card> getCards() {
		return (HashMap<Player, Card>) this.cards.clone();
	}
	
	public String toString() {
		String result = "";
		
		for( Map.Entry<Player, Card> entry : this.cards.entrySet() ) {
			result += entry.getKey().toString() + " - " + entry.getValue().toString() + "\n";
		}
		
		return result;
	}
	
	public Player getLatestPlayer() {
		return this.latestPlayer;
	}
	
	public Card getLatestCard() {
		return this.latestCard;
	}
	
	public Player getWinner() {
		Player winner = null;
		Card cardWin = null;
		for(Map.Entry<Player, Card> entry : this.cards.entrySet()) {
			Player actualPlayer = entry.getKey();
			Card actuelCard = entry.getValue();
			if(cardWin != null) {
				winner = actualPlayer;
				cardWin = actuelCard;
			}
			else {
				
			}
		}
		
		return winner;
	}
}
