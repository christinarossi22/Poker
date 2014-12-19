
//Java Document
//Java Document
public class Christina implements Player{
	
	private int stack;
	private String name;
	private Card[] hand = null;
	
	public Christina() {
		name = "Christina";
	}
	
	public Christina(String n) {
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
	//start the battle of the bots
	
	public String fcob(Card[] board, int toYou, int yourStack, int theirStack, int pt){
		try {
			
			if ((hand[0].getVal() == 1 && hand[1].getVal() == 1 )||
					(hand[0].getVal() == hand[1].getVal() ))
				{
				 return "bet";
				}
			else if (hand[0].getVal() == 1 || hand[1].getVal() == 1)	
			{
					return "call";
			}
			else if (board[4] != null)
			{
				if (hand[0].getVal() == board[0].getVal() || hand[1].getVal() == board[0].getVal() ||
						hand[0].getVal() == board[1].getVal() || hand[1].getVal() == board[1].getVal() ||
						hand[0].getVal() == board[2].getVal() || hand[1].getVal() == board[2].getVal() ||
					    hand[0].getVal() == board[3].getVal() || hand[1].getVal() == board[3].getVal() ||
					    hand[0].getVal() == board[4].getVal() || hand[1].getVal() == board[4].getVal()  )
				{
					return "bet";
				}
				else if(toYou == 0){
					return "call";
				}
				else{
					return"fold";
				}
			}
			else if (board[3] != null)
			{
				if (hand[0].getVal() == board[0].getVal() || hand[1].getVal() == board[0].getVal() ||
						hand[0].getVal() == board[1].getVal() || hand[1].getVal() == board[1].getVal() ||
						hand[0].getVal() == board[2].getVal() || hand[1].getVal() == board[2].getVal() ||
						hand[0].getVal() == board[3].getVal() || hand[1].getVal() == board[3].getVal())
				{
					return "bet";
				}
				else if(hand[0].getVal() > 10 || hand[1].getVal() > 10 ){
					return "call";
				}
				else{
					return "fold";
				}
			}
			else if (board[2] != null)
			{
				if (hand[0].getVal() == board[0].getVal() || hand[1].getVal() == board[0].getVal() ||
						hand[0].getVal() == board[1].getVal() || hand[1].getVal() == board[1].getVal() ||
						hand[0].getVal() == board[2].getVal() || hand[1].getVal() == board[2].getVal())
				{
					return "bet";
				}
				else if(hand[0].getVal() > 10 || hand[1].getVal() > 10 ||toYou==0 ){
					return "call";
				}
				else{
					return "call";
				}
			}
			else if (board[0]==null){
				if(hand[0].getVal() > 10 || hand[1].getVal() > 10 ||toYou==0 ){
				return "call";
			}
				else if ((hand[0].getVal() == 1 && hand[1].getVal() == 1 )||
						(hand[0].getVal() == hand[1].getVal() ))
					{
					 return "bet";
					}
				else if (hand[0].getVal() == 1 || hand[1].getVal() == 1)	
				{
						return "call";
				}
				else{
					return "fold";
				}
				
			}
			else{
				return "fold";
				}
		}
		catch (Exception e)
			{
				return "fold";
			}
	}
	//end the battle of the bots
	public String foc(Card[] board, int toYou, int yourStack, int theirStack, int pt){
				return "call";
	}
	
	
	
	
}