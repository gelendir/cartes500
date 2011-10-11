package game;

import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	private Stack<Card> cards = new Stack<Card>();

	public Deck() {
		CardValue values[] = CardValue.values();
		Suit suits[] = Suit.values();

		for(int i = 0; i < suits.length - 2; ++i) {

			for(int j = 0; j < values.length - 1; ++j) {

				if(!suits[i].equals(Suit.BLACK) && 
						!suits[i].equals(Suit.COLOR) &&
						!suits[i].equals(Suit.NONE)) {

					if(!values[j].equals(CardValue.JOKER) && 
							!values[j].equals(CardValue.TWO) && 
							!values[j].equals(CardValue.THREE)) {

						cards.add(new Card(suits[i], values[j]));

					}

				}

			}

		}
		
		this.cards.add(new Card(Suit.BLACK, CardValue.JOKER));
		this.cards.add(new Card(Suit.COLOR, CardValue.JOKER));
	}

	public void mixCard() {
		/*Random rand = new Random();
		for(int i = 0; i < cards.size(); ++i) {
			int pos = rand.nextInt(cards.size());
			Card temp = cards.elementAt(i);
			cards.set(i, cards.elementAt(pos));
			cards.set(pos, temp);
		}*/
		Collections.shuffle(this.cards);
	}

	public Card takeCard() {
		return cards.pop();
	}

	public boolean isEmpty() {
		return cards.empty();
	}

	public Stack<Card> getDeck() {
		return this.cards;
	}
}
