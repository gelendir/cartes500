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
	
	public static /*final*/ int CARD_HEIGHT;// = 279;
	public static /*final*/ int CARD_WIDTH;// = 192;
	
	private BufferedImage image;

	private ImageCard() {
		try {
			this.image = ImageIO.read(new File(ImageCard.imageName));
			ImageCard.CARD_HEIGHT = (int) Math.floor(this.image.getHeight() / (double)5);
			ImageCard.CARD_WIDTH = (int) Math.floor(this.image.getWidth() / (double)13);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		return ImageCard.instance.image.getSubimage(colNumber * ImageCard.CARD_WIDTH, 
				rowNumber * ImageCard.CARD_HEIGHT, 
				ImageCard.CARD_WIDTH, 
				ImageCard.CARD_HEIGHT);

	}
}
