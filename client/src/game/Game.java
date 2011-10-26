package game;

import game.enumeration.Suit;
import java.util.ArrayList;

/**
 * La classe Game sert à gérer le jeu.
 * @author Frédérik Paradis
 */
public class Game {

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
	 * Le constructeur initialise le jeu avec des valeurs par défaut.
	 */
	public Game() {

	}

	/**
	 * Cette méthode retourne le tableau des joueurs de la partie.
	 * @return Retourne le tableau des joueurs de la partie.
	 */
	public Player[] getPlayers() {
		return this.players;
	}

	/**
	 * Cette méthode sert à changer les joueurs de la parties.
	 * @param players Les joueurs de la partie
	 */
	public void setPlayers(Player players[]) {
		this.players = players;
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
	 * Cette méthode sert à modifier la main d'un joueur avec
	 * des nouvelles cartes.
	 * @param finalHand Les nouvelles cartes du joueur
	 * @param player Le joueur en question
	 * @return Retourne vrai si le joueur existe; sinon faux.
	 */
	/*public boolean chooseCardFromSecretBet(ArrayList<Card> finalHand, Player player) {
		int indexPlayer = findIndexPlayer(player);
		if(indexPlayer != -1) {
			if(this.players[indexPlayer] != null && 
					this.players[indexPlayer].getHand() != null &&
					this.players[indexPlayer].getHand().getCards() != null) {
				this.players[indexPlayer].getHand().setCards(finalHand);
				return true;
			}
		}

		return false;
	}*/

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
			first = new Bet(6, Suit.SPADES);
			suitIndex = first.getSuit().ordinal();
		}
		else {
			suitIndex = first.getSuit().ordinal() + 1;
		}

		Suit suits[] = Suit.values();
		for(int j = first.getNbRounds(); j <= 10; ++j) {
			for(int i = suitIndex; i <= Suit.NONE.ordinal(); ++i) {
				bets.add(new Bet(j, suits[i]));
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
	 * @see Bet
	 */
	public boolean setBet(Bet bet, Player player) {
		int indexPlayer = findIndexPlayer(player);
		if(indexPlayer != -1) {
			if(isValidBet(bet, player)) {
				this.players[indexPlayer].setOriginalBet(bet);
				if(bet != null) {
					this.indexPlayerBetWinner = indexPlayer;
				}

				return true;
			}	
		}


		return false;
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

			if(bet.getNbRounds() >=6 && bet.getNbRounds() <= 10) {

				if(bet.getSuit() != Suit.COLOR &&
						bet.getSuit() != Suit.BLACK) {

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
}
