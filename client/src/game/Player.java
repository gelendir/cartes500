package game;

public class Player {
	private Hand hand = null;
	private Bet bet = null;
	//private boolean turn = false;
	private String name = "";
	private int turnWin = 0;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setOriginalBet(Bet card) {
		this.bet = card;
	}
	
	public Bet getOriginalBet() {
		return this.bet;
	}
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public int getTurnWin() {
		return this.turnWin;
	}
	
	public void addTurnWin() {
		++this.turnWin;
	}
	
	
	/*public boolean isTurn() {
		return this.turn;
	}
	
	public void beginTurn() {
		this.turn = true;
	}
	
	public void endTurn() {
		this.turn = false;
	}*/
	
	public String toString() {
		return this.name;
	}
	
	public boolean equals( Player player ) {
		
		return this.name.equals( player.name );
		
	}
	
}
