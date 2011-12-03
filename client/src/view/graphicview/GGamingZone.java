package view.graphicview;

import game.card.Card;
import game.enumeration.CardValue;
import game.enumeration.Suit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import javax.swing.JPanel;

public class GGamingZone extends JPanel {

	private GCard[] gcards = new GCard[4];
	private Point[] points = { new Point(1, 2), new Point(0, 1), new Point(1, 0), new Point(2, 1) };

	public GGamingZone() throws Exception {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int i = 0;
		for(Point point : this.points) {		
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = point.x;
			c.gridy = point.y;
			//this.gcards[i] = new GCard(unittest.TestCardFactory.createCard(Suit.CLUBS, CardValue.NINE));
			//this.gcards[i].resize(35);
			//this.add(this.gcards[i], c);
			++i;
		}
	}

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
		this.revalidate();
	}

	public void flushGamingZone() {
		this.removeAll();
	}
}
