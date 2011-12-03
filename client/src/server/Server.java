package server;

import exception.AlreadyConnectedException;
import exception.GameException;
import exception.InvalidBetException;
import exception.NotYourTurnToBet;
import exception.ServerException;
import exception.ServerStateException;
import game.Bet;
import game.Game;
import game.Player;
import game.card.Card;
import game.card.Deck;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import view.ConsoleView;

import client.Client;

/**
 * Serveur du jeu de 500. Classe en charge de gérer les connexions, 
 * les événements venant des clients (joueurs) et de mettre 
 * à jour l'état du jeu selon les réponses reçues.Le serveur communique 
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
public class Server implements ServerInterface {
	
	static private String BUNDLE = "server";

	private LinkedHashMap<Client, Player> clients 	= null;
	private ServerState state 						= ServerState.CONNECT;
	private Player currentPlayer 					= null;
	private Game game								= null;
	
	private PropertyResourceBundle bundle;
	
	public Server() {
		
		this.bundle = (PropertyResourceBundle) ResourceBundle.getBundle( Server.BUNDLE );
		this.clients = new LinkedHashMap<Client, Player>( Game.MAX_PLAYERS );
		this.state = ServerState.CONNECT;
		this.game = null;
			
	}
	
	public void assertState( ServerState askedState ) {
		
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
	
	public void assertState( ServerState[] askedStates ) {
		
		for( ServerState state: askedStates ) {
			this.assertState( state );
		}
		
	}

	/**
	 * Connexion d'un client au serveur. 
	 * Voir la classe ServerInterface pour plus de détails.
	 * @throws AlreadyConnectedException 
	 * @throws GameException 
	 * @see ServerInterface#connectClient(Client, Player)
	 */
	@Override
	public boolean connectClient(Client client, Player player) throws AlreadyConnectedException, GameException {
		
		this.assertState( ServerState.CONNECT );
		
		if( 
			   this.clients.containsKey( client )
			|| this.clients.containsValue( player )
		) 
		{
			throw new AlreadyConnectedException();
		}
		
		this.clients.put( client,  player );
		
		if( this.clients.size() == Game.MAX_PLAYERS ) {
			this.startGame();
		}
		
		return true;
		
	}

	private void startGame() throws GameException {
		
		Player[] players = (Player[])this.clients.values().toArray();
		Deck deck = new Deck();
		
		this.game = new Game( players, deck );
		
		this.state = ServerState.BET;
		
		for( Map.Entry<Client, Player> entry : this.clients.entrySet() ) {
			
			Client client = entry.getKey();
			Player player = entry.getValue();
			
			client.notifyBettingTime( player.getHand() );
			
		}
		
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
	 * @throws ServerException 
	 * @throws RemoteException 
	 * @see ServerInterface#disconnectClient(Client)
	 */
	@Override
	public void disconnectClient(Client client) throws RemoteException {
		
		this.assertState(
			new ServerState[]{ ServerState.CONNECT, ServerState.GAME }
		);
		
		Player disconnectedPlayer = this.clients.remove( client );
		
		for( Client notify: this.clients.keySet() ) {
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
		return this.currentPlayer;
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
	
	private void checkClientsTurnToBet( Client client ) throws NotYourTurnToBet {
		
		Player player = this.clients.get( client );
		
		if( player != this.game.getNextPlayerToBet() ) {
			throw new NotYourTurnToBet();
		}
	}
	
	private void setBetAndCheckForValidBet( Bet bet, Player player ) throws InvalidBetException {
		
		boolean valid = this.game.setBet( bet, player );
		if( !valid ) {
			throw new InvalidBetException();
		}
		
	}

	/**
	 * Mise d'un joueur.
	 * Voir la classe ServerInterface pour plus de détails.
	 * @throws NotYourTurnToBet 
	 * @throws InvalidBetException 
	 * @see ServerInterface#setBetForPlayer(Client, Bet)
	 */
	@Override
	public void setBetForPlayer(Client client, Bet bet) throws NotYourTurnToBet, InvalidBetException {
		
		Player player = this.clients.get( client );
		
		this.assertState( ServerState.BET );
		this.checkClientsTurnToBet( client );
		this.setBetAndCheckForValidBet( bet, player );
		
		for( Map.Entry<Client, Player> entry : this.clients.entrySet() ) {
			
			Client clientToNotify = entry.getKey();
			Player playerToNotify = entry.getValue();
			
			clientToNotify.notifyPlayerBet(player, bet);
			
		}
		
		//working here
		
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
