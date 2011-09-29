package client;

import server.Server;
import game.Game;
import game.Player;

public abstract class AbstractClient implements ClientInterface {
	
	private Game game;
	private Server server;
	private Player player;
	
	public AbstractClient( Server server, Game game, Player player ) {
		
		this.server = server;
		this.game = game;
		this.player = player;
		
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
