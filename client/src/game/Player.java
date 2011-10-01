package game;

public class Player {
	private Hand hand = null;
	private Card bet = null;
	private boolean turn = false;
	private String name = "";
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setOriginalBet(Card card) {
		this.bet = card;
	}
	
	public Card getOriginalBet() {
		return this.bet;
	}
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public boolean isTurn() {
		return this.turn;
	}
	
	public void beginTurn() {
		this.turn = true;
	}
	
	public void endTurn() {
		this.turn = false;
	}
	
	public String toString() {
		return this.name;
	}
}
