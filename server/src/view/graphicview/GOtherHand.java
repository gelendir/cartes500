package view.graphicview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Cette classe représente la main graphique d'un autre joueur.
 * @author Frédérik Paradis
 */
public class GOtherHand extends JPanel {

	/**
	 * Le nombre de carte du joueur.
	 */
	private int nbCard;
	
	/**
	 * L'image du dos de carte.
	 */
	private BufferedImage image;

	/**
	 * Ce constructeur initialise la main avec le 
	 * nombre de carte.
	 * @param nbCard Le nombre de carte
	 */
	public GOtherHand(int nbCard) {
		this.nbCard = nbCard;

		this.image = ImageCard.getInstance().getEmptyCard();
		this.setPreferredSize(new Dimension(ImageCard.getInstance().getExposedCardPart() * (this.nbCard - 1) + ImageCard.getInstance().getCardWidth(),
				ImageCard.getInstance().getCardHeight() + GHand.CARD_MARGIN_TOP));
	}


	/**
	 * Cette méthode met à jour l'affichage de la main.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < this.nbCard; ++i) {
			g.drawImage( this.image, i * ImageCard.getInstance().getExposedCardPart(), ImageCard.getInstance().getExposedCardPart(), null);
		}
	}

	/**
	 * Cette méthode retourne le nombre de carte.
	 * @return Retourne le nombre de carte.
	 */
	public int getNbCard() {
		return this.nbCard;
	}

	/**
	 * Cette méthode supprime une carte de la main.
	 */
	public void removeCard() {
		--this.nbCard;
		this.repaint();
	}
	
	/**
	 * Cette méthode modifie le nombre de carte.
	 * @param nbCard Le nombre de carte.
	 */
	public void setNbCard(int nbCard) {
		this.nbCard = nbCard;
		this.repaint();
	}
}
