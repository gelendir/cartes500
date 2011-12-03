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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.IView;

public class GraphicView extends JFrame implements IView, GCardListener {

	private GGamingZone gamingZone = new GGamingZone();
	
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
		GHand ghand = new GHand();
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
		this.setVisible(true);
		
		//GBetDialog s = new GBetDialog(this);
		//s.setVisible(true);
		//GCardChooserDialog s = new GCardChooserDialog(cards, this);
		//s.setVisible(true);
	}
	
	@Override
	public void choseenCard(Card card) {
		this.gamingZone.setCard(1, card);
	}
	
	@Override
	public Player createPlayer() {
		String name = JOptionPane.showInputDialog(this, "Veuillez entrer votre nom d'utilisateur.");
		Player player = new Player(name);
		return player;
	}

	@Override
	public Card getCardToPlay(Hand hand, Suit suit) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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

	public static void main(String[] args) throws Exception {
		new GraphicView();
	}
}
