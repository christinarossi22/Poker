// Java Document
import java.util.Scanner;
import javax.swing.JOptionPane;

public class HumanPlayer implements Player{
	
	private int stack;
	private String name;
	private Card[] hand = null;
	
	public HumanPlayer() {
		stack = 10;			//SET INTIAL STACK AMOUNT
		name = "Human Player";
	}
	
	public HumanPlayer(String n) {
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
			/* if (board != null && board.length > 0) {
				System.out.println("Board:");
				if (board[0] != null) {
					printCardValue(board[0].getVal());
					System.out.print(", " + board[0].getSuit());
					for (int i = 1; i < board.length; i++) {
						if (board[i] != null) {
							printCardValue(board[i].getVal());
							System.out.print(", " + board[i].getSuit());
						}
					}
				}
			}*/
			
			Object[] options = { "Bet", "Call", "Fold" };
			String msg = "\nThe bet is " + toYou + " to you.";
			msg += "\nThe pot is currently " + pt + ".";
			msg += "\nYour stack: " + yourStack + "\nTheir Stack: " + theirStack;
			
			msg += "\nWould you like to Bet, Call, or Fold?";
			int jOptResponse = JOptionPane.showOptionDialog(null, msg, "QUESTION_MESSAGE", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			
			/*System.out.println("\n\t\tJOption Response: " + jOptResponse + "\n\n");
			
			System.out.println("\nThe bet is " + toYou + " to you.");
			System.out.println("\nThe pot is currently " + pt + ".");
			System.out.println("\nYour stack: " + yourStack + "\tTheir Stack: " + theirStack);
			
			System.out.println("Would you like to (B)et, (C)all, or (F)old?");
			Scanner input = new Scanner(System.in);
			String rsp = input.nextLine();
			
			System.out.println("|" + rsp + "|");
			System.out.println("|" + (rsp != "B") + "|");
			
			
			while (!rsp.equals("F") && !rsp.equals("f") && !rsp.equals("fold") && !rsp.equals("Fold") && !rsp.equals("B") && !rsp.equals("b") && !rsp.equals("bet") && !rsp.equals("Bet") && !rsp.equals("C") && !rsp.equals("c") && !rsp.equals("Call") && !rsp.equals("call")) {
				System.out.println("I'm sorry, but I didn't understand that command.");
				System.out.println("Would you like to (B)et, (C)all, or (F)old?");
				input = new Scanner(System.in);
			    rsp = input.nextLine();
			}	
			
			if (rsp.equals("F") || rsp.equals("f") || rsp.equals("fold") || rsp.equals("Fold")) {
				rsp = "fold";
			}
			else if (rsp.equals("B") || rsp.equals("b") || rsp.equals("bet") || rsp.equals("Bet")) {
				rsp = "bet";
			}
			else if (rsp.equals("C") || rsp.equals("c") || rsp.equals("Call") || rsp.equals("call")) {
				rsp = "call";
			}
			*/
			
			String rsp = "fold";
			
			if (jOptResponse == 2) {
				rsp = "fold";
			}
			else if (jOptResponse == 0) {
				rsp = "bet";
			}
			else {
				rsp = "call";
			}
			
			return rsp;
	}
	
	public String foc(Card[] board, int toYou, int yourStack, int theirStack, int pt){
			/*if (board != null && board.length > 0) {
				System.out.println("Board:");
				if (board[0] != null) {
					printCardValue(board[0].getVal());
					System.out.print(", " + board[0].getSuit());
					for (int i = 1; i < board.length; i++) {
						if (board[i] != null) {
							printCardValue(board[i].getVal());
							System.out.print(", " + board[i].getSuit());
						}
					}
				}
			}
			*/
			
			Object[] options = { "Call", "Fold" };
			String msg = "\nThe bet is " + toYou + " to you.";
			msg += "\nThe pot is currently " + pt + ".";
			msg += "\nYour stack: " + yourStack + "\nTheir Stack: " + theirStack;
			
			msg += "\nWould you like to Call or Fold?";
			int jOptResponse = JOptionPane.showOptionDialog(null, msg, "QUESTION_MESSAGE", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			
			String rsp = "fold";
			
			if (jOptResponse == 0) {
				rsp = "call";
			}
			else {
				rsp = "fold";
			}
			
			
			return rsp;
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