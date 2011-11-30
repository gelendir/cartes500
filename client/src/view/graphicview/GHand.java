package view.graphicview;

import game.Hand;
import game.card.Card;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;


public class GHand extends JPanel implements MouseListener {

	public static final int CARD_MARGIN_TOP = 15;
	public static final int CHOSEN_CARD_MARGIN_TOP = 0;
	public static /*final*/ int EXPOSED_CARD_PART = (int) (ImageCard.CARD_WIDTH * 0.15);

	private LinkedList<GCard> gcards = new LinkedList<GCard>();
	
	public GHand() throws Exception {
		this.setLayout(null);
	}

	public boolean setHand(Hand hand) {
		Card cards[] = hand.getCards();
				
		if(cards == null) {
			return false;
		}
		else {
			int size_cards = cards.length;
			Insets insets = this.getInsets();
			
			for(Card card : cards) {
				GCard gcard = new GCard(card);
				this.gcards.add(gcard);
				gcard.addMouseListener(this);
				this.add(gcard);
				Dimension size = gcard.getPreferredSize();
				gcard.setBounds(GHand.EXPOSED_CARD_PART * (size_cards - 1) + insets.left, 
						GHand.CARD_MARGIN_TOP + insets.top,
						size.width, size.height);
				--size_cards;
			}
			
			this.setPreferredSize(new Dimension(GHand.EXPOSED_CARD_PART * (this.gcards.size() - 1) + ImageCard.CARD_WIDTH, ImageCard.CARD_HEIGHT + GHand.CARD_MARGIN_TOP));
			
			return true;
		}
	}
	public boolean removeCard(Card card) {
		
		return false;
	}
	
	public void setPlayableCards(ArrayList<Card> cards) {
		Insets insets = this.getInsets();
		for(GCard gcard : this.gcards) {
			Rectangle rect = gcard.getBounds();
			if(cards.contains(gcard.getCard())) {
				gcard.setBounds(rect.x, GHand.CHOSEN_CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
			}
			else {
				gcard.setBounds(rect.x, GHand.CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
			}
		}
	}
	
	public void resetPlayableCards() {
		Insets insets = this.getInsets();
		for(GCard gcard : this.gcards) {
			Rectangle rect = gcard.getBounds();
			gcard.setBounds(rect.x, GHand.CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(((GCard)e.getSource()).getCard());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
