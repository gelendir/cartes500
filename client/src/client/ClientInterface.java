package client;

import game.Bet;
import game.Card;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ClientInterface extends Remote {
	
	public void notifyStartNewGame( ArrayList<Player> players ) throws RemoteException;
	
	public void notifyPlayerTurn( Player player, Card card ) throws RemoteException;
	
	public Card notifyYourTurn( Suit suit ) throws RemoteException;
	
	public void notifyPlayerDisconnect( Player player ) throws RemoteException;
	
	public void notifyPlayerConnect( Player player ) throws RemoteException;
	
	public Bet notifyBettingTime( Hand hand ) throws RemoteException;
	
	public void notifyWinner( Player player, Player player2 ) throws RemoteException;
	
	public void notifyExit() throws RemoteException;
	
	public void notifyPlayerBet( Player player, Bet bet );
	
	public void notifyChangeCardsAfterBet( Card[] newCards );
	
	public void notifyBetWinner( Player player, Suit gameSuit );
	
	public void notifyTurnWinner( Player player );
	
}
