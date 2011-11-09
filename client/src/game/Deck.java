package game;

import exception.GameException;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.Collections;
import java.util.Stack;

/**
 * Cette classe représente un paquet de carte. Elle est en fait une 
 * fabrique de carte. Elle se charge de créer les cartes selon le 
 * jeu du 500 et de les mélanger si nécessaire.
 * @author Frédérik Paradis
 */
public class Deck {
	
	/**
	 * La collection de carte. On utilise une pile puisque l'on enlèvera
	 * la carte du dessus la plupart du temps. 
	 */
	private Stack<Card> cards = new Stack<Card>();

	/**
	 * Le constructeur se charge de créer les cartes.
	 */
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

						try {
							cards.add(new Card(suits[i], values[j]));
						} catch (GameException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

			}

		}
		
		try {
			this.cards.add(new Card(Suit.BLACK, CardValue.JOKER));
			this.cards.add(new Card(Suit.COLOR, CardValue.JOKER));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Cette méthode se charge de mélanger les cartes de manière 
	 * aléatoire.
	 */
	public void mixCards() {
		/*Random rand = new Random();
		for(int i = 0; i < cards.size(); ++i) {
			int pos = rand.nextInt(cards.size());
			Card temp = cards.elementAt(i);
			cards.set(i, cards.elementAt(pos));
			cards.set(pos, temp);
		}*/
		Collections.shuffle(this.cards);
	}

	/**
	 * Cette méthode retourne la carte du dessus du paquet.
	 * @return Retourne la carte du dessus du paquet
	 */
	public Card takeCard() {
		return cards.pop();
	}

	/**
	 * Cette méthode vérifie si le paquet est vide ou non.
	 * @return Retourne vrai si le paquet est vide; sinon faux.
	 */
	public boolean isEmpty() {
		return cards.empty();
	}

	/**
	 * Cette méthode retourne la collection de cartes.
	 * @return Retourne la collection de cartes.
	 */
	public Stack<Card> getDeck() {
		return this.cards;
	}
}
