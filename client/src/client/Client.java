package client;

import game.Card;
import game.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

import server.Turn;

public class Client implements ClientInterface {

	@Override
	public void startNewGame(ArrayList<Card> cards, ArrayList<Player> players)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyYourTurn() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Turn notifyTurn(Turn turn) throws RemoteException {
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
