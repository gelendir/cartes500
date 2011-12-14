package game;

import exception.EmptyDeckException;
import exception.GameException;
import exception.GameHasStartedException;
import exception.InvalidBetException;
import exception.InvalidCardException;
import exception.NotYourTurnException;
import exception.NotYourTurnToBet;
import exception.PlayerDidNotWinBetException;
import exception.PlayerNotInGameException;
import exception.TurnException;
import game.enumeration.Suit;
import game.card.Card;
import game.card.Deck;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
	 * La pile des tours jouées.
	 */
	private LinkedList<Turn> turns = null;

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
	 * Le constructeur initialise le jeu avec des valeurs par défaut.
	 * @throws GameException 
	 */
	public Game( Player[] players, Deck deck ) throws GameException {

		if( players.length != Game.MAX_PLAYERS ) {
			throw new GameException("Game must absolutely have 4 players");
		}

		this.deck = deck;
		this.deck.mixCards();
		this.players = players;
		this.turns = new LinkedList<Turn>();

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

	/**
	 * Cette méthode retourne le prochain joueur à qui c'est son
	 * tour de miser.
	 * @return Retourne le prochain joueur à qui c'est son
	 * tour de miser.
	 */
	public Player getNextPlayerToBet() {
		
		return this.players[ this.indexPlayerNextBet ];

	}
	
	/**
	 * Cette méthode indique si les mises sont finiss.
	 * @return Retourne vrai si les mises sont finies; 
	 * faux sinon.
	 */
	public boolean areBetsFinished() {
		
		return( this.indexPlayerNextBet >= this.players.length );
		
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

	/**
	 * Cette méthode retourne les 2 gagnants de la partie
	 * sous forme de tableau à 2 éléments.
	 * @return Retourne les 2 gagnants de la partie
	 * sous forme de tableau à 2 éléments.
	 */
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
	
	/**
	 * Cette méthode retourne un tableau avec les 6 cartes
	 * de la main secret.
	 * @return Retourne un tableau avec les 6 cartes
	 * de la main secret.
	 */
	public Card[] createSecretHand() {
		
		Card newCards[] = new Card[ Game.NB_CARDS_SECRET_HAND ];
		
		for( int i = 0; i < Game.NB_CARDS_SECRET_HAND; i++ ) {
			newCards[i] = this.deck.takeCard();
		}
		
		return newCards;	
		
	}
	
	/**
	 * Cette méthode initialise un nouveau tour à condition
	 * que le tour précédent soit fini.
	 * @throws TurnException Est lancé lorsque le tour n'est
	 * pas fini.
	 */
	public void newTurn() throws TurnException {
		
		if( this.turns.size() == 0 ) {
			
			Turn turn = new Turn( this.getGameSuit() );
			this.turns.push( turn );
			
		} else {
		
			Turn currentTurn = this.turns.peek();
				
			if ( currentTurn.isTurnFinished() ) {
				
				currentTurn.incrementWinner();
				Turn turn = new Turn( this.getGameSuit() );
				this.turns.push( turn );
				
			} else {
				
				throw new TurnException("Turn is not finished");
				
			}
			
		}
	}
	
	/**
	 * Cette méthode retourne le tour actuel du jeu.
	 * @return Retourne le tour actuel du jeu.
	 */
	private Turn currentTurn() {
		
		if( this.turns.size() == 0 ) {
			return null;
		}
		
		return this.turns.peek();
	}
	
	/**
	 * Cette méthode retourne l'atoût du tour.
	 * @return Retourne l'atoût du tour.
	 */
	public Suit getTurnSuit() {
		return this.currentTurn().getTurnSuit();
	}

	/**
	 * Cette méthode sert à faire jouer une carte à un joueur.
	 * @param player Le joueur qui joue la carte.
	 * @param card La carte elle-même.
	 * @throws GameException Est lancé lorsque un joueur joue un tour
	 * qui n'est pas le sien ou joue une carte qu'il n'a pas.
	 */
	public void playCard(Player player, Card card) throws GameException {
		
		if( !player.equals( this.nextPlayer() ) ) {
			throw new NotYourTurnException();
		}
		
		if( !player.hasCard( card ) ) {
			throw new InvalidCardException();
		}
		
		Turn currentTurn = this.turns.peek();
		player.playCard( card );
		currentTurn.addCard( player, card );
		
	}
	
	/**
	 * Cette méthode indique si le tour actuel est fini.
	 * @return Retourne vrai si le tour est fini; faux sinon.
	 */
	public boolean isTurnFinished() {
		return this.currentTurn().isTurnFinished();
	}
	
	/**
	 * Cette méthode retourne le gagnant du tour actuel.
	 * @return Retourne le gagnant du tour actuel.
	 * @throws TurnException Lancé lorsque le tour n'est pas fini.
	 */
	public Player getTurnWinner() throws TurnException {
		return this.currentTurn().getWinner();
	}
	
	/**
	 * Cette méthode retourne le prochain joueur à jouer dans
	 * la partie c'est-à-dire que c'est son tour.
	 * @return Retourne le prochain joueur à jouer dans
	 * la partie c'est-à-dire que c'est son tour.
	 */
	public Player nextPlayer() {
		
		if( this.turns.size() == 0 ) {
			return this.getBestPlayerBet();
		}
		
		//Turn turn = this.currentTurn();
		Player latestPlayer = this.currentTurn().getLatestPlayer();
		
		if( latestPlayer == null ) {
			
			if( this.turns.size() <= 1 ) {
				return this.getBestPlayerBet();
			}
			
			Turn lastTurn = this.turns.get(1);
			try {
				return lastTurn.getWinner();
			} catch (TurnException e) {
				return null;
			}
			
		}
		
		int indexPlayer = this.findIndexPlayer( latestPlayer );
		
		if( indexPlayer + 1 == Game.MAX_PLAYERS ) {
			return this.players[0];
		}
		
		return this.players[ indexPlayer + 1 ];
		
	}

	/**
	 * Cette méthode modifie la main d'un joueur après qu'il ait choisi les
	 * cartes dans la main secrete.
	 * @param player Le joueur qui a choisi les cartes.
	 * @param cards Les cartes chosies.
	 * @throws GameException Est lancé lorsque la partie est déjà commencé
	 * ou que le joueur n'a pas gagné la mise.
	 */
	public void newHandAfterSecretBet(Player player, ArrayList<Card> cards) throws GameException {
		
		if( this.turns.size() > 0 ) {
			throw new GameHasStartedException();
		}
		
		if( !player.equals( this.getBestPlayerBet() ) )
		{
			throw new PlayerDidNotWinBetException();
		}
		
		this.getBestPlayerBet().getHand().setCards( cards );
		
	}

	/**
	 * Cette méthode indique si la partie est fini.
	 * @return Retourne vrai si la partie est fini; faux sinon.
	 */
	public boolean isGameFinished() {
		
		return( this.turns.size() > Game.MAX_TURNS );
		
	}

}
