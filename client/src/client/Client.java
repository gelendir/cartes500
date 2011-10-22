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

public class Client implements ClientInterface {
	
	private Server server;
	private AbstractView view;
	private Player player;
	private ArrayList<Player> players;
	
	public Client( Server server, AbstractView view ) throws RemoteException {
		
		this.server = server;
		this.view = view;
		this.players = null;
		
		this.view.welcome();
		this.player = this.view.createPlayer();
		
	}
	
	public boolean connect() throws RemoteException {
		return this.server.connectClient( this, this.player );
	}

	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void notifyPlayerTurn( Player player, Card card )
			throws RemoteException {
		
		this.view.showPlayerTurn( player, card );
		
	}

	@Override
	public Card notifyYourTurn( Suit suit ) {
		
		Card card = this.view.getCardToPlay( this.player.getHand(), suit );
		this.player.getHand().playCard( card );
		return card;

	}

	@Override
	public void notifyPlayerDisconnect(Player player) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyWinner(Player player, Player player2) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyExit() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyPlayerConnect(Player player) throws RemoteException {
		
		this.view.playerConnected( player );	
		
	}

	@Override
	public Bet notifyBettingTime( Hand hand ) throws RemoteException {
		
		this.player.setHand( hand );
		Bet bet = this.view.askBet( hand );
		return bet;
		
	}


	@Override
	public void notifyPlayerBet(Player player, Bet bet) {
		
		this.view.playerHasBet( player, bet );
		
		
	}

	@Override
	public void notifyStartNewGame(ArrayList<Player> players)
			throws RemoteException {
		
		this.players = players;
		
	}

	@Override
	public void notifyChangeCardsAfterBet( Card[] newCards ) {
		
		ArrayList<Card> cards = this.view.changeCards( this.player.getHand(), newCards );
		this.server.setNewHandAfterBet( this, cards );
		
	}



}
