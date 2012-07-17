package game.enumeration;

/**
 * Cet enum représente la valeur de la carte.
 * @author Frédérik Paradis
 */
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
	
	/**
	 * La valeur de la carte en entier.
	 */
	private final int value;
	
	/**
	 * Le constructeur initialise la valeur avec l'entier la représentant.
	 * @param value La valeur entière
	 */
	private CardValue(int value) {
		this.value =  value;
	}

	/**
	 * Cette méthode retourne la valeur de la carte.
	 * @return Retourne la valeur de la carte.
	 */
	public int getValue() {
		return this.value;			
	}
}
