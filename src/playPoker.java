// Java Document
import java.io.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.security.SecureRandom;
import pokerai.game.eval.spears.*;

public class playPoker {
	
	public static void main(String[] args) {
		
		boolean debug = false;		//DEBUG VARIABLE - SET TO FALSE TO HAVE PRODUCTION (i.e. SLOW) DEALING
		int timeout = 400;			//AMOUNT OF TIME TO WAIT BEFORE MOVING ON...in milliseconds (1000 = one second)
		if ( args.length > 2) {
			//int i = Integer.parseInt(s.trim());
			//System.out.println("|" + args[2] + "|" + "debug" + "|" + (args[2] == "debug"));
			
			try
			{
			  // the String to int conversion happens here
			  int to = Integer.parseInt(args[2]);
			  
			  //while (to < 0 || to > 5000) {
				  
				  //System.out.println("Please enter a number between 100 and 5000 (in milliseconds) for the timeout.");
				  //Scanner input = new Scanner();
				  //to = input.readInt();
			  //}
			  
			  if (to == 0) {
				  debug = true;
			  }
			  else {
				  timeout = to;
			  }
			  
			  // print out the value after the conversion
			  //System.out.println("int i = " + i);
			}
			catch (NumberFormatException nfe)
			{
			  debug = true;
			}

		}
			

		int button = 0;				//WHO IS THE DEALER
		int raiseCounter = 0;		//HOW MANY TIMES HAS THERE BEEN A RAISE IN THIS ROUND OF BETTING
		final int raiseLimit = 4;	//MAX NUM OF TIMES SOMEONE CAN RAISE PER ROUND
		final int initStack = 500;	//STARTING STACK AMOUNT - USUALLY 100
		boolean itsOn = false;		//IF THE GAME IS STILL ON (true) OR OVER (false)
		Card[] deck = null;			//GAME DECK
		Card[] board = new Card[5]; //BOARD, OR DEALT CARDS		
		int pot = 0;
		final int smallBlind = 5;
		final int bigBlind = 10;
		int topCard = 0;
		boolean allIn = false;		//SET IF SOMEONE MAKES ALL IN CALL
		int[] stacks = {initStack, initStack};
		Card[] buttonHand = new Card[2];
		Card[] nonHand = new Card[2];
		Player[] players = new Player[2];
		simplegraphics SG = new simplegraphics();
		if (!debug) {
			SG.init();
		}
		
		
		//get players
		try {
			Class theClass  = Class.forName(args[0]);
			Player p1 = (Player)theClass.newInstance();
	
			Class theClass2  = Class.forName(args[1]);
			Player p2 = (Player)theClass2.newInstance();
			
			players[0] = p1;
			players[1] = p2;
		
			if (!debug) {
				drawPokerWindow(SG, players, stacks, null, null, null, button, "It's on!!!", pot);
				try {
					Thread.sleep(timeout);
				}
				catch(Exception e){
					//If this thread was intrrupted by nother thread
				}
			}
			
			//determine first dealer
			deck = getDeck();
			
			Card p1deal;
			Card p2deal;
			
			do {
			p1deal = deck[topCard];
			topCard++;
			p2deal = deck[topCard];
			topCard++;
			} while (p1deal.getVal() == p2deal.getVal());
			
			if (p1deal.getVal() > p2deal.getVal()) {
				button = 1;
			}
			else {
				button = 0;
			}
			//System.out.println("Button: " + button + "\nNon-button: " + nonButton(button) );
			itsOn = true;
			
			
			
			while (itsOn) {
				
				//CLEAN UP ANY PREVIOUS HANDS
				//clean up hand
				players[button].giveHand();
				players[nonButton(button)].giveHand();
				board = new Card[5];
				
				//ship the button
				button = nonButton(button);
				
				//if there was an allIn, set allIn to false in case allin player won
				allIn = false;
				
				//CHECK TO SEE IF A PLAYER IS 'BLINDED OUT' 
				//(i.e. the player does not have enough to place the automatic, or "blind", bet)
				
				if (stacks[button] > smallBlind) { 
					//if the button has enough to post the small blind, do so.
					stacks[button] -= smallBlind;
					pot = smallBlind;
				}
				else {
					//otherwise, you go all in with your blind
					pot = stacks[button];
					stacks[button] = 0;
					allIn = true;
					System.out.println(players[button].getName() + " is all in blind!");
				}
				
				if (allIn) {
					//if the button is blinded in AND...
					if (stacks[nonButton(button)] >= pot) {
						//...the non-button player has more than the button player, it's automatically called
						stacks[nonButton(button)] -= bigBlind;
						pot += bigBlind;
					}
					else {
						pot += stacks[nonButton(button)];
						stacks[nonButton(button)] = 0;
					}
					
				}
				else if (stacks[nonButton(button)] > bigBlind) {
					stacks[nonButton(button)] -= bigBlind;
					pot += bigBlind;
				}
				else {
					pot += stacks[nonButton(button)];
					stacks[nonButton(button)] = 0;
					allIn = true;
					System.out.println(players[nonButton(button)].getName() + " is all in blind!");
				}
				
				//if OK, then get deck
				deck = getDeck();
				topCard = 0;
				
				//deal hand
				Card temp1;
				Card temp2;
				Card temp3; //used in dealing flop
				
				temp1 = deck[topCard];
				topCard++;
				temp2 = deck[topCard];
				topCard++;
				buttonHand[0] = temp1;
				nonHand[0] = temp2;
				temp1 = deck[topCard];
				topCard++;
				temp2 = deck[topCard];
				topCard++;
				buttonHand[1] = temp1;
				nonHand[1] = temp2;
				printCardValue(buttonHand[0].getVal());
				System.out.print("(" + buttonHand[0].getSuit() + ") ");
				printCardValue(buttonHand[1].getVal());
				System.out.println("(" + buttonHand[1].getSuit() + ") ");
				
				printCardValue(nonHand[0].getVal());
				System.out.print("(" + nonHand[0].getSuit() + ") ");
				printCardValue(nonHand[1].getVal());
				System.out.println("(" + nonHand[1].getSuit() + ") ");
				
				players[button].getHand(buttonHand);
				players[nonButton(button)].getHand(nonHand);
				
				if (!debug) {
					drawPokerWindow(SG, players, stacks, buttonHand, nonHand, null, button, "Current dealer: " + players[button].getName(), pot);
					try {
						Thread.sleep(timeout);
					}
					catch(Exception e){
						//If this thread was intrrupted by nother thread
					}
				}
				else {
					players[0].displayInfo();
					System.out.println("Current stack: " + stacks[0]);
					players[1].displayInfo();
					System.out.println("Current stack: " + stacks[1]);
					System.out.println("\nCurrent dealer: " + players[button].getName());
					System.out.println("Current pot: " + pot);
				}
				
				//handle bet
				//public String fcob(char[] board, int toYou, int yourStack, int theirStack)
				String resp = "bet";
				int actionCounter = 0;
				int action = button;
				int toYou = 5;
				
				if (debug) { System.out.println("AllIn? " + allIn);}
					
				//handle flop betting round
				resp = "bet";
				if (allIn) {
					resp = "allin";
				}
				
				if (debug) { System.out.println("Resp? " + resp); }
				
				while (resp == "bet") {
					if (allIn || raiseCounter >= raiseLimit) {
						resp = players[action].foc(board, toYou, stacks[action], stacks[nonAction(action)], pot);
						if (resp != "call") {
							resp = "fold";
						}
							
					}
					else {
						resp = players[action].fcob(board, toYou, stacks[action], stacks[nonAction(action)], pot);
					}

					
					if (resp == "call" && stacks[action] <= toYou) {
						resp = "allin";
						//System.out.println(players[action].getName() + " tries to call, but goes all in to do so.");
					}
					if (resp == "bet" && stacks[action] < (toYou * 2) ) {
						//if they are trying to bet more than they have, see if they can just call...
						if (stacks[action] > toYou) {
							resp = "call";
						}
						else {
							resp = "allin";
						}
					}
					if (resp == "check" && toYou > 0) {
						resp = "fold";
					}
					
					if (resp == "fold") {
						stacks[nonAction(action)] += pot;
						pot = 0;
						if (!debug) {
							drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " folds. " + players[nonAction(action)].getName() + " wins the pot.", pot);
							try {
								Thread.sleep(timeout);
							}
							catch(Exception e){
								//If this thread was intrrupted by nother thread
							}
						}
						else {
							System.out.println(players[action].getName() + " folds.");
							System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
						}
					}
					else if (resp == "bet") {
						//System.out.println(pot);
						if (toYou == 0) { 
							toYou = smallBlind;
							stacks[action] -= smallBlind;
							pot += smallBlind;
						}
						else {
							stacks[action] -= toYou * 2;
							pot += toYou * 2;
						}
						raiseCounter++;
						//System.out.println(pot);
						if (!debug) {
							drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " bets.", pot);
							try {
								Thread.sleep(timeout);
							}
							catch(Exception e){
								//If this thread was intrrupted by nother thread
							}
						}
						else {
							System.out.println(players[action].getName() + " bets.");
						}
						
					}
					else if (resp == "call" || resp == "check") {
						if (allIn) {
							pot += toYou;
							stacks[action] -= toYou;
							allIn = true;
							if (!debug) {
								drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls the all in!", pot);
								try {
									Thread.sleep(timeout);
								}
								catch(Exception e){
									//If this thread was intrrupted by nother thread
								}
							}
							else {
								System.out.println(players[action].getName() + " calls the all in!");
								System.out.println("To you: " + toYou);
							}
							//int difference = toYou - stacks[action];
							//pot += toYou;
							//pot -= difference;
							//stacks[nonAction(action)] += difference;
							//stacks[action] = 0;
							
						}
						else {
							stacks[action] -= toYou;
							pot += toYou;
							if (actionCounter == 0) {
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " checks.", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " checks.");
								}
							}
							else {
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls.", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " calls.");
								}
							}
							
						}
						toYou = 0;
					}
					else if (resp == "allin") {
						if (stacks[action] <= toYou) {
							int difference = toYou - stacks[action];
							pot += stacks[action];
							pot -= difference;
							stacks[nonAction(action)] += difference;
							stacks[action] = 0;
							allIn = true;
							resp = "allin";
						}
						else {
							pot += stacks[action];
							toYou = stacks[action] - toYou;
							stacks[action] = 0;
							resp = "bet";
							allIn = true;
						}
						if (!debug) {
							drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " moves all in!", pot);
							try {
								Thread.sleep(timeout);
							}
							catch(Exception e){
								//If this thread was intrrupted by nother thread
							}
						}
						else {
							System.out.println(players[action].getName() + " moves all in!");
						}
					}
					else {
						stacks[nonAction(action)] += pot;
						pot = 0;
						if (!debug) {
							drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + "  gave an illegal response to action!  They fold!" + players[nonAction(action)].getName() + " wins the pot.", pot);
							try {
								Thread.sleep(timeout);
							}
							catch(Exception e){
								//If this thread was intrrupted by nother thread
							}
						}
						else {
							System.out.println(players[action].getName() + " gave an illegal response to action!  They fold!");
							System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
						}
						
					}
					
					action = nonAction(action);
					actionCounter++;
					
					if (actionCounter < 2 && (resp == "call" || resp == "check")) {
						resp = "bet";
					}
					
					if (toYou < 0) {
						stacks[action] -= toYou;
						resp = "allin";
					}
				}
				
				raiseCounter = 0;
				
				
				if (resp == "call" || resp == "check" || resp == "allin") {
					//deal flop
					temp1 = deck[topCard];
					topCard++;
					temp2 = deck[topCard];
					topCard++;
					temp3 = deck[topCard];
					topCard++;
					
					board[0] = temp1;
					board[1] = temp2;
					board[2] = temp3;
					
					System.out.print("Flop comes: ");
					
					printCardValue(temp1.getVal());
					System.out.print("(" + temp1.getSuit() + ") ");
					printCardValue(temp2.getVal());
					System.out.print("(" + temp2.getSuit() + ") ");
					printCardValue(temp3.getVal());
					System.out.println("(" + temp3.getSuit() + ").");
					if (!debug) {
						drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, "Flop comes...", pot);
						try {
							Thread.sleep(timeout);
						}
						catch(Exception e){
							//If this thread was intrrupted by nother thread
						}
					}
					else {
						players[0].displayInfo();
						System.out.println("Current stack: " + stacks[0]);
						players[1].displayInfo();
						System.out.println("Current stack: " + stacks[1]);
						System.out.println("\nCurrent dealer: " + players[button].getName());
						System.out.println("Current pot: " + pot);
					}
					
					if (debug) { System.out.println("AllIn? " + allIn);}
					
					//handle flop betting round
					resp = "bet";
					if (allIn) {
						resp = "allin";
					}
					
					if (debug) { System.out.println("Resp? " + resp); }
					
					actionCounter = 0;
					action = button;
					toYou = 0;
					
					while (resp == "bet") {
						if (allIn || raiseCounter >= raiseLimit) {
							resp = players[action].foc(board, toYou, stacks[action], stacks[nonAction(action)], pot);
							if (resp != "call") {
								resp = "fold";
							}
								
						}
						else {
							resp = players[action].fcob(board, toYou, stacks[action], stacks[nonAction(action)], pot);
						}
	
						
						if (resp == "call" && stacks[action] < toYou) {
							resp = "allin";
						}
						if (resp == "bet" && stacks[action] < (toYou * 2) ) {
							//if they are trying to bet more than they have, see if they can just call...
							if (stacks[action] >= toYou) {
								resp = "call";
							}
							else {
								resp = "allin";
							}
						}
						if (resp == "check" && toYou > 0) {
							resp = "fold";
						}
						
						if (resp == "fold") {
							stacks[nonAction(action)] += pot;
							pot = 0;
							if (!debug) {
								drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " folds. " + players[nonAction(action)].getName() + " wins the pot.", pot);
								try {
									Thread.sleep(timeout);
								}
								catch(Exception e){
									//If this thread was intrrupted by nother thread
								}
							}
							else {
								System.out.println(players[action].getName() + " folds.");
								System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
							}
							
						}
						else if (resp == "bet") {
							//System.out.println(pot + "|" + toYou);
							if (toYou == 0) { 
								toYou = smallBlind;
								stacks[action] -= smallBlind;
								pot += smallBlind;
							}
							else {
								stacks[action] -= toYou * 2;
								pot += toYou * 2;
							}
							raiseCounter++;
							//System.out.println(pot);
							if (!debug) {
								drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " bets.", pot);
								try {
									Thread.sleep(timeout);
								}
								catch(Exception e){
									//If this thread was intrrupted by nother thread
								}
							}
							else {
								System.out.println(players[action].getName() + " bets.");
							}
							
						}
						else if (resp == "call" || resp == "check") {
							if (allIn) {
								int difference = toYou - stacks[action];
								pot += toYou;
								pot -= difference;
								stacks[nonAction(action)] += difference;
								stacks[action] = 0;
								allIn = true;
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls the all in!", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " calls the all in!");
								}
								
							}
							else {
								stacks[action] -= toYou;
								pot += toYou;
								if (actionCounter == 0) {
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " checks.", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " checks.");
									}
								}
								else {
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls.", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " calls.");
									}
								}
								
							}
							toYou = 0;
						}
						else if (resp == "allin") {
							if (stacks[action] < toYou) {
								int difference = toYou - stacks[action];
								pot += stacks[action];
								pot -= difference;
								stacks[nonAction(action)] += difference;
								stacks[action] = 0;
								allIn = true;
								resp = "allin";
							}
							else {
								pot += stacks[action];
								toYou = stacks[action] - toYou;
								stacks[action] = 0;
								resp = "bet";
								allIn = true;
							}
							if (!debug) {
								drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " moves all in!", pot);
								try {
									Thread.sleep(timeout);
								}
								catch(Exception e){
									//If this thread was intrrupted by nother thread
								}
							}
							else {
								System.out.println(players[action].getName() + " moves all in!");
							}
						}
						else {
							stacks[nonAction(action)] += pot;
							pot = 0;
							if (!debug) {
								drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + "  gave an illegal response to action!  They fold!" + players[nonAction(action)].getName() + " wins the pot.", pot);
								try {
									Thread.sleep(timeout);
								}
								catch(Exception e){
									//If this thread was intrrupted by nother thread
								}
							}
							else {
								System.out.println(players[action].getName() + " gave an illegal response to action!  They fold!");
								System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
							}
							
						}
						
						action = nonAction(action);
						actionCounter++;
						
						if (actionCounter < 2 && (resp == "call" || resp == "check")) {
							resp = "bet";
						}
						
						if (toYou < 0) {
							stacks[action] -= toYou;
							resp = "allin";
						}
					}
					
					raiseCounter = 0;
					
					//deal turn
					if (resp == "call" || resp == "check" || resp == "allin") {
						
						temp1 = deck[topCard];
						topCard++;
						
						board[3] = temp1;

						System.out.print("Turn comes: ");
						printCardValue(temp1.getVal());
						System.out.println("(" + temp1.getSuit() + ").");
						if (!debug) {
							drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, "Turn comes...", pot);
							try {
								Thread.sleep(timeout);
							}
							catch(Exception e){
								//If this thread was intrrupted by nother thread
							}
						}
						else {
							players[0].displayInfo();
							System.out.println("Current stack: " + stacks[0]);
							players[1].displayInfo();
							System.out.println("Current stack: " + stacks[1]);
							System.out.println("\nCurrent dealer: " + players[button].getName());
							System.out.println("Current pot: " + pot);
						}

					
						//handle turn betting round
						if (debug) { System.out.println("AllIn? " + allIn);}
					
						resp = "bet";
						if (allIn) {
							resp = "allin";
						}
						
						if (debug) { System.out.println("Resp? " + resp); }
						
						actionCounter = 0;
						action = button;
						toYou = 0;
						
						while (resp == "bet") {
							if (allIn || raiseCounter >= raiseLimit) {
								resp = players[action].foc(board, toYou, stacks[action], stacks[nonAction(action)], pot);
								if (resp != "call") {
									resp = "fold";
								}
									
							}
							else {
								resp = players[action].fcob(board, toYou, stacks[action], stacks[nonAction(action)], pot);
							}
		
							
							if (resp == "call" && stacks[action] < toYou) {
								resp = "allin";
							}
							if (resp == "bet" && stacks[action] < (toYou * 2) ) {
								//if they are trying to bet more than they have, see if they can just call...
								if (stacks[action] >= toYou) {
									resp = "call";
								}
								else {
									resp = "allin";
								}
							}
							if (resp == "check" && toYou > 0) {
								resp = "fold";
							}
							
							if (resp == "fold") {
								stacks[nonAction(action)] += pot;
								pot = 0;
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " folds. " + players[nonAction(action)].getName() + " wins the pot.", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " folds.");
									System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
								}
								
							}
							else if (resp == "bet") {
								if (toYou == 0) { 
									toYou = bigBlind;
									stacks[action] -= bigBlind;
									pot += bigBlind;
								}
								else {
									stacks[action] -= toYou * 2;
									pot += toYou * 2;
								}
								raiseCounter++;
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " bets.", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " bets.");
								}
								
							}
							else if (resp == "call" || resp == "check") {
								if (allIn) {
									int difference = toYou - stacks[action];
									pot += toYou;
									pot -= difference;
									stacks[nonAction(action)] += difference;
									stacks[action] = 0;
									allIn = true;
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls the all in!", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " calls the all in!");
									}
									
								}
								else {
									stacks[action] -= toYou;
									pot += toYou;
									if (actionCounter == 0) {
										if (!debug) {
											drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " checks.", pot);
											try {
												Thread.sleep(timeout);
											}
											catch(Exception e){
												//If this thread was intrrupted by nother thread
											}
										}
										else {
											System.out.println(players[action].getName() + " checks.");
										}
									}
									else {
										if (!debug) {
											drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls.", pot);
											try {
												Thread.sleep(timeout);
											}
											catch(Exception e){
												//If this thread was intrrupted by nother thread
											}
										}
										else {
											System.out.println(players[action].getName() + " calls.");
										}
									}
									
								}
								toYou = 0;
							}
							else if (resp == "allin") {
								if (stacks[action] < toYou) {
									int difference = toYou - stacks[action];
									pot += stacks[action];
									pot -= difference;
									stacks[nonAction(action)] += difference;
									stacks[action] = 0;
									allIn = true;
									resp = "allin";
								}
								else {
									pot += stacks[action];
									toYou = stacks[action] - toYou;
									stacks[action] = 0;
									resp = "bet";
									allIn = true;
								}
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " moves all in!", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " moves all in!");
								}
							}
							else {
								stacks[nonAction(action)] += pot;
								pot = 0;
								if (!debug) {
									drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + "  gave an illegal response to action!  They fold!" + players[nonAction(action)].getName() + " wins the pot.", pot);
									try {
										Thread.sleep(timeout);
									}
									catch(Exception e){
										//If this thread was intrrupted by nother thread
									}
								}
								else {
									System.out.println(players[action].getName() + " gave an illegal response to action!  They fold!");
									System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
								}
								
							}
							
							action = nonAction(action);
							actionCounter++;
							
							if (actionCounter < 2 && (resp == "call" || resp == "check")) {
								resp = "bet";
							}
							
							if (toYou < 0) {
								stacks[action] -= toYou;
								resp = "allin";
							}
						}
					
						raiseCounter = 0;
					
				
				
				//deal river
						if (resp == "call" || resp == "check" || resp =="allin") {
							
							temp1 = deck[topCard];
							topCard++;
							
							board[4] = temp1;
	
							System.out.print("River comes: ");
							printCardValue(temp1.getVal());
							System.out.println("(" + temp1.getSuit() + ").");
							if (!debug) {
								drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, "Here's the river...", pot);
								try {
									Thread.sleep(timeout);
								}
								catch(Exception e){
									//If this thread was intrrupted by nother thread
								}
							}
							else {
								players[0].displayInfo();
								System.out.println("Current stack: " + stacks[0]);
								players[1].displayInfo();
								System.out.println("Current stack: " + stacks[1]);
								System.out.println("\nCurrent dealer: " + players[button].getName());
								System.out.println("Current pot: " + pot);
							}
						
							//handle river betting round
							if (debug) { System.out.println("AllIn? " + allIn);}
					
							resp = "bet";
							if (allIn) {
								resp = "allin";
							}
							
							if (debug) { System.out.println("Resp? " + resp); }
							
							actionCounter = 0;
							action = button;
							toYou = 0;
							
							while (resp == "bet") {
								if (allIn || raiseCounter >= raiseLimit) {
									resp = players[action].foc(board, toYou, stacks[action], stacks[nonAction(action)], pot);
									if (resp != "call") {
										resp = "fold";
									}
										
								}
								else {
									resp = players[action].fcob(board, toYou, stacks[action], stacks[nonAction(action)], pot);
								}
			
								
								if (resp == "call" && stacks[action] < toYou) {
									resp = "allin";
								}
								if (resp == "bet" && stacks[action] < (toYou * 2) ) {
									//if they are trying to bet more than they have, see if they can just call...
									if (stacks[action] >= toYou) {
										resp = "call";
									}
									else {
										resp = "allin";
									}
								}
								if (resp == "check" && toYou > 0) {
									resp = "fold";
								}
								
								if (resp == "fold") {
									stacks[nonAction(action)] += pot;
									pot = 0;
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " folds. " + players[nonAction(action)].getName() + " wins the pot.", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " folds.");
										System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
									}
									
								}
								else if (resp == "bet") {
									if (toYou == 0) { 
										toYou = bigBlind;
										stacks[action] -= bigBlind;
										pot += bigBlind;
									}
									else {
										stacks[action] -= toYou * 2;
										pot += toYou * 2;
									}
									raiseCounter++;
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " bets.", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " bets.");
									}
									
								}
								else if (resp == "call" || resp == "check") {
									if (allIn) {
										int difference = toYou - stacks[action];
										pot += toYou;
										pot -= difference;
										stacks[nonAction(action)] += difference;
										stacks[action] = 0;
										allIn = true;
										if (!debug) {
											drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls the all in!", pot);
											try {
												Thread.sleep(timeout);
											}
											catch(Exception e){
												//If this thread was intrrupted by nother thread
											}
										}
										else {
											System.out.println(players[action].getName() + " calls the all in!");
										}
										
									}
									else {
										stacks[action] -= toYou;
										pot += toYou;
										if (actionCounter == 0) {
											if (!debug) {
												drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " checks.", pot);
												try {
													Thread.sleep(timeout);
												}
												catch(Exception e){
													//If this thread was intrrupted by nother thread
												}
											}
											else {
												System.out.println(players[action].getName() + " checks.");
											}
										}
										else {
											if (!debug) {
												drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " calls.", pot);
												try {
													Thread.sleep(timeout);
												}
												catch(Exception e){
													//If this thread was intrrupted by nother thread
												}
											}
											else {
												System.out.println(players[action].getName() + " calls.");
											}
										}
										
									}
									toYou = 0;
								}
								else if (resp == "allin") {
									if (stacks[action] < toYou) {
										int difference = toYou - stacks[action];
										pot += stacks[action];
										pot -= difference;
										stacks[nonAction(action)] += difference;
										stacks[action] = 0;
										allIn = true;
										resp = "allin";
									}
									else {
										pot += stacks[action];
										toYou = stacks[action] - toYou;
										stacks[action] = 0;
										resp = "bet";
										allIn = true;
									}
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + " moves all in!", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " moves all in!");
									}
								}
								else {
									stacks[nonAction(action)] += pot;
									pot = 0;
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[action].getName() + "  gave an illegal response to action!  They fold!" + players[nonAction(action)].getName() + " wins the pot.", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[action].getName() + " gave an illegal response to action!  They fold!");
										System.out.println(players[nonAction(action)].getName() + " wins the pot.\n");
									}
									
								}
								
								action = nonAction(action);
								actionCounter++;
								
								if (actionCounter < 2 && (resp == "call" || resp == "check")) {
									resp = "bet";
								}
								
								if (toYou < 0) {
									stacks[action] -= toYou;
									resp = "allin";
								}
							}
					
					
					
						
							raiseCounter = 0;
							
							
							if (debug) { System.out.println("AllIn? " + allIn);}
					
							if (allIn) {
								resp = "allin";
							}
							
							if (debug) { System.out.println("Resp? " + resp); }
							
							if (resp == "call" || resp == "check" || resp == "allin") {
								//DETERMINE WINNER, PAY WINNER
								SevenCardEvaluator sce = new SevenCardEvaluator();
								
								int buttonEval = sce.evaluate(new pokerai.game.eval.spears.Card[] {makeEvalCard(board[0]), makeEvalCard(board[1]), makeEvalCard(board[2]), makeEvalCard(board[3]), makeEvalCard(board[4]), makeEvalCard(buttonHand[0]), makeEvalCard(buttonHand[1])});
								
								int nonEval = sce.evaluate(new pokerai.game.eval.spears.Card[] {makeEvalCard(board[0]), makeEvalCard(board[1]), makeEvalCard(board[2]), makeEvalCard(board[3]), makeEvalCard(board[4]), makeEvalCard(nonHand[0]), makeEvalCard(nonHand[1])});
								//evaluate player1 hand
								
								//UNCOMMENT NEXT TWO LINES TO PRINT OUT HAND RANKS FOR COMPARISON
								//System.out.println("\n" + players[button].getName() + " hand ranks as " + buttonEval + ".");
								//System.out.println(players[nonButton(button)].getName() + " hand ranks as " + nonEval + ".");
								
								if (buttonEval < nonEval) { //lowest rank wins
									stacks[button] += pot;
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[button].getName() + " wins!", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[button].getName() + " wins!");
									}
									
								}
								else if (nonEval < buttonEval) {
									stacks[nonButton(button)] += pot;
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, players[nonButton(button)].getName() + " wins!", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println(players[nonButton(button)].getName() + " wins!");
									}
									
								}
								else {
									stacks[button] += Math.ceil(pot / 2.0);
									stacks[nonButton(button)] += Math.floor(pot / 2.0);
									if (!debug) {
										drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, "CHOP!", pot);
										try {
											Thread.sleep(timeout);
										}
										catch(Exception e){
											//If this thread was intrrupted by nother thread
										}
									}
									else {
										System.out.println("CHOP!");
									}
									
								}
								
								
								pot = 0;
							
							}
						}
					}
				}
				
				try {
					Thread.sleep(timeout * 2);
				}
				catch(Exception e){
					//If this thread was intrrupted by nother thread
				}

				//SEE IF WE CAN GO AGAIN
				if (stacks[0] <= 0 || stacks[1] <= 0) {
					itsOn = false;
				}
				
			}
			
			if (!debug) {
				drawPokerWindow(SG, players, stacks, buttonHand, nonHand, board, button, "GAME OVER!", 0);
				try {
					Thread.sleep(timeout);
				}
				catch(Exception e){
					//If this thread was intrrupted by nother thread
				}
			}
			else {				
				players[0].displayInfo();
				System.out.println("Current stack: " + stacks[0]);
				players[1].displayInfo();
				System.out.println("Current stack: " + stacks[1]);
			}
		}
		catch ( ClassNotFoundException ex ){
		  System.err.println( ex + " Interpreter class must be in class path.");
		}
		catch( InstantiationException ex ){
		  System.err.println( ex + " Interpreter class must be concrete.");
		}
		catch( IllegalAccessException ex ){
		  System.err.println( ex + " Interpreter class must have a no-arg constructor.");
		}
		
	}
	
	public static Card[] getDeck() {
		//initialize 52 cards
		Card[] d = new Card[52];
		int dIndex = 0;
		char[] suits = {'H', 'D', 'C', 'S'};
		Random rgen = new SecureRandom();  // Random number generator

		for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
			for (int cardVal = 1; cardVal < 14; cardVal++) {
				d[dIndex] = new Card(cardVal, suits[suitIndex]);
				dIndex++;
			}
		}
		//shuffle
		
		
		for (int i=0; i<d.length; i++) {
			int randomPosition = rgen.nextInt(d.length);
			Card temp = d[i];
			d[i] = d[randomPosition];
			d[randomPosition] = temp;
		}
		
		return d;
		
	}
	
	public static int nonButton(int Button) {
		return ((Button - 1) * - 1);
	}
	
	public static int nonAction(int act) {
		return ((act - 1) * -1);
	}
	
	public static void printCardValue(int val) {
		if (val == 13) {
			System.out.print("K");
		}
		else if (val == 12) {
			System.out.print("Q");
		}
		else if (val == 11) {
			System.out.print("J");
		}
		else if (val == 1) {
			System.out.print("A");
		}
		else {
			System.out.print(val);
		}
	}
	
	public static pokerai.game.eval.spears.Card makeEvalCard(Card c) {
		//board[0].getVal(), board[0].getSuit()
		//Rank newRank = new Rank( c.getVal().toString() );
		//Suit newSuit = new Suit( c.getSuit().toString() );
		//pokerai.game.eval.spears.Card newCard = new pokerai.game.eval.spears.Card(newRank, newSuit);
		//System.out.println("In makeEvalCard: ");
		int val = c.getVal();
		String valString = "" + c.getVal();
		//System.out.println("\tEntering 'if': ");
		if (val == 13) {
			valString = "K";
		}
		else if (val == 12) {
			valString = "Q";
		}
		else if (val == 11) {
			valString = "J";
		}
		else if (val == 1) {
			valString = "A";
		}
		else if (val == 10) {
			valString = "T";
		}
		//System.out.println("\tExited 'if': ");
		
		String suitString = "" + c.getSuit();
		//System.out.println("\tRETURNING: ");
		return pokerai.game.eval.spears.Card.get(Rank.parse(valString), Suit.parse(suitString));
	}
	
	public static void drawPokerWindow(simplegraphics sg, Player[] plyrs, int[] stcks, Card[] btnHand, Card[] nHand, Card[] brd, int btn, String msg, int pt)		 {
		sg.setRefs(plyrs, stcks, btnHand, nHand, brd, btn, msg, pt);
		sg.repaint();
	}
	
	
}