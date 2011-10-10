package view;

import server.Server;
import server.Turn;
import game.Bet;
import game.Card;
import game.Game;
import game.Hand;
import game.Player;

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
	
	public abstract Card getCardToPlay( Hand hand );
	
	public abstract void showPlayerTurn( Turn turn );

	public abstract void playerConnected( Player player );
	
	public abstract Bet askBet( Hand hand );
	
	public abstract void playerHasBet( Player player, Bet bet );

	public abstract void welcome();
	
}
