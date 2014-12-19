// Java Document
public interface Player{
	
	public String getName();
	
	public void getHand(Card[] h);
	
	public void giveHand();
	
	public void displayInfo();
	
	public String fcob(Card[] board, int toYou, int yourStack, int theirStack, int pt);
	
	public String foc(Card[] board, int toYou, int yourStack, int theirStack, int pt);
	
	
	
}
