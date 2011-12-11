package database;

import game.Player;

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
					"	NB_PLAYED_GAME INTEGER NOT NULL SET DEFAULT 0, " +
					"	NB_WON_GAME INTEGER NOT NULL SET DEFAULT 0, " +
					"	TOTAL_POINTS INTEGER NOT NULL SET DEFAULT 0" +
					");";
	private static final String GET_PLAYER_SQL_QUERY = 
			"SELECT" +
					"	ID_PLAYER, " +
					"	NAME, " +
					"	NB_PLAYED_GAME, " +
					"	NB_WON_GAME, " +
					"	TOTAL_POINTS" +
					"FROM " +
					"	PLAYER" +
					"WHERE" +
					"	PLAYER.NAME = ?";

	private static final String INSERT_PLAYER_SQL_QUERY = 
			"INSERT INTO PLAYER (NAME) VALUES (?);";
	
	private Connection conn;

	public Statistics() throws ClassNotFoundException, SQLException {
		this.conn = Database.getConnection(Statistics.DATABASE_NAME);

		Statement stat = conn.createStatement();
		stat.executeUpdate(Statistics.CREATE_TABLE_PLAYER);
		stat.close();
	}

	public PlayerStatitics getPlayer(Player player) throws SQLException {
		return this.getPlayer(player.getName());
	}

	public PlayerStatitics getPlayer(String name) throws SQLException {
		PlayerStatitics retour;
		
		PreparedStatement prep = this.conn.prepareStatement(Statistics.GET_PLAYER_SQL_QUERY);
		prep.setString(1, name);
		ResultSet result = prep.executeQuery();
		
		prep.getGeneratedKeys();

		if(result.next()) {
			int idPlayer = result.getInt("ID_PLAYER");
			name = result.getString("NAME");
			int nbPlayedGame = result.getInt("NB_PLAYED_GAME");
			int nbWonGame = result.getInt("NB_WON_GAME");
			int totalPoints = result.getInt("TOTAL_POINTS");
			
			retour = new PlayerStatitics(
					idPlayer, 
					name, 
					nbPlayedGame, 
					nbWonGame, 
					totalPoints);
		}
		else {
			PreparedStatement insert = this.conn.prepareStatement(Statistics.INSERT_PLAYER_SQL_QUERY);
			insert.setString(1, name);
			ResultSet lastID = insert.getGeneratedKeys();
			int lastInsertID = 0;
			if(lastID.next()) {
				lastInsertID = lastID.getInt(1);
			}
			
			retour = new PlayerStatitics(lastInsertID, name, 0,	0, 0);
			
			insert.close();
		}
		
		result.close();
		prep.close();
		
		return retour;
	}
}
