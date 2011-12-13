package database;

import exception.InvalidBetException;
import game.Bet;
import game.Player;
import game.enumeration.Suit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Statistics {

	private static final String DATABASE_NAME = "statistics";
	private static final String CREATE_TABLE_PLAYER = 
			"CREATE TABLE IF NOT EXISTS PLAYER (" +
					"	ID_PLAYER INTEGER PRIMARY KEY NOT NULL, " +
					"	NAME TEXT NOT NULL, " +
					"	NB_PLAYED_GAME INTEGER NOT NULL DEFAULT 0, " +
					"	NB_WON_GAME INTEGER NOT NULL DEFAULT 0, " +
					"	TOTAL_POINTS INTEGER NOT NULL DEFAULT 0" +
					");";
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

	private static final String INSERT_PLAYER_SQL_QUERY = 
			"INSERT INTO PLAYER (NAME) VALUES (?);";

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

	private Connection conn;

	public Statistics() throws ClassNotFoundException, SQLException {
		this.conn = Database.getConnection(Statistics.DATABASE_NAME);
		this.conn.setAutoCommit(true);

		Statement stat = this.conn.createStatement();
		stat.executeUpdate(Statistics.CREATE_TABLE_PLAYER);
		stat.close();
	}

	public PlayerStatistics getPlayer(Player player) throws SQLException {
		return this.getPlayer(player.getName());
	}

	public PlayerStatistics getPlayer(String name) throws SQLException {
		PlayerStatistics retour;

		PreparedStatement prep = this.conn.prepareStatement(Statistics.GET_PLAYER_SQL_QUERY);
		prep.setString(1, name);
		ResultSet result = prep.executeQuery();

		if(result.next()) {
			int idPlayer = result.getInt("ID_PLAYER");
			name = result.getString("NAME");
			int nbPlayedGame = result.getInt("NB_PLAYED_GAME");
			int nbWonGame = result.getInt("NB_WON_GAME");
			int totalPoints = result.getInt("TOTAL_POINTS");

			retour = new PlayerStatistics(this,
					idPlayer, 
					name, 
					nbPlayedGame, 
					nbWonGame, 
					totalPoints);
			result.close();
			prep.close();
		}
		else {
			result.close();
			prep.close();

			PreparedStatement insert = this.conn.prepareStatement(Statistics.INSERT_PLAYER_SQL_QUERY);
			insert.setString(1, name);
			insert.executeUpdate();
			ResultSet lastID = insert.getGeneratedKeys();
			int lastInsertID = 0;
			if(lastID.next()) {
				lastInsertID = lastID.getInt(1);
			}

			retour = new PlayerStatistics(this, lastInsertID, name, 0,	0, 0);

			lastID.close();
			insert.close();
		}


		return retour;
	}

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
