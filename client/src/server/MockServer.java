package server;

import game.Bet;
import game.Card;
import game.Deck;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

import java.rmi.RemoteException;
import java.util.ArrayList;

import client.Client;

public class MockServer extends Server {
	
	private Deck deck;
	private Client client = null;
	private Player player = null;
	private ArrayList<Player> players;
	
	public MockServer() {
		this.deck = new Deck();
		this.deck.mixCard();
	}

	@Override
	public boolean connectClient(Client client, Player player)
			throws RemoteException {
		
		System.out.println("SERVER: Client with player " + player.getName() + " has connected.");
		this.client = client;
		this.player = player;
		
		Player[] players = { new Player("p2"), new Player("p3"), new Player("p4") };
		
		try {
			players[0].setHand( new Hand( this.deck ) );
			players[1].setHand( new Hand( this.deck ) );
			players[2].setHand( new Hand( this.deck ) );
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for( Player p : players ) {
			client.notifyPlayerConnect( p );
		}
			
		Hand hand = null;
		try {
			hand = new Hand( deck );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Bet bet = client.notifyBettingTime( hand );
		
		System.out.println("SERVER: Client with player " + this.player.getName() + " has bet " + bet.toString() );
		
		Bet bet2 = new Bet( 2, Suit.CLUBS );
		Bet bet3 = new Bet( 3, Suit.DIAMONDS );
		Bet bet4 = new Bet( 4, Suit.HEARTS );
		
		client.notifyPlayerBet( players[0], bet2 );
		client.notifyPlayerBet( players[1], bet3 );
		client.notifyPlayerBet( players[2], bet4 );
		
		ArrayList<Player> playerOrder = new ArrayList<Player>();
		playerOrder.add( this.player );
		playerOrder.add( players[0] );
		playerOrder.add( players[1] );
		playerOrder.add( players[2] );
		
		this.players = playerOrder;
		
		this.client.notifyStartNewGame( playerOrder );
		
		return true;
	}

	@Override
	public void playCard(Client client, Card card) throws RemoteException {
		
		Turn turn = new Turn();
		
		System.out.println("SERVER: player " + client.getPlayer().toString() + " has played card " + card.toString() );
		
		try {
			turn.addCard( client.getPlayer() , card );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for( int i = 1; i < this.players.size(); i++ ) {
			
			Player player = this.players.get(i);
			
			Card toPlay = player.getHand().getCards()[0];
			
			try {
				turn.addCard(player, toPlay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.client.notifyTurn( turn );
			
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

}
