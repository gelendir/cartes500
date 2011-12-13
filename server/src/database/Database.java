package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database {

	private static final String JDBC_DRIVER = "org.sqlite.JDBC";
	private static final String CONNECTION_STRING = "jdbc:sqlite:%s";

	private Map<String, Connection> connections = new HashMap<String, Connection>();

	private static Database instance = null;

	private static Database getInstance() throws ClassNotFoundException {
		if(Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}

	public static Connection getConnection(String database) throws ClassNotFoundException, SQLException {
		return Database.getInstance().getOrCreateConnection(database);
	}

	public static void deleteConnection(String database) {
		try {
			Database.getInstance().removeConnection(database);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Database() throws ClassNotFoundException {
		Class.forName(Database.JDBC_DRIVER);
	}

	private Connection getOrCreateConnection(String database) throws SQLException {
		if(!this.connections.containsKey(database)) {

			this.connections.put(
					database, 
					DriverManager.getConnection(
							String.format(
									Database.CONNECTION_STRING, 
									database + ".db"
									)
							)
					);
		}
		return this.connections.get(database);
	}

	private void removeConnection(String database) {
		if(this.connections.containsKey(database)) {
			try {
				if(!this.connections.get(database).isClosed()) {
					this.connections.get(database).close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connections.remove(database);
		}

	}

	@Override
	protected void finalize() {
		for(Connection c : connections.values()) {
			try {
				if(!c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
