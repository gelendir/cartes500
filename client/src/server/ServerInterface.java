package server;

import exception.AlreadyConnectedException;
import exception.GameException;
import exception.InvalidBetException;
import exception.InvalidCardException;
import exception.NotYourTurnToBet;
import exception.ServerException;
import exception.TurnException;
import game.Bet;
import game.Player;
import game.card.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import client.Client;

/**
 * Interface Serveur du jeu. Cette interface est utilisé par
 * le registraire RMI à fin de permettre à des clients d'interagir 
 * directement avec le serveur de manière transparente. Veuillez vous 
 * référer à la documentation de la classer Server pour plus de détails
 * sur la responsabilité du serveur.
 * 
 * @see Server
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public interface ServerInterface extends Remote {
	
	/**
	 * Connexion d'un client au serveur. Pour se connecter, un client
	 * doit récupérer le serveur à travers RMI puis s'authentifier avec
	 * cette méthode. Un client doit se connecter avant de pouvoir participer
	 * à une partie de cartes.
	 * 
	 * @param client Le client qui se connecte
	 * @param player Le profil du joueur qui se connecte
	 * @return Retourne vrai si la connexion a réussi, faux si le client est déja connecté
	 * ou si un joueur avec le même profil est déja présent.
	 * @throws RemoteException Erreurs RMI.
	 */
	public void connectClient( Client client, Player player ) throws AlreadyConnectedException, GameException, RemoteException;
	
	/**
	 * Carte joué par un joueur. Un client appelle cette méthode lorsqu'un joueur
	 * veut déposer une carte sur la table. Un client peut seulement déposer une carte
	 * lors de son tour. Lorsqu'un client dépose une carte, les autres clients sont
	 * notifiés de quel joueur a joué quel carte.
	 * 
	 * @param client Le client à déposer une carte
	 * @param card La carte à jouer
	 * @throws RemoteException Erreurs RMI.
	 * @throws TurnException 
	 * @throws InvalidCardException 
	 * @throws GameException 
	 */
	public void playCard( Client client, Card card ) throws GameException;
	
	/**
	 * Déconnexion d'un client. Méthode appelé par un client lorsqu'il veut se
	 * déconnecter du serveur. Un client peut se déconnecter à n'importe quel moment durant le jeu. 
	 * Si un client se déconnecte avant la fin de la partie, le jeu ne peut continuer 
	 * (car une partie de cartes doit obligatoirement avoir 4 joueurs). 
	 *  
	 * @param client Le client qui veut se déconnecter
	 * @throws RemoteException Erreurs RMI.
	 */
	public void disconnectClient( Client client ) throws RemoteException;
	
	/**
	 * Mise d'un joueur. Méthode appelé lorsqu'un client est prêt à faire une mise. Une mise est fait lorsque le serveur
	 * a distribué des cartes à chaque joueur. Un client ne peut faire une mise avant que ça soit son tour.
	 * 
	 * @param client Le client qui veut faire une mise
	 * @param bet La mise du joueur
	 * @throws RemoteException Erreurs RMI.
	 * @throws NotYourTurnToBet 
	 * @throws InvalidBetException 
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 */
	public void sendBet( Client client, Bet bet ) throws GameException;
	
	/**
	 * Accesseur du joueur courant. Retourne le joueur courant, c'est-à-dire le prochain joueur à jouer une carte
	 * dans la partie. À chaque fois qu'un joueur dépose une carte, le joueur courant avance au prochain
	 * joueur dans le tour. 
	 * 
	 * @return Le joueur courant.
	 * @throws RemoteException Erreurs RMI.
	 */
	public Player getCurrentPlayer() throws RemoteException;
	
	/**
	 * Accesseur du gagnant. Retourne le joueur ayant remporté le plus de tours à la fin de la partie.
	 * Cette fonction retournera seulement un résultat à la fin de la partie.
	 * 
	 * @return Le joueur avec le plus de tours remportés, null si la partie n'est pas terminé
	 * @throws RemoteException Erreurs RMI.
	 */
	public Player[] getWinners() throws RemoteException;
	
	/**
	 * Accesseur du score. Retourne le score du joueur ayant remporté le plus de parties. Cette fonction
	 * retournera seulement un résultat à la fin de la partie.
	 * 
	 * TODO: This looks broken. Re-read rules to see how score interacts with bets.
	 *  
	 * @return Le score du joueur ayant gagné la partie.
	 * @throws RemoteException Erreurs RMI.
	 */
	public int getScore() throws RemoteException;
	
	/**
	 * Mutateur de la nouvelle main. Lorsqu'un joueur remporte la mise, il a le droit de changer
	 * les cartes de sa main avec de nouvelles cartes distribué par le serveur. Lorsque le client
	 * a fini de choisi les nouvelles cartes, il signale au serveur sa nouvelle main avec cette méthode.
	 * 
	 * @param client Le client qui a remporté la mise
	 * @param cards Toutes les cartes dans la main du joueur.
	 * @throws RemoteException Erreurs RMI.
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 */
	public void sendNewHand( Client client, ArrayList<Card> cards ) throws RemoteException, TurnException, InvalidCardException;
	
	public Player[] getPlayerList();
}
