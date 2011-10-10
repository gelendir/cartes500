package server;

import game.Bet;
import game.Card;
import game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.Client;

public interface ServerInterface extends Remote {
	
	public boolean connectClient( Client client, Player player ) throws RemoteException;
	
	public void playCard( Client client, Card card ) throws RemoteException;
	
	public void disconnectClient( Client client );
	
	public void setBetForPlayer( Client client, Bet bet );
	
	public Player getCurrentPlayer();
	
	public Player getWinner();
	
	public int getScore();
	
}
