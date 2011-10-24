package server;

import game.Bet;
import game.Card;
import game.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

import client.Client;

/**
 * Serveur du jeu. Classe en charge de gérer les connexions, 
 * les événements venant des clients (joueurs) et de mettre 
 * à jour l'état du jeu selon les réponses reçues. Le serveur
 * commence à l'état "en attente", c'est-à-dire qu'il attend que
 * 4 clients se connecent. Ensuite, il démarre le jeu, distribue
 * les cartes à chaque joueur, et fait avancer le jeu selon
 * ce que chaque client joue comme carte. 
 * 
 * @see ServerInterface
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class Server implements ServerInterface {

	/**
	 * Connexion d'un client au serveur. 
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#connectClient(Client, Player)
	 */
	@Override
	public boolean connectClient(Client client, Player player)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Carte joué par un joueur.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#playCard(Client, Card)
	 */
	@Override
	public void playCard(Client client, Card card) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Déconnexion d'un client.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#disconnectClient(Client)
	 */
	@Override
	public void disconnectClient(Client client) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Accesseur du joueur courant.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#getCurrentPlayer()
	 */
	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Accesseur du gagnant.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#getCurrentPlayer()
	 */
	@Override
	public Player getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Accesseur du score.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#getScore()
	 */
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Mise d'un joueur.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#setBetForPlayer(Client, Bet)
	 */
	@Override
	public void setBetForPlayer(Client client, Bet bet) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Mutateur de la nouvelle main.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#setNewHandAfterBet(Client, ArrayList)
	 */
	@Override
	public void setNewHandAfterBet(Client client, ArrayList<Card> cards) {
		// TODO Auto-generated method stub
		
	}

}
