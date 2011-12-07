package view.graphicview;

import game.Hand;
import game.card.Card;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class GCardChooserDialog extends JDialog implements GCardListener {

	private GHand ghand = new GHand();
	private ArrayList<Card> choosenCard = new ArrayList<Card>();
	private JButton button = new JButton("Choose card!");

	public GCardChooserDialog(ArrayList<Card> cards, JFrame owner) {
		super(owner);

		this.setLayout(new FlowLayout());
		
		this.ghand.setHand(cards);
		this.ghand.addGCardListener(this);
		this.add(this.ghand);
		
		this.button.setEnabled(false);
		this.button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(GCardChooserDialog.this.button.isEnabled()) {
					GCardChooserDialog.this.dispose();
				}
			}
		});
		this.add(this.button);

		this.setModal(true);
		this.pack();
		this.setResizable(false);
	}

	@Override
	public void choseenCard(Card card) {
		if(!this.choosenCard.contains(card)) {
			if(this.choosenCard.size() < Hand.MAX_CARDS) {	
				this.choosenCard.add(card);
			}
		}
		else {
			this.choosenCard.remove(card);
		}
		
		if(this.choosenCard.size() == Hand.MAX_CARDS) {
			this.button.setEnabled(true);
		}
		else {
			this.button.setEnabled(false);
		}

		this.ghand.setPlayableCards(this.choosenCard);
	}
	
	public ArrayList<Card> getChoosenCard() {
		return this.choosenCard;
	}
}
