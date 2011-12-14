package game.enumeration;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Cet enum représente la sorte d'une carte.
 * @author Frédérik Paradis
 */
public enum Suit {
	SPADES (1),
	CLUBS (2), 
	DIAMONDS (3),
	HEARTS (4),
	NONE (5), //Utile pour la mise (bet) de départ
	BLACK (6),
	COLOR (7);
	
	static private final String BUNDLE_NAME= "suit";
	static private final PropertyResourceBundle BUNDLE = (PropertyResourceBundle) ResourceBundle.getBundle( Suit.BUNDLE_NAME );
	
	/**
	 * La valeur de la sorte selon la force de la sorte.
	 */
	private final int value;
	
	/**
	 * Le constructeur initialise l'élément avec sa sorte.
	 * @param value La valeur de la sorte selon la force de la sorte
	 */
	private Suit(int value) {
		this.value = value;
	}
	
	/**
	 * Cette méthode retourne la valeur de la sorte.
	 * @return Retourne la valeur de la sorte.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Cette méthode retourne la représentation textuelle
	 * de la sorte de la carte.
	 */
	public String toString() {
		return Suit.BUNDLE.getString( Integer.toString( this.value ) );
	}
	
}