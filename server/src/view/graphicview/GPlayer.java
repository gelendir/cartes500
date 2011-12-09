package view.graphicview;

import java.awt.FlowLayout;

import game.Player;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GPlayer extends JPanel {

	private Player player;
	
	private JPanel informationPlayer;
	private JPanel ghand;
	
	private JLabel name = new JLabel();
	private JLabel bet = new JLabel();
	private JLabel nbWonTurn = new JLabel();
	private JLabel turn = new JLabel();
	private JLabel winner = new JLabel();
	
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
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
				
		this.name.setText("Player name: " + player.getName());
		
		if(player.getOriginalBet() != null) {
			this.bet.setText("Bet: " + player.getOriginalBet().toString());
		}
		else {
			this.bet.setText("Has not bet.");
		}
		this.nbWonTurn.setText("Won turn: " + Integer.toString(player.getTurnWin()));
		
		this.refresh();
	}
	
	public void itsYourTurn() {
		this.turn.setText("It's your turn.");
	}
	
	public void itsHisTurn() {
		this.turn.setText("It's his turn.");
	}
	
	public void itsNotYourTurn() {
		this.turn.setText("");
	}
	
	public void setWinner() {
		this.winner.setText("You are a winner!");
	}
	
	public void setLooser() {
		this.winner.setText("You are a looser!");
	}
	
	private void refresh() {
		this.repaint();
		this.revalidate();
	}
}

