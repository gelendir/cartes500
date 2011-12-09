package view.graphicview;

import game.card.Card;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GHand extends JPanel {

	public static final int CARD_MARGIN_TOP = 15;
	public static final int CHOSEN_CARD_MARGIN_TOP = 0;

	private ArrayList<GCard> gcards = new ArrayList<GCard>();
	private ArrayList<GCardListener> gcardlistener = new ArrayList<GCardListener>();
	private ArrayList<Card> playableCards = new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();

	public GHand() {
		this.setLayout(null);
	}

	public void setHand(ArrayList<Card> cards) {
		if(cards == null) {
			throw new NullPointerException();
		}
		else {
			this.removeAll();
			this.gcards.clear();
			this.hand.clear();
			
			int sizeCards = cards.size();
			Insets insets = this.getInsets();
			MouseAdapter mouselistener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GHand.this.notifyGCardlistener(((GCard)e.getSource()).getCard());
				}
			};

			for(Card card : cards) {
				this.hand.add(card);
				GCard gcard = new GCard(card);
				this.gcards.add(gcard);
				gcard.addMouseListener(mouselistener);
				this.add(gcard);
				Dimension size = gcard.getPreferredSize();
				gcard.setBounds(ImageCard.getInstance().getExposedCardPart() * (sizeCards - 1) + insets.left, 
						GHand.CARD_MARGIN_TOP + insets.top,
						size.width, size.height);
				--sizeCards;
			}
			
			this.setPreferredSize(
					new Dimension(ImageCard.getInstance().getExposedCardPart() * (this.gcards.size() - 1) + ImageCard.getInstance().getCardWidth(), 
							ImageCard.getInstance().getCardHeight() + GHand.CARD_MARGIN_TOP));

			this.refresh();
		}
	}
	public void removeCard(Card card) {

		this.hand.remove(card);
		
		GCard toBeRemove = null;
		for(GCard gcard: this.gcards) {
			if(gcard.getCard().equals(card)) {
				toBeRemove = gcard;
				break;
			}
		}
		
		if(toBeRemove != null) {
			this.gcards.remove(toBeRemove);
			this.remove(toBeRemove);
		}
		
		this.refresh();
	}

	public void setPlayableCards(ArrayList<Card> cards) {
		this.resetPlayableCards();
		this.playableCards.clear();

		Insets insets = this.getInsets();
		for(GCard gcard : this.gcards) {
			Rectangle rect = gcard.getBounds();
			if(cards.contains(gcard.getCard())) {
				gcard.setBounds(rect.x, GHand.CHOSEN_CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
				this.playableCards.add(gcard.getCard());
			}
			else {
				gcard.setBounds(rect.x, GHand.CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
			}
		}
	}

	public ArrayList<Card> getPlayableCards() {
		return this.playableCards;
	}

	public void resetPlayableCards() {
		/*Insets insets = this.getInsets();
		for(GCard gcard : this.gcards) {
			Rectangle rect = gcard.getBounds();
			gcard.setBounds(rect.x, GHand.CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
		}*/
		this.playableCards.clear();
		this.setHand((ArrayList<Card>)this.hand.clone());
		
		this.refresh();
	}

	public void addGCardListener(GCardListener l) {
		this.gcardlistener.add(l);
	}

	public void removeGCardListener(GCardListener l) {
		this.gcardlistener.remove(l);
	}
	
	private void notifyGCardlistener(Card card) {
		for(GCardListener gcardlistener: this.gcardlistener) {
			gcardlistener.choseenCard(card);
		}
	}
	
	private void refresh() {
		this.repaint();
		this.revalidate();
	}
}
