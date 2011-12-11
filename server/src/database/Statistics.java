package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Statistics {

	private static final String DATABASE_NAME = "statistics";
	private static final String CREATE_TABLE_PLAYER = 
			"CREATE TABLE IF NOT EXISTS PLAYER (" +
					"	ID_PLAYER INTEGER PRIMARY KEY NOT NULL, " +
					"	NAME TEXT NOT NULL, " +
					"	NB_PLAYED_GAME INTEGER NOT NULL, " +
					"	NB_WON_GAME INTEGER NOT NULL, " +
					"	TOTAL_POINTS INTEGER NOT NULL" +
					");";
	
	private Connection conn;

	public Statistics() throws ClassNotFoundException, SQLException {
		this.conn = Database.getConnection(Statistics.DATABASE_NAME);

		Statement stat = conn.createStatement();
		stat.executeUpdate(Statistics.CREATE_TABLE_PLAYER);
		stat.close();
	}
}
