// Java Document
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class simplegraphics extends JFrame
{
  int x=150,y=100,xdir=1,ydir=2 ;
  final int suitCharYCoord = 185;
  private Player[] players;
  private int[] stacks;
  private Card[] buttonHand;
  private Card[] nonHand;
  private Card[] board;
  private int button;
  private String msg;
  private int pot;
  
  public simplegraphics()
  {
    boolean initial=true;
    //setVisible(true);
    //setSize(900,500);
  }
  
  public void init() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
   	setSize(900,500);
  }
  
  public void paint(Graphics g)
  {
	Font cardValFont = new Font("Arial", Font.BOLD, 24);
	Font cardSuitFont = new Font("Arial", Font.BOLD, 48);
	  
  	g.translate(0,25);//move origin down to compromise for the title bar
 
  	g.setColor(new Color(0, 102, 0));               //felt color
  	g.fillRect(0,0,getWidth(),getHeight());//background
 
	g.setColor(Color.white);
	g.fillRect(30,10,220,80);  //name background
	g.fillRect((getWidth() - 250),10,220,80); //name2 background
	g.setColor(Color.black);
	g.setFont(new Font("Arial", Font.BOLD, 24));
	
	//names
	g.drawString(players[0].getName(), 60, 40);
	g.drawString(players[1].getName(), (getWidth() - 240), 40);
	
	//stacks
	g.drawString("Chip count: " + stacks[0], 50, 80);
	g.drawString("Chip count: " + stacks[1], (getWidth() - 240), 80);
	
	if (buttonHand != null) {  //if the players have cards
		//System.out.println(buttonHand.toString());
		g.setColor(Color.white);	//SET COLOR FOR THE CARDS
		g.fillRect(30,100,105,147);//card background
		g.fillRect(145,100,105,147);//card2 background
		g.fillRect((getWidth() - 250),100,105,147);//card background
		g.fillRect((getWidth() - 135),100,105,147);//card2 background
	
		//settings for cards
		g.setColor(Color.black);
		
		//g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.setFont(cardSuitFont);
		if (button == 0) {
			//cardvals
			if (buttonHand[0].getSuit() == 'H' || buttonHand[0].getSuit() == 'D') {
				g.setColor(Color.red);
				if (buttonHand[0].getSuit() == 'H') {
					g.drawString("\u2665", 70, suitCharYCoord);
				}
				else {
					g.drawString("\u2666", 70, suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (buttonHand[0].getSuit() == 'S') {
					g.drawString("\u2660", 70, suitCharYCoord);
				}
				else {
					g.drawString("\u2663", 70, suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(buttonHand[0].getVal()), 40, 125);   
			g.setFont(cardSuitFont);
			if (buttonHand[1].getSuit() == 'H' || buttonHand[1].getSuit() == 'D') {
				g.setColor(Color.red);
				if (buttonHand[1].getSuit() == 'H') {
					g.drawString("\u2665", 185, suitCharYCoord);
				}
				else {
					g.drawString("\u2666", 185, suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (buttonHand[1].getSuit() == 'S') {
					g.drawString("\u2660", 185, suitCharYCoord);
				}
				else {
					g.drawString("\u2663", 185, suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(buttonHand[1].getVal()), 155, 125);
			g.setFont(cardSuitFont);
			if (nonHand[0].getSuit() == 'H' || nonHand[0].getSuit() == 'D') {
				g.setColor(Color.red);
				if (nonHand[0].getSuit() == 'H') {
					g.drawString("\u2665", (getWidth() - 210), suitCharYCoord);
				}
				else {
					g.drawString("\u2666", (getWidth() - 210), suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (nonHand[0].getSuit() == 'S') {
					g.drawString("\u2660", (getWidth() - 210), suitCharYCoord);
				}
				else {
					g.drawString("\u2663", (getWidth() - 210), suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(nonHand[0].getVal()), (getWidth() - 240), 125);
			g.setFont(cardSuitFont);
			if (nonHand[1].getSuit() == 'H' || nonHand[1].getSuit() == 'D') {
				g.setColor(Color.red);
				if (nonHand[1].getSuit() == 'H') {
					g.drawString("\u2665", (getWidth() - 95), suitCharYCoord);
				}
				else {
					g.drawString("\u2666", (getWidth() - 95), suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (nonHand[1].getSuit() == 'S') {
					g.drawString("\u2660", (getWidth() - 95), suitCharYCoord);
				}
				else {
					g.drawString("\u2663", (getWidth() - 95), suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(nonHand[1].getVal()), (getWidth() - 125), 125);
			g.setFont(cardSuitFont);
		}
		else {
			//cardvals
			if (nonHand[0].getSuit() == 'H' || nonHand[0].getSuit() == 'D') {
				g.setColor(Color.red);
				if (nonHand[0].getSuit() == 'H') {
					g.drawString("\u2665", 70, suitCharYCoord);
				}
				else {
					g.drawString("\u2666", 70, suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (nonHand[0].getSuit() == 'S') {
					g.drawString("\u2660", 70, suitCharYCoord);
				}
				else {
					g.drawString("\u2663", 70, suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(nonHand[0].getVal()), 40, 125); 
			g.setFont(cardSuitFont);
			if (nonHand[1].getSuit() == 'H' || nonHand[1].getSuit() == 'D') {
				g.setColor(Color.red);
				if (nonHand[1].getSuit() == 'H') {
					g.drawString("\u2665", 185, suitCharYCoord);
				}
				else {
					g.drawString("\u2666", 185, suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (nonHand[1].getSuit() == 'S') {
					g.drawString("\u2660", 185, suitCharYCoord);
				}
				else {
					g.drawString("\u2663", 185, suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(nonHand[1].getVal()), 155, 125);
			g.setFont(cardSuitFont);
			if (buttonHand[0].getSuit() == 'H' || buttonHand[0].getSuit() == 'D') {
				g.setColor(Color.red);
				if (buttonHand[0].getSuit() == 'H') {
					g.drawString("\u2665", (getWidth() - 210), suitCharYCoord);
				}
				else {
					g.drawString("\u2666", (getWidth() - 210), suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (buttonHand[0].getSuit() == 'S') {
					g.drawString("\u2660", (getWidth() - 210), suitCharYCoord);
				}
				else {
					g.drawString("\u2663", (getWidth() - 210), suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(buttonHand[0].getVal()), (getWidth() - 240), 125);  
			g.setFont(cardSuitFont);
			if (buttonHand[1].getSuit() == 'H' || buttonHand[1].getSuit() == 'D') {
				g.setColor(Color.red);
				if (buttonHand[1].getSuit() == 'H') {
					g.drawString("\u2665", (getWidth() - 95), suitCharYCoord);
				}
				else {
					g.drawString("\u2666", (getWidth() - 95), suitCharYCoord);
				}
			}
			else {
				g.setColor(Color.black);
				if (buttonHand[1].getSuit() == 'S') {
					g.drawString("\u2660", (getWidth() - 95), suitCharYCoord);
				}
				else {
					g.drawString("\u2663", (getWidth() - 95), suitCharYCoord);
				}
			}
			g.setFont(cardValFont);
			g.drawString(getCardValue(buttonHand[1].getVal()), (getWidth() - 125), 125);
			g.setFont(cardSuitFont);
		}
	}
	
	g.setFont(cardValFont);
	g.setColor(Color.white);
	//print pot
	if (pot > 0) {
		g.drawString("Pot: " + pot, (getWidth() / 2) - 15, 190);
	}
	
	//Print message
	int msgLen = msg.length();
	int msgLenHalf = msgLen / 2;
	int msgLenHalfDistance = msgLenHalf * 15;
    g.drawString(msg, ((getWidth()/2) - msgLenHalfDistance + 25), 280);
	
	//print the board
	if (board != null && board.length > 0) {
		int boardOffset = 120;
		int boardCardSpan = 115;
	//		g.fillRect(30,100,105,147);
		for(int i=0; i<board.length; i++){
			if (board[i] != null) {
				g.setColor(Color.white);
				g.fillRect(  (boardOffset + (i * boardCardSpan)), 300, 105, 147);
				
				g.setFont(cardSuitFont);
				
				//cardvals
				if (board[i].getSuit() == 'H' || board[i].getSuit() == 'D') {
					g.setColor(Color.red);
					if (board[i].getSuit() == 'H') {
						g.drawString("\u2665", (boardOffset + (i * boardCardSpan) + 35), 385);
					}
					else {
						g.drawString("\u2666", (boardOffset + (i * boardCardSpan) + 35), 385);
					}
				}
				else {
					g.setColor(Color.black);
					if (board[i].getSuit() == 'S') {
						g.drawString("\u2660", (boardOffset + (i * boardCardSpan) + 35), 385);
					}
					else {
						g.drawString("\u2663", (boardOffset + (i * boardCardSpan) + 35), 385);
					}
				}
				g.setFont(cardValFont);
				g.drawString(getCardValue(board[i].getVal()), (boardOffset + (i * boardCardSpan) + 10), 325);   
			}
		}
	}
	
  }

  
  public void setRefs(Player[] plyrs, int[] stcks, Card[] btnHand, Card[] nHand, Card[] brd, int btn, String message, int pt) {
	    //HERE, WE SHALL SET THE REFERENCES!
	    //System.out.println("Setting references...");
	    players = plyrs;
  		stacks = stcks;
 		buttonHand = btnHand;
  		nonHand = nHand;
		button = btn;
		msg = message;
		board = brd;
		pot = pt;
  }
  
  private static String getCardValue(int val) {
		if (val == 13) {
			return "K";
		}
		else if (val == 12) {
			return "Q";
		}
		else if (val == 11) {
			return "J";
		}
		else if (val == 1) {
			return "A";
		}
		else {
			return "" + val;
		}
	}
  
}