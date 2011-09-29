package client;

import game.Card;
import game.Game;
import game.Player;

import java.rmi.RemoteException;

import server.Server;
import server.Turn;

public class ConsoleClient extends AbstractClient {
	
	public ConsoleClient( Server server, Game game, Player player ) {
		
		super( server, game, player );
		
	}

	@Override
	public void giveCards(Card[] cards) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPlayingOrder(Player[] players) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Turn notifyPlayerTurn(Turn turn) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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

}
