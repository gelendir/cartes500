package database;

import java.io.Serializable;
import java.sql.SQLException;

import game.Bet;

/**
 * Cette classe représente un joueur dans la base de données.
 * @author Frédérik Paradis
 */
public class PlayerStatistics implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * L'instance de Statistics permettant d'enregistrer les modifications
	 * sur un joueur.
	 */
	private transient Statistics statistics;
	
	/**
	 * L'ID_PLAYER du joueur
	 */
	private int idPlayer;
	
	/**
	 * Le nom du joueur.
	 */
	private String name;
	
	/**
	 * Le nombre de parties jouées du joueur.
	 */
	private int nbPlayedGame;
	
	/**
	 * Le nombre de parties gagnées du joueur.
	 */
	private int nbWonGame;
	
	/**
	 * Le nombre total de pointa du joueur.
	 */
	private int totalPoints;

	/**
	 * Ce constructeur initialise le joueur avec toutes ses données.
	 * @param statistics L'instance de Statistics permettant de sauvegarder le
	 * joueur.
	 * @param idPlayer L'ID_PLAYER du joueur
	 * @param name Le nom du joueur
	 * @param nbPlayedGame Le nombre de parties jouées du joueur
	 * @param nbWonGame Le nombre de parties gagnées du joueur
	 * @param totalPoints Le nombre total de points du joueur
	 */
	PlayerStatistics(Statistics statistics,
			int idPlayer, 
			String name, 
			int nbPlayedGame, 
			int nbWonGame, 
			int totalPoints) {
		this.statistics = statistics;
		this.idPlayer = idPlayer;
		this.name = name;
		this.nbPlayedGame = nbPlayedGame;
		this.nbWonGame = nbWonGame;
		this.totalPoints = totalPoints;
	}

	/**
	 * Cette méthode retourne l'ID_PLAYER du joueur.
	 * @return Retourne l'ID_PLAYER du joueur.
	 */
	public int getIdPlayer() {
		return this.idPlayer;
	}

	/**
	 * Cette méthode retourne le nom du joueur.
	 * @return Retourne le nom du joueur.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Cette méthode ajoute une partie jouée au joueur.
	 */
	public void addAPlayedGame() {
		++this.nbPlayedGame;
	}

	/**
	 * Cette méthode ajoute une victoire au joueur
	 * ainsi que le nombre de point que la mise donne.
	 * @param bet La mise gagnante
	 */
	public void addAWonGame(Bet bet) {
		int points = 40;
		points += bet.getSuit().getValue() * 20 - 20;
		points += (bet.getNbRounds() - 6) * 100;
		
		++this.nbWonGame;
		this.totalPoints += points;
	}

	/**
	 * Cette méthode retourne le nombre de parties jouées 
	 * du joueur.
	 * @return Retourne le nombre de parties jouées 
	 * du joueur.
	 */
	public int getNbPlayedGame() {
		return this.nbPlayedGame;
	}

	/**
	 * Cette méthode retourne le nombre de parties gagnées 
	 * du joueur.
	 * @return Retourne le nombre de parties gagnées 
	 * du joueur.
	 */
	public int getNbWonGame() {
		return this.nbWonGame;
	}
	
	/**
	 * Cette méthode retourne le nombre total de points 
	 * du joueur.
	 * @return Retourne le nombre total de points
	 * du joueur.
	 */
	public int getTotalPoints() {
		return this.totalPoints;
	}
	
	/**
	 * Cette méthode retourne la moyenne de points par 
	 * partie du joueur.
	 * @return Retourne la moyenne de points par 
	 * partie du joueur.
	 */
	public double getAveragePointsPerGame()
	{
		if( this.nbPlayedGame > 0 ) {
			return ((double)this.totalPoints / (double)this.nbPlayedGame);
		} else {
			return 0.0;
		}
	}
	
	/**
	 * Cette méthode retourne le rapport entre le nombre de
	 * parties jouées et le nombre de partie gagnée.
	 * @return Retourne le rapport entre le nombre de
	 * parties jouées et le nombre de partie gagnée.
	 */
	public double getRatio() {
		
		if( this.nbWonGame > 0 ) {
			return (double)this.nbPlayedGame / (double)this.nbWonGame;
		} else {
			return 0;
		}
		
	}
	
	/**
	 * Cette méthode enregistre les modifications du joueur dans
	 * la base de données.
	 * @throws SQLException
	 */
	public void save() throws SQLException {
		this.statistics.savePlayer(this);
	}
}
