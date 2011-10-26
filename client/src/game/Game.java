package game;

import game.enumeration.Suit;
import java.util.ArrayList;

public class Game {

	private Player players[] = null;
	private int indexPlayerBetWinner = -1;
	
	public Game() {

	}

	public Player[] getPlayers() {
		return this.players;
	}

	public void setPlayers(Player players[]) {
		this.players = players;
	}

	public Suit getGameSuit() {
		return this.players[this.indexPlayerBetWinner].getOriginalBet().getSuit();
	}
	
	public Player getBestPlayerBet() {
		return this.players[this.indexPlayerBetWinner];
	}

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

	public ArrayList<Bet> getPlayableBets(Player player) {
		int indexPlayer = findIndexPlayer(player);
		Bet first = null;
		ArrayList<Bet> bets = new ArrayList<Bet>(25);
		int suitIndex = 0;
		
		if(indexPlayer == 0) {
			first = new Bet(6, Suit.SPADES);
			suitIndex = first.getSuit().ordinal();
		}
		else if(indexPlayer != -1) {
			first = this.players[indexPlayer - 1] != null ? this.players[indexPlayer - 1].getOriginalBet() : null;
			suitIndex = first.getSuit().ordinal() + 1;
		}


		if(first != null) {
			Suit suits[] = Suit.values();

			for(int j = first.getNbRounds(); j <= 10; ++j) {

				for(int i = suitIndex; i <= Suit.NONE.ordinal(); ++i) {
					bets.add(new Bet(j, suits[i]));
				}

				suitIndex = Suit.SPADES.ordinal();

			}

		}

		return bets;
	}

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

	private int findIndexPlayer(Player player) {
		for(int i = 0; i < this.players.length; ++i) {
			if(this.players[i].equals(player)) {
				return i;
			}
		}

		return -1;
	}
}
