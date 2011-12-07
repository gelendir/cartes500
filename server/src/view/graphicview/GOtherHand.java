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

public class GOtherHand extends JPanel {

	public enum Orientation {
		HORIZONTAL,
		VERTICAL
	}

	private Orientation orientation;
	private int nbCard;
	private BufferedImage image;

	public GOtherHand(Orientation orientation, int nbCard) {
		this.nbCard = nbCard;
		this.orientation = orientation;

		if(this.orientation.equals(Orientation.HORIZONTAL)) {
			this.image = ImageCard.getInstance().getEmptyCard();
			this.setPreferredSize(new Dimension(ImageCard.getInstance().getExposedCardPart() * (this.nbCard - 1) + ImageCard.getInstance().getCardWidth(),
					ImageCard.getInstance().getCardHeight() + GHand.CARD_MARGIN_TOP));
		}
		else {
			this.image = GOtherHand.rotate(ImageCard.getInstance().getEmptyCard(), 90);
			this.setPreferredSize(new Dimension(ImageCard.getInstance().getCardHeight(),
					ImageCard.getInstance().getExposedCardPart() * (this.nbCard - 1) + ImageCard.getInstance().getCardWidth() + GHand.CARD_MARGIN_TOP));
		}
	}


	public void paint(Graphics g) {
		super.paint(g);
		if(this.orientation.equals(Orientation.HORIZONTAL)) {
			for(int i = 0; i < this.nbCard; ++i) {
				g.drawImage( this.image, i * ImageCard.getInstance().getExposedCardPart(), ImageCard.getInstance().getExposedCardPart(), null);
			}
		}
		else {
			for(int i = 0; i < this.nbCard; ++i) {
				g.drawImage( this.image, ImageCard.getInstance().getExposedCardPart(), i * ImageCard.getInstance().getExposedCardPart(), null);
			}
		}

		//g.drawImage( this.image, 0 , 0, null);
	}

	private static BufferedImage rotate(BufferedImage img, int angle) {  
		/*int w = img.geWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(h, w, img.getType());  
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        g.drawImage(img, null, 0, 0);
        return dimg;*/
		
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
		return img;
	}
	
	public static BufferedImage rotate90DX(BufferedImage bi, int s) {
	    int width = bi.getWidth();
	    int height = bi.getHeight();
	    BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
	    for(int i=0; i<width; i++)
	        for(int j=0; j<height; j++)
	            biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));
	    return biFlip;
	}


	public int getNbCard() {
		return this.nbCard;
	}


	public void setNbCard(int nbCard) {
		this.nbCard = nbCard;
		this.repaint();
	}
}
