// Java Document
public class gradeStudent {
	
	public static void main(String[] args) {
		int tmp = 1000;
		if (args.length > 1) {
		try
			{
			  // the String to int conversion happens here
			  tmp  = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException nfe)
			{
			  //debug = true;
			  
			}
		}
		final int numGames = tmp;//DEFAULT IS 1000 GAMES!
		final int pcnt10 = numGames / 10;
		
		if (args.length < 1) {
			System.out.println("Usage:  java gradeStudent [studentClassName]");
		}
		else {
			final String STUDENT_PLAYER_NAME = args[0].toString();
			gradePoker gp;
			gradePoker gp2;
			gradePoker gp3;
		
			double aiWin = 0;
			double playerWinGradeAI = 0;
			double playerWinDumbBetter = 0;
			double playerWinDumbCaller = 0;
			for (int i = 0; i < numGames; i++) {
				if (i % pcnt10 == 0) {
					System.out.print(i / 10 + "%");
				}
				else if (i % 10 == 0) {
					System.out.print(".");
				}
				gp = new gradePoker(STUDENT_PLAYER_NAME);
				int val = gp.play();
				if (val == -1) {
					System.out.println("PLAY EXITED WITH ERROR!!!");
				}
				else if (val == 0) {
					aiWin++;
					//System.out.println("AI Wins!");
				}
				else {
					playerWinGradeAI++;
					//System.out.println("Student Wins!");
				}
			}
			aiWin = 0;
			for (int i = 0; i < numGames; i++) {
				if (i % pcnt10 == 0) {
					System.out.print(i / 10 + "%");
				}
				else if (i % 10 == 0) {
					System.out.print(".");
				}
				gp2 = new gradePoker("DumbBetter", STUDENT_PLAYER_NAME);
				int val = gp2.play();
				if (val == -1) {
					System.out.println("PLAY EXITED WITH ERROR!!!");
				}
				else if (val == 0) {
					aiWin++;
					//System.out.println("AI Wins!");
				}
				else {
					playerWinDumbBetter++;
					//System.out.println("Student Wins!");
				}
			}
			aiWin = 0;
			for (int i = 0; i < numGames; i++) {
				if (i % pcnt10 == 0) {
					System.out.print(i / 10 + "%");
				}
				else if (i % 10 == 0) {
					System.out.print(".");
				}
				gp3 = new gradePoker("DumbCaller", STUDENT_PLAYER_NAME);
				int val = gp3.play();
				if (val == -1) {
					System.out.println("PLAY EXITED WITH ERROR!!!");
				}
				else if (val == 0) {
					aiWin++;
					//System.out.println("AI Wins!");
				}
				else {
					playerWinDumbCaller++;
					//System.out.println("Student Wins!");
				}
			}
			
			
			
			
			
			//System.out.println("AI won " + aiWin + "times.");
			double playerWinPcnt;
			playerWinPcnt = playerWinGradeAI / numGames;
			System.out.println("Student won versus gradeAI " + playerWinGradeAI + " times (" + (playerWinPcnt * 100) + "%).");
			
			playerWinPcnt = playerWinDumbBetter / numGames;
			System.out.println("Student won versus DumbBetter " + playerWinDumbBetter + " times (" + (playerWinPcnt * 100) + "%).");
			
			playerWinPcnt = playerWinDumbCaller / numGames;
			System.out.println("Student won versus DumbCaller " + playerWinDumbCaller + " times (" + (playerWinPcnt * 100) + "%).");
		}
		
	}
	
	
	
	
}