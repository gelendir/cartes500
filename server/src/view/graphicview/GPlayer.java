package view.graphicview;

import java.awt.FlowLayout;

import game.Player;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cette classe est la partie graphique permettant de représenter
 * l'état d'un joueur sur le jeu.
 * @author Frédérik Paradis
 */
public class GPlayer extends JPanel {
	
	/**
	 * JPanel contenant les informations du joueurs.
	 */
	private JPanel informationPlayer;
	
	/**
	 * JPanel étant soit la main d'un autre joueur
	 * ou la main du joueur actuel.
	 */
	private JPanel ghand;
	
	
	/**
	 * JLabel contenant le nom du joueur.
	 */
	private JLabel name = new JLabel();
	
	/**
	 * JLabel contenant la mise du joueur.
	 */
	private JLabel bet = new JLabel();
	
	/**
	 * JLabel contenant le nombre de tour gagné du joueur.
	 */
	private JLabel nbWonTurn = new JLabel();
	
	/**
	 * JLabel indiquant c'est à qui de jouer.
	 */
	private JLabel turn = new JLabel();
	
	/**
	 * JLabel indiquant c'est à qui de jouer.
	 */
	private JLabel winner = new JLabel();
	
	/**
	 * Le constructeur initialise le GPlayer avec la main
	 * graphique du joueur.
	 * @param ghand La main graphique du joueur
	 */
	public GPlayer(JPanel ghand) {
		this.setLayout(new FlowLayout());
		
		this.informationPlayer = new JPanel();
		this.informationPlayer.setLayout(new BoxLayout(this.informationPlayer, BoxLayout.PAGE_AXIS));
		this.informationPlayer.add(this.name);
		this.informationPlayer.add(this.bet);
		this.informationPlayer.add(this.nbWonTurn);
		this.informationPlayer.add(this.turn);
		this.informationPlayer.add(this.winner);
		this.add(this.informationPlayer);
		
		
		this.ghand = ghand;
		this.add(this.ghand);
		this.refresh();
	}

	/**
	 * Cette méthode modifie les informations affichées par ceux
	 * du joueur passé en paramètre.
	 * @param player Le joueur.
	 */
	public void setPlayer(Player player) {				
		this.name.setText("Player name: " + player.getName());
		
		if(player.getOriginalBet() != null) {
			this.bet.setText("Bet: " + player.getOriginalBet().getNbRounds() + " of " + player.getOriginalBet().getSuit());
		}
		else {
			this.bet.setText("Has not bet.");
		}
		this.nbWonTurn.setText("Won turn: " + Integer.toString(player.getTurnWin()));
		
		this.refresh();
	}
	
	/**
	 * Cette méthode indique au joueur que c'est à son tour.
	 */
	public void itsYourTurn() {
		this.turn.setText("It's your turn.");
	}
	
	/**
	 * Cette méthode indique au joueur que c'est au tour d'un
	 * autre joueur.
	 */
	public void itsHisTurn() {
		this.turn.setText("It's his turn.");
	}
	
	/**
	 * Cette méthode indique au joueur que ce n'est pas à son tour.
	 */
	public void itsNotYourTurn() {
		this.turn.setText("");
	}
	
	/**
	 * Cette méthode indique au joueur que ce n'est pas au tour d'un
	 * autre joueur.
	 */
	public void itsNotHisTurn() {
		this.turn.setText("");
	}
	
	/**
	 * Cette méthode indique au joueur qu'il est le gagnant.
	 */
	public void setWinner() {
		this.winner.setText("You are a winner!");
	}
	
	/**
	 * Cette méthode indique au joueur qu'il n'est pas le gagnant.
	 */
	public void setLooser() {
		this.winner.setText("You are a looser!");
	}
	
	/**
	 * Cette méthode actualise l'interface.
	 */
	private void refresh() {
		this.repaint();
		this.revalidate();
	}
}

