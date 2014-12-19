// Java Document
// Java Document
public class Chan implements Player{
	
	private int stack;
	private String name;
	private Card[] hand = null;
	
	public Chan() {
		name = "Johnny Chan";
	}
	
	public Chan(String n) {
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
			System.out.print(hand[0].getVal());
			System.out.print(hand[0].getSuit());
			System.out.print(",");
			System.out.print(hand[1].getVal());
			System.out.println(hand[1].getSuit());
		}
	}
	
	public String fcob(Card[] board, int toYou, int yourStack, int theirStack, int pt){
			if (hand[0].getVal() > 10 || hand[1].getVal() > 10) {
				return "bet";
			}
			else {
				double r = Math.random();
				if (r >= .5) {
					return "call";
				}
				else if (r >= .25) {
					return "bet";
				}
				else {
					return "fold";
				}
			}		
	}
	
	public String foc(Card[] board, int toYou, int yourStack, int theirStack, int pt){
				return "call";
	}
	
	
	
	
}