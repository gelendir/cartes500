package server;

import exception.AlreadyConnectedException;
import exception.GameException;
import exception.InvalidBetException;
import exception.InvalidCardException;
import exception.NotYourTurnToBet;
import exception.ServerException;
import exception.ServerStateException;
import exception.TurnException;
import exception.UnexpectedBehaviorException;
import game.Bet;
import game.Game;
import game.Player;
import game.Turn;
import game.card.Card;
import game.card.Deck;
import game.enumeration.Suit;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import view.ConsoleView;

import client.Client;
import client.ClientInterface;

/**
 * Serveur du jeu de 500. Classe en charge de gérer les connexions, 
 * les événements venant des clients (joueurs) et de mettre 
 * à jour l'état du jeu selon les réponses reçues. Le serveur communique 
 * avec les clients en utilisant le patron de conception "Observeur Observé". 
 * Le serveur commence à l'état "en attente", c'est-à-dire qu'il attend que
 * 4 clients se connectent. Ensuite, il démarre le jeu, distribue
 * les cartes à chaque joueur, et fait avancer le jeu selon
 * ce que chaque client joue comme carte. 
 * 
 * @see ServerInterface
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class Server implements ServerInterface, Runnable {
	
	/**
	 * Nom du du fichier .properties à récupérer
	 */
	static private String BUNDLE = "server";

	/**
	 * Table d'association entre le client le joueur de connecté.
	 * On utilise un LinkedHashMap pour conserver l'ordre de connexion
	 * des joueurs.
	 */
	private LinkedHashMap<ClientInterface, Player> clients 	= null;
	
	/**
	 * État du joueur. Utilisé pour implémenter une state machine minimaliste
	 */
	private ServerState state 						= ServerState.CONNECT;
	
	private ServerState nextState 					= null;
	
	/**
	 * Instance de la partie en cours. Utilisé comme partie "Modèle" d'une
	 * architecture MVC.
	 */
	private Game game								= null;
	
	/**
	 * Ressource pour récupérer les libellés de texte. 
	 */
	private PropertyResourceBundle bundle;
	
	/**
	 * Constructeur de la classe. Crée une nouvelle instance du serveur.
	 */
	public Server() {
		
		this.bundle = (PropertyResourceBundle) ResourceBundle.getBundle( Server.BUNDLE );
		this.clients = new LinkedHashMap<ClientInterface, Player>( Game.MAX_PLAYERS );
		this.state = ServerState.CONNECT;
		this.nextState = ServerState.CONNECT;
		this.game = null;
			
	}
	
	
	@Override
	public void run() {
		
		while( this.state != ServerState.DISCONNECT ) {
			
			synchronized( this ) {
				
				if( this.state == this.nextState  ) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						this.nextState = ServerState.DISCONNECT;
					}
				}
				
				this.state = this.nextState;
				
			}
			
			if( this.state == ServerState.BET ) {
				
				try {
					this.startBets();
				} catch (RemoteException e) {
					this.state = ServerState.DISCONNECT;
				}
				
			} else if( this.state == ServerState.DISTRIBUTE_SECRET_HAND ) {
				
				try {
					this.distributeSecretHand();
				} catch (RemoteException e) {
					this.state = ServerState.DISCONNECT;
				}
				
			} else if ( this.state == ServerState.GAME ) {
				
				try {
					this.startGame();
				} catch (RemoteException e) {
					this.state = ServerState.DISCONNECT;
				}
				
			} else if ( this.state == ServerState.END ) {
				
				try {
					this.endGame();
				} catch (RemoteException e) {
					this.state = ServerState.DISCONNECT;
				}
				
			}
			
		}
		
	}
	
	private void changeState( ServerState state ) {
		
		synchronized( this ) {
			this.nextState = state;
			this.notify();
		}
		
	}
	
	
	
	/**
	 * Cette fonction sert à implémenter une "state machine" minimaliste 
	 * sur le serveur. Le serveur contient plusieurs états qui représentent l'avancement
	 * des phases du jeu (Par exemple: phase de connexion, phase de distribution des cartes,
	 * phase de la mise, etc). Durant chaque état, le client a le droit d'accomplir un nombre
	 * limité d'actions sur le serveur. Cette fonction permet de vérifier si l'action demandé 
	 * par le client est permi selon l'état du serveur. Si l'état est invalide, le serveur lancera
	 * une exception au client.
	 * 
	 * @param askedState L'état dans lequel le serveur doit se retrouver pour permettre l'action.
	 * @throws ServerStateException Exception indiquant que l'action demandé est impossible dans l'état actuel du serveur. 
	 */
	private void assertState( ServerState askedState ) {
		
		ServerState currentState = this.state;
		
		if( !this.state.equals( state ) ) {
			
			String msg = MessageFormat.format( 
					this.bundle.getString("stateException"), 
					currentState.toString(), 
					askedState.toString() 
					);
			
			throw new ServerStateException( msg );
		}
		
	}
	
	/**
	 * Voir la documentation de la fonction au même nom.
	 * 
	 * @param askedStates Permet de vérifier plusieurs états à la fois.
	 */
	public void assertState( ServerState[] askedStates ) {
		
		for( ServerState state: askedStates ) {
			this.assertState( state );
		}
		
	}

	/**
	 * Connexion d'un client au serveur. 
	 * Voir la classe ServerInterface pour plus de détails.
	 * @throws RemoteException 
	 * @see ServerInterface#connectClient(Client, Player)
	 */
	@Override
	public void connectClient(ClientInterface client, Player player) throws RemoteException {
		
		this.assertState( ServerState.CONNECT );
		
		//Vérification si le client est déja connecté au serveur
		if( 
			   this.clients.containsKey( client )
			|| this.clients.containsValue( player )
		) 
		{
			throw new AlreadyConnectedException();
		}
		
		this.clients.put( client,  player );
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {
			if( clientToNotify != client ) {
				clientToNotify.notifyPlayerConnect( player );
			}
			
		}
		
		//Si tous les joueurs se sont connectés, démarrer la phase des mises.
		if( this.clients.size() == Game.MAX_PLAYERS ) {
			this.sendPlayerList();
			this.changeState( ServerState.BET );
		}
		
	}
	
	private void sendPlayerList() throws RemoteException {
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {
			clientToNotify.notifyPlayerList( this.getPlayerList() );
		}
		
	}

	/**
	 * Fonction utilitaire permettant de notifier tous les joueurs qu'ils doivent maintenant
	 * émettre une mise au serveur
	 * @throws RemoteException 
	 */
	private void startBets() throws RemoteException {
		
		this.assertState( ServerState.BET );
		
		Player[] players = new Player[ this.clients.size() ];
		int counter = 0;
		
		for( Player player: this.clients.values() ) {
			players[counter] = player;
			counter++;
		}
		
		Deck deck = new Deck();
		
		this.createGame( players, deck );
			
		for( Map.Entry<ClientInterface, Player> entry : this.clients.entrySet() ) {
			
			ClientInterface client = entry.getKey();
			Player player = entry.getValue();
			
			client.notifyYourTurnToBet( player.getHand() );
			
		}
		
	}
	
	/**
	 * Fonction utilitaire permettant de créer une nouvelle partie pour le jeu de cartes.
	 * La fonction muselle les exceptions de la classe Game car l'instance du jeu est 
	 * seulement crée lorsque tout les joueurs sont connectés.
	 * @param players
	 * @param deck
	 */
	private void createGame( Player[] players, Deck deck )
	{
		try {
			this.game = new Game( players, deck );
		} catch (GameException e) {
			throw new UnexpectedBehaviorException( e );
		}
	}

	/**
	 * Carte joué par un joueur.
	 * Voir la classe ServerInterface pour plus de détails.
	 * 
	 * @see ServerInterface#playCard(Client, Card)
	 * @throws GameException Exception lancé si le joueur a déposé une carte invalide.
	 * @throws RemoteException 
	 */
	@Override
	public void playCard(ClientInterface client, Card card) throws GameException, RemoteException {
		
		this.assertState( ServerState.GAME );
		
		Player player = this.clients.get( client );
		
		this.game.playCard( player, card );
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {
			clientToNotify.notifyPlayerTurn( player, card );
		}
		
		if( this.game.isTurnFinished() ) {
			this.startNewTurn();
		}
		
		if( this.game.isGameFinished() ) {
			
			this.changeState( ServerState.END );
			
		} else {
			
			ClientInterface next = this.clientForPlayer( game.nextPlayer() );
			next.notifyYourTurn( this.game.getTurnSuit() );
		
		}	
		
	}
	
	/**
	 * Fonction utilitaire pour déclencher les procédures de fin de partie.
	 * Détermine qui a gagné la partie et informe les joueurs.
	 * 
	 * @throws RemoteException Erreurs RMI.
	 */
	private void endGame() throws RemoteException {
		
		this.assertState( ServerState.END );
		
		Player[] winners = this.game.getWinners();
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {
			clientToNotify.notifyWinner( winners[0], winners[1] );
		}
		
	}

	/**
	 * Fonction utilitaire permettant de démarrer un nouveau tour.
	 * @throws RemoteException 
	 */
	public void startNewTurn() throws RemoteException {
		
		Player turnWinner = this.fetchTurnWinner();
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {
			clientToNotify.notifyTurnWinner( turnWinner );
		}
		
		this.createNewTurn();
			
	}
	
	/**
	 * Fonction utilitaire pour récupérer le gagnant du tour et de 
	 * museler les exceptions générés par la classe Game.
	 * Les exceptions sont muselées car l'ordre dans lequel les joueurs dépose une carte
	 * est déja géré par la fonction playCard.
	 * 
	 * @return Le joueur qui a remporté le tour
	 * @see playCard
	 */
	private Player fetchTurnWinner() {
		
		Player turnWinner;
		
		try {
			turnWinner = this.game.getTurnWinner();
		} catch (TurnException e) {
			throw new UnexpectedBehaviorException( e );
		}
		
		return turnWinner;
		
	}
	
	/**
	 * Fonction utilitaire permettant de museler les exceptions de la classe Game.
	 * Les exceptions sont muselés car l'ordre dans lequel les joueurs dépose une carte
	 * est déja géré par la fonction playCard.
	 * 
	 * @see playCard
	 */
	private void createNewTurn() {
		
		try {
			this.game.newTurn();
		} catch (TurnException e) {
			throw new UnexpectedBehaviorException( e );
		}
	}

	/**
	 * Déconnexion d'un client.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @throws RemoteException 
	 * @see ServerInterface#disconnectClient(Client)
	 */
	@Override
	public void disconnectClient(ClientInterface client) throws RemoteException {
		
		this.assertState(
			new ServerState[]{ ServerState.CONNECT, ServerState.GAME }
		);
		
		Player disconnectedPlayer = this.clients.remove( client );
		
		for( ClientInterface notify: this.clients.keySet() ) {
			notify.notifyPlayerDisconnect( disconnectedPlayer );
		}
		
	}

	/**
	 * Accesseur du joueur courant.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @see ServerInterface#getCurrentPlayer()
	 */
	@Override
	public Player getCurrentPlayer() {
		return this.game.nextPlayer();
	}

	/**
	 * Accesseur du gagnant.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @throws ServerException 
	 * @see ServerInterface#getCurrentPlayer()
	 */
	@Override
	public Player[] getWinners() {
		
		this.assertState( ServerState.END );
		
		return this.game.getWinners();

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
	 * @throws RemoteException 
	 * 
	 * @throws NotYourTurnToBet 
	 * @throws InvalidCardException 
	 * @see ServerInterface#sendBet(Client, Bet)
	 */
	@Override
	public void sendBet(ClientInterface client, Bet bet) throws GameException, RemoteException {
		
		this.assertState( ServerState.BET );
		
		Player player = this.clients.get( client );
		this.game.addBet( bet, player );
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {
			clientToNotify.notifyPlayerBet(player, bet);			
		}
		
		if( this.game.areBetsFinished() ) {
			this.notifyBetWinner();
			this.changeState( ServerState.DISTRIBUTE_SECRET_HAND );
		}
		
	}
	
	/**
	 * Fonction utilitaire permettant de retrouver le client associé au profil d'un joueur.
	 * @param player Le joueur à rechercher.
	 * @return Le client d'associé au joueur.
	 */
	private ClientInterface clientForPlayer(Player player)
	{
		
		for( Map.Entry<ClientInterface, Player> entry : this.clients.entrySet() ) {
			if( entry.getValue().equals( player ) ) {
				return entry.getKey();
			}
		}
		
		return null;
		
	}
	
	/**
	 * Fonction utilitaire permettant de notifier tout les clients du gagnant du tour.
	 * @throws RemoteException 
	 */
	private void notifyBetWinner() throws RemoteException {
		
		Player betWinner = this.game.getBestPlayerBet();
		Bet winningBet = betWinner.getOriginalBet();
		Suit gameSuit = winningBet.getSuit();
		
		for( ClientInterface clientToNotify: this.clients.keySet() ) {			
			clientToNotify.notifyBetWinner( betWinner, gameSuit );
		}
		
	}
	
	/**
	 * Fonction utilitaire permettant de distribuer de nouvelles cartes au gagnant de la mise.
	 * @throws RemoteException 
	 */
	private void distributeSecretHand() throws RemoteException {
		
		Player betWinner = this.game.getBestPlayerBet();
		Card[] secretHand = this.game.createSecretHand();
		
		ClientInterface client = this.clientForPlayer( betWinner );
		client.notifyChangeCardsAfterBet( secretHand );
		
	}

	/**
	 * Mutateur de la nouvelle main.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @throws RemoteException 
	 * @throws GameException 
	 * 
	 * @see ServerInterface#sendNewHand(Client, ArrayList)
	 */
	@Override
	public void sendNewHand(ClientInterface client, ArrayList<Card> cards) throws RemoteException, GameException {
		
		this.assertState( ServerState.DISTRIBUTE_SECRET_HAND );
		
		Player player = this.clients.get( client );
		
		this.game.newHandAfterSecretBet( player, cards );
		
		this.changeState( ServerState.GAME );
		
	}
	
	/**
	 * Fonction utilitaire permettant de notifier les joueurs
	 * que la partie peut maintenant débuter et démarrer une nouvelle partie après la période
	 * de mises.
	 * @throws RemoteException 
	 * 
	 */
	private void startGame() throws RemoteException {
		
		this.state = ServerState.GAME;
		
		ArrayList<Player> players = new ArrayList<Player>( this.clients.size() );
		
		for( Player p: this.clients.values() ) {
			players.add(p);
		}
		
		for( ClientInterface client: this.clients.keySet() ) {
			client.notifyStartNewGame( players );
		}
		
		Player betWinner = this.game.getBestPlayerBet();
		ClientInterface first = this.clientForPlayer( betWinner );

		this.createNewTurn();

		first.notifyYourTurn( this.game.getTurnSuit() );
		
	}

	/**
	 * Accesseur de la liste des joueurs. Veuillez vous référér à la documentation
	 * de l'interface pour plus de détails.
	 * 
	 * @see ServerInterface#getPlayerList()
	 */
	@Override
	public Player[] getPlayerList() {
		
		Player[] players = new Player[ this.clients.size() ];
		int counter = 0;
		
		for( Player player: this.clients.values() ) {
			players[counter] = player;
			counter++;
		}
		
		return players;
	}
	
	public Player nextPlayer() {
		
		return this.game.nextPlayer();
		
	}



}
