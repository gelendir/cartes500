package view;

import server.Server;
import game.Game;
import game.Player;

public abstract class AbstractView {
	
	private Game game;
	private Server server;
	private Player player;
	
	public AbstractView( Server server, Game game, Player player ) {
		
		this.server = server;
		this.game = game;
		
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

}