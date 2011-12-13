package view.graphicview;

import game.Bet;
import game.card.Card;
import game.card.Deck;
import game.Hand;
import game.Player;
import game.enumeration.Suit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.IView;

/**
 * Cette classe est l'interface graphique principale du
 * programme. Elle implémente l'interface IView.
 * @author Frédérik Paradis
 */
public class GraphicView extends JFrame implements IView, GCardListener {

	/**
	 * La zone de jeu où les cartes sont déposés.
	 */
	private GGamingZone gamingZone = new GGamingZone();
	
	/**
	 * Le joueur actuel du jeu.
	 */
	private Player actualPlayer;

	
	/**
	 * La liste des joueurs du jeu.
	 */
	private Player[] playerList = new Player[4];
	
	/**
	 * La liste des réprésentation graphique des joueurs
	 */
	private GPlayer[] gplayers = new GPlayer[4];
	
	/**
	 * Les mains des autres joueurs.
	 */
	private GOtherHand[] gotherhand = new GOtherHand[3];

	
	/**
	 * L'index du prochain joueur a se connecter
	 */
	private int connectPlayer = 0;

	
	/**
	 * La main du joueur actuel.
	 */
	private GHand ghand = new GHand();

	
	/**
	 * CountDownLatch servant à bloquer la fonction getCardToPlay tant
	 * que l'utilisateur n'a pas choisi de carte.
	 */
	private CountDownLatch cardChosed = new CountDownLatch(1);
	
	/**
	 * La carte choisie par le joueur pour le tour actuel.
	 */
	private Card choosenCard;

	/**
	 * Ce constructeur initialise tous les composants d'interface
	 * du jeu.
	 */
	public GraphicView() {
		super();
		this.setTitle("Jeu du 500");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		Deck deck = new Deck();
		deck.mixCards();

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		this.gotherhand[0] = new GOtherHand(10);
		this.gplayers[1] = new GPlayer(this.gotherhand[0]);
		this.add(this.gplayers[1], c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		this.gotherhand[1] = new GOtherHand(10);
		this.gplayers[2] =  new GPlayer(this.gotherhand[1]);
		this.add(this.gplayers[2], c);

		this.ghand.addGCardListener(this);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		this.gplayers[0] =  new GPlayer(this.ghand);
		this.add(this.gplayers[0], c);

		c.fill = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 1;
		this.gotherhand[2] = new GOtherHand(10);
		this.gplayers[3] =  new GPlayer(this.gotherhand[2]);
		this.add(this.gplayers[3], c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(this.gamingZone, c);

		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * @see GCardListener#choseenCard(Card)
	 */
	@Override
	public void choseenCard(Card card) {
		if(this.ghand.getPlayableCards().contains(card)) {
			this.choosenCard = card;
			this.cardChosed.countDown();
			this.cardChosed = new CountDownLatch(1);
			
			this.ghand.removeCard(card);
			this.ghand.resetPlayableCards();

			this.repaint();
			this.validate();
			this.pack();
		}
	}

	/**
	 * @see IView#createPlayer()
	 */
	@Override
	public Player createPlayer() {
		String name = JOptionPane.showInputDialog(this, "Veuillez entrer votre nom d'utilisateur.");
		this.actualPlayer = new Player(name);
		
		this.playerConnected(this.actualPlayer);

		return this.actualPlayer;
	}

	/**
	 * @see IView#getCardToPlay(Hand, Suit)
	 */
	@Override
	public Card getCardToPlay(Hand hand, Suit suit) {
		ArrayList<Card> cards = new ArrayList<Card>(Hand.MAX_CARDS);
		Card[] c = hand.getCards();
		for(Card card: c) {
			cards.add(card);
		}
		ghand.setHand(cards);
		ghand.setPlayableCards(hand.getPlayableCard(suit));

		this.repaint();
		this.validate();
		this.pack();

		try {
			this.cardChosed.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.choosenCard;
	}

	/**
	 * @see IView#showPlayerTurn(Player, Card)
	 */
	@Override
	public void showPlayerTurn(Player player, Card card) {
		int indexPlayer = findPlayerIndex(player, this.playerList);
		this.gamingZone.setCard(indexPlayer + 1, card);

		this.playerList[indexPlayer] = player;
		this.gplayers[indexPlayer].setPlayer(player);

		if(indexPlayer != 0) {
			this.gotherhand[indexPlayer - 1].removeCard();
		}
	}

	/**
	 * @see IView#playerConnected(Player)
	 */
	@Override
	public void playerConnected(Player player) {
		this.playerList[this.connectPlayer] = player;
		++this.connectPlayer;
		this.changePlayerStatus(player);
	}

	/**
	 * @see IView#askBet(Hand)
	 */
	@Override
	public Bet askBet(Hand hand) {
		ArrayList<Card> cards = new ArrayList<Card>(Hand.MAX_CARDS);
		Card[] c = hand.getCards();
		for(Card card: c) {
			cards.add(card);
		}

		this.ghand.setHand(cards);
		this.pack();

		GBetDialog s = new GBetDialog(this);
		s.setVisible(true);

		return s.getBet();
	}

	/**
	 * @see IView#playerHasBet(Player, Bet)
	 */
	@Override
	public void playerHasBet(Player player, Bet bet) {		
		player.setOriginalBet(bet);
		this.changePlayerStatus(player);
	}

	/**
	 * @see IView#welcome()
	 */
	@Override
	public void welcome() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see IView#changeCards(Hand, Card[])
	 */
	@Override
	public ArrayList<Card> changeCards(Hand oldHand, Card[] availableCards) {
		ArrayList<Card> cards = new ArrayList<Card>(Hand.MAX_CARDS + Hand.MIN_CARDS);
		Card[] oldCards = oldHand.getCards();

		for(Card card: oldCards) {
			cards.add(card);
		}

		for(Card card: availableCards) {
			cards.add(card);
		}

		GCardChooserDialog s = new GCardChooserDialog(cards, this);
		s.setVisible(true);

		return s.getChoosenCard();
	}

	/**
	 * @see IView#showBetWinner(Player, Suit)
	 */
	@Override
	public void showBetWinner(Player player, Suit gameSuit) {

		this.changePlayerStatus(player);

	}

	/**
	 * @see IView#showGameStart(Player)
	 */
	@Override
	public void showGameStart(Player first) {
		this.nextPlayer(first);
	}

	/**
	 * @see IView#showWinners(Player, Player)
	 */
	@Override
	public void showWinners(Player player, Player player2) {
		this.changePlayerStatus(player);
		this.changePlayerStatus(player2);

		for(int i = 0; i < this.playerList.length; ++i) {
			if(this.playerList[i].equals(player) || this.playerList[i].equals(player2)) {
				this.gplayers[i].setWinner();
			}
			else {
				this.gplayers[i].setLooser();
			}
		}
	}

	/**
	 * @see IView#showTurnWinner(Player)
	 */
	@Override
	public void showTurnWinner(Player player) {
		this.changePlayerStatus(player);
		this.gamingZone.flushGamingZone();
	}

	/**
	 * @see IView#setPlayerList(Player[])
	 */
	@Override
	public void setPlayerList(Player[] players) {
		int j = findPlayerIndex(this.actualPlayer, players);
		for(int i = 0; i < this.playerList.length; ++i) {
			this.playerList[i] = players[j]; 

			this.gplayers[i].setPlayer(this.playerList[i]);

			++j;
			if(j > 3) {
				j = 0;
			}
		}
		this.pack();
	}

	/**
	 * @see IView#nextPlayer(Player)
	 */
	@Override
	public void nextPlayer(Player player) {
		this.changePlayerStatus(player);

		for(int i = 0; i < this.playerList.length; ++i) {
			if(i == 0) {
				if(this.playerList[i].equals(player)) {
					this.gplayers[i].itsYourTurn();
				} else {
					this.gplayers[i].itsNotYourTurn();
				}
			} else {
				if(this.playerList[i].equals(player)) {
					this.gplayers[i].itsHisTurn();
				} else {
					this.gplayers[i].itsNotHisTurn();
				}
			}
		}
	}

	/**
	 * @see IView#showBetInvalid()
	 */
	@Override
	public void showBetInvalid() {
		JOptionPane.showMessageDialog(this, "The bet is invalid. If you do not want to bet, clik on Cancel", "Invalid bet", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Cette méthode permet de trouver l'index d'un joueur
	 * dans un tableau de joueur.
	 * @param player Le joueur
	 * @param players Le tableau de joueur
	 * @return Retourne l'index du joueur
	 */
	private static int findPlayerIndex(Player player, Player[] players) {
		for(int i = 0; i <  players.length; ++i) {
			if(players[i] != null && players[i].equals(player)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Cette méthode permet de mettre à jour le statut d'un joueur. 
	 * @param player Le joueur à mettre à jour.
	 */
	private void changePlayerStatus(Player player) {
		int indexPlayer = findPlayerIndex(player, this.playerList);
		this.playerList[indexPlayer] = player;
		this.gplayers[indexPlayer].setPlayer(player);

		this.revalidate();
		this.repaint();
		this.pack();
	}
}
