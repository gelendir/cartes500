package server;

import game.Card;
import game.Player;

import java.util.HashMap;
import java.util.Map;

public class Turn {
	
	final private static int MAX_CARDS = 4;
	
	private HashMap<Player, Card> cards;
	
	public Turn() {
		this.cards = new HashMap<Player, Card>( Turn.MAX_CARDS );
	}
	
	public void addCard( Player player, Card card ) throws Exception {
		
		if( this.cards.size() >= Turn.MAX_CARDS ) {
			throw new Exception("Cannot add more cards to this turn");
		} else if ( this.cards.containsKey( player ) ) {
			throw new Exception("Player has already played a card");
		}
		
		this.cards.put( player, card );
		
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

}
