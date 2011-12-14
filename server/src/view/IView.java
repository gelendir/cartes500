package view;

import java.util.ArrayList;

import database.PlayerStatistics;

import game.Bet;
import game.Hand;
import game.Player;
import game.card.Card;
import game.enumeration.Suit;

/**
 * Interface pour interagir avec le joueur humain. Dans la version
 * finale de ce projet, le joueur pourra choisir d'utiliser une interface graphique
 * ou une interface en mode texte (console). Cette classe représente la partie "Vue"
 * dans le modèle MVC de l'application client. Pour les besoins du protoype, cette interface
 * agit en mode "Dumb view". C'est-à-dire qu'elle attend de recevoir de l'information
 * à afficher au lieu d'interroger le modèle.
 * 
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public interface IView {
	
	/**
	 * Demande à l'utilisateur de créer un nouveau profil de joueur.
	 * Ce profil sera ensuite envoyé au serveur lors de la connexion.
	 * 
	 * @return Le profil du joueur.
	 */
	public Player createPlayer();
	
	/**
	 * Demande à l'utilisateur quel carte qu'il voudrait jouer lors de son
	 * tour. Cette carte sera ensuite envoyé au serveur.
	 * 
	 * @param hand La main du joueur (l'ensemble de cartes)
	 * @param suit La sorte demandé pour le tour
	 * @return La carte que le joueur veut jouer
	 */
	public Card getCardToPlay( Hand hand, Suit suit );
	
	/**
	 * Met à jour l'affichage pour indiquer ce qu'un joueur a
	 * déposé comme carte sur le jeu.
	 * 
	 * @param player Le joueur qui a joué
	 * @param card La carte déposé
	 */
	public void showPlayerTurn( Player player, Card card );

	/**
	 * Met à jour l'affichage pour indique qu'un joueur vient juste
	 * de se connecter au serveur.
	 * 
	 * @param player Le joueur qui c'est connecté
	 */
	public void playerConnected( Player player );
	
	/**
	 * Demande au joueur quel mise qu'il voudrait faire avant le début
	 * de la partie. Cette mise sera, par la suite, envoyé au serveur.
	 * 
	 * @param hand Les cartes du joueur. Nécessaire pour aider le joueur
	 * à faire une mise
	 * @return La mise fait par le joueur.
	 */
	public Bet askBet( Hand hand );
	
	/**
	 * Met à jour l'affichage pour indiquer à l'utilisateur qu'un joueur
	 * a fini de faire une mise avant le début de la partie.
	 * 
	 * @param player Le joueur ayant fait la mise
	 * @param bet La mise du joueur
	 */
	public void playerHasBet( Player player, Bet bet );

	/**
	 * Message de bienvenue affiché au joueur avant de se connecter au serveur.
	 * 
	 */
	public void welcome();
	
	/**
	 * Demande au joueur d'échanger ses cartes (si désiré) si ce joueur a la plus grande mise 
	 * de fait sur le jeu. 
	 * 
	 * @param oldHand Les anciennes cartes du joueur
	 * @param availableCards Les nouvelles cartes disponibles pour échanger
	 * @return La nouvelle main du joueur, nouvelles cartes incluses.
	 */
	public ArrayList<Card> changeCards( Hand oldHand, Card[] availableCards );
	
	/**
	 * Met à jour l'affichage pour indiquer quel joueur a la plus grande mise de fait à la 
	 * fin de la période de mises.
	 * 
	 * @param player Le joueur avec la plus grande mise.
	 * @param gameSuit L'atout du jeu, déterminé à partir de la mise du gagnant
	 */
	public void showBetWinner( Player player, Suit gameSuit );
	
	/**
	 * Indique à l'utilisateur que la partie démarrera sous peu.
	 * 
	 * @param first Le premier joueur à jouer dans le premier tour.
	 */
	public void showGameStart( Player first );
	
	/**
	 * Indique à l'utilisateur quels sont les joueurs qui ont gagné le jeu
	 * à la fin de la partie.
	 * 
	 * @param player Le premier gagnant
	 * @param player2 Le deuxième gagnant
	 */
	public void showWinners( Player player, Player player2 );
	
	/**
	 * Met à jour l'affichage pour indiquer quel joueur a remporté le tour,
	 * à la fin du tour.
	 * 
	 * @param player Le joueur ayant remporté le tour
	 */
	public void showTurnWinner( Player player );
	
	/**
	 * Met à jour la liste de joueurs 
	 * 
	 * @param players
	 */
	public void setPlayerList(Player[] players);
	
	/**
	 * Indique à l'interface que la mise jouée par le joueur est invalide.
	 * Cette méthode permet d'avertir le client que le bet n'est pas valide.
	 */
	public void showBetInvalid();
	
	/**
	 * Cette méthode indique qui est le prochain joueur à jouer.
	 * @param player Le prochain joueur à jouer.
	 */
	public void nextPlayer( Player player );

	/**
	 * Cette méthode envoie les statistiques sur le joueur actuel.
	 * @param playerStats Les statistiques du joueur.
	 */
	public void showStatistics(PlayerStatistics playerStats);
	
}
