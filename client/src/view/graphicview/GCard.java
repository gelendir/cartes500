package view.graphicview;

import game.card.Card;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GCard extends JPanel {
	
	private BufferedImage  image;
	private Card card;
	
	public GCard(Card card) {
		this.card = card;
		this.image = ImageCard.getImage(card);
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(this.image, 0, 0, null);
	}
	
	public Card getCard() {
		return this.card;
	}
}
