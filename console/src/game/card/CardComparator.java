package game.card;


import java.util.Comparator;

/**
 * Comparateur de cartes. 
 * Classe utilitaire pour trier en ordre de valeur un ensemble de cartes.
 * Le tri ne s'occupe pas de la sorte de la carte, seulement son symbole
 * (Ace, 2, 3, etc). Cette classe est utilisé avec le framework de Collections
 * de java.
 * 
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class CardComparator implements Comparator<Card> {
	
	/**
	 * Détermine laquelle de 2 cartes a la plus grande valeur, c'est-à-dire
	 * quel carte est la plus forte. Retourne -1 si carte1 est plus petite que carte2,
	 * +1 si carte1 est plus grande que carte 2, et 0 si les 2 cartes ont la même valeur.
	 * Cette fonction ne prend pas en compte la sorte de la carte.
	 * 
	 * @return Entier indiquant laquelle des 2 cartes est plus grande.
	 */
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
