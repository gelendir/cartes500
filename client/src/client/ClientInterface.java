package client;

import game.Bet;
import game.Hand;
import game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import server.Turn;

public interface ClientInterface extends Remote {
	
	public void notifyStartNewGame( ArrayList<Player> players ) throws RemoteException;
	
	public void notifyTurn( Turn turn ) throws RemoteException;
	
	public void notifyPlayerDisconnect( Player player ) throws RemoteException;
	
	public void notifyPlayerConnect( Player player ) throws RemoteException;
	
	public Bet notifyBettingTime( Hand hand ) throws RemoteException;
	
	public void notifyWinner( Player player ) throws RemoteException;
	
	public void notifyExit() throws RemoteException;
	
	public void notifyPlayerBet( Player player, Bet bet );

}
