package view.graphicview;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;

import database.PlayerStatistics;

/**
 * Cette classe sert de boîte de dialogue pour afficher
 * les statistiques au joueur.
 * @author Frédérik Paradis
 */
public class GStatisticDialog extends JDialog {
	
	/**
	 * Ce constructeur initialise la boîte de dialogue
	 * avec les statistiques du joueur.
	 * @param stats Les statistiques du joueur
	 * @param owner Le fenêtre parent de la boîte de dialogue.
	 */
	public GStatisticDialog(PlayerStatistics stats, JFrame owner) {
		super(owner);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		
		this.add(new JLabel("Player name: " + stats.getName()));
		
		NumberFormatter f = new NumberFormatter(new DecimalFormat("###.#%"));
		try {
			this.add(new JLabel("Ratio: " + f.valueToString(((double)stats.getNbWonGame() / (double)stats.getNbPlayedGame()))));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.add(new JLabel("Total points: " + stats.getTotalPoints()));
		this.add(new JLabel("Average points per game: " + stats.getTotalPoints() / stats.getNbPlayedGame()));
		
		JButton close = new JButton("Close window");
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GStatisticDialog.this.dispose();
			}
		});
		this.add(close);
		
		this.setModal(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
	}
	
}
