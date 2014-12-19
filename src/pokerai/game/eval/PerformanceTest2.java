package pokerai.game.eval;

import com.biotools.meerkat.Hand;
import pokerai.game.eval.alberta.HandEvaluatorAlberta;
import pokerai.game.eval.indiana.FastHandEvaluator;
import pokerai.game.eval.indiana.HandEvaluator;
import pokerai.game.eval.spears.SevenCardEvaluator;
import pokerai.game.eval.spears2p2.StateTableEvaluator;
import pokerai.game.eval.stevebrecher.HandEval;

import java.awt.*;
import java.awt.event.InputEvent;

public class PerformanceTest2 {

  /*

    This is the primary class that is used for benchmarking the hand evaluators
      from March 30-th 2008

    It is the same benchmark used for comparing the 2+2 hand evaluators.

    @Author Indiana 2008

    Todo:
    - RayW/LUV works for 7-card hands only, for 5 and 6 card hands, ArrayIndexOutOfBonud exception - Doublecheck.
    - Steve B. with partial state generation, implement and try

   */

  static String[] ref = {
          "( 1) Indiana-1, 2006, http://pokerai.org/pf3",
          "( 2) Indiana-3, 2007, http://pokerai.org/pf3",
          "( 3) University of Alberta, 2000, http://spaz.ca/poker",
          "( 4) Spears port of Kevin Suffecool's C evaluator, http://pokerai.org/pf3",
          "( 5) Spears port of 2+2 evaluator, http://pokerai.org/pf3, http://forumserver.twoplustwo.com",
          "( 6) Steve Brecher HandEval, http://www.stevebrecher.com/Software/software.html",
          "( 7) Spears adaptation of RayW LUT hand evaluator, http://pokerai.org/pf3, http://forumserver.twoplustwo.com",
          "( 8) Steve Brecher HandEval with [url=http://archives1.twoplustwo.com/showthreaded.php?Cat=0&Number=8582014&page=0&vc=1]partial state generation[/url], http://pokerai.org/pf3",
          "( 9) Pokerstove (Andrew Prock's) jpoker, http://www.pokerstove.com/download/jpoker.tar.gz",
  };

  public static final void waitfor(int ms) { try { Thread.currentThread().sleep(ms); } catch (Exception e) { e.printStackTrace(); }}

  public static void main(String args[]) {
    try {
      Robot x = new Robot();
      waitfor(5000);
      x.mouseMove(1000, 500);
      x.mousePress(InputEvent.BUTTON1_MASK); waitfor(100); x.mouseRelease(InputEvent.BUTTON1_MASK);
      x.mousePress(InputEvent.BUTTON1_MASK); waitfor(100); x.mouseRelease(InputEvent.BUTTON1_MASK);
      x.mousePress(InputEvent.BUTTON1_MASK); waitfor(100); x.mouseRelease(InputEvent.BUTTON1_MASK);
      System.exit(1);
    } catch (Exception e) {};
    //correctnessTest();
    //testIndiana();
    //testIndiana3();
    //testAlberta();
    //testSpears();
    //testSpears2p2();
    testSteveBrecher();
    //testRayW();
    testSteveBrecherE2();
    //testPokerstove();
  }

  public static void testIndiana() {
    int[] hand = new int[7];
    long time = System.currentTimeMillis();
    long sum = 0;
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  // need to have all the assignments here, as the array is sorted internally
                  hand[0] = h1;
                  hand[1] = h2;
                  hand[2] = h3;
                  hand[3] = h4;
                  hand[4] = h5;
                  hand[5] = h6;
                  hand[6] = h7;
                  sum += HandEvaluator.defineHand(hand);
                }}}}}}}
    print(sum, time, 133784560, 0);
  }

  public static void testIndiana3() {
    int[] hand = new int[7];
    long time = System.currentTimeMillis();
    long sum = 0;
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  // need to have all the assignments here, as the array is sorted internally
                  hand[0] = h1;
                  hand[1] = h2;
                  hand[2] = h3;
                  hand[3] = h4;
                  hand[4] = h5;
                  hand[5] = h6;
                  hand[6] = h7;
                  sum += FastHandEvaluator.evaluate7(hand);
                }}}}}}}
    print(sum, time, 133784560, 1);
  }


  public static void testAlberta() {
    long time = System.currentTimeMillis();
    Hand h = new Hand();
    long sum = 0;
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  h.makeEmpty();
                  h.addCard(h1);
                  h.addCard(h2);
                  h.addCard(h3);
                  h.addCard(h4);
                  h.addCard(h5);
                  h.addCard(h6);
                  h.addCard(h7);
                  sum += HandEvaluatorAlberta.rankHand(h);
                }}}}}}}
    print(sum, time, 133784560, 2);
  }

  public static void testSpears() {
    SevenCardEvaluator eval = new SevenCardEvaluator();
    //pokerai.game.eval.spears.Card c = pokerai.game.eval.spears.Card.parse("Th");
    //System.out.println("should be 34: " + c.ordinal());
    pokerai.game.eval.spears.Card[] cards = new pokerai.game.eval.spears.Card[7];
    for (int i = 0; i < 7; i++) cards[i] = pokerai.game.eval.spears.Card.parse("AsAh");
    //pokerai.game.eval.spears.Card[] deck = pokerai.game.eval.spears.Card.values();
    long time = System.currentTimeMillis();
    long sum = 0;
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  cards[0] = pokerai.game.eval.spears.Card.get(h1);
                  cards[1] = pokerai.game.eval.spears.Card.get(h2);
                  cards[2] = pokerai.game.eval.spears.Card.get(h3);
                  cards[3] = pokerai.game.eval.spears.Card.get(h4);
                  cards[4] = pokerai.game.eval.spears.Card.get(h5);
                  cards[5] = pokerai.game.eval.spears.Card.get(h6);
                  cards[6] = pokerai.game.eval.spears.Card.get(h7);
                  sum += eval.evaluate(cards);
                }}}}}}}
    print(sum, time, 133784560, 3);
  }


  public static void testSpears2p2() {
    //pokerai.game.eval.spears2p2.Card c = pokerai.game.eval.spears2p2.Card.parse("Th");
    //System.out.println("should be 34: " + c.ordinal());
    StateTableEvaluator.initialize();
    pokerai.game.eval.spears2p2.Card[] cards = new pokerai.game.eval.spears2p2.Card[7];
    for (int i = 0; i < 7; i++) cards[i] = pokerai.game.eval.spears2p2.Card.parse("AsAh");
    //pokerai.game.eval.spears.Card[] deck = pokerai.game.eval.spears.Card.values();
    long time = System.currentTimeMillis();
    long sum = 0;
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  cards[0] = pokerai.game.eval.spears2p2.Card.get(h1);
                  cards[1] = pokerai.game.eval.spears2p2.Card.get(h2);
                  cards[2] = pokerai.game.eval.spears2p2.Card.get(h3);
                  cards[3] = pokerai.game.eval.spears2p2.Card.get(h4);
                  cards[4] = pokerai.game.eval.spears2p2.Card.get(h5);
                  cards[5] = pokerai.game.eval.spears2p2.Card.get(h6);
                  cards[6] = pokerai.game.eval.spears2p2.Card.get(h7);
                  sum += StateTableEvaluator.getRank(cards);
                  //System.out.println(sum);
                }}}}}}}
    print(sum, time, 133784560, 4);
  }

  public static void testRayW() {
  //pokerai.game.eval.spears2p2.Card c = pokerai.game.eval.spears2p2.Card.parse("Th");
  //System.out.println("should be 34: " + c.ordinal());
  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
  long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  StateTableEvaluator.initialize();
  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
  long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb");
  long time = System.currentTimeMillis();
  int h1, h2, h3, h4, h5, h6, h7;
  int u0, u1, u2, u3, u4, u5;
  int[] handRanks = StateTableEvaluator.handRanks;
  int[]          handEnumerations = new int[10];
  int[][] equivalencyEnumerations = new int[10][3000];
  String[] handDescriptions = {"Invalid Hand", "High Card", "One Pair", "Two Pair", "Three of a Kind",
                             "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};
  int numHands = 0;
  int handRank;
  long sum = 0;
  int NT = 10;
  for (int i = 0; i < NT; i++) {
    sum = 0;
    for (h1 = 1; h1 < 47; h1++) {
      u0 = handRanks[53 + h1];
      for (h2 = h1 + 1; h2 < 48; h2++) {
        u1 = handRanks[u0 + h2];
        for (h3 = h2 + 1; h3 < 49; h3++) {
          u2 = handRanks[u1 + h3];
          for (h4 = h3 + 1; h4 < 50; h4++) {
            u3 = handRanks[u2 + h4];
            for (h5 = h4 + 1; h5 < 51; h5++) {
              u4 = handRanks[u3 + h5];
              for (h6 = h5 + 1; h6 < 52; h6++) {
                u5 = handRanks[u4 + h6];
                for (h7 = h6 + 1; h7 < 53; h7++) {
                  sum += handRanks[u5 + h7];
                  /*
                  handRank = handRanks[u5 + h7];
                  handEnumerations[handRank >>> 12]++;
                  numHands++;
                  equivalencyEnumerations[handRank >>> 12][handRank & 0xFFF]++;
                  */
                }}}}}}}
  }
  print(sum, time/NT, 133784560, 6);
}

  public static void testSteveBrecher() {
    //pokerai.game.eval.stevebrecher.Card c = new pokerai.game.eval.stevebrecher.Card("Th");
    //System.out.println("should be 34: " + c.ordinal());
    //int[] cards = new int[7];
    //for (int i = 0; i < 7; i++) cards[i] = new pokerai.game.eval.stevebrecher.Card("AsAh");
    //pokerai.game.eval.spears.Card[] deck = pokerai.game.eval.spears.Card.values();
    //CardSet cs = new CardSet();
    long time = System.currentTimeMillis();
    long sum = 0;
    //*
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  //pokerai.game.eval.spears.Hand h = new pokerai.game.eval.spears.Hand();
                  long key = 0;
                  key |= (0x1L << (h1));
                  key |= (0x1L << (h2));
                  key |= (0x1L << (h3));
                  key |= (0x1L << (h4));
                  key |= (0x1L << (h5));
                  key |= (0x1L << (h6));
                  key |= (0x1L << (h7));
                  sum += HandEval.hand7Eval(key);
                }}}}}}}
    print(sum, time, 133784560, 5);
  }

  public static void testSteveBrecherE2() {
    //pokerai.game.eval.stevebrecher.Card c = new pokerai.game.eval.stevebrecher.Card("Th");
    //System.out.println("should be 34: " + c.ordinal());
    //int[] cards = new int[7];
    //for (int i = 0; i < 7; i++) cards[i] = new pokerai.game.eval.stevebrecher.Card("AsAh");
    //pokerai.game.eval.spears.Card[] deck = pokerai.game.eval.spears.Card.values();
    //CardSet cs = new CardSet();
    long time = System.currentTimeMillis();
    long sum = 0;

    int ix1, ix2, ix3, ix4, ix5, ix6, ix7;
    long board1, board2, board3, board4, board5, board6;
    //int handTypeSum[9];
    //for (ix1 = 0; ix1 < 9; ix1++) handTypeSum[ix1] = 0;

    long key = 0;
    long key1, key2, key3, key4, key5, key6, key7;
    int h1, h2, h3, h4, h5, h6, h7;

    for (h1 = 0; h1 < 52; h1++) {
      key1 = key | (0x1L << (h1));
      for (h2 = h1 + 1; h2 < 52; h2++) {
        key2 = key1 | (0x1L << (h2));
        for (h3 = h2 + 1; h3 < 52; h3++) {
          key3 = key2 | (0x1L << (h3));
          for (h4 = h3 + 1; h4 < 52; h4++) {
            key4 = key3 | (0x1L << (h4));
            for (h5 = h4 + 1; h5 < 52; h5++) {
              key5 = key4 | (0x1L << (h5));
              for (h6 = h5 + 1; h6 < 52; h6++) {
                key6 = key5 | (0x1L << (h6));
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  key7 = key6 | (0x1L << (h7));
                  sum += HandEval.hand7Eval(key7);
                }}}}}}}


    //*

    print(sum, time, 133784560, 7);
  }

  public static void testPokerstove() {
    int[] hand = new int[7];
    long time = System.currentTimeMillis();
    long sum = 0;
    int h1, h2, h3, h4, h5, h6, h7;
    for (h1 = 0; h1 < 52; h1++) {
      for (h2 = h1 + 1; h2 < 52; h2++) {
        for (h3 = h2 + 1; h3 < 52; h3++) {
          for (h4 = h3 + 1; h4 < 52; h4++) {
            for (h5 = h4 + 1; h5 < 52; h5++) {
              for (h6 = h5 + 1; h6 < 52; h6++) {
                for (h7 = h6 + 1; h7 < 52; h7++) {
                  // need to have all the assignments here, as the array is sorted internally
                  hand[0] = h1;
                  hand[1] = h2;
                  hand[2] = h3;
                  hand[3] = h4;
                  hand[4] = h5;
                  hand[5] = h6;
                  hand[6] = h7;
                  //pokerai.game.eval.jpoker.Hand h = new pokerai.game.eval.jpoker.Hand(hand);
                  //sum += h.evaluate();
                  sum += pokerai.game.eval.jpoker.HandEvaluator.doFullEvaluation(hand);
                }}}}}}}
    print(sum, time, 133784560, 8);
  }


  public static void print(long sum, long time, long n, int ind) {
    time = System.currentTimeMillis() - time; // time given is start time
    //long handsPerSec = Math.round(1000 / ((time*1.0)/ n));
    long handsPerSec = Math.round(n / (time / 1000.0));
    System.out.println(ref[ind]);
    System.out.println(" --- Hands per second: [b]" + handsPerSec + "[/b], hands " + n + ", checksum " + sum + ", total time: " + time);
    System.out.println();
  }

}
