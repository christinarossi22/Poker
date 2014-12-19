// Java Document
public class DumbBetter implements Player{
	
	private int stack;
	private String name;
	private Card[] hand = null;
	
	public DumbBetter() {
		stack = 10;			//SET INTIAL STACK AMOUNT
		name = "Dumb Better";
	}
	
	public DumbBetter(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void getHand(Card[] h) {
		hand = h;
	}
	
	public void giveHand() {
		hand = null;
	}
	
	public void displayInfo() {
		System.out.println("");
		System.out.println("Player name: " + name);
		if (hand != null) {
			System.out.println("Hand:");
			printCardValue(hand[0].getVal());
			System.out.print("(" + hand[0].getSuit() + ")");
			System.out.print(",");
			printCardValue(hand[1].getVal());
			System.out.println("(" + hand[1].getSuit() + ")");
		}
	}
	
	public String fcob(Card[] board, int toYou, int yourStack, int theirStack, int pt){
			return "bet";
	}
	
	public String foc(Card[] board, int toYou, int yourStack, int theirStack, int pt){
			return "call";
	}
	
	private void printCardValue(int val) {
		if (val == 13) {
			System.out.print("K");
		}
		else if (val == 12) {
			System.out.print("Q");
		}
		else if (val == 11) {
			System.out.print("J");
		}
		else {
			System.out.print(val);
		}
	}
	
}