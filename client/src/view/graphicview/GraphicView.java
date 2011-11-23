package view.graphicview;

import game.Bet;
import game.card.Card;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.JFrame;

import view.IView;

public class GraphicView extends JFrame implements IView {

	public GraphicView() {
		super();
		
		this.setTitle("Jeu du 500");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
		this.setSize(400, 500);
		this.setVisible(true);
	}

	@Override
	public Player createPlayer() {
		// TODO Auto-generated method stub
		return null;
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

}
