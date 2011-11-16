package game;

import exception.InvalidCardException;
import exception.TurnException;
import game.card.Card;
import game.card.CardComparator;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Un tour lors d'une partie de cartes.
 * Utilitaire permettant d'accumuler les cartes déposés par chaque joueur lors
 * d'une partie et de déterminer le gagnant du tour lorsque chaque joueur a déposé sa carte.
 * Un tour ne peut être utilisé plus d'une fois. Une nouvelle instance de cette classe
 * doit être crée pour chaque nouveau tour dans le jeu.
 * À la fin du tour, il est de la responsabilité du serveur de noter le gagnant du tour 
 * et de disposer de l'instance. 
 * 
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class Turn {
	
	/**
	 * Le nombre maximum de cartes qui peuvent être déposés dans un tour.
	 */
	final private static int MAX_CARDS = 4;
	
	/**
	 * Ensemble associatif indiquant quel joueur a déposé quel carte.
	 * L'ensemble est doublement chaîne à fin de noter l'ordre dans lequel 
	 * les joueurs ont déposé leurs cartes.
	 */
	private LinkedHashMap<Player, Card> cards;
	
	/**
	 * Le dernier joueur du tour à avoir déposé une carte
	 */
	private Player latestPlayer = null;
	
	/**
	 * La dernière carte à être déposé durant ce tour.
	 */
	private Card latestCard = null;
	
	/**
	 * L'atout de la partie. Utilisé à la fin du tour pour déterminer
	 * quel carte qui a été déposé est la plus forte.
	 */
	private Suit gameSuit = null;

	
	/**
	 * Constructeur. Crée un nouveau tour prêt à reçevoir des cartes des joueurs.
	 * 
	 * @param suit L'atout de la partie.
	 */
	public Turn(Suit suit) {
		this.cards = new LinkedHashMap<Player, Card>( Turn.MAX_CARDS );
		this.gameSuit = suit;
	}
		
	/**
	 * Permet de d'ajouter une carte au tour. En d'autres mots, permet à un joueur
	 * de déposer sa carte sur la table. Un joueur peut seulement déposer une seule carte
	 * durant le tour.
	 * 
	 * @param player Le joueur qui a déposé la carte
	 * @param card La carte à jouer lors de ce tour
	 * @throws TurnException Erreur si le joueur a déja joué une carte 
	 * ou si tout les joueurs ont déja joué
	 */
	public void addCard( Player player, Card card ) throws TurnException {
		
		if( this.cards.size() >= Turn.MAX_CARDS ) {
			throw new TurnException("Cannot add more cards to this turn");
		} else if ( this.cards.containsKey( player ) ) {
			throw new TurnException("Player has already played a card");
		}
		
		this.cards.put( player, card );
		this.latestPlayer = player;
		this.latestCard = card;
		
	}
	
	/**
	 * Accesseur. Retourne la suite forte du tour. La suite d'un tour est déterminé
	 * par la première carte déposé dans le tour. Si aucune carte avec l'atout de la partie
	 * a été déposé, la suite du tour devient l'atout lorsque viens le temps de déterminer
	 * la carte la plus forte.
	 * 
	 * @return La suite du tour, null si aucune carte n'a été déposée.
	 */
	public Suit getTurnSuit() {
		
		if( this.cards.size() == 0 ) {
			return null;
		}
		
		Iterator<Card> iterator = this.cards.values().iterator();
		Card card = iterator.next();
		
		if( this.cards.size() <= 1 && card.getCardValue() == CardValue.JOKER ) {
			return null;
		}
		
		while( card.getCardValue() == CardValue.JOKER ) {
			card = iterator.next();
		}
		
		return card.getSuit();
		
	}
	
	/**
	 * Accesseur. Retourne le nombre de cartes qui ont été déposés dans le tour.
	 * @return Entier représentant le nombre de cartes déposés.
	 */
	public int nbCards() {
		return this.cards.size();
	}
	
	/**
	 * Représentation textuelle des cartes déposés dans ce tour.
	 * Utilisé lorsqu'un joueur joue en console.
	 * 
	 * @return Représentation du tour sous forme de chaîne de charactères.
	 */
	public String toString() {
		String result = "";
		
		for( Map.Entry<Player, Card> entry : this.cards.entrySet() ) {
			result += entry.getKey().toString() + " - " + entry.getValue().toString() + "\n";
		}
		
		return result;
	}
	
	
	/**
	 * Accesseur. Retourne le dernier joueur à avoir déposé une carte dans le tour. 
	 * @return Le dernier jouer à avoir déposé une carte.
	 */
	public Player getLatestPlayer() {
		return this.latestPlayer;
	}
	
	/**
	 * Accesseur. Retourne la dernière carte à avoir été déposé dans le tour.
	 * @return La dernière carte à avoir été déposé.
	 */
	public Card getLatestCard() {
		return this.latestCard;
	}
	
	/**
	 * Utilitaire permettant de filtrer toutes les cartes 
	 * d'une certaine sorte dans le tour. Utilisé dans la fonction
	 * pour déterminer qui a gagné le tour.
	 * 
	 * @param suit La suite à filtrer. Seulement les cartes de cette
	 * suite seront retournés.
	 * @return Les cartes filtrés et triés en ordre croissant de valeur.
	 */
	private ArrayList<Card> filterCards( Suit suit ) {
		
		ArrayList<Card> cards = new ArrayList<Card>();
		for( Card c : this.cards.values() ) {
			if( c.getSuit().equals( suit ) ) {
				cards.add( c );
			}
		}
		
		CardComparator comparator = new CardComparator();
		Collections.sort(cards, comparator);
		
		return cards;
	}
	
	/**
	 * Utilitaire permettant de retrouver le joueur ayant déposé une carte.
	 * Utilisé dans la fonction pour déterminer qui a gagné le tour.
	 *  
	 * @param card La carte à chercher.
	 * @return Le joueur ayant déposé la carte recherché.
	 */
	private Player playerFromCard( Card card ) {
		
		for( Map.Entry<Player, Card> entry : this.cards.entrySet() ) {
			if ( entry.getValue().equals( card ) ) {
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	/**
	 * Ajoute une partie de plus au nombres de parties remportés par le gagnant du tour.
	 * Utilisé à la fin du tour pour mettre à jour le pointage de la partie.
	 * 
	 * @throws TurnException Retourne les mêmes exceptions que getWinner()
	 * @throws InvalidCardException 
	 * @see getWinner
	 */
	public void incrementWinner() throws TurnException, InvalidCardException {
		
		Player winner = this.getWinner();
		winner.addTurnWin();
		
	}
	
	/**
	 * Détermine le joueur ayant déposé la carte la plus forte à la fin d'un tour.
	 * Les cartes sont triés en ordre de valeur selon l'ordre de priorité suivant :
	 * 
	 * 	- Le Joker de couleur
	 * 	- Le Joker noir
	 * 	- Le Valet d'atout
	 *  - Le Valet de la même couleur que l'atout
	 *  - La carte la plus forte de l'atout
	 *  - La carte la plus forte de la suite du tour.
	 *  
	 *  Si la partie se joue sans atout, alors l'ordre de priorité devient le suivant :
	 *  
	 * 	- Le Joker de couleur
	 * 	- Le Joker noir
	 *  - La carte la plus forte de la suite du tour
	 *  - La carte la plus forte de Pique
	 *  - La carte la plus forte de Trèfle
	 *  - La carte la plus forte de Carreau
	 *  - La carte la plus forte de Coeur
	 *  
	 * @return Le joueur ayant déposé la carte la plus forte.
	 * @throws TurnException Erreur si des joueurs n'ont pas encore déposé leur cartes.
	 * @throws InvalidCardException 
	 */
	public Player getWinner() throws TurnException, InvalidCardException {
		
		if( this.cards.size() != Turn.MAX_CARDS ) {
			throw new TurnException("Not all players have played a card in this turn");
		}
		
		Suit gameSuit = this.gameSuit;
		Suit turnSuit = this.getTurnSuit();
				
		Card colorJoker = new Card( Suit.COLOR, CardValue.JOKER );
		Card blackJoker = new Card( Suit.BLACK, CardValue.JOKER );
		
		ArrayList<Card> strongCards = null;
		
		Card search = null;
				
		Card strongJack = null;
		Card weakJack = null;
		
		//If this turn has a game suit, then the jacks of the same
		//color as the game suit have priority over other cards
		if( !gameSuit.equals( Suit.NONE ) ) {
			
			strongJack = new Card( gameSuit, CardValue.JACK );
				
			if( gameSuit == Suit.CLUBS ) {
				weakJack =  new Card( Suit.SPADES, CardValue.JACK );
			} else if ( gameSuit == Suit.SPADES ) {
				weakJack =  new Card( Suit.CLUBS, CardValue.JACK );
			} else if ( gameSuit == Suit.DIAMONDS ) {
				weakJack =  new Card( Suit.HEARTS, CardValue.JACK );
			} else if ( gameSuit == Suit.HEARTS ) {
				weakJack =  new Card( Suit.DIAMONDS, CardValue.JACK );
			}
		}
		
		//Determine if a player has a joker or a jack
		if ( this.cards.containsValue( colorJoker ) ) {
			search = colorJoker;
		} else if ( this.cards.containsValue( blackJoker ) ) {
			search = blackJoker;
		} else if( strongJack != null && this.cards.containsValue( strongJack ) ) {
			search = strongJack;
		} else if ( weakJack != null && this.cards.containsValue( weakJack ) ) {
			search = weakJack;
		}
		
		if( search != null ) {
			return this.playerFromCard( search );
		}
		
		if( gameSuit.equals( Suit.NONE ) ) {
			gameSuit = turnSuit;
		}
						
		strongCards = this.filterCards( gameSuit );
		if( strongCards.size() > 0 ) {
			return this.playerFromCard( strongCards.get( strongCards.size() - 1 ) );
		}
			
		strongCards = this.filterCards( turnSuit );
		return this.playerFromCard( strongCards.get( strongCards.size() - 1 ) );
		
	}
}
