package game.enumeration;

public enum Suit {
	SPADES (1),
	CLUBS (2), 
	DIAMONDS (3),
	HEARTS (4),
	BLACK (5),
	COLOR (5);
	
	private final int value;
	
	private Suit(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
}