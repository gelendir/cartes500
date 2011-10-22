package view;

import java.util.ArrayList;

import server.Server;
import game.Bet;
import game.Card;
import game.Game;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

public abstract class AbstractView {
	
	private Game game;
	private Server server;
	private Player player;
	
	public AbstractView() {
		
	}
	
	protected Game getGame() {
		return this.game;
	}
	
	protected Server getServer() {
		return this.server;
	}
	
	protected Player getPlayer() {
		return this.player;
	}
	
	public abstract Player createPlayer();
	
	public abstract Card getCardToPlay( Hand hand, Suit suit );
	
	public abstract void showPlayerTurn( Player player, Card card );

	public abstract void playerConnected( Player player );
	
	public abstract Bet askBet( Hand hand );
	
	public abstract void playerHasBet( Player player, Bet bet );

	public abstract void welcome();
	
	public abstract ArrayList<Card> changeCards( Hand oldHand, Card[] availableCards );
	
	public abstract void showBetWinner( Player player, Suit gameSuit );
	
}
