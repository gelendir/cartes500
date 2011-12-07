package client;

import exception.InvalidBetException;
import exception.InvalidCardException;
import exception.NotYourTurnToBet;
import exception.TurnException;
import game.Bet;
import game.Hand;
import game.Player;
import game.card.Card;
import game.enumeration.Suit;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Interface Client du jeu. Cette interface est utilisé par
 * le registraire RMI à fin de permettre à des clients d'interagir 
 * directement avec le serveur de manière transparente. Veuillez vous 
 * référer à la documentation de la classe Client pour plus de détails
 * sur la responsabilité du client.
 * 
 * @see Client
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public interface ClientInterface extends Remote {
	
	/**
	 * Événement lancé lorsque le serveur démarre la partie. Une partie
	 * peut seulement être démarré une fois que tout les clients ont fait une mise.
	 * 
	 * @param players L'ordre dans lequel les joueurs se sont connectés.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyStartNewGame( ArrayList<Player> players ) throws RemoteException;
	
	/**
	 * Événement lancé lorsqu'un joueur dans la partie a déposé une carte. Un
	 * joueur peut seulement déposer une carte lorsque c'est son tour de jouer.
	 * 
	 * @param player Le joueur ayant déposé la carte.
	 * @param card La carte déposé par le joueur.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyPlayerTurn( Player player, Card card ) throws RemoteException;
	
	/**
	 * Événement lancé lorsque c'est au tour de ce client de jouer une carte.
	 * Cette fonction retourne aussi la carte que ce client veut déposer sur le jeu.
	 * 
	 * @param suit La suite du tour. Utilisé pour indiquer au client quels cartes
	 * qu'il peut jouer.
	 * @return La carte que ce client veut jouer.
	 * @throws RemoteException Erreurs RMI
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 */
	public void notifyYourTurn( Suit suit ) throws RemoteException;
	
	/**
	 * Événement lancé lorsqu'un joueur se déconnecte du serveur. Une partie
	 * ne peut continuer s'il manque un joueur, donc si un joueur se déconnecte 
	 * avant la fin de la partie, le jeu ne peut plus continuer.
	 * 
	 * TODO: Pour les besoins du prototype cette fonction n'est pas encore
	 * implémenté.
	 * 
	 * @param player Le joueur qui c'est déconnecté.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyPlayerDisconnect( Player player ) throws RemoteException;
	
	/**
	 * Événement lancé lorsqu'un joueur se connecte au serveur. Un joueur peut
	 * seulement se connecter avant le début d'une partie.
	 * 
	 * @param player Le joueur de connecté
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyPlayerConnect( Player player ) throws RemoteException;
	
	/**
	 * Événement lancé lorsqu'il est rendu temps aux joueurs de faire une mise.
	 * La période de mise peut seulement s'enclencher après la connexion de tout
	 * les joueurs. Une mise ne peut être fait après le début de la partie. C'est
	 * aussi lors de cet événement que le serveur distribue les cartes à chaque joueur.
	 *  
	 * @param hand Les cartes distribués au joueur.
	 * @return La mise fait par ce client.
	 * @throws RemoteException Erreurs RMI.
	 * @throws InvalidBetException 
	 * @throws NotYourTurnToBet 
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 */
	public void notifyBettingTime( Hand hand ) throws RemoteException;
	
	/**
	 * Événement lancé à la fin d'une partie pour indiquer les joueurs qui ont
	 * gagné le jeu. Cet événement est appelé seulement lorsque tout les joueurs 
	 * ont joué dix tours.
	 * 
	 * @param player Le premier joueur de l'équipe à avoir gagné
	 * @param player2 Le deuxième joueur de l'équipe à avoir gagné.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyWinner( Player player, Player player2 ) throws RemoteException;
	
	/**
	 * Événement lancé lorsque le serveur s'apprête à se fermer (Shutdown).
	 * Tout les clients doivent se déconnecter en recevant cet événement.
	 * 
	 * TODO: Pour les besoins du protoype, cette fonction n'est pas encore implémenté.
	 * 
	 * @throws RemoteException
	 */
	public void notifyExit() throws RemoteException;
	
	/**
	 * Événement lancé lorsqu'un joueur fait une mise. Un joueur peut seulement
	 * faire une mise lorsque c'est son tour de le faire.
	 * 
	 * @param player Le joueur à avoir misé
	 * @param bet La mise du joueur.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyPlayerBet( Player player, Bet bet ) throws RemoteException;
	
	/**
	 * Événement lancé lorsqu'un joueur a remporté la mise et qu'il peut maintenant
	 * changer les cartes dans sa main si désiré. Cet événement retourne aussi les 
	 * nouvelles cartes de la main pour ce joueur, après avoir sélectionné les nouvelles
	 * cartes.
	 * 
	 * @param newCards Nouvelles cartes disponibles.
	 * @throws RemoteException Erreurs RMI.
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 */
	public void notifyChangeCardsAfterBet( Card[] newCards ) throws RemoteException;
	
	/**
	 * Événement lancé lorsque la période de mises est terminé. Cet événement
	 * signale au client lequel des joueurs a remporté la mise, et donc lequel 
	 * des joueurs commencera le premier tour de la partie.
	 * 
	 * @param player Le joueur avec la plus haute mise.
	 * @param gameSuit L'atout du jeu, déterminé à partir de la mise du gagnant
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyBetWinner( Player player, Suit gameSuit ) throws RemoteException;
	
	/**
	 * Événement lancé à la fin d'un tour pour indiquer quel joueur a remporté le tour.
	 * Le gagnant sera le joueur qui débutera le prochain tour
	 * 
	 * @param player Le joueur ayant remporté le tour.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void notifyTurnWinner( Player player ) throws RemoteException;
	
}
