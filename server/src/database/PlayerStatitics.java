package database;

public class PlayerStatitics {
	private int idPlayer;
	private String name;
	private int nbPlayedGame;
	private int nbWonGame;
	private int totalPoints;
	
	public PlayerStatitics() {
		
	}
	
	public int getIdPlayer() {
		return idPlayer;
	}

	public String getName() {
		return name;
	}
	
	public void addAPlayedGame() {
		++this.nbPlayedGame;
	}
	
	public void addAWonGame(int points) {
		++this.nbWonGame;
		this.totalPoints += points;
	}
	
	public int getNbPlayedGame() {
		return this.nbPlayedGame;
	}

	public int getNbWonGame() {
		return this.nbWonGame;
	}

	public int getTotalPoints() {
		return this.totalPoints;
	}
}
