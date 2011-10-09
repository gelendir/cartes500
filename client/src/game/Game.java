package game;

import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.util.ArrayList;

public class Game {

	private Player players[] = null;
	private int actualPlayerTurn = 0;

	public Game() {

	}

	public int nextTurn() {
		if(++this.actualPlayerTurn >= this.players.length) {
			this.actualPlayerTurn = 0;
		}
		return this.actualPlayerTurn;
	}

	public int getTurn() {
		return this.actualPlayerTurn;
	}

	public Player[] getPlayers() {
		return this.players;
	}

	public void setPlayers(Player players[]) {
		this.players = players;
	}

	public ArrayList<Card> getPlayableBets(int indexPlayer) {
		Card first = null;
		ArrayList<Card> bets = new ArrayList<Card>(25);

		if(indexPlayer == 0) {
			try {
				first = new Card(Suit.NONE, CardValue.FIVE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			first = this.players[indexPlayer - 1] != null ? this.players[indexPlayer - 1].getOriginalBet() : null;
		}


		if(first != null) {
			int cardValueIndex = first.getCardValue().ordinal();
			int suitIndex= first.getSuit().ordinal() + 1;

			CardValue values[] = CardValue.values();
			Suit suits[] = Suit.values();

			for(int j = cardValueIndex; j <= CardValue.TEN.ordinal(); ++j) {

				for(int i = suitIndex; i <= Suit.NONE.ordinal(); ++i) {

					try {
						bets.add(new Card(suits[i], values[j]));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				suitIndex = Suit.SPADES.ordinal();

			}

		}

		return bets;
	}

	public boolean setBet(Card bet, int indexPlayer) {
		if(isValidBet(bet, indexPlayer)) {
			this.players[indexPlayer].setOriginalBet(bet);
			return true;
		}	

		return false;
	}

	private boolean isValidBet(Card bet, int indexPlayer) {
		
		if(bet != null) {
			
			if(bet.getCardValue().ordinal() >= CardValue.SIX.ordinal() && bet.getCardValue().ordinal() <= CardValue.TEN.ordinal()) {
				
				if(bet.getSuit() != Suit.COLOR) {
					
					for(int i = 0; i < indexPlayer; ++i) {
						
						if(this.players[i] != null && this.players[i].getOriginalBet() != null) {
							
							if(this.players[i].getOriginalBet().getCardValue().getValue() >= bet.getCardValue().getValue()) {
								return false;
							}
							else if(this.players[i].getOriginalBet().getCardValue().getValue() == bet.getCardValue().getValue()) {
								
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

	public static void main(String[] args) throws Exception {
		Game game = new Game();
		Player players[] = new Player[4];
		for(int i = 0; i < players.length; ++i) {
			players[i] = new Player("Fred");
		}
		game.setPlayers(players);
		ArrayList<Card> bets = game.getPlayableBets(0);
		boolean result = game.isValidBet(new Card(Suit.CLUBS, CardValue.SEVEN), 0);
		game.setBet(new Card(Suit.CLUBS, CardValue.SEVEN), 0);
		bets = game.getPlayableBets(1);
	}
}
