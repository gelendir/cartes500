package client;

import game.Card;
import game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.Turn;

public interface ClientInterface extends Remote {
		
	public void giveCards( Card[] cards ) throws RemoteException;
	
	public void setPlayingOrder( Player[] players ) throws RemoteException;
	
	public Turn notifyPlayerTurn( Turn turn ) throws RemoteException;
	
	public void notifyPlayerDisconnect( Player player ) throws RemoteException;
	
	public void notifyWinner( Player player ) throws RemoteException;
	
	public void notifyExit() throws RemoteException;

}
