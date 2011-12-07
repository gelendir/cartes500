package view.graphicview;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import exception.InvalidBetException;
import game.Bet;
import game.enumeration.Suit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class GBetDialog extends JDialog implements MouseListener {

	private JComboBox<Suit> suitList;
	private JComboBox<Integer> valueList;
	private JButton betButton;
	private Bet bet;
	
	public GBetDialog(JFrame owner) {
		super(owner);
		
		this.setLayout(new FlowLayout());
		
		Suit[] suits = { Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.NONE };
		this.suitList = new JComboBox<Suit>(suits);
		this.add(this.suitList);
		
		Integer[] value = { 6, 7, 8, 9, 10 };
		this.valueList = new JComboBox<Integer>(value);
		this.add(this.valueList);
		
		this.betButton = new JButton("Bet!");
		this.betButton.addMouseListener(this);
		this.add(this.betButton);
		
		this.setModal(true);
		this.pack();
		this.setResizable(false);
	}
	
	public Bet getBet() {
		return this.bet;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		try {
			this.bet = new Bet((Integer)this.valueList.getSelectedItem(), (Suit)this.suitList.getSelectedItem());
			this.dispose();
		} catch (InvalidBetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
