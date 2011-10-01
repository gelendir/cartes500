package view;

import java.io.PrintStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

import game.Game;
import game.Player;
import server.Server;

public class ConsoleView extends AbstractView {
	
	protected Scanner in;
	protected PrintStream out;
	protected PropertyResourceBundle bundle;
	
	public ConsoleView( Server server, Game game, Player player ) {
		
		super( server, game, player );
		this.in = new Scanner( System.in );
		this.out = System.out;
		this.bundle = (PropertyResourceBundle) ResourceBundle.getBundle("console");
		
	}
	
	public static ConsoleView welcome( Server server, Game game ) {
		
		PropertyResourceBundle bundle = (PropertyResourceBundle) ResourceBundle.getBundle("console");
		PrintStream out = System.out;
		Scanner in = new Scanner( System.in );
		
		out.println( bundle.getString("banner") );
		out.println( bundle.getString("enterName") );
		
		String name = in.nextLine();
		
		Player player = new Player( name );
		
		out.println( player );
		
		return new ConsoleView( server, game, player );
	}
	
}
