package view.graphicview;

import game.card.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Cette classe sert à garder l'unique instance des images 
 * de carte de jeu. Elle permet de récupérer les différentes
 * images.
 * @author Frédérik Paradis
 */
public class ImageCard {
	
	/**
	 * Singleton de l'image des cartes.
	 */
	private static ImageCard instance = new ImageCard();

	
	/**
	 * Le chemin de l'image des cartes.
	 */
	private static final String imageName = "/images/little_cards.png";
	
	
	/**
	 * La hauteur d'une carte.
	 */
	private int cardHeight;
	
	/**
	 * La largeur d'une carte.
	 */
	private int cardWidth;
	
	
	/**
	 * La partie exposée d'un carte afin de montrer
	 * la sorte de la carte.
	 */
	private int exposedCardPart;
	
	
	/**
	 * L'image des cartes.
	 */
	private BufferedImage image;
	
	/**
	 * L'image d'un dos de carte.
	 */
	private BufferedImage emptyCard;

	/**
	 * Cette fonction permet de récupérer l'instance du Singleton.
	 * @return Retourne l'instance du Singleton.
	 */
	public static ImageCard getInstance() {
		return ImageCard.instance;
	}
	
	/**
	 * Le constructeur charge l'image des cartes et initialise les 
	 * les attributs de la classe.
	 */
	private ImageCard() {
		try {
			InputStream in = new BufferedInputStream(getClass().getResourceAsStream(ImageCard.imageName));
			//this.image = ImageIO.read(new File(ImageCard.imageName));
			this.image = ImageIO.read(in);
			this.resize(75);
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
	
	/**
	 * Cette méthode retourne la hauteur d'une carte.
	 * @return Retourne la hauteur d'une carte.
	 */
	public int getCardHeight() {
		return this.cardHeight;
	}
	
	/**
	 * Cette méthode retourne la largeur d'une carte.
	 * @return Retourne la largeur d'une carte.
	 */
	public int getCardWidth() {
		return this.cardWidth;
	}
	
	/**
	 * Cette méthode retourne la taille de la partie 
	 * exposée d'une carte permettant de voir la sorte 
	 * de la carte. 
	 * @return Retourne la taille de la partie 
	 * exposée d'une carte.
	 */
	public int getExposedCardPart() {
		return exposedCardPart;
	}
	
	/**
	 * Cette méthode retourne l'image du dos d'une carte.
	 * @return Retourne l'image du dos d'une carte.
	 */
	public BufferedImage getEmptyCard() {
		return this.emptyCard;
	}

	/**
	 * Cette méthode permet d'obtenir l'image d'une carte.
	 * @param card La carte voulue.
	 * @return Retourne l'image de la carte voulue.
	 */
	public static BufferedImage getImage(Card card) {
		//La ligne de l'image
		int rowNumber;
		
		//La colonne de l'image
		int colNumber;
		
		
		//On trouve la ligne de l'image.
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

		//On trouve la colonne de l'image.
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

		//On découpe notre image pour avoir la bonne carte.
		return ImageCard.getInstance().image.getSubimage(colNumber * ImageCard.getInstance().getCardWidth(), 
				rowNumber * ImageCard.getInstance().getCardHeight(), 
				ImageCard.getInstance().getCardWidth(), 
				ImageCard.getInstance().getCardHeight());

	}
	
	/**
	 * Cette méthode redimensionne l'image des cartes selon un 
	 * certain pourcentage.
	 * @param pourcent Le pourcentage de redimensionnement.
	 */
	private void resize(int pourcent) {
		int width = (int)(this.image.getWidth() * (pourcent / 100.0));
		int height = (int)(this.image.getHeight() * (pourcent / 100.0));
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(this.image, 0, 0, width, height, null);

		graphics2D.dispose();
		this.image = scaledImage;
	}
}
