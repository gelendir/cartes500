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

/**
 * Classe s'occupant d'interagir avec un joueur humain en mode texte (console).
 * Pour plus de détails sur le fonctionnement de cette vue, veuillez consulter 
 * la documentation de la classe AbstractView
 * 
 * @see AbstractView
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public class ConsoleView extends AbstractView {
	
	/**
	 * Fichier de langues à utiliser lors de l'affichage.
	 */
	final private static String BUNDLE = "console";
	
	/**
	 * Indiquateur des cartes qu'un joueur peut jouer durant le tour.
	 */
	final private static String PLAYABLE = "(*)";
	
	/**
	 * Interface utilisé pour lire les charactères tappés sur le clavier par l'utilisateur.
	 */
	protected Scanner in;
	
	/**
	 * Interface utilisé pour afficher du texte à l'écran.
	 */
	protected PrintStream out;
	
	/**
	 * Fichier ressource utilisé pour récupérer du texte et des libelés affichés à l'écran.
	 */
	protected PropertyResourceBundle bundle;
	
	/**
	 * Constructeur. Crée une nouvelle instance de la vue console.
	 * 
	 * @param in Le stream à lire en entrée
	 * @param out Le stream à utiliser en sortie
	 */
	public ConsoleView( Scanner in, PrintStream out ) {
		
		super();
		this.in = in;
		this.out = out;
		this.bundle = (PropertyResourceBundle) ResourceBundle.getBundle( ConsoleView.BUNDLE );
		
	}
	
	/**
	 * Affiche un message de bienvenue.
	 * Voir La classe AbstractView pour plus de détails.
	 * 
	 * @see AbstractView#welcome()
	 */
	public void welcome() {
		
		this.out.println( bundle.getString("banner") );

	}
	
	/**
	 * Crée un nouveau profil de joueur.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#createPlayer()
	 */
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
	
	/**
	 * Fonction utilitaire pour imprimer toutes les
	 * cartes dans une main.
	 * 
	 * @param hand Les cartes à afficher
	 * @param suit La sorte demandé. Utilisé pour afficher une étoile
	 * à côté des cartes que le joueur peut déposer.
	 */
	public void printHand( Hand hand, Suit suit ) {
		
		this.printCards( hand.getCards(), hand.getPlayableCard( suit ) );
		
	}
		
	/**
	 * Fonction utilitaire pour imprimer toutes les
	 * cartes dans une main.
	 * 
	 * @param cards Les cartes à afficher
	 */
	public void printCards( Card[] cards ) {

		this.printCards( cards, new ArrayList<Card>() );
		
	}
	
	/**
	 * Fonction utilitaire pour imprimer toutes les
	 * cartes dans une main.
	 * 
	 * @param cards Les cartes à afficher
	 * @param playable Les cartes qu'un joueur peut déposer. Utilisé
	 * pour afficher une étoile à coté des cartes jouables.
	 */
	public void printCards( Card[] cards, ArrayList<Card> playable ) {
		
		Card card = null;
		
		String cardTemplate = this.bundle.getString("cardSelectionTemplate");
		String cardLine = "";
				
		for( int i = 0; i < cards.length; ++i ) {
			
			card = cards[i];
			
			if( playable.contains( card ) ) {
				cardLine = MessageFormat.format( 
						cardTemplate, 
						Integer.toString( i ), 
						card.toString(), 
						ConsoleView.PLAYABLE );
			} else {
				cardLine = MessageFormat.format( 
						cardTemplate, 
						Integer.toString( i ), 
						card.toString(),
						"" );
			}
			
			this.out.println( cardLine );
			
		}
		
		this.out.println();
		
	}
	
	/**
	 * Affiche un message indiquant qu'un joueur c'est connecté.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#playerConnected(Player)
	 */
	public void playerConnected( Player player ) {
		
		String msg = MessageFormat.format( 
				this.bundle.getString("playerConnected"),
				player.getName() 
				);
		
		
		this.out.println( msg );
		
	}
	
	/**
	 * Notification qu'un joueur a fait une mise.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#playerHasBet(Player, Bet)
	 */
	public void playerHasBet( Player player, Bet bet ) {
		
		String msg = MessageFormat.format(
				this.bundle.getString("playerBet"),
				player.getName(),
				bet == null ? 0 : Integer.toString( bet.getNbRounds() ),
				bet == null ? "Nothing" : bet.getSuit().toString()
				);
		
		this.out.println( msg );
		
	}
	
	/**
	 * Affiche un message en attente de la connexion des autres joueurs.
	 * 
	 */
	public void printWaitingForOtherPlayers() {
		
		this.out.println( this.bundle.getString("waitingForOtherPlayers") );
		
	}
	
	/**
	 * Demande une mise au joueur.
	 * Voir la classe AbstractView pour plus de détails
	 * @throws Exception 
	 * 
	 * @see AbstractView#askBet(Hand)
	 */
	public Bet askBet( Hand hand ) throws Exception {
				
		this.out.println( this.bundle.getString("allPlayersConnected" ) );
		
		int nbRounds = -1;
		int nbSuit = -1;		
		
		Bet bet = null;
		
		this.out.println( this.bundle.getString("currentHandBanner") );
		this.printCards( hand.getCards() );
		
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
			
			Suit suit = Bet.SUITS[ nbSuit ];
			
			bet  = new Bet( nbRounds, suit );
			
		}
		
		return bet;
		
	}
	
	/**
	 * Affiche une liste de toutes les sortes de cartes disponibles.
	 * Utilisé lorsqu'on demande à l'utilisateur de faire une mise.
	 * 
	 */
	public void printSuits() {
		
		String template = this.bundle.getString( "suitSelectionTemplate" );
		
		for( int i = 0; i < Bet.SUITS.length; i++ ) {
			
			Suit s = Bet.SUITS[ i ];
			
			String msg = MessageFormat.format( template, Integer.toString( i ), s.toString() );
			this.out.println( msg );
			
		}
		
	}
	
	/**
	 * Demande à l'utilisateur quel carte jouer.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#getCardToPlay(Hand, Suit)
	 */
	public Card getCardToPlay( Hand hand, Suit suit ) {
		
		int cardNumber = -1;
		
		Card[] cards = hand.getCards();
		ArrayList<Card> playableCards = hand.getPlayableCard( suit );
		
		this.out.println( this.bundle.getString("playerYourTurn") );
		this.out.println( this.bundle.getString("currentHandBanner") );
		
		this.printHand( hand, suit );
				
		while( 
			cardNumber < 0 
			|| !( playableCards.contains( cards[ cardNumber ] ) ) 
		) {
			this.out.println( this.bundle.getString("playerSelectCard") );
			cardNumber = this.in.nextInt();
		}
		
		Card card = cards[ cardNumber ];
		
		return card;

	}
	
	/**
	 * Affiche la carte déposé par un joueur.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#showPlayerTurn(Player, Card)
	 */
	public void showPlayerTurn( Player player, Card card ) {
			
		String template = this.bundle.getString("playerTurn");
		
		String result = MessageFormat.format( 
				template, player.toString(), 
				card.toString() 
				);
		
		this.out.println( result );
		
	}
	
	/**
	 * Valide qu'un utilisateur a bien entré une liste de chiffres
	 * séparé par une virgule. Utilisé pour validé la saisie des cartes
	 * qu'un utilisateur veut échanger.
	 * 
	 * @param list Le texte entré par l'utilisateur.
	 * @return Vrai si la liste est valide.
	 */
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
	
	/**
	 * demande à l'utilisateur d'échanger des nouvelles cartes.
	 * Voir la classe AbstractView pour plus de détails 
	 * 
	 * @see AbstractView#changeCards(Hand, Card[])
	 */
	public ArrayList<Card> changeCards( Hand oldHand, Card[] availableCards ) {
		
		Card[] oldCards = oldHand.getCards();
		String selection = null;
		
		ArrayList<Card> discard = new ArrayList<Card>();
		ArrayList<Card> selected = new ArrayList<Card>();
		ArrayList<Card> newCards = new ArrayList<Card>( Hand.MAX_CARDS );
			
		//Print the cards in the players' hand and the new cards
		this.out.println( this.bundle.getString("changeCards") );
		this.out.println( this.bundle.getString("currentHandBanner") );
		this.printCards( oldHand.getCards() );
		
		this.out.println( this.bundle.getString("changeCardsNewHand") );
		this.printCards( availableCards );
		
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

	/**
	 * Affiche le gagnant de la mise.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#showBetWinner(Player, Suit)
	 */
	@Override
	public void showBetWinner(Player player, Suit gameSuit) {
		
		String template = this.bundle.getString("playerBetWinner");
		String msg = MessageFormat.format( template, player.getName(), gameSuit.toString() );
		this.out.println( msg );
		
	}

	/**
	 * Affiche un message de début de partie.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#showGameStart(Player)
	 */
	@Override
	public void showGameStart(Player first) {
		
		String template = this.bundle.getString("gameStart");
		String msg = MessageFormat.format( template, first.getName() );
		this.out.println( msg );
		
	}

	/**
	 * Affiche les gagnants de la partie.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#showWinners(Player, Player)
	 */
	@Override
	public void showWinners(Player player, Player player2) {
		
		this.out.println( this.bundle.getString("gameEnd") );
		
		String template = this.bundle.getString("gameWinners");
		String msg = MessageFormat.format( template, player.getName(), player2.getName() );
		this.out.println( msg );
		
	}

	/**
	 * Affiche le gagnant du tour.
	 * Voir la classe AbstractView pour plus de détails
	 * 
	 * @see AbstractView#showTurnWinner(Player)
	 */
	@Override
	public void showTurnWinner(Player player) {
		
		String template = this.bundle.getString("turnWinner");
		String msg = MessageFormat.format( template, player.getName() );		
		this.out.println( msg );
		
	}

}
