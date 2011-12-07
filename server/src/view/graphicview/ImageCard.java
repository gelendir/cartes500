package view.graphicview;

import game.card.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCard {
	private static ImageCard instance = new ImageCard();

	private static final String imageName = "images/little_cards.png";
	
	public int cardHeight;// = 279;
	public int cardWidth;// = 192;
	
	private int exposedCardPart;
	
	private BufferedImage image;
	private BufferedImage emptyCard;

	public static ImageCard getInstance() {
		return ImageCard.instance;
	}
	
	private ImageCard() {
		try {
			this.image = ImageIO.read(new File(ImageCard.imageName));
			this.cardHeight = (int) Math.floor(this.image.getHeight() / (double)5);
			this.cardWidth = (int) Math.floor(this.image.getWidth() / (double)13);
			this.emptyCard = this.image.getSubimage(2 * this.cardWidth, 
					4 * this.cardHeight, 
					this.cardWidth, 
					this.cardHeight);
			this.exposedCardPart = (int) (this.cardWidth * 0.15);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCardHeight() {
		return this.cardHeight;
	}
	
	public int getCardWidth() {
		return this.cardWidth;
	}
	
	public int getExposedCardPart() {
		return exposedCardPart;
	}
	
	public BufferedImage getEmptyCard() {
		return this.emptyCard;
	}

	public static BufferedImage getImage(Card card) {
		int rowNumber;
		int colNumber;
		
		if(card.getSuit().equals(Suit.CLUBS)) {
			rowNumber = 0;
		}
		else if(card.getSuit().equals(Suit.DIAMONDS)) {
			rowNumber = 1;
		}
		else if(card.getSuit().equals(Suit.HEARTS)) {
			rowNumber = 2;
		}
		else if(card.getSuit().equals(Suit.SPADES)) {
			rowNumber = 3;
		}
		else { //if(card.getSuit().equals(Suit.BLACK) || card.getSuit().equals(Suit.COLOR)) {
			rowNumber = 4;
		}

		if(card.getCardValue().equals(CardValue.ACE)) {
			colNumber = 0;
		}
		else if(card.getCardValue().equals(CardValue.JOKER)) {
			if(card.getSuit().equals(Suit.BLACK)) {
				colNumber = 0;
			}
			else { //if(card.getSuit().equals(Suit.COLOR)) {
				colNumber = 1;
			}
		}
		else {
			colNumber = card.getCardValue().getValue();
		}

		return ImageCard.getInstance().image.getSubimage(colNumber * ImageCard.getInstance().getCardWidth(), 
				rowNumber * ImageCard.getInstance().getCardHeight(), 
				ImageCard.getInstance().getCardWidth(), 
				ImageCard.getInstance().getCardHeight());

	}
}
