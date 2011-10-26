package client;

import game.Bet;
import game.Card;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

import java.rmi.RemoteException;
import java.util.ArrayList;

import server.Server;
import view.AbstractView;
/**
 * Client du jeu de 500. La classe Client est en charge de se
 * connecter au serveur de jeu, d'agir en tant qu'intermédiaire entre
 * le serveur et le joueur humain et d'agir comme controleur pour l'interface
 * (console ou graphique) du jeu. La classe Client communique avec le serveur
 * en utilisant le patron de conception "Observeur Observé". Le client observe le serveur,
 * et reçoit des notifications d'événements lors des divers actions dans le jeu.
 * Les fonctions événements de la classe client sont préfixés avec le mot "notify".
 * Cette classe représente la partie "Controlleur" dans le modèle MVC de l'application
 * client.
 * 
 * @see ClientInterface
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class Client implements ClientInterface {
	
	/**
	 * Le serveur auquel ce client est connecté
	 */
	private Server server;
	
	/**
	 * La vue (console ou graphique) à utiliser
	 */
	private AbstractView view;
	
	/**
	 * Le profil du joueur géré par ce client
	 */
	private Player player;
	
	/**
	 * La liste des joueurs participant au jeu.
	 */
	private ArrayList<Player> players;
	
	/**
	 * Le gagnant de la mise.
	 */
	private Player betWinner = null;
	
	/**
	 * L'atout du jeu
	 * @See game.enumeration.Suit
	 */
	private Suit gameSuit;
	
	/**
	 * Constructeur. Crée une nouvelle instance du client.
	 * 
	 * @param server Le serveur du jeu.
	 * @param view La vue à utiliser pour interagir avec le joueur.
	 * @throws RemoteException Erreurs RMI.
	 */
	public Client( Server server, AbstractView view ) throws RemoteException {
		
		this.server = server;
		this.view = view;
		this.players = null;
		
		this.view.welcome();
		this.player = this.view.createPlayer();
		
	}
	
	/**
	 * Fonction appelé lorsque le client est prêt à se connecter au serveur.
	 * Le client s'enregistrera au près du serveur à travers RMI.
	 * 
	 * @return Si la connexion a réussi ou échoué.
	 * @throws RemoteException Erreurs RMI.
	 */
	public boolean connect() throws RemoteException {
		return this.server.connectClient( this, this.player );
	}

	/**
	 * Accesseur. Retourne le profil du joueur assoicé à ce client.
	 * 
	 * @return Le joueur.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Notification du tour d'un joueur.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyPlayerTurn(Player, Card)
	 */
	@Override
	public void notifyPlayerTurn( Player player, Card card )
			throws RemoteException {
		
		this.view.showPlayerTurn( player, card );
		
	}

	/**
	 * Notification du tour au joueur courant.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyYourTurn(Suit)
	 */
	@Override
	public Card notifyYourTurn( Suit suit ) {
		
		Card card = this.view.getCardToPlay( this.player.getHand(), suit );
		this.player.getHand().playCard( card );
		return card;

	}

	/**
	 * Notification de la déconnexion d'un joueur
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyPlayerDisconnect(Player)
	 */
	@Override
	public void notifyPlayerDisconnect(Player player) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Notification du gagnant du jeu.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyWinner(Player, Player)
	 */
	@Override
	public void notifyWinner(Player player, Player player2) throws RemoteException {
		this.view.showWinners( player, player2 );

	}

	/**
	 * Notification de fermeture du serveur.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyExit()
	 */
	@Override
	public void notifyExit() throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Notification de la connexion d'un joueur.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyPlayerConnect(Player)
	 */
	@Override
	public void notifyPlayerConnect(Player player) throws RemoteException {
		
		this.view.playerConnected( player );	
		
	}

	/**
	 * Notification de la période de mise.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyBettingTime(Hand)
	 */
	@Override
	public Bet notifyBettingTime( Hand hand ) throws RemoteException {
		
		this.player.setHand( hand );
		Bet bet = this.view.askBet( hand );
		return bet;
		
	}

	/**
	 * Notification de la mise d'un joueur.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyPlayerBet(Player, Bet)
	 */
	@Override
	public void notifyPlayerBet(Player player, Bet bet) {
		
		this.view.playerHasBet( player, bet );
		
		
	}

	/**
	 * Notification du démarrage d'une nouvelle partie.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyStartNewGame(ArrayList)
	 */
	@Override
	public void notifyStartNewGame(ArrayList<Player> players)
			throws RemoteException {
		
		this.players = players;
		this.view.showGameStart( this.betWinner );
		
	}

	/**
	 * Notification de nouvelles cartes après avoir gagné une mise.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyChangeCardsAfterBet(Card[])
	 */
	@Override
	public void notifyChangeCardsAfterBet( Card[] newCards ) {
		
		ArrayList<Card> cards = this.view.changeCards( this.player.getHand(), newCards );
		this.server.setNewHandAfterBet( this, cards );
		
	}

	/**
	 * Notification du gagnant de la mise.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyBetWinner(Player, Suit)
	 */
	@Override
	public void notifyBetWinner(Player player, Suit gameSuit) {
		this.gameSuit = gameSuit;
		this.betWinner = player;
		this.view.showBetWinner( player, gameSuit );
	}

	/**
	 * Notification du gagnant du tour.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyTurnWinner(Player)
	 */
	@Override
	public void notifyTurnWinner(Player player) {
		this.view.showTurnWinner( player );
	}

}
