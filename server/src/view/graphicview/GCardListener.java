package view.graphicview;

import game.card.Card;

/**
 * Cette interface sert à informer les Listener
 * que l'utilisateur a choisi une carte.
 * @author Frédérik Paradis
 */
public interface GCardListener {
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur a 
	 * choisi une carte.
	 * @param card La carte que l'utilisateur a choisie.
	 */
	public void choseenCard(Card card);
}
