package game;

import exception.DoesNotHaveCardException;
import game.card.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Joueur dans le jeu de 500.
 * Représente le profil d'un joueur humain qui participe à une partie de cartes 500.
 * Cette classe regroupe aussi d'autres informations reliés à un joueur comme le
 * nombre de tours remportés durant une partie, les cartes dans sa main et la mise
 * du joueur.
 * 
 * @see Hand
 * @see Bet
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class Player implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7950307699192824310L;

	/**
	 * La main du joueur. Contient les cartes qu'un joueur peut jouer.
	 */
	private transient Hand hand = null;
	
	/**
	 * La mise fait pair le joueur au début de la partie.
	 */
	private Bet bet = null;
	
	/**
	 * Le nom du joueur.
	 */
	private String name = "";
	
	/**
	 * Le nombre de tours que le joueur a remporté.
	 */
	private int turnWin = 0;
	
	/**
	 * Constructeur. Crée un nouveau profil pour un joueur.
	 * @param name Le nom du joueur.
	 */
	public Player(String name) {
		this.name = name;
	}
	
	/**
	 * Accesseur. Retourne le nom du joueur.
	 * @return Le nom du joueur sous forme de chaîne de charactères.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Mutateur. Associe une mise à un joueur. Un joueur est en mesure
	 * de faire une mise après avoir reçu les cartes dans sa main.
	 * 
	 * @param bet La mise du joueur.
	 */
	public void setOriginalBet(Bet bet) {
		this.bet = bet;
	}
	
	/**
	 * Accesseur. Retourne la mise que le joueur a fait en début de partie.
	 * Retourne null si le joueur n'a pas encore misé.
	 * 
	 * @return La mise du joueur, null si aucune mise.
	 */
	public Bet getOriginalBet() {
		return this.bet;
	}
	
	/**
	 * Mutateur. Associe une main de cartes à un joueur. Un joueur reçoit
	 * sa main après s'être connecté au serveur.
	 * 
	 * @param hand La main du joueur.
	 */
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	/**
	 * Accesseur. Retourne la main (ainsi que les cartes) du joueur.
	 * Retourne null si le joueur n'a pas encore reçu sa main.
	 * 
	 * @return La main du joueur, null si aucune main.
	 */
	public Hand getHand() {
		return this.hand;
	}
	
	/**
	 * Accesseur. Retourne le nombre de manches que le joueur 
	 * a remporté lors d'une partie.
	 * 
	 * @return Entier représentant le nombre de manches gagnés.
	 */
	public int getTurnWin() {
		return this.turnWin;
	}
	
	/**
	 * Incrémente (de 1) le nombre de manches qu'un joueur a remporté.
	 * Utilisé à la fin d'un tour si le joueur a déposé la carte la plus forte
	 * du tour.
	 */
	public void addTurnWin() {
		++this.turnWin;
	}
		
	/**
	 * Représentation sous forme de chaîne de caractères du profil d'un joueur.
	 * Utilisé surtout lorsqu'un joueur joue en mode console.
	 * 
	 * @return Chaîne de charactères représentant le nom du joueur.
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * Méthode de comparaison. Permet de comparer de profils et d'indiquer si c'est le même joueur.
	 * Pour les besoins du prototype, seulement le nom du joueur est utilisé pour déterminer l'égalité
	 * entre deux profiles de joueur.
	 * 
	 * @param player Le joueur à comparer.
	 * @return Retourne vrai si les deux profiles représentent le même joueur.
	 */
	public boolean equals( Player player ) {
		
		return this.name.equals( player.name );
		
	}
	
	public boolean hasCard( Card card ) {
		return this.hand.hasCard(card);
	}
	
	public void playCard( Card card ) throws DoesNotHaveCardException {
		
		if( !this.hasCard(card) ) {
			throw new DoesNotHaveCardException();
		}
		
		this.hand.playCard(card);
	}
	
}
