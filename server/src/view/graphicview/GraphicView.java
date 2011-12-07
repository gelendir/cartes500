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

public class GraphicView extends JFrame implements IView, GCardListener {

	private GGamingZone gamingZone = new GGamingZone();
	private Player actualPlayer;
	
	private GHand ghand = new GHand();
	
	private CountDownLatch cardChosed = new CountDownLatch(1);
	private Card choosenCard;
	
	public GraphicView() throws Exception {
		super();
		this.setTitle("Jeu du 500");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		Deck deck = new Deck();
		deck.mixCards();


		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(new GOtherHand(GOtherHand.Orientation.HORIZONTAL, 10), c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		this.add(new GOtherHand(GOtherHand.Orientation.HORIZONTAL, 10), c);

		Hand hand = new Hand(deck);
		ArrayList<Card> cards = new ArrayList<Card>(hand.getCards().length);
		for(Card card: hand.getCards()) {
			cards.add(card);
		}
		ghand.setHand(cards);
		ghand.setPlayableCards(hand.getPlayableCard(Suit.HEARTS));
		ghand.addGCardListener(this);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		this.add(ghand, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 2;
		c.gridy = 1;
		this.add(new GOtherHand(GOtherHand.Orientation.HORIZONTAL, 10), c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(this.gamingZone, c);

		this.pack();
		//this.setVisible(true);
		
		//GBetDialog s = new GBetDialog(this);
		//s.setVisible(true);
	}
	
	@Override
	public void choseenCard(Card card) {
		this.choosenCard = card;
		this.cardChosed.countDown();
		this.gamingZone.setCard(1, card);
		System.out.println("clique");
	}
	
	@Override
	public Player createPlayer() {
		String name = JOptionPane.showInputDialog(this, "Veuillez entrer votre nom d'utilisateur.");
		this.actualPlayer = new Player(name);
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
		try {
			this.cardChosed.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("debloque");
		return this.choosenCard;
	}

	@Override
	public void showPlayerTurn(Player player, Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerConnected(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public Bet askBet(Hand hand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playerHasBet(Player player, Bet bet) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void showGameStart(Player first) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showWinners(Player player, Player player2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTurnWinner(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerList(Player[] players) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception {
		GraphicView g = new GraphicView();
		TestGUI test= new TestGUI();
		test.setGraphicView(g);
		new Thread(test).start();
		g.setVisible(true);
	}
}
