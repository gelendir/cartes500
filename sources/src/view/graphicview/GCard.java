package view.graphicview;

import game.card.Card;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Cette classe représente un carte du joueur actuel
 * dans l'interface graphique.
 * @author Frédérik Paradis
 */
public class GCard extends JPanel {
	
	/**
	 * L'image de la carte.
	 */
	private BufferedImage image;
	
	/**
	 * La carte représentée par l'image.
	 */
	private Card card;
	
	/**
	 * Le constructeur initialise un carte graphique
	 * avec la carte passée en paramètre.
	 * @param card La carte graphique voulue.
	 */
	public GCard(Card card) {
		this.card = card;
		this.image = ImageCard.getImage(card);
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	/**
	 * Cette méthode affiche l'image.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(this.image, 0, 0, null);
	}
	
	/**
	 * Cette méthode sert à redimensionner l'image
	 * selon un certain pourcentage.
	 * @param pourcent Le pourcentage de redimensionnement.
	 */
	public void resize(int pourcent) {
		int width = (int)(this.image.getWidth() * (pourcent / 100.0));
		int height =(int)(this.image.getHeight() * (pourcent / 100.0));
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(this.image, 0, 0, width, height, null);

		graphics2D.dispose();
		this.image = scaledImage;
		this.setPreferredSize(new Dimension(width, height));
		this.repaint();
	}
	
	/**
	 * Cette méthode retourne la carte réprésentée par cette instance de GCard.
	 * @return Retourne la carte réprésentée par cette instance de GCard.
	 */
	public Card getCard() {
		return this.card;
	}
}
