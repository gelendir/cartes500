package server;

import game.Bet;
import game.Card;
import game.Deck;
import game.Game;
import game.Hand;
import game.Player;
import game.Turn;
import game.enumeration.Suit;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import client.Client;

public class MockServer extends Server {
	
	final static public int MAX_TURNS = 10;
	final static public int MAX_PLAYERS = 4;
	final static public int MIN_BET = 6;
	final static public int NB_CARDS_NEW_HAND = 6;
	

	private Deck deck;
	private Client client = null;
	private Player player = null;
	private Player[] players = null;
	private Game game = null;
	private int nbTurn = 1;
	private Turn turn = null;

	public MockServer() {
		this.game = new Game();
		this.deck = new Deck();
		this.deck.mixCards();
	}
	
	public void setClient( Client client ) {
		this.client = client;
		this.player = client.getPlayer();
	}
	
	private void connectPlayers() throws RemoteException {
		
		this.players = new Player[]{ this.player, new Player("bot2"), new Player("bot3"), new Player("bot4") };
		
		this.client.connect();
		
		//Connections
		this.game.setPlayers( this.players );
				
		//Start i and 1 to skip the first player since it's us
		for( int i = 1; i < this.players.length; i++ ) {
			client.notifyPlayerConnect( this.players[i] );
		}
		
	}
	
	private Bet createHands() throws Exception {
		
		Hand hand = new Hand( deck );
		Bet bet = this.client.notifyBettingTime( hand );
		
		System.out.println("SERVER: Player " + this.player.getName() + " has bet " + bet );
		
		//Bot hands
		this.players[1].setHand( new Hand( this.deck ) );
		this.players[2].setHand( new Hand( this.deck ) );
		this.players[3].setHand( new Hand( this.deck ) );
		
		
		return bet;
		
	}
	
	private void createAndManageBets() throws Exception {
		
		//Client bet
		Bet bet = this.createHands();
		this.game.setBet( bet, this.player );
		
		//Bot bets		
		for( int i = 1; i < MockServer.MAX_PLAYERS; i++ ) {
			
			ArrayList<Bet> botBets = this.game.getPlayableBets( this.players[i] );
			Bet botBet = null;
			
			if( botBets.size() > 0 ) {
				botBet = botBets.get(0);
			}
			
			this.game.setBet( botBet, this.players[i] );
			this.client.notifyPlayerBet( this.players[i], botBet );
			
		}
		
		Player betWinner = this.game.getBestPlayerBet();
		Suit gameSuit = this.game.getGameSuit();
		this.client.notifyBetWinner( betWinner, gameSuit );
		
		for( Player p : this.players ) {
			p.getHand().setGameSuit( gameSuit );
		}
		
		if( betWinner == this.player ) {
			
			Card newCards[] = new Card[ MockServer.NB_CARDS_NEW_HAND ];

			for( int i = 0; i < 6; i++ ) {
				newCards[i] = deck.takeCard();
			}
			
			this.client.notifyChangeCardsAfterBet( newCards );
		}
		
	}
	
	private void showWinner() throws RemoteException {
		
		int indexPlayer = 0;
		for(int i = 0; i < this.players.length; ++i) {
			if(players[i].equals(this.game.getBestPlayerBet())) {
				indexPlayer = i;
				break;
			}
		}

		int teammate = 0;
		if(indexPlayer == 0) {
			teammate = 2;
		}
		else if(indexPlayer == 1) {
			teammate = 3;
		}
		else if(indexPlayer == 2) {
			teammate = 0;
		}
		else if(indexPlayer == 3) {
			teammate = 1;
		}
		
		//if(players[indexPlayer].getTurnWin() + players[teammate].getTurnWin() >= this.game.getBestPlayerBet().getOriginalBet().getNbRounds()) {
		//	this.client.notifyWinner(players[indexPlayer], players[teammate]);
		//}
		
		this.client.notifyWinner( players[indexPlayer], players[teammate] );
		
	}
	
	public void startGame() throws RemoteException, Exception {
		
		this.connectPlayers();
		this.createAndManageBets();
		
		ArrayList<Player> playingOrder = new ArrayList<Player>(Arrays.asList( players ));
		this.client.notifyStartNewGame( playingOrder );
		
		int playerIndex = 0;
		Player currentPlayer;
		Suit currentSuit = null;
		
		this.turn = new Turn( this.game.getGameSuit() );
		
		while( this.nbTurn < MockServer.MAX_TURNS ) {
			
			if( playerIndex >= MockServer.MAX_PLAYERS ) {
				playerIndex = 0;
				this.nbTurn++;
				
				this.client.notifyTurnWinner( this.turn.getWinner() );
				
				this.turn = new Turn( this.game.getGameSuit() );
			}
			
			currentPlayer = this.players[ playerIndex ];
			
			if( playerIndex == 0 ) {
				
				Card card = this.client.notifyYourTurn( null );
				currentSuit = card.getSuit();
				this.turn.addCard( currentPlayer , card );
				
			} else {
				
				Card botCard = currentPlayer.getHand().getPlayableCard( currentSuit ).get(0);
				
				this.turn.addCard( currentPlayer, botCard );
				currentPlayer.getHand().playCard( botCard );
				this.client.notifyPlayerTurn( currentPlayer, botCard );
				
			}	
		
			playerIndex++;
			
		}
		
		this.showWinner();
		
	}

	@Override
	public boolean connectClient(Client client, Player player)
	throws RemoteException {

		System.out.println("SERVER: Client with player " + player.getName() + " has connected.");
		this.player = player;
		return true;
		
	}

	@Override
	public void playCard(Client client, Card card) throws RemoteException {
		
		System.out.println("SERVER: player " + client.getPlayer().toString() + " has played card " + card.toString() );

		try {
			this.turn.addCard( client.getPlayer() , card );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnectClient(Client client) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBetForPlayer(Client client, Bet bet) {
	}

	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNewHandAfterBet(Client client, ArrayList<Card> cards) {

		System.out.println("SERVER: Player " + client.getPlayer().toString() + " has new hand " + cards.toString() );
		client.getPlayer().getHand().setCards( cards );

	}

}
