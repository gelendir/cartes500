package view;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

import game.Bet;
import game.Card;
import game.Hand;
import game.Player;
import game.enumeration.Suit;
import server.Turn;

public class ConsoleView extends AbstractView {
	
	final private static String BUNDLE = "console";
	
	protected Scanner in;
	protected PrintStream out;
	protected PropertyResourceBundle bundle;
	
	public ConsoleView( Scanner in, PrintStream out ) {
		
		super();
		this.in = in;
		this.out = out;
		this.bundle = (PropertyResourceBundle) ResourceBundle.getBundle( ConsoleView.BUNDLE );
		
	}
	
	public void welcome() {
		
		this.out.println( bundle.getString("banner") );

	}
	
	public void gameStarted() {
		
	}
	
	public Player createPlayer()
	{
		
		String name = "";
		
		while( name == "" || name == null ) {
			this.out.println( this.bundle.getString("enterName") );
			name = this.in.nextLine();
		}
		
		String template = this.bundle.getString("welcomePlayer");
		String msg = MessageFormat.format( template , name );
		
		this.out.println( msg );
		
		Player player = new Player( name );
		
		return player;
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
	
	public void playerConnected( Player player ) {
		
		String msg = MessageFormat.format( 
				this.bundle.getString("playerConnected"),
				player.getName() 
				);
		
		
		this.out.println( msg );
		
	}
	
	public void playerHasBet( Player player, Bet bet ) {
		
		String msg = MessageFormat.format(
				this.bundle.getString("playerBet"),
				player.getName(),
				Integer.toString( bet.getNbRounds() ),
				bet.getSuit().toString()
				);
		
		this.out.println( msg );
		
	}
	
	public void printWaitingForOtherPlayers() {
		
		this.out.println( this.bundle.getString("waitingForOtherPlayers") );
		
	}
	
	public Bet askBet( Hand hand ) {
		
		
		this.out.println( this.bundle.getString("allPlayersConnected" ) );
		
		int nbRounds = -1;
		Card[] cards = hand.getCards();
		int maxRounds = cards.length;
		int suitId = 0;
		int maxSuit = Suit.values().length;
		
		this.printHand( hand );
		
		while( nbRounds < 0 || nbRounds > maxRounds ) {
			
			this.out.println( this.bundle.getString( "playerAskBetNbCards" ) );
			nbRounds = this.in.nextInt();
			
		}
		
		this.printSuits();

		while( suitId <= 0 || suitId > maxSuit ) {
			
			this.out.println( this.bundle.getString( "playerAskBetSuit" ) );
			suitId = this.in.nextInt();
		}
		
		//TODO: ugly hack
		Suit suit = null;
		Suit[] suites = Suit.values();
		for( int i = 0; i < suites.length && suit == null; i++ ) {
			if( suites[i].getValue() == suitId ) {
				suit = suites[i];
			}
		}
		
		return new Bet( nbRounds, suit );
		
	}
	
	public void printSuits() {
		
		String template = this.bundle.getString( "suitSelectionTemplate" );
		
		for( Suit s : Suit.values() ) {
			String msg = MessageFormat.format( template, Integer.toString( s.getValue() ), s.toString() );
			this.out.println( msg );
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
	
	public void showPlayerTurn( Turn turn ) {
			
		String template = this.bundle.getString("playerTurn");
		
		String result = MessageFormat.format( 
				template, turn.getLatestPlayer().toString(), 
				turn.getLatestCard().toString() 
				);
		
		this.out.println( result );
		
	}

}
