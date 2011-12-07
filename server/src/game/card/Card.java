package game.card;

import exception.InvalidCardException;
import game.Turn;
import game.enumeration.CardValue;
import game.enumeration.Suit;

/**
 * La classe Card représente une carte de jeu.
 * @author Frédérik Paradis
 * */
public class Card {
	
	/**
	 * La sorte de la carte
	 */
	private final Suit suit;
	
	/**
	 * La valeur de la carte
	 */
	private final CardValue value;
	
	
	/**
	 * Génère un Joker noir. Utilisé dans la classe Turn
	 * pour déterminer le gagnant d'un tour
	 * 
	 * @see Turn#getWinner()
	 * @return Le Joker
	 */
	public final static Card blackJoker() {
		
		try {
			return new Card( Suit.BLACK, CardValue.JOKER );
		} catch (InvalidCardException e) {
			return null;
		}
		
	}
	
	/**
	 * Génère un Joker de couleur. Utilisé dans la classe Turn
	 * pour déterminer le gagnant d'un tour
	 * 
	 * @see Turn#getWinner()
	 * @return Le Joker
	 */
	public final static Card colorJoker() {
		
		try {
			return new Card( Suit.COLOR, CardValue.JOKER );
		} catch (InvalidCardException e) {
			return null;
		}
		
	}
	
	/**
	 * Génère un Valet. Utilisé dans la classe Turn
	 * pour déterminer le gagnant d'un tour
	 * 
	 * @param suit La sorte du valet.
	 * @see Turn#getWinner()
	 * @return Le Joker
	 */
	public final static Card createJack( Suit suit ) {
		
		try {
			return new Card( suit, CardValue.JACK );
		} catch( InvalidCardException e) {
			return null;
		}
		
	}
	
	/**
	 * Le constructeur de Card crée une nouvelle carte avec la sorte et
	 * la valeur spécifié. Une exception est levée lorsque l'on crée une carte
	 * qui n'existe pas. Par exemple, un Joker de Pique lèverait une exception.
	 * @param suit La sorte de la carte
	 * @param value La valeur de la carte 
	 * @throws InvalidCardException
	 */
	Card(Suit suit, CardValue value) throws InvalidCardException {
		if(value.equals(CardValue.JOKER)) {
			if(!suit.equals(Suit.BLACK) && !suit.equals(Suit.COLOR)) {
				throw new InvalidCardException();
			}
		}
		else if(suit.equals(Suit.BLACK) || suit.equals(Suit.COLOR)) {
			throw new InvalidCardException();
		}
		
		this.suit = suit;
		this.value = value;
	}
	
	/**
	 * Cette méthode retourne la sorte de la carte.
	 * @return Retourne la sorte de la carte
	 */
	public Suit getSuit() {
		return this.suit;
	}
	
	/**
	 * Cette méthode retourne la valeur de la carte.
	 * @return Retourne la valeur de la carte
	 */
	public CardValue getCardValue() {
		return this.value;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Card) {
			Card card = (Card)obj;
			if(this.suit == card.suit && this.value == card.value) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String s = this.value.name().toLowerCase();
		s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
		
		String s2 = this.suit.name().toLowerCase();
		s2 = s2.substring(0,1).toUpperCase() + s2.substring(1).toLowerCase();
		
		return s + " of " + s2;
	}
}
