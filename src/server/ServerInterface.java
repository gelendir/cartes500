package server;

import game.Card;
import game.Player;
import client.AbstractClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	
	public boolean addClient( AbstractClient client, Player player ) throws RemoteException;
	
	public void playCard( AbstractClient client, Card card ) throws RemoteException;
	
	public void disconnectClient( AbstractClient client );
	
	public Player getCurrentPlayer();
	
	public Player[] getPlayingOrder();
	
	public Player getWinner();
	
	public int getScore();
	
}
