package database;

import game.Bet;

public class PlayerStatitics {
	private int idPlayer;
	private String name;
	private int nbPlayedGame;
	private int nbWonGame;
	private int totalPoints;

	PlayerStatitics(int idPlayer, 
			String name, 
			int nbPlayedGame, 
			int nbWonGame, 
			int totalPoints) {
		this.idPlayer = idPlayer;
		this.name = name;
		this.nbPlayedGame = nbPlayedGame;
		this.nbWonGame = nbWonGame;
		this.totalPoints = totalPoints;
	}

	public int getIdPlayer() {
		return this.idPlayer;
	}

	public String getName() {
		return this.name;
	}

	public void addAPlayedGame() {
		++this.nbPlayedGame;
	}

	public void addAWonGame(Bet bet) {
		int points = 40;
		points += bet.getSuit().getValue() * 20 - 20;
		points += (bet.getNbRounds() - 6) * 100;
		
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
