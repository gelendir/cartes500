package client;

import game.Bet;
import game.Card;
import game.Hand;
import game.Player;
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
	 */
	public Card notifyYourTurn( Suit suit ) throws RemoteException;
	
	public void notifyPlayerDisconnect( Player player ) throws RemoteException;
	
	public void notifyPlayerConnect( Player player ) throws RemoteException;
	
	public Bet notifyBettingTime( Hand hand ) throws RemoteException;
	
	public void notifyWinner( Player player, Player player2 ) throws RemoteException;
	
	public void notifyExit() throws RemoteException;
	
	public void notifyPlayerBet( Player player, Bet bet );
	
	public void notifyChangeCardsAfterBet( Card[] newCards );
	
	public void notifyBetWinner( Player player, Suit gameSuit );
	
	public void notifyTurnWinner( Player player );
	
}
