package view.graphicview;

import game.Bet;
import game.card.Card;
import game.card.Deck;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.IView;
import view.TestGUI;
import view.graphicview.GOtherHand.Orientation;

public class GraphicView extends JFrame implements IView, GCardListener {

	private GGamingZone gamingZone = new GGamingZone();
	private Player actualPlayer;

	private Player[] playerList = new Player[4];
	private GPlayer[] gplayers = new GPlayer[4];

	private int connectPlayer = 0;
	
	private GHand ghand = new GHand();

	private CountDownLatch cardChosed = new CountDownLatch(1);
	private Card choosenCard;

	public GraphicView() throws Exception {
		super();
		this.setTitle("Jeu du 500");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		Deck deck = new Deck();
		deck.mixCards();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		this.gplayers[1] =  new GPlayer(new GOtherHand(Orientation.HORIZONTAL, 10));
		this.add(this.gplayers[1], c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		this.gplayers[2] =  new GPlayer(new GOtherHand(Orientation.HORIZONTAL, 10));
		this.add(this.gplayers[2], c);

		/*Hand hand = new Hand(deck);
		ArrayList<Card> cards = new ArrayList<Card>(hand.getCards().length);
		for(Card card: hand.getCards()) {
			cards.add(card);
		}
		this.ghand.setHand(cards);
		this.ghand.setPlayableCards(hand.getPlayableCard(Suit.HEARTS));*/
		this.ghand.addGCardListener(this);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		this.gplayers[0] =  new GPlayer(this.ghand);
		this.add(this.gplayers[0], c);

		c.fill = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 1;
		this.gplayers[3] =  new GPlayer(new GOtherHand(Orientation.HORIZONTAL, 10));
		this.add(this.gplayers[3], c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(this.gamingZone, c);
		
		this.pack();
		this.setLocationRelativeTo(null);
		//this.setVisible(true);
	}

	@Override
	public void choseenCard(Card card) {
		if(this.ghand.getPlayableCards().contains(card)) {
			this.choosenCard = card;
			this.cardChosed.countDown();
			this.gamingZone.setCard(1, card);
			this.ghand.removeCard(card);
			this.ghand.resetPlayableCards();

			this.repaint();
			this.validate();
			this.pack();
		}
	}

	@Override
	public Player createPlayer() {
		String name = JOptionPane.showInputDialog(this, "Veuillez entrer votre nom d'utilisateur.");
		this.actualPlayer = new Player(name);
		
		/*this.playerList[0] = this.actualPlayer;
		++this.connectPlayer;*/
		//this.playerConnected(this.actualPlayer);
		
		return this.actualPlayer;
	}

	@Override
	public Card getCardToPlay(Hand hand, Suit suit) {
		ArrayList<Card> cards = new ArrayList<Card>(Hand.MAX_CARDS);
		Card[] c = hand.getCards();
		for(Card card: c) {
			cards.add(card);
		}
		ghand.setHand(cards);
		ghand.setPlayableCards(hand.getPlayableCard(suit));

		this.repaint();
		this.validate();
		this.pack();

		try {
			this.cardChosed.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.choosenCard;
	}

	@Override
	public void showPlayerTurn(Player player, Card card) {
		int indexPlayer = findPlayerIndex(player, this.playerList);
		this.gamingZone.setCard(indexPlayer + 1, card);

		this.playerList[indexPlayer] = player;
		this.gplayers[indexPlayer].setPlayer(player);

	}

	@Override
	public void playerConnected(Player player) {
		this.playerList[this.connectPlayer] = player;
		++this.connectPlayer;
		this.changePlayerStatus(player);
	}

	@Override
	public Bet askBet(Hand hand) {
		ArrayList<Card> cards = new ArrayList<Card>(Hand.MAX_CARDS);
		Card[] c = hand.getCards();
		for(Card card: c) {
			cards.add(card);
		}
		
		this.ghand.setHand(cards);
		this.pack();
		
		GBetDialog s = new GBetDialog(this);
		s.setVisible(true);

		return s.getBet();
	}

	@Override
	public void playerHasBet(Player player, Bet bet) {
		this.playerConnected(player);
		
		player.setOriginalBet(bet);
		this.changePlayerStatus(player);
	}

	@Override
	public void welcome() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Card> changeCards(Hand oldHand, Card[] availableCards) {
		ArrayList<Card> cards = new ArrayList<Card>(Hand.MAX_CARDS + Hand.MIN_CARDS);
		Card[] oldCards = oldHand.getCards();

		for(Card card: oldCards) {
			cards.add(card);
		}

		for(Card card: availableCards) {
			cards.add(card);
		}

		GCardChooserDialog s = new GCardChooserDialog(cards, this);
		s.setVisible(true);

		return s.getChoosenCard();
	}

	@Override
	public void showBetWinner(Player player, Suit gameSuit) {

		this.changePlayerStatus(player);

	}

	@Override
	public void showGameStart(Player first) {
		this.changePlayerStatus(first);

		for(int i = 0; i < this.playerList.length; ++i) {
			if(i == 0) {
				if(this.playerList[i].equals(first)) {
					this.gplayers[i].itsYourTurn();
				} else {
					this.gplayers[i].itsNotYourTurn();
				}
			} else {
				if(this.playerList[i].equals(first)) {
					this.gplayers[i].itsHisTurn();
				} else {
					this.gplayers[i].itsNotHisTurn();
				}
			}
		}
	}

	@Override
	public void showWinners(Player player, Player player2) {
		this.changePlayerStatus(player);
		this.changePlayerStatus(player2);

		for(int i = 0; i < this.playerList.length; ++i) {
			if(this.playerList[i].equals(player) || this.playerList[i].equals(player2)) {
				this.gplayers[i].setWinner();
			}
			else {
				this.gplayers[i].setLooser();
			}
		}
	}

	@Override
	public void showTurnWinner(Player player) {
		this.changePlayerStatus(player);
	}

	@Override
	public void setPlayerList(Player[] players) {
		int j = findPlayerIndex(this.actualPlayer, players);
		for(int i = 0; i < this.playerList.length; ++i) {
			this.playerList[i] = players[j]; 

			this.gplayers[i].setPlayer(this.playerList[i]);

			++j;
			if(j > 3) {
				j = 0;
			}
		}
		this.pack();
	}
	
	@Override
	public void showBetInvalid() {
		// TODO Auto-generated method stub
		
	}

	private static int findPlayerIndex(Player player, Player[] players) {
		for(int i = 0; i <  players.length; ++i) {
			if(players[i] != null && players[i].equals(player)) {
				return i;
			}
		}
		return -1;
	}

	private void changePlayerStatus(Player player) {
		int indexPlayer = findPlayerIndex(player, this.playerList);
		this.playerList[indexPlayer] = player;
		this.gplayers[indexPlayer].setPlayer(player);
		
		this.revalidate();
		this.repaint();
		this.pack();
	}

	public static void main(String[] args) throws Exception {
		GraphicView g = new GraphicView();
		TestGUI test= new TestGUI();
		test.setGraphicView(g);
		new Thread(test).start();
		g.setVisible(true);
	}
}
