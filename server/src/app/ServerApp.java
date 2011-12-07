package app;

import game.Game;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import server.Server;
import server.ServerInterface;
import view.ConsoleView;
import view.IView;
import view.graphicview.GraphicView;

/**
 * La classe App se charge d'initialiser le programme avec les
 * arguments passés en paramètre.
 * @author Gregory Eric Sanderson
 */
public class ServerApp {
	
	/**
	 * L'adresse de l'hôte par défaut du régistraire RMI.
	 */
	final public static String 	DEFAULT_HOST = "localhost";
	
	/**
	 * Le port de l'hôte par défaut du régistraire RMI.
	 */
	final public static int 	DEFAULT_PORT = 2222;
	
	/**
	 * Le nom du programme
	 */
	final public static String 	SERVER_NAME = "Cartes500Server";
	
	final public static String PROGRAM_NAME = "Cartes 500 Serveur";
	
	/**
	 * L'interface Stub du serveur
	 */
	private static ServerInterface server = null;
	
	/**
	 * L'instance du régistraire RMI
	 */
	private static Registry registry = null;
	
	/**
	 * Cette méthode se charge de lire les arguments.
	 * @param args Les arguments du programme
	 * @return Retourne sous forme d'objet les arguments de la ligne
	 *         de commande
	 */
	private static CommandLine parseArgs(String[] args) {
		
		Options options = new Options();		
		
		@SuppressWarnings("static-access")
		Option port = OptionBuilder.withLongOpt("port")
									.withArgName("PORT")
									.hasArg()
									.withDescription("Port of the rmi registry server")
									.create("p");
		
		@SuppressWarnings("static-access")
		Option host = OptionBuilder.withLongOpt("host")
									.withArgName("HOST")
									.hasArg()
									.withDescription( "Address of the rmi registry server, a.k.a the game server" )
									.create("H");

		options.addOption("h", "help", false, "print this help message");
		options.addOption( host );
		options.addOption( port );
										
		CommandLineParser parser = new PosixParser();
		
		CommandLine cmd = null;
		
		try{
			cmd = parser.parse( options, args );
		} catch (ParseException e) {
			System.out.println("ERROR: cannot parse arguments.");
			System.out.println( e.getMessage() );
		}
		
		if( cmd != null && cmd.hasOption("help") ) {
		
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( ServerApp.PROGRAM_NAME , options );
			return null;

		}

		return cmd;
	}
	
	private static boolean connectRMI( Server server, String host, int port ) {
		
		boolean connected = true;
		
		Registry registry;
		
		try {
		
			System.out.println("Exporting server stub...");
			ServerInterface stub = (ServerInterface)UnicastRemoteObject.exportObject( server, 0 );
			
			System.out.println("Getting Registry...");
			registry = LocateRegistry.getRegistry( host, port );
			
			System.out.println("Binding server...");
			registry.rebind( ServerApp.SERVER_NAME, stub );
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected = false;
		}
		
		return connected;
		
	}

	/**
	 * Le main du programme se charge d'initialiser le jeu et lire les
	 * arguments du programme en les interprétant.
	 * @param args Les arguments de la ligne de commande
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		CommandLine cmd = null;
		String host = ServerApp.DEFAULT_HOST;
		int port = ServerApp.DEFAULT_PORT;
		
		cmd = ServerApp.parseArgs( args );
		
		if( cmd != null ) {
			
			if ( cmd.hasOption("host") ) {
				host = cmd.getOptionValue("host");
			}
			
			if ( cmd.hasOption("port") ) {
				port = Integer.parseInt( cmd.getOptionValue("port") );
			}
			
			Server server = new Server();
			
			boolean connected = ServerApp.connectRMI( server, host, port );
			
			if( !connected ) {
				System.out.println("Error connecting to RMI. Please see stack trace");
			}
			
		}

	}
	
}
