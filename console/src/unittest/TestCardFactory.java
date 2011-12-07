package unittest;

import game.card.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.lang.reflect.Constructor;

public class TestCardFactory {
	
	final static public Card createCard( Suit suit, CardValue value ) throws Exception  {
		
		Class[] constructorArgs = new Class[]{ Suit.class, CardValue.class };
		Object[] args = new Object[]{ suit, value };
		
		Constructor constructor = Card.class.getDeclaredConstructor( constructorArgs );
		constructor.setAccessible(true);
		
		Card card = (Card) constructor.newInstance( args );
		
		return card;
		
	}
	
	
	public static void main( String[] args ) throws Exception {
		
		Card card = TestCardFactory.createCard( Suit.DIAMONDS , CardValue.EIGHT  );
		
	}

}
