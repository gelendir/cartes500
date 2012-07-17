package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe gère les connexions à la BD. Le patron de conception
 * de cette classe est un Singleton multiple permettant d'avoir plusieurs
 * connexions à différentes base de données.
 * @author Frédérik Paradis
 */
public class Database {

	/**
	 * La chaine de connexion à la base de données.
	 */
	private static final String JDBC_DRIVER = "org.sqlite.JDBC";
	
	/**
	 * Le Connection String de la base de données.
	 */
	private static final String CONNECTION_STRING = "jdbc:sqlite:%s";

	/**
	 * Association entre les noms de BD et leur connexion.
	 */
	private Map<String, Connection> connections = new HashMap<String, Connection>();

	/**
	 * L'instance unique de Database.
	 */
	private static Database instance = null;

	/**
	 * Cette méthode retourne l'instance unique de Database.
	 * @return Retourne l'instance unique de Database.
	 * @throws ClassNotFoundException
	 */
	private static Database getInstance() throws ClassNotFoundException {
		if(Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}

	/**
	 * Cette méthode retourne la connexion d'une base de données.
	 * @param database Le nom de la BD
	 * @return Retourne la connexion d'une base de données.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String database) throws ClassNotFoundException, SQLException {
		return Database.getInstance().getOrCreateConnection(database);
	}

	/**
	 * Cette méthode ferme et supprime une connexion à une base de
	 * données.
	 * @param database Le nom de la BD à fermer.
	 */
	public static void deleteConnection(String database) {
		try {
			Database.getInstance().removeConnection(database);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ce constructeur initialise le pilote JDBC.
	 * @throws ClassNotFoundException
	 */
	private Database() throws ClassNotFoundException {
		Class.forName(Database.JDBC_DRIVER);
	}

	/**
	 * Cette méthode retourne la connexion d'une base de données.
	 * @param database Le nom de la BD
	 * @return Retourne la connexion d'une base de données.
	 * @throws SQLException
	 */
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

	/**
	 * Cette méthode ferme et supprime une connexion à une base de
	 * données.
	 * @param database Le nom de la BD à fermer.
	 */
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

	/**
	 * Le destructeur ferme toutes les connexions de base de données.
	 */
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
