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

public class GCard extends JPanel {
	
	private BufferedImage image;
	private Card card;
	
	public GCard(Card card) {
		this.card = card;
		this.image = ImageCard.getImage(card);
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(this.image, 0, 0, null);
	}
	
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
	
	public Card getCard() {
		return this.card;
	}
}
