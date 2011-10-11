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
import game.Turn;
import game.enumeration.Suit;

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
		int nbSuit = -1;
		
		Suit suit = Suit.NONE;
		
		this.printHand( hand );
		
		while ( ! ( nbRounds == 0 || (nbRounds >= Bet.MIN_BET && nbRounds <= Bet.MAX_BET ) ) ) {
			
			this.out.println( this.bundle.getString( "playerAskBetNbCards" ) );
			nbRounds = this.in.nextInt();
			
		}
		
		this.printSuits();
		
		if( nbRounds > 0 ) {
			
			while( nbSuit < 0 || nbSuit > Bet.SUITS.length ) {
				
				this.out.println( this.bundle.getString( "playerAskBetSuit" ) );
				nbSuit = this.in.nextInt();
			}
			
			suit = Bet.SUITS[ nbSuit ];
			
		}
		
		return new Bet( nbRounds, suit );
		
	}
	
	public void printSuits() {
		
		String template = this.bundle.getString( "suitSelectionTemplate" );
		
		for( int i = 0; i < Bet.SUITS.length; i++ ) {
			
			Suit s = Bet.SUITS[ i ];
			
			String msg = MessageFormat.format( template, Integer.toString( i ), s.toString() );
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
