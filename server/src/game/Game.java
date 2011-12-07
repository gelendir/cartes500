package game;

import exception.EmptyDeckException;
import exception.GameException;
import exception.InvalidBetException;
import exception.InvalidCardException;
import exception.NotYourTurnException;
import exception.NotYourTurnToBet;
import exception.PlayerNotInGameException;
import exception.TurnException;
import game.enumeration.Suit;
import game.card.Card;
import game.card.Deck;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe Game sert à gérer le jeu.
 * @author Frédérik Paradis
 */
public class Game {

	final static public int MAX_TURNS = 10;
	final static public int MAX_PLAYERS = 4;
	final static public int NB_CARDS_SECRET_HAND = 6;

	/**
	 * Le tableau des joueurs de la partie
	 */
	private Player players[] = null;

	/**
	 * L'index dans le tableau des joueurs du joueur 
	 * qui a gagné la mise.
	 */
	private int indexPlayerBetWinner = -1;
	
	/**
	 * L'index dans le tableau du prochain joueur a faire
	 * une mise
	 */
	private int indexPlayerNextBet = 0;

	/**
	 * la pile de cartes utilisé pour ce jeu
	 */
	private Deck deck = null;
	
	/**
	 * Représente le la manche courante du jeu
	 */
	private Turn currentTurn = null;

	/**
	 * Le constructeur initialise le jeu avec des valeurs par défaut.
	 * @throws GameException 
	 */
	public Game( Player[] players, Deck deck ) throws GameException {

		if( players.length != Game.MAX_PLAYERS ) {
			throw new GameException("Game must absolutely have 4 players");
		}

		this.deck = deck;
		this.players = players;

		this.createHands();

	}

	private void createHands() throws EmptyDeckException {

		for( Player player: this.players ) {
			player.setHand( new Hand( this.deck ) );
		}

	}

	/**
	 * Cette méthode retourne le tableau des joueurs de la partie.
	 * @return Retourne le tableau des joueurs de la partie.
	 */
	public Player[] getPlayers() {
		return this.players;
	}

	/**
	 * Cette méthode retourne l'atoût de la partie.
	 * @return Retourne l'atoût de la partie.
	 */
	public Suit getGameSuit() {
		return this.players[this.indexPlayerBetWinner].getOriginalBet().getSuit();
	}

	/**
	 * Cette méthode retourne le joueur qui a gagné la mise de départ..
	 * @return Retourne le joueur qui a gagné la mise de départ.
	 */
	public Player getBestPlayerBet() {
		return this.players[this.indexPlayerBetWinner];
	}

	/**
	 * Cette méthode retourne une collection de Bet que le joueur peut jouer.
	 * @param player Le joueur en question
	 * @return Retourne une collection de Bet que le joueur peut jouer.
	 * @see Bet
	 */
	public ArrayList<Bet> getPlayableBets(Player player) {
		int indexPlayer = findIndexPlayer(player);
		Bet first = null;
		ArrayList<Bet> bets = new ArrayList<Bet>(25);
		int suitIndex = 0;

		if(indexPlayer > 0) {
			first = this.players[indexPlayer - 1] != null ? this.players[indexPlayer - 1].getOriginalBet() : null;
		}

		if(first == null) {
			try {
				first = new Bet(6, Suit.SPADES);
				suitIndex = first.getSuit().ordinal();
			} catch (InvalidBetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			suitIndex = first.getSuit().ordinal() + 1;
		}

		Suit suits[] = Suit.values();
		for(int j = first.getNbRounds(); j <= 10; ++j) {
			for(int i = suitIndex; i <= Suit.NONE.ordinal(); ++i) {
				try {
					bets.add(new Bet(j, suits[i]));
				} catch (InvalidBetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			suitIndex = Suit.SPADES.ordinal();
		}

		return bets;
	}

	/**
	 * Cette méthode sert à effectuer la mise de départ d'un joueur.
	 * @param bet La mise du joueur
	 * @param player Le joueur
	 * @return  Retourne vrai si la mise est valide; faux sinon.
	 * @throws InvalidBetException 
	 * @throws NotYourTurnToBet 
	 * @see Bet
	 */
	public void addBet(Bet bet, Player player) throws GameException {
		
		int indexPlayer = findIndexPlayer(player);
		
		if( indexPlayer <= -1 ) {
			throw new PlayerNotInGameException();
		}
		
		if( !player.equals( this.getNextPlayerToBet() ) ) {
			throw new NotYourTurnToBet();
		}
			
		if(isValidBet(bet, player)) {
			
			this.players[indexPlayer].setOriginalBet(bet);
			this.indexPlayerNextBet++;
			
			if(bet != null) {
				this.indexPlayerBetWinner = indexPlayer;
				return;
			} 
			
		} else {
			throw new InvalidBetException();
		}

	}

	public Player getNextPlayerToBet() {
		
		return this.players[ this.indexPlayerNextBet ];

	}
	
	public boolean areBetsFinished() {
		
		return( this.indexPlayerNextBet > this.players.length );
		
	}

	/**
	 * Cette méthode sert à savoir si la mise de départ d'un joueur est valide.
	 * @param bet La mise du joueur
	 * @param player Le joueur
	 * @return  Retourne vrai si la mise est valide; faux sinon.
	 * @see Bet
	 */
	public boolean isValidBet(Bet bet, Player player) {

		int indexPlayer = findIndexPlayer(player);

		if(bet != null && indexPlayer != -1) {

			for(int i = 0; i < indexPlayer; ++i) {

				if(this.players[i] != null && this.players[i].getOriginalBet() != null) {

					if(this.players[i].getOriginalBet().getNbRounds() > bet.getNbRounds()) {
						return false;
					}
					else if(this.players[i].getOriginalBet().getNbRounds() == bet.getNbRounds()) {

						if(this.players[i].getOriginalBet().getSuit().getValue() >= bet.getSuit().getValue()) {
							return false;
						}

					}

				}

			}

		}

		return true;
	}

	/**
	 * Cette méthode retourne l'index du joueur dans le tableau de joueurs.
	 * @param player Le joueur en question
	 * @return Retourne l'index du joueur dans le tableau de joueurs.
	 */
	private int findIndexPlayer(Player player) {
		for(int i = 0; i < this.players.length; ++i) {
			if(this.players[i].equals(player)) {
				return i;
			}
		}

		return -1;
	}

	public Player[] getWinners() {

		int winner = -1;
		Player players[] = this.players;

		for(int i = 0; i < players.length && winner < 0; ++i) {

			Player p = players[i];

			if( p.equals( this.getBestPlayerBet() ) ) {
				winner = i;
			}

		}

		int winnerTeammate = 0;
		int loser = 0;
		int loserTeammate = 0;

		if( winner == 0 ) {
			winnerTeammate = 2;
			loser = 1;
			loserTeammate = 3;
		} else if ( winner == 1 ) {
			winnerTeammate = 3;
			loser = 0;
			loserTeammate = 2;
		} else if ( winner == 2 ) {
			winnerTeammate = 0;
			loser = 1;
			loserTeammate = 3;
		} else if ( winner == 3 ) {
			winnerTeammate = 1;
			loser = 0;
			loserTeammate = 2;
		}

		int roundsWon = this.players[winner].getTurnWin() + this.players[winnerTeammate].getTurnWin();
		int roundsBet = this.players[winner].getOriginalBet().getNbRounds();


		Player[] winners;

		if( roundsWon >= roundsBet ) {
			winners = new Player[]{ this.players[winner], this.players[winnerTeammate] };
		} else {
			winners = new Player[]{ this.players[loser], this.players[loserTeammate] };
		}

		return winners;

	}
	
	public Card[] createSecretHand() {
		
		Card newCards[] = new Card[ Game.NB_CARDS_SECRET_HAND ];
		
		for( int i = 0; i < Game.NB_CARDS_SECRET_HAND; i++ ) {
			newCards[i] = this.deck.takeCard();
		}
		
		return newCards;	
		
	}
	
	public void newTurn() throws TurnException {
		
		if( 
				this.currentTurn == null
				|| this.currentTurn.isTurnFinished()
		) {
			this.currentTurn = new Turn( this.getGameSuit() );
		} else {
			throw new TurnException("Turn is not finished");
		}
	}
	
	public Suit getTurnSuit() {
		return this.currentTurn.getTurnSuit();
	}

	public void playCard(Player player, Card card) throws GameException {
		
		if( !player.equals( this.nextPlayer() ) ) {
			throw new NotYourTurnException();
		}
		
		ArrayList<Card> hand = new ArrayList<Card>(
			Arrays.asList( player.getHand().getCards() )
		);
		
		if( !hand.contains( card ) ) {
			throw new InvalidCardException();
		}
		
		this.currentTurn.addCard( player, card );
		
	}

	public boolean isTurnFinished() {
		return this.currentTurn.isTurnFinished();
	}

	public Player getTurnWinner() throws TurnException {
		return this.currentTurn.getWinner();
	}
	
	public Player nextPlayer() {
		
		if( this.currentTurn == null ) {
			return this.players[0];
		}
		
		Player latestPlayer = this.currentTurn.getLatestPlayer();
		
		if( latestPlayer == null ) {
			return this.players[0];
		}
		
		int indexPlayer = this.findIndexPlayer( latestPlayer );
		
		if( indexPlayer + 1 == Game.MAX_PLAYERS ) {
			return this.players[0];
		}
		
		return this.players[ indexPlayer + 1 ];
		
	}

}
