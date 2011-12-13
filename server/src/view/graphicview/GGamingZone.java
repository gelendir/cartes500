package view.graphicview;

import game.card.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * Cette classe 
 * @author Frédérik Paradis
 */
public class GGamingZone extends JPanel {

	/**
	 * Les 4 cartes graphiques
	 */
	private GCard[] gcards = new GCard[4];
	
	/**
	 * La position des cartes à placer dans le GridBagLayout
	 * en ordre pour les positions des joueurs.
	 */
	private Point[] points = { new Point(1, 2), new Point(0, 1), new Point(1, 0), new Point(2, 1) };

	/**
	 * Ce constructeur sert à initialiser la zone de jeu.
	 */
	public GGamingZone() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int i = 0;
		for(Point point : this.points) {		
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = point.x;
			c.gridy = point.y;
			++i;
		}
	}

	/**
	 * Cette méthode sert à placer une carte sur le jeu
	 * selon la position du joueur sur le jeu qui a joué 
	 * la carte.
	 * @param position La position du joueur
	 * @param card La carte jouée
	 */
	public void setCard(int position, Card card) {
		GridBagConstraints c = new GridBagConstraints();	
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = this.points[position - 1].x;
		c.gridy = this.points[position - 1].y;
		GCard gcard = new GCard(card);
		gcard.resize(35);

		if(this.gcards[position - 1] != null) {
			this.remove(this.gcards[position - 1]);
		}
		this.gcards[position - 1] = gcard;
		this.add(gcard, c);
		
		this.repaint();
		this.revalidate();
	}

	/**
	 * Cette méthode sert à vider la zone de jeu des cartes présentes.
	 */
	public void flushGamingZone() {
		this.removeAll();
	}
}
