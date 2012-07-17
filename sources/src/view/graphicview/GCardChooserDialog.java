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

/**
 * Cette classe sert à l'interface graphique permettant
 * à l'utilisateur qui a gagné la mise de départ de choisir
 * les cartes qu'il souhaite.
 * @author Frédérik Paradis
 */
public class GCardChooserDialog extends JDialog implements GCardListener {

	/**
	 * Représente la main de toutes les cartes pouvant
	 * être choisies par l'utilisateur
	 */
	private GHand ghand = new GHand();
	
	/**
	 * Tableau contenant la liste des cartes choisies par l'utilisateur.
	 */
	private ArrayList<Card> choosenCard = new ArrayList<Card>();
	
	/**
	 * Le bouton permettant d'effectuer le choix des cartes.
	 */
	private JButton button = new JButton("Choose card!");

	/**
	 * Initialise la boîte de dialogue avec les cartes et le
	 * bouton de choix des cartes.
	 * @param cards La liste des cartes pouvant être chosie par
	 * l'utilisateur.
	 * @param owner Le fenêtre parente de ce dialogue
	 */
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
		this.setLocationRelativeTo(owner);
	}

	/**
	 * Cette méthode est appelé lorsque l'utilisateur clique
	 * sur une carte. Lorsqu'une carte est cliquée, on lève
	 * ou on baisse la carte selon si elle était levée ou pas.
	 * Lorsqu'il y a 10 cartes qui sont levées, on active le bouton
	 * permettant de valider le choix sinon il est désactivé.
	 */
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
	
	/**
	 * Cette méthode retourne un tableau des cartes choisies par
	 * le joueur.
	 * @return Retourne un tableau des cartes choisies par
	 * le joueur.
	 */
	public ArrayList<Card> getChoosenCard() {
		return this.choosenCard;
	}
}
