// Java Document

public class Card {
	
	private char suit;
	private int val;
	
	public Card() {
		suit = 'N';
		val = 0;
	}
	
	public Card(char s, int v) {
		suit = s;
		val = v;
	}
	
	public Card(int v, char s) {
		suit = s;
		val = v;
	}
	
	public char getSuit() {
		return suit;
	}
	
	public int getVal() {
		return val;
	}
	
	
}