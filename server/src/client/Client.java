package client;

import exception.AlreadyConnectedException;
import exception.GameException;
import exception.InvalidBetException;
import exception.InvalidCardException;
import exception.NotYourTurnToBet;
import exception.TurnException;
import game.Bet;
import game.Hand;
import game.Player;
import game.card.Card;
import game.enumeration.Suit;

import java.rmi.RemoteException;
import java.util.ArrayList;

import server.ServerInterface;
import view.IView;
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
	private ServerInterface server;
	
	/**
	 * La vue (console ou graphique) à utiliser
	 */
	private IView view;
	
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
	 * Indique si la partie en cours est terminé
	 */
	private boolean gameFinished = false;
	
	/**
	 * Constructeur. Crée une nouvelle instance du client.
	 * 
	 * @param server Le serveur du jeu.
	 * @param view La vue à utiliser pour interagir avec le joueur.
	 * @throws RemoteException Erreurs RMI.
	 */
	public Client( ServerInterface server, IView view ) throws RemoteException {
		
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
	 * @throws GameException 
	 * @throws AlreadyConnectedException 
	 */
	public void connect() {
		try {
			this.server.connectClient( this, this.player );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void notifyPlayerTurn( Player player, Card card ) {
		
		this.view.showPlayerTurn( player, card );
		
	}

	/**
	 * Notification du tour au joueur courant.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * @throws GameException 
	 * @throws RemoteException 
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 * 
	 * @see ClientInterface#notifyYourTurn(Suit)
	 */
	@Override
	public void notifyYourTurn( Suit suit ) {
		
		Card card = this.view.getCardToPlay( this.player.getHand(), suit );
		this.player.getHand().playCard( card );
		
		try {
			this.server.playCard( this, card );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Notification de la déconnexion d'un joueur
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyPlayerDisconnect(Player)
	 */
	@Override
	public void notifyPlayerDisconnect(Player player) {
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
	public void notifyWinner(Player player, Player player2) {
		this.view.showWinners( player, player2 );
		this.gameFinished = true;

	}

	/**
	 * Notification de fermeture du serveur.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * 
	 * @see ClientInterface#notifyExit()
	 */
	@Override
	public void notifyExit() {
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
	public void notifyPlayerConnect(Player player) {
		
		this.view.playerConnected( player );	
		
	}

	/**
	 * Notification de la période de mise.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * @throws RemoteException 
	 * @throws InvalidBetException 
	 * @throws NotYourTurnToBet 
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 * 
	 * @see ClientInterface#notifyYourTurnToBet(Hand)
	 */
	@Override
	public void notifyYourTurnToBet( Hand hand ) throws RemoteException {
		
		this.player.setHand( hand );
		Player[] players = this.server.getPlayerList();
		this.view.setPlayerList( players );
		boolean validBet = false;
		
		Bet bet = null;
		
		while( !validBet ) {
			
			bet = this.view.askBet( hand );
			
			try {
				this.server.sendBet( this, bet );
				validBet = true;
			} catch( InvalidBetException e ) {
				this.view.showBetInvalid();
			} catch (GameException e) {
				e.printStackTrace();
			}
			
		}
		
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
	public void notifyStartNewGame(ArrayList<Player> players) {
		
		this.players = players;
		this.view.showGameStart( this.betWinner );
		
	}

	/**
	 * Notification de nouvelles cartes après avoir gagné une mise.
	 * Veuillez vous référer à la documentation de la classe ClientInterface
	 * pour plus de détails.
	 * @throws RemoteException 
	 * @throws InvalidCardException 
	 * @throws TurnException 
	 * 
	 * @see ClientInterface#notifyChangeCardsAfterBet(Card[])
	 */
	@Override
	public void notifyChangeCardsAfterBet( Card[] newCards ) {
		
		ArrayList<Card> cards = this.view.changeCards( this.player.getHand(), newCards );
		try {
			this.player.getHand().setCards( cards );
			this.server.sendNewHand( this, cards );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public boolean isGameFinished() {
		return this.gameFinished;
	}

	@Override
	public void notifyPlayerList(Player[] players) throws RemoteException {
		this.view.setPlayerList(players);
	}

	@Override
	public void notifyBettingTime() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
