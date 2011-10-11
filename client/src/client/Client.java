package client;

import game.Bet;
import game.Card;
import game.Hand;
import game.Player;
import game.Turn;

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
		this.server.connectClient( this , this.player );
		
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void notifyTurn(Turn turn) throws RemoteException {
		
		this.view.showPlayerTurn( turn );
		Player lastPlayer = turn.getLatestPlayer();
		
		int index = this.players.indexOf( lastPlayer );
		
		if( 
				( index + 1 >= this.players.size() && this.players.get(0).equals( this.player ) ) ||
				( this.players.get( index + 1 ).equals( this.player ) )
		) {
			
			Card card = this.view.getCardToPlay( this.player.getHand() );
			this.server.playCard( this, card );
			
		}
		
	}

	@Override
	public void notifyPlayerDisconnect(Player player) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyWinner(Player player) throws RemoteException {
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
		
		if( this.players.get(0).equals( this.player ) ) {
			
			Card card = this.view.getCardToPlay( this.player.getHand() );
			this.server.playCard( this, card );
			
		}
		
	}

	@Override
	public void notifyChangeCardsAfterBet( Card[] newCards ) {
		
		ArrayList<Card> cards = this.view.changeCards( this.player.getHand(), newCards );
		this.server.setNewHandAfterBet( this, cards );
		
	}


}
