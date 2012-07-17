package database;

import game.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * Cette classe sert à gérer les statistiques des joueurs dans
 * une base de données. Elle fait également office de Fabrique 
 * de PlayerStatistics. 
 * @author Frédérik Paradis
 */
public class Statistics {

	/**
	 * Le nom de la base de données.
	 */
	private static final String DATABASE_NAME = "statistics";
	
	/**
	 * La requête SQL de création de la table PLAYER.
	 */
	private static final String CREATE_TABLE_PLAYER = 
			"CREATE TABLE IF NOT EXISTS PLAYER (" +
					"	ID_PLAYER INTEGER PRIMARY KEY NOT NULL, " +
					"	NAME TEXT NOT NULL, " +
					"	NB_PLAYED_GAME INTEGER NOT NULL DEFAULT 0, " +
					"	NB_WON_GAME INTEGER NOT NULL DEFAULT 0, " +
					"	TOTAL_POINTS INTEGER NOT NULL DEFAULT 0" +
					");";
	
	/**
	 * Requête SQL de sélection d'un joueur selon son
	 * nom de joueur.
	 */
	private static final String GET_PLAYER_SQL_QUERY = 
			"SELECT " +
					"	ID_PLAYER, " +
					"	NAME, " +
					"	NB_PLAYED_GAME, " +
					"	NB_WON_GAME, " +
					"	TOTAL_POINTS " +
					"FROM " +
					"	PLAYER " +
					"WHERE " +
					"	PLAYER.NAME = ?";

	/**
	 * Requête d'insertion d'un nouveau joueur dans la BD.
	 */
	private static final String INSERT_PLAYER_SQL_QUERY = 
			"INSERT INTO PLAYER (NAME) VALUES (?);";

	/**
	 * Requête de modification d'un joueur dans la BD.
	 */
	private static final String UPDATE_PLAYER_SQL_QUERY = 
			"UPDATE " +
					"	PLAYER " +
					"SET " +
					"	NAME = ?, " +
					"	NB_PLAYED_GAME = ?, " +
					"	NB_WON_GAME = ?, " +
					"	TOTAL_POINTS = ? " +
					"WHERE" +
					"	ID_PLAYER = ?;";

	/**
	 * La connexion à la BD.
	 */
	private Connection conn;

	/**
	 * Le constructeur initialise la connexion à la BD et
	 * crée la table PLAYER si elle n'existe pas déjà.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Statistics() throws ClassNotFoundException, SQLException {
		this.conn = Database.getConnection(Statistics.DATABASE_NAME);
		this.conn.setAutoCommit(true);

		//On crée la table si elle n'existe pas déjà.
		Statement stat = this.conn.createStatement();
		stat.executeUpdate(Statistics.CREATE_TABLE_PLAYER);
		stat.close();
	}

	/**
	 * Cette méthode retourne un PlayerStatistics selon le joueur passé 
	 * en paramètre. Cette méthode équivaut à faire getPlayer(player.getName());.
	 * @param player Le joueur voulue. 
	 * @return Retourne un PlayerStatistics selon le joueur passé 
	 * en paramètre.
	 * @throws SQLException
	 */
	public PlayerStatistics getPlayer(Player player) throws SQLException {
		return this.getPlayer(player.getName());
	}

	/**
	 * Cette méthode retourne un PlayerStatistics selon le joueur passé 
	 * en paramètre. Si le joueur n'est pas déjà présent dans la base de 
	 * données, un nouveau joueur est créé et retourné.
	 * @param name Le nom du joueur.
	 * @return Retourne le PlayerStatistics voulu.
	 * @throws SQLException
	 */
	public PlayerStatistics getPlayer(String name) throws SQLException {
		PlayerStatistics retour;

		//On va chercher nos informations sur le joueur.
		PreparedStatement prep = this.conn.prepareStatement(Statistics.GET_PLAYER_SQL_QUERY);
		prep.setString(1, name);
		ResultSet result = prep.executeQuery();

		if(result.next()) {
			//On va chercher les différentes valeurs des colonnes.
			int idPlayer = result.getInt("ID_PLAYER");
			name = result.getString("NAME");
			int nbPlayedGame = result.getInt("NB_PLAYED_GAME");
			int nbWonGame = result.getInt("NB_WON_GAME");
			int totalPoints = result.getInt("TOTAL_POINTS");

			//On crée notre PlayerStatistics.
			retour = new PlayerStatistics(this,
					idPlayer, 
					name, 
					nbPlayedGame, 
					nbWonGame, 
					totalPoints);
			
			//On ferme le Statement précédemment ouvert.
			result.close();
			prep.close();
		}
		else {
			//On ferme le Statement précédemment ouvert.
			result.close();
			prep.close();

			PreparedStatement insert = this.conn.prepareStatement(Statistics.INSERT_PLAYER_SQL_QUERY);
			insert.setString(1, name);
			insert.executeUpdate();
			
			//On récupère l'ID_PLAYER qui vient juste d'être créé.
			ResultSet lastID = insert.getGeneratedKeys();
			int lastInsertID = 0;
			if(lastID.next()) {
				lastInsertID = lastID.getInt(1);
			}

			//On crée notre PlayerStatistics.
			retour = new PlayerStatistics(this, lastInsertID, name, 0,	0, 0);

			//On ferme notre Statement.
			lastID.close();
			insert.close();
		}


		return retour;
	}

	/**
	 * Cette méthode enregistre les modifications d'un joueur
	 * dans la base de données.
	 * @param p Les PlayerStatistics à enregistrer.
	 * @throws SQLException
	 */
	public void savePlayer(PlayerStatistics p) throws SQLException {
		PreparedStatement update = this.conn.prepareStatement(Statistics.UPDATE_PLAYER_SQL_QUERY);
		update.setString(	1, p.getName());
		update.setInt(		2, p.getNbPlayedGame());
		update.setInt(		3, p.getNbWonGame());
		update.setInt(		4, p.getTotalPoints());
		update.setInt(		5, p.getIdPlayer());
		update.executeUpdate();
		update.close();
	}
}
