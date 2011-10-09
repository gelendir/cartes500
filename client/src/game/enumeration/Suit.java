package game.enumeration;

public enum Suit {
	SPADES (1),
	CLUBS (2), 
	DIAMONDS (3),
	HEARTS (4),
	NONE (5), //Utile pour la mise (bet) de départ
	BLACK (6),
	COLOR (7);
	
	private final int value;
	
	private Suit(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
}