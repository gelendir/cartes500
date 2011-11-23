package view.graphicview;

import game.card.Card;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GHand extends JPanel implements MouseListener {

	public GHand() {
		this.setLayout(null);
		Insets insets = this.getInsets();
		
		GCard carte = new GCard(2);
		carte.addMouseListener(this);
		Dimension size = carte.getPreferredSize();
		carte.setBounds(50 + insets.left, 15 + insets.top,
		             size.width, size.height);
		this.add(carte);
		
		carte = new GCard(1);
		carte.addMouseListener(this);
		size = carte.getPreferredSize();
		carte.setBounds(25 + insets.left, 0 + insets.top,
		             size.width, size.height);
		this.add(carte);
		this.setPreferredSize(new Dimension(25*2 + size.width, size.height + 15));
	}
	
	public void addCard(Card card) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(((GCard)e.getSource()).valeur);
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
	
	public static void main(String[] args){
		try{
			JFrame f = new JFrame("Offscreen Paint");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GHand p = new GHand();
			f.add(p);
			f.pack();
			f.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
