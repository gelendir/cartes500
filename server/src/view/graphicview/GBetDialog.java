package view.graphicview;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import exception.InvalidBetException;
import game.Bet;
import game.enumeration.Suit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Cette classe sert à afficher une boite de dialogue
 * servant à l'utilisateur pour choisir son 
 * @author Frédérik Paradis
 */
public class GBetDialog extends JDialog {

	private JComboBox suitList;
	private JComboBox valueList;
	private Bet bet;
	
	public GBetDialog(JFrame owner) {
		super(owner);
				
		this.setLayout(new FlowLayout());
		
		Suit[] suits = { Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.NONE };
		this.suitList = new JComboBox(suits);
		this.add(this.suitList);
		
		Integer[] value = { 6, 7, 8, 9, 10 };
		this.valueList = new JComboBox(value);
		this.add(this.valueList);
		
		JButton betButton = new JButton("Bet!");
		betButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GBetDialog.this.finishBet();
			}
		});
		this.add(betButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GBetDialog.this.bet = null;
				GBetDialog.this.dispose();
			}
		});
		this.add(cancelButton);
		
		this.setModal(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
	}
	
	public Bet getBet() {
		return this.bet;
	}

	public void finishBet() {
		try {
			this.bet = new Bet((Integer)this.valueList.getSelectedItem(), (Suit)this.suitList.getSelectedItem());
			this.dispose();
		} catch (InvalidBetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
