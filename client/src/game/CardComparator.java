package game;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
	
	@Override
	public int compare(Card arg0, Card arg1) {
		// TODO Auto-generated method stub
		
		int value1 = arg0.getCardValue().getValue();
		int value2 = arg1.getCardValue().getValue();
		
		if( value1 > value2 ) {
			return 1;
		} else if ( value1 < value2 ) {
			return -1;
		} else {
			return 0;
		}
		
	}

}
