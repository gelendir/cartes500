package view;

import exception.EmptyDeckException;
import game.Hand;
import game.card.Deck;
import game.enumeration.Suit;
import view.graphicview.GraphicView;

public class TestGUI implements Runnable {

	private GraphicView g;
	
	@Override
	public void run() {
		
		Deck deck = new Deck();
		deck.mixCards();
		Hand hand = null;
		try {
			hand = new Hand( deck );
		} catch (EmptyDeckException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			synchronized (this) {
				this.wait(3000);
			}
			System.out.println(g.getCardToPlay(hand, Suit.CLUBS));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setGraphicView(GraphicView g) {
		this.g = g;
	}

}
