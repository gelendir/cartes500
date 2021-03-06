package app;

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

import client.Client;
import client.ClientInterface;

import server.ServerInterface;
import view.ConsoleView;
import view.IView;
import view.graphicview.GraphicView;

/**
 * La classe App se charge d'initialisé le programme avec les
 * arguments passés en paramètre.
 * @author Gregory Eric Sanderson
 */
public class ClientApp {
	
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
	final public static String 	PROGRAM_NAME = "Cartes 500 Client console";
	
	final public static String SERVER_NAME = "Cartes500Server";
	
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
		options.addOption("g", "gui", false, "Use this option to have a GUI frontend.");
										
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
			formatter.printHelp( ClientApp.PROGRAM_NAME , options );
			return null;

		}

		return cmd;
	}
	
	/**
	 * Cette méthode se charge d'effectuer la connexion avec
	 * les régistraire RMI.
	 * @param host L'hôte du régistraire
	 * @param port Le port du régistraire
	 * @return Retourne vrai si la connexion a pu être effectuée; sinon faux.
	 */
	private static boolean connectToServer( String host, int port ) {
			
		try {
			
			System.out.println("Connecting to RMI Registry...");
			ClientApp.registry = LocateRegistry.getRegistry( host, port );
			ClientApp.server = (ServerInterface)registry.lookup( ClientApp.SERVER_NAME );
			
		} catch (RemoteException e) {
			System.out.println("Error during connection to server. Stacktrace:");
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			System.out.println("Error during connection to server. Stacktrace:");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Connected.");
		
		return true;
		
	}
	
	private static boolean exportClient( Client client ) {
						
		boolean exported = true;
		
		try {
			ClientInterface stub = (ClientInterface)UnicastRemoteObject.exportObject( client, 0 );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exported = false;
		}
		
		return exported;
		
	}

	/**
	 * Le main du programme se charge d'initialiser le jeu et lire les
	 * arguments du programme en les interprétant.
	 * @param args Les arguments de la ligne de commande
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		CommandLine cmd = null;
		String host = ClientApp.DEFAULT_HOST;
		int port = ClientApp.DEFAULT_PORT;
		
		cmd = ClientApp.parseArgs( args );
		
		if( cmd != null ) {
			
			if ( cmd.hasOption("host") ) {
				host = cmd.getOptionValue("host");
			}
			
			if ( cmd.hasOption("port") ) {
				port = Integer.parseInt( cmd.getOptionValue("port") );
			}
			
			boolean connected = ClientApp.connectToServer( host, port );
			
			if( !connected ) {
				System.out.println("Error connecting to RMI. Please see stack trace");
			} else {
				
				Scanner in = new Scanner( System.in );
				IView view = new ConsoleView( in , System.out );
				
				if ( cmd.hasOption("gui") ) {
					view = new GraphicView();
				}
				Client client = new Client( ClientApp.server, view );
				
				boolean exported = ClientApp.exportClient( client );
				
				if( !exported ) {
					System.out.println("Error exporting client to RMI. Please see stack trace");
				} else {
					
					client.connect();

					if ( cmd.hasOption("gui") ) {
						((GraphicView)view).setVisible(true);
					}
					
					//TODO: remove this
					while( !client.isGameFinished() ) {
						Thread.sleep( 2000 );
					}
				}
				
			}			
			
		}

	}
	
}
