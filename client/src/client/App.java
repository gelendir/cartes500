package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

public class App {
	
	final public static String 	DEFAULT_HOST = "localhost";
	final public static int 	DEFAULT_PORT = 2222;
	final public static String 	PROGRAM_NAME = "Cartes500";
	
	private static ServerInterface server = null;
	private static Registry registry = null;
	
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
		options.addOption("c", "console", false, "start console app");
		options.addOption("g", "graphic", false, "start graphical app");
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
			formatter.printHelp( App.PROGRAM_NAME , options );
			return null;

		}

		//So Greg is Greg...
		return cmd;
	}
	
	private static boolean connectToServer( String host, int port ) {
		
		App.server = new Server();
			
		try {
			
			System.out.println("Connecting to RMI Registry...");
			App.registry = LocateRegistry.getRegistry( host, port );
			App.server = (ServerInterface)registry.lookup( App.PROGRAM_NAME );
			
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CommandLine cmd = null;
		String host = App.DEFAULT_HOST;
		int port = App.DEFAULT_PORT;
		
		cmd = App.parseArgs( args );
		
		if( cmd != null ) {
			
			if ( cmd.hasOption("host") ) {
				host = cmd.getOptionValue("host");
			}
			
			if ( cmd.hasOption("port") ) {
				port = Integer.parseInt( cmd.getOptionValue("port") );
			}
			
			System.out.println(host);
			System.out.println(port);
			
			//App.connectToServer( host, port );
			
		}

	}
	
}
