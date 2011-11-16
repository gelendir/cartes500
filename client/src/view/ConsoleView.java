package view;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
		String suffix = "";
				
		for( int i = 0; i < cards.length; ++i ) {
			
			card = cards[i];		
			suffix = "";
			
			if( playable.contains( card ) ) {
				suffix = ConsoleView.PLAYABLE;
			}
			
			cardLine = MessageFormat.format( 
					cardTemplate, 
					Integer.toString( i ), 
					card.toString(), 
					suffix );
			
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
		
		int nbRounds = 0;
		String suit = this.bundle.getString("nothing");
		
		if( bet != null ) {
			nbRounds = bet.getNbRounds();
			suit = bet.getSuit().toString();
		}
		
		String msg = MessageFormat.format(
				this.bundle.getString("playerBet"),
				player.getName(),
				Integer.toString( nbRounds ),
				suit
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
	 * Affiche à l'écran un message et demande à l'utilisateur de rentrer
	 * un chiffre à la console. Redemande le message à boucle tant et autant
	 * que ce qui est saisi comme texte n'est pas un chiffre ou n'est pas
	 * compris inclusivement entre le minimum et le maximum.
	 * 
	 * @param message Le message à afficher à la console
	 * @param min Le chiffre minimum que l'utilisateur doit entrer
	 * @param max Le chiffre maximum que l'utilisateur doit entrer
	 * @return Le chiffre entré par l'utilisateur
	 */
	private int askInt( String message, int min, int max ) {
		
		boolean answered = false;
		int answer = 0;
		
		while( !answered ) {
			
			this.out.println( message );
			
			try{
				
				answer = this.in.nextInt();
				if( answer >= min && answer <= max ) {
					answered = true;
				}
				
			} catch( InputMismatchException e ) {
				
				this.in.skip(".*");
				
			}
			
			
		}
		
		return answer;
		
	}
	
	/**
	 * Demande une mise au joueur.
	 * Voir la classe AbstractView pour plus de détails
	 * @throws Exception 
	 * 
	 * @see AbstractView#askBet(Hand)
	 */
	public Bet askBet( Hand hand ) {
				
		Bet bet = null;
		boolean validBet = false;
		
		this.out.println( this.bundle.getString("allPlayersConnected" ) );
		this.out.println( this.bundle.getString("currentHandBanner") );
		this.printCards( hand.getCards() );
		
		while( !validBet ) {
		
			int nbRounds = this.askBetNbRounds();	
			
			if( nbRounds > 0 ) {
				
				this.printSuits();
				Suit suit = this.askBetSuit();
				
				try {
					bet = new Bet( nbRounds, suit );
					validBet = true;
				} catch (Exception e) {
					
					this.out.println( this.bundle.getString("invalidBet") );
					
				}
				
			} else {
				
				validBet = true;
				
			}
			
		}
		
		return bet;
		
	}

	/**
	 * Demande à l'utilisateur le nombre de tours qu'il veut miser.
	 * 
	 * @see askBet
	 * @return le nombre de tours.
	 */
	private int askBetNbRounds() {
		
		int nbRounds = -1;

		while( !(
					nbRounds == 0 || 
					( nbRounds >= Bet.MIN_BET && nbRounds <= Bet.MAX_BET ) 
				) 
		)
		{
		
			nbRounds = this.askInt(
					this.bundle.getString( "playerAskBetNbCards" ), 
					0,
					Bet.MAX_BET
					);
		}
		
		return nbRounds;
	}
	
	/**
	 * Demande à l'utilisateur la sorte qu'il veut miser
	 * 
	 * @see askBet
	 * @return La suite.
	 */
	private Suit askBetSuit() {
		
		int nbSuit = this.askInt(
				this.bundle.getString( "playerAskBetSuit" ),
				0,
				Bet.SUITS.length
				);
		
		Suit suit = Bet.SUITS[ nbSuit ];
		
		return suit;
		
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
		)
		{
		
			cardNumber = this.askInt(
				this.bundle.getString( "playerSelectCard" ),
				0,
				cards.length - 1 
				);
			
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
