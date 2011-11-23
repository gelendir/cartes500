package view.graphicview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GCard extends JPanel {
	
	BufferedImage  image;
	int valeur;
	
	public GCard(int valeur) {
		this.valeur = valeur;
		try {
			String imageName = "images/card.jpg";
			File input = new File(imageName);
			image = ImageIO.read(input);
		} catch (IOException ie) {
			System.out.println("Error:"+ie.getMessage());
		}
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( image, 0, 0, null);
	}
}
