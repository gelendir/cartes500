package game.enumeration;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

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
	
	private final int value;
	
	private Suit(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String toString() {
		
		return Suit.BUNDLE.getString( Integer.toString( this.value ) );
		
	}
	
}