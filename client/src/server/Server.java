package server;

import game.Bet;
import game.Card;
import game.Player;

import java.rmi.RemoteException;

import client.Client;


public class Server implements ServerInterface {

	@Override
	public boolean connectClient(Client client, Player player)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playCard(Client client, Card card) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnectClient(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBetForPlayer(Client client, Bet bet) {
		// TODO Auto-generated method stub
		
	}

}
