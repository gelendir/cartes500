package game;

import exception.EmptyDeckException;
import game.card.Card;
import game.card.Deck;
import game.enumeration.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Cette classe sert à représenter la main d'un joueur. Elle contient
 * donc une collection de carte.
 * @author Frédérik Paradis
 * @see Card
 * @see Deck
 * @see Suit
 */
public class Hand implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8447840828554178769L;

	/**
	 * Le nombre maximum de cartes dans la main.
	 */
	final static public int MAX_CARDS = 10;
	
	/**
	 * Le nombre minimum de cartes dans la main.
	 */
	final static public int MIN_CARDS = 6;
	
	/**
	 * La collection de cartes de la main. L'attribut est « transient » parce que,
	 * lors de la sérialisation, on ne veut pas que les cartes soient sérialisées
	 * avec la main.
	 */
	private ArrayList<Card> hand = new ArrayList<Card>(MAX_CARDS);
	
	/**
	 * Le nombre de carte de la main. Cet attribut existe parce que, lors de la 
	 * sérialisation, nous n'avons plus les cartes et nous avons donc besoin de cet
	 * attribut pour avoir le nombre de carte. 
	 */
	private int numberOfCard = 0;
	
	/**
	 * Le constructeur crée la main à partir d'un paquet de carte.
	 * @param deck Le paquet de carte d'où les cartes de la main vont
	 *             être prises.
	 * @throws EmptyDeckException
	 * @see Deck
	 */
	public Hand(Deck deck) throws EmptyDeckException {
		for(int i = 0; i < MAX_CARDS; ++i) {
			if(deck.isEmpty()) {
				throw new EmptyDeckException();
			}
			else {
				this.hand.add(deck.takeCard());
				++this.numberOfCard;
			}
		}
	}
	
	/**
	 * Cette méthode retourne le tableau des cartes de la main.
	 * @return Retourne le tableau des cartes de la main.
	 */
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
	
	/**
	 * Cette méthode permet de changer les cartes de la main.
	 * @param cards La collection de carte
	 */
	public void setCards(ArrayList<Card> cards) {
		this.hand = (ArrayList<Card>) cards.clone();
		this.numberOfCard = this.hand.size();
	}
	
	/**
	 * Cette méthode permet de jouer une carte d'une main.
	 * @param card La carte à jouer
	 * @return Retourne vrai si la carte a été trouvé et enlevé de la main;
	 *         sinon faux.
	 */
	public boolean playCard(Card card) {
		if(this.hand != null && this.hand.contains(card)) {
			this.hand.remove(card);
			--this.numberOfCard;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Cette méthode sert à savoir les cartes jouables selon l'atoût
	 * du tour.
	 * @param turn L'atoût du tour.
	 * @return Retourne la collection de cartes jouables.
	 */
	public ArrayList<Card> getPlayableCard(Suit turn) {
		if(this.hand != null && turn != null && turn != Suit.NONE) {
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
				return ret;
			}
		}
		
		return this.hand;
	}
	
	/**
	 * Cette méthode retourne le nombre de carte dans la main.
	 * @return Retourne le nombre de carte dans la main.
	 */
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
					hand.numberOfCard == this.numberOfCard) {
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
	
	public boolean hasCard( Card card ) {
		
		return this.hand.contains( card );
		
	}
}
