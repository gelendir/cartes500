package game;

import java.util.Arrays;

import game.enumeration.Suit;

/**
 * Mise d'un joueur fait au début d'une partie.
 * Un Bet contient le nombre de tours ainsi que la suite qu'un joueur
 * a misé. Une mise est émis par un joueur après avoir reçu sa main.
 * Cette mise est utilisé à la fin d'une partie pour déterminer quel joueur a gagné la partie.
 * Un joueur peut miser entre 6 et 10 tours, avec ou sans atout.
 * 
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class Bet {
	
	/**
	 * Le nombre minimum de tours qu'un joueur doit miser
	 */
	final static public int MIN_BET = 6;
	
	/**
	 * Le nombre maxium de tours qu'un joueur doit miser
	 */
	final static public int MAX_BET = 10;
	
	/**
	 * Les sortes de cartes disponibles pour miser.
	 */
	final static public Suit[] SUITS = { 
		Suit.SPADES, 
		Suit.CLUBS, 
		Suit.DIAMONDS, 
		Suit.HEARTS, 
		Suit.NONE 
	};
	
	/**
	 * Le nombre de tours que le joueur a misé
	 */
	private int nbRounds;
	
	/**
	 * L'atout que le joueur à misé
	 */
	private Suit suit;
	
	/**
	 * Constructeur. Crée une nouvelle mise.
	 * @param nbRounds Le nombre de tours que le joueur désire remporter
	 * @param suit L'atout que le joueur désire.
	 * @throws Exception 
	 */
	public Bet( int nbRounds, Suit suit ) throws Exception {
		
		
		if( nbRounds < Bet.MIN_BET || nbRounds > Bet.MAX_BET ) {
			throw new Exception("number of bets must be between " + Bet.MIN_BET + " and " + Bet.MAX_BET );
		} else if ( ! Arrays.asList( Bet.SUITS ).contains( suit ) ) {
			throw new Exception("cannot use this suit in a bet");
		}
		
		this.nbRounds = nbRounds;
		this.suit = suit;
		
	}
	
	/**
	 * Acceseur. Retourne le nombre de tours que le joueur a misé,
	 * c'est-à-dire le nombre de tours que le joueur doit gagner pour 
	 * remporter la mise.
	 * @return Le nombre de tours.
	 */
	public int getNbRounds() {
		return this.nbRounds;
	}
	
	/**
	 * Acceseur. Retourne l'atout que le joueur a misé.
	 * @return La suite représentant l'atout.
	 */
	public Suit getSuit() {
		return this.suit;
	}

	/**
	 * Retourne une représentation sous forme de chaîne de charactères d'une mise.
	 * Utilisé surtout pour le client en console.
	 */
	public String toString() {
		return "Bet[" + this.suit.toString() + "," + Integer.toString( this.nbRounds ) + "]";
	}
	
	/**
	 * Vérifie si l'objet passé en paramètre a bien les mêmes valeurs que l'objet
	 * courant.
	 * @return Retourne vrai si l'objet est le même; sinon faux.
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Bet) {
			Bet bet = (Bet)obj;
			if(this.suit == bet.suit && this.nbRounds == bet.nbRounds) {
				return true;
			}
		}
		return false;
	}

}
