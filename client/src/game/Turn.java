package game;


import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Turn {
	
	final private static int MAX_CARDS = 4;
	
	private LinkedHashMap<Player, Card> cards;
	
	private Player latestPlayer = null;
	private Card latestCard = null;
	private Suit suit = null;
	
	private boolean haveSort = false;
	
	public Turn(Suit suit) {
		this.cards = new LinkedHashMap<Player, Card>( Turn.MAX_CARDS );
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
	
	public LinkedHashMap<Player, Card> getCards() {
		return (LinkedHashMap<Player, Card>) this.cards.clone();
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
	
	private ArrayList<Card> filterCards( Suit suit ) {
		
		ArrayList<Card> cards = new ArrayList<Card>();
		for( Card c : this.cards.values() ) {
			if( c.getSuit().equals( suit ) ) {
				cards.add( c );
			}
		}
		
		CardComparator comparator = new CardComparator();
		Collections.sort(cards, comparator);
		
		return cards;
	}
	
	private Player playerFromCard( Card card ) {
		
		for( Map.Entry<Player, Card> entry : this.cards.entrySet() ) {
			if ( entry.getValue().equals( card ) ) {
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	public void incrementWinner() throws Exception {
		
		Player winner = this.getWinner();
		winner.addTurnWin();
		
	}
	
	public Player getWinner() throws Exception {
		
		if( this.cards.size() != Turn.MAX_CARDS ) {
			throw new Exception("Not all players have played a card in this turn");
		}
		
		Suit strongSuit = this.suit;
		Suit turnSuit = this.cards.values().iterator().next().getSuit();
		Suit weakSuit = null;
		
		Card colorJoker = new Card( Suit.COLOR, CardValue.JOKER );
		Card blackJoker = new Card( Suit.BLACK, CardValue.JOKER );
		
		ArrayList<Card> strongCards = null;
		
		Card search = null;
		
		if( turnSuit == Suit.CLUBS ) {
			weakSuit = Suit.SPADES;
		} else if ( turnSuit == Suit.SPADES ) {
			weakSuit = Suit.CLUBS;
		} else if ( turnSuit == Suit.DIAMONDS ) {
			weakSuit = Suit.HEARTS;
		} else if ( turnSuit == Suit.HEARTS ) {
			weakSuit = Suit.DIAMONDS;
		}
		
		Card strongJack = new Card( turnSuit, CardValue.JACK );
		Card weakJack = new Card( weakSuit, CardValue.JACK  );

		//Determine if a player has a joker
		if ( this.cards.containsValue( colorJoker ) ) {
			search = colorJoker;
		} else if ( this.cards.containsValue( blackJoker ) ) {
			search = blackJoker;
		} else if( this.cards.containsValue( strongJack ) ) {
			search = strongJack;
		} else if ( this.cards.containsValue( weakJack ) ) {
			search = weakJack;
		}
		
		if( search != null ) {
			return this.playerFromCard( search );
		}
		
		if ( !strongSuit.equals( Suit.NONE ) ) {
			
			strongCards = this.filterCards( strongSuit );
			if( strongCards.size() > 0 ) {
				return this.playerFromCard( strongCards.get( strongCards.size() - 1 ) );
			}
			
		} 
		
		strongSuit = turnSuit;
		strongCards = this.filterCards( strongSuit );
		return this.playerFromCard( strongCards.get( strongCards.size() - 1 ) );
		
	}
}
