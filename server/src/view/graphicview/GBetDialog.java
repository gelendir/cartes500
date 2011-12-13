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
 * servant à l'utilisateur pour choisir sa mise de jeu.
 * @author Frédérik Paradis
 */
public class GBetDialog extends JDialog {

	/**
	 * Ce ComboBox sert à afficher la liste des sortes
	 * de cartes.
	 */
	private JComboBox suitList;
	
	/**
	 * Ce ComboBox sert à afficher la liste des valeurs
	 * possible pour la mise.
	 */
	private JComboBox valueList;
	
	/**
	 * La mise que le joueur a choisi.
	 */
	private Bet bet;
	
	/**
	 * Cette méthode initialise la boîte de
	 * dialogue.
	 * @param owner La fenêtre parente du dialogue
	 */
	public GBetDialog(JFrame owner) {
		super(owner);
		
		//On affiche les éléments un à côté de l'autre.
		this.setLayout(new FlowLayout());
		
		//On affiche les valeurs de la liste des sortes.
		Suit[] suits = { Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.NONE };
		this.suitList = new JComboBox(suits);
		this.add(this.suitList);
		
		//On affiche les valeurs de la liste des valeurs possibles de la mise.
		Integer[] value = { 6, 7, 8, 9, 10 };
		this.valueList = new JComboBox(value);
		this.add(this.valueList);
		
		//On crée le bouton de mise et on lui ajoute un MouseListener
		JButton betButton = new JButton("Bet!");
		betButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GBetDialog.this.finishBet();
			}
		});
		this.add(betButton);
		
		//Même chose pour le bouton d'annulation. Le bouton d'annulation sert à ne 
		//rien miser.
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
	
	/**
	 * Cette méthode retourne la mise du joueur. Elle retourne
	 * null dans le cas où le joueur n'a pas voulu miser.
	 * @return Retourne la mise du joueur. Elle retourne
	 * null dans le cas où le joueur n'a pas voulu miser.
	 */
	public Bet getBet() {
		return this.bet;
	}

	/**
	 * Cette méthode est appelée lorsque l'utilisateur a choisi
	 * sa mise.
	 */
	private void finishBet() {
		try {
			this.bet = new Bet((Integer)this.valueList.getSelectedItem(), (Suit)this.suitList.getSelectedItem());
			
			//On ferme la fenêtre.
			this.dispose();
		} catch (InvalidBetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
