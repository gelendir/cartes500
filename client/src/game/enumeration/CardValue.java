package game.enumeration;

public enum CardValue {
	TWO (1),
	THREE (2),
	FOUR (3),
	FIVE (4),
	SIX (5),
	SEVEN (6),
	EIGHT (7),
	NINE (8),
	TEN (9),
	JACK (10),
	QUEEN (11),
	KING (12),
	ACE (13),
	JOKER (14);
	
	private final int value;
	private CardValue(int value) {
		this.value =  value;
	}
	
	public int getValue() {
		return this.value;			
	}
}
