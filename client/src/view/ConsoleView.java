package view;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
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
		
		String banner = this.bundle.getString("currentHandBanner");
		this.printHand( hand, banner );
		
	}
	
	public void printHand( Hand hand, String banner ) {
		
		this.printCards( hand.getCards(), banner );
		
	}
	
	public void printCards( Card[] cards, String banner ) {

		Card card = null;
		
		String cardTemplate = this.bundle.getString("cardSelectionTemplate");
		String cardLine = "";
		
		this.out.println( banner );
		
		for( int i = 0; i < cards.length; ++i ) {
			
			card = cards[i];
			cardLine = MessageFormat.format( cardTemplate, Integer.toString( i ), card.toString() );
			this.out.println( cardLine );
					
		}
		
		this.out.println();
		
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
	
	public boolean validateCommaList( String list ) {
		
		int number;
		
		if( list == null ) {
			return false;
		}
		
		for( String token: list.split(",") ) {
			try {
				number = Integer.parseInt( token );
			} catch ( NumberFormatException e ) {
				return false;
			}
			if( number < 0 ) {
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<Card> changeCards( Hand oldHand, Card[] availableCards ) {
		
		Card[] oldCards = oldHand.getCards();
		String selection = null;
		
		ArrayList<Card> discard = new ArrayList<Card>();
		ArrayList<Card> selected = new ArrayList<Card>();
		ArrayList<Card> newCards = new ArrayList<Card>( Hand.MAX_CARDS );
			
		//Print the cards in the players' hand and the new cards
		this.out.println( this.bundle.getString("changeCards") );
		this.printHand( oldHand );
		this.printCards( availableCards, this.bundle.getString("changeCardsNewHand") );
		
		//Ask the player for the list of cards in his hand to discard
		while( ! this.validateCommaList( selection ) ) {
			this.out.println( this.bundle.getString("changeCardsSelectOldCards") );
			selection = this.in.nextLine();
		}
		
		//Take the cards selected and create a list of cards to discard
		for( String token : selection.split(",") ) {
			discard.add( oldCards[ Integer.parseInt( token ) ] ); 
		}
		
		if( discard.size() > 0 ) {
		
			//Select cards from the new hand
			int nbDiscard = discard.size();
			selection = null;
			
			//Prepare message telling the player to select new cards
			String template = this.bundle.getString("changeCardsSelectNewCards");
			String msg = MessageFormat.format( template, Integer.toString( nbDiscard ) );
			
			while( 	selection == null ||
					(	!this.validateCommaList( selection ) &&
						selection.split(",").length != nbDiscard
					)
				)
			{
				this.out.println( msg );
				selection = this.in.nextLine();
			}
			
			for( String token : selection.split(",") ) {
				selected.add( availableCards[ Integer.parseInt( token ) ] );
			}
			
		}
		
		//Create list of new cards from the combination of cards that have not been discarded
		//and the new cards selected by the player
		for( Card c : oldCards ) {
			if( !discard.contains( c ) ) {
				newCards.add( c );
			}
		}
		
		for( Card c : selected ) {
			newCards.add( c );
		}
		
		return newCards;
		
	}

}
