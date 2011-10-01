package client;

import game.Card;
import game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import server.Turn;

public interface ClientInterface extends Remote {
	
	public void startNewGame( ArrayList<Card> cards, ArrayList<Player> players ) throws RemoteException;
	
	public void notifyYourTurn() throws RemoteException;
	
	public Turn notifyTurn( Turn turn ) throws RemoteException;
	
	public void notifyPlayerDisconnect( Player player ) throws RemoteException;
	
	public void notifyWinner( Player player ) throws RemoteException;
	
	public void notifyExit() throws RemoteException;

}
