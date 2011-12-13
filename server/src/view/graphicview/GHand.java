package view.graphicview;

import game.card.Card;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Cette classe représente la main du joueur actuel.
 * @author Frédérik Paradis
 */
public class GHand extends JPanel {

	/**
	 * La marge du bas d'une carte qui est levée
	 */
	public static final int CARD_MARGIN_TOP = 15;
	
	/**
	 * La marge du bas d'une carte qui n'est pas levée.
	 */
	public static final int CHOSEN_CARD_MARGIN_TOP = 0;

	
	/**
	 * La liste des cartes graphiques du joueurs.
	 */
	private ArrayList<GCard> gcards = new ArrayList<GCard>();
	
	/**
	 * La liste des Listeners sur les cartes qui sont cliqués.
	 */
	private ArrayList<GCardListener> gcardlistener = new ArrayList<GCardListener>();
	
	/**
	 * La liste des cartes jouables par le joueurs.
	 */
	private ArrayList<Card> playableCards = new ArrayList<Card>();
	
	/**
	 * La liste des cartes de la main.
	 */
	private ArrayList<Card> hand = new ArrayList<Card>();

	/**
	 * Le constructeur initialise la main graphique.
	 */
	public GHand() {
		this.setLayout(null);
	}

	/**
	 * Cette méthode permet d'initialiser ou de modifier
	 * la liste des cartes de la main.
	 * @param cards La liste des cartes de la main.
	 */
	public void setHand(ArrayList<Card> cards) {
		if(cards == null) {
			throw new NullPointerException();
		}
		else {
			//On supprime toutes les cartes de la main.
			this.removeAll();
			this.gcards.clear();
			this.hand.clear();
			
			int sizeCards = cards.size();
			Insets insets = this.getInsets();
			
			//On initialise le MouseListener sur toutes les cartes.
			MouseAdapter mouselistener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GHand.this.notifyGCardlistener(((GCard)e.getSource()).getCard());
					System.out.println("Carte cliquée: " + ((GCard)e.getSource()).getCard().toString());
				}
			};

			for(Card card : cards) {
				this.hand.add(card);
				GCard gcard = new GCard(card);
				this.gcards.add(gcard);
				gcard.addMouseListener(mouselistener);
				this.add(gcard);
				Dimension size = gcard.getPreferredSize();
				
				//On place la carte dans la main.
				gcard.setBounds(ImageCard.getInstance().getExposedCardPart() * (sizeCards - 1) + insets.left, 
						GHand.CARD_MARGIN_TOP + insets.top,
						size.width, size.height);
				
				--sizeCards;
			}
			
			//On modifie la taille du Panel.
			this.setPreferredSize(
					new Dimension(ImageCard.getInstance().getExposedCardPart() * (this.gcards.size() - 1) + ImageCard.getInstance().getCardWidth(), 
							ImageCard.getInstance().getCardHeight() + GHand.CARD_MARGIN_TOP));

			this.refresh();
		}
	}
	
	/**
	 * Cette méthode supprime une carte de la main.
	 * @param card La carte à supprimer.
	 */
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

	/**
	 * Cette méthode sert à indiquer les cartes jouables donc les cartes
	 * qui seront levées.
	 * @param cards Les cartes jouables.
	 */
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

	/**
	 * Cette méthode retourne les cartes jouables par le joueur.
	 * @return Retourne les cartes jouables par le joueur.
	 */
	public ArrayList<Card> getPlayableCards() {
		return this.playableCards;
	}

	/**
	 * Cette méthode réinitialise toutes les cartes comme 
	 * étant non jouables.
	 */
	public void resetPlayableCards() {
		/*Insets insets = this.getInsets();
		for(GCard gcard : this.gcards) {
			Rectangle rect = gcard.getBounds();
			gcard.setBounds(rect.x, GHand.CARD_MARGIN_TOP + insets.top, rect.width, rect.height);
		}*/
		this.playableCards.clear();
		
		//On réinitialise la main avec les cartes actuels.
		this.setHand((ArrayList<Card>)this.hand.clone());
		
		this.refresh();
	}

	/**
	 * Cette méthode ajoute un GCardListener au client à 
	 * avertir lorsqu'une carte est cliquée.
	 * @param l Le client à ajouter.
	 */
	public void addGCardListener(GCardListener l) {
		this.gcardlistener.add(l);
	}

	/**
	 * Cette méthode supprimer un GCardListener des clients à 
	 * avertir lorsqu'une carte est cliquée.
	 * @param l Le client à supprimer.
	 */
	public void removeGCardListener(GCardListener l) {
		this.gcardlistener.remove(l);
	}
	
	/**
	 * Cette méthode averti tous les clients qu'une carte a 
	 * été cliquée.
	 * @param card La carte cliquée.
	 */
	private void notifyGCardlistener(Card card) {
		for(GCardListener gcardlistener: this.gcardlistener) {
			gcardlistener.choseenCard(card);
			System.out.println("notify listener");
		}
	}
	
	/**
	 * Cette méthode actualise l'interface.
	 */
	private void refresh() {
		this.repaint();
		this.revalidate();
	}
}
