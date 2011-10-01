package game;

public class Game {

	private Player players[];
	private int actualPlayerTurn = 0;
	
	public Game() {
		
	}
	
	public int nextTurn() {
		if(++this.actualPlayerTurn >= this.players.length) {
			this.actualPlayerTurn = 0;
		}
		return this.actualPlayerTurn;
	}

	public int getTurn() {
		return this.actualPlayerTurn;
	}
	
	public Player[] getPlayers() {
		return this.players;
	}
	
	public void setPlayers(Player players[]) {
		this.players = players;
	}
}
