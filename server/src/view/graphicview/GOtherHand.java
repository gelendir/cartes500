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
	 * 
	 */
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < this.nbCard; ++i) {
			g.drawImage( this.image, i * ImageCard.getInstance().getExposedCardPart(), ImageCard.getInstance().getExposedCardPart(), null);
		}
	}

	public int getNbCard() {
		return this.nbCard;
	}

	public void removeCard() {
		--this.nbCard;
		this.repaint();
	}
	
	public void setNbCard(int nbCard) {
		this.nbCard = nbCard;
		this.repaint();
	}
}
