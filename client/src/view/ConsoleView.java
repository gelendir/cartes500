package view;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

import game.Card;
import game.Game;
import game.Hand;
import game.Player;
import server.Server;
import server.Turn;

public class ConsoleView extends AbstractView {
	
	final private static String BUNDLE = "console";
	
	protected Scanner in;
	protected PrintStream out;
	protected PropertyResourceBundle bundle;
	
	private ConsoleView( Server server, Game game, Player player, 
			Scanner in, PrintStream out, PropertyResourceBundle bundle ) {
		
		super( server, game, player );
		this.in = in;
		this.out = out;
		this.bundle = bundle;
		
	}
	
	public static ConsoleView welcome( Server server, Game game ) {
		
		PropertyResourceBundle bundle = (PropertyResourceBundle) ResourceBundle.getBundle( ConsoleView.BUNDLE );
		PrintStream out = System.out;
		Scanner in = new Scanner( System.in );
		
		out.println( bundle.getString("banner") );
		out.println( bundle.getString("enterName") );
		
		String name = in.nextLine();
		
		Player player = new Player( name );
		
		out.println( player );
		
		return new ConsoleView( server, game, player, in, out, bundle );
	}
	
	public void printTurn( Turn turn ) {
		
		this.out.println( this.bundle.getString("currentGameBoardBanner") );
		this.out.println( turn.toString() );
		
	}
	
	public void printHand( Hand hand ) {
		
		Card[] cards = hand.getCards();
		Card card = null;
		
		String cardTemplate = this.bundle.getString("cardSelectionTemplate");
		String cardLine = "";
		
		this.out.println( this.bundle.getString("currentHandBanner") );
		
		for( int i = 0; i < cards.length; ++i ) {
			
			card = cards[i];
			cardLine = MessageFormat.format( cardTemplate, Integer.toString( i ), card.toString() );
			this.out.println( cardLine );
					
		}
		
	}
	
	public Card getCardToPlay( Hand hand ) {
		
		int cardNumber = -1;
		
		this.printHand( hand );
		
		while( cardNumber < 0 ) {
			this.out.println( this.bundle.getString("playerSelectCard") );
			cardNumber = this.in.nextInt();
		}
		
		Card card = hand.getCards()[ cardNumber ];
		
		return card;

	}
	
	public void showPlayerTurn( Player player, Card card ) {
		
		String template = this.bundle.getString("playerTurn");
		String result = MessageFormat.format( template, player.toString(), card.toString() );
		
		this.out.println( result );
		
	}

}
