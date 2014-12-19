// Java Document
public class oneOnOne {
	
	public static void main(String[] args) {
		
		final int numGames = 10;  //DEFAULT IS 1000 GAMES!
		final int pcnt10 = numGames / 10;
		
		if (args.length < 2) {
			System.out.println("Usage:  java oneOnOne <player1 class name> <player2 class name>");
		}
		else {
			gradePoker gp2;
			
			double aiWin = 0;
			for (int i = 0; i < numGames; i++) {
				gp2 = new gradePoker(args[0].toString(), args[1].toString());
				int val = gp2.play();
				System.out.println(args[val].toString() + " wins!");
			}
			
		}
		
	}
	
	
	
	
}