import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * Final game logic for Mancala. Thos versoin of mancala is similar to most, 
 * exept that a player gets another round only when his last stone lands on any 
 * empty pit. That includes the mancala, but only when its empty. Landing 
 * on a non-empty mancala will not reward another round. Also, landing on an empty
 * pit does not reward the you the stones on the opposite (your opponents side).  
 * 
 * @author Mohamed Shafee
 * @version 1.1 3/09/2019
 *
 */

public class MancalaBoard {

    // if one player's pits are empty, the game is over
    public static final int GAMEOVER[] = {0,0,0,0,0,0};
    
    // number of pits for each player
 	public static final int BOARDSIZE = 6;

    // these are the reference variables for the two players
    // we will treat player1 as "south" with right mancala, player2 as "north" with left mancala
    static Player player1 = new Player();
    static Player player2 = new Player();
    
    // a boolean variable to control who's turn it is.
    // if set equal to true, player2 is next. Otherwise, player1 is next. 
    static boolean playerTurn = true;
    
    
    
    /**
    *
    * Sets all board values to beginning-of-game values.
    *
    */
    
    void initialize() {
        player1.setMancala(0); 
        player2.setMancala(0);
        Arrays.fill(player1.pits, 4);
        Arrays.fill(player2.pits, 4);
    }
    
    /**
     *
     * Coordinates the play of one game.
     *
     */

    void play() {
        Scanner kbd = new Scanner(System.in);
        int pit;
        System.out.println("Here's the board; \nPlayer1, your pits are on the bottom and your mancala on the right. \nPlayer2, your pits are at the top and your mancala on the left.\n");
        do {        // this loops checks for a winner after each round
        	do {    /** this loop handles "extra" turns for landing in an empty pit 
            		 * The "while" can execute a turn for a player depending on the 
            	     * value of the boolean switch (playerTurn). If a player's turn ends,
            		 * the player switch method is executed. 
            		 */
                display();
                do { // this loop makes sure the selected pit is valid for play
                	System.out.print((playerTurn? "Player1, ": "Player2, ")+"Which pit will you play from? ");
                    pit = kbd.nextInt();
                } while (playerTurn? !valid(pit, playerTurn) : !valid(player2InputConverter(pit), playerTurn));
            } while ((playerTurn? player1.playMove(pit, playerTurn): player2.playMove(player2InputConverter(pit), playerTurn)) == 0);
        	
        	if (getWinner() < 0)
        		System.out.println("\nNext Player's Turn!\n");
        		switchPlayer(playerTurn);
       
        } while (getWinner() < 0);
        
        sweep();     // Sweeps up the remaining stones and rewards it to the respective player.
        display();   // displays the final board. 
        
        if (player1.getMancala() > player2.getMancala())
        	System.out.println("\nPlayer1 Wins!\n");
        else 
        	System.out.println("\nPlayer2 Wins!\n");
    }
    
    /**
    *
    * negates the value of playerTurn in order to switch players
    *
    */
    
    public static void switchPlayer(boolean currentTurn) {
    	playerTurn = !currentTurn;
    }
    
    /**
    *
    * plays the remaining stones on the opposite players side.
    * @param stones remaining number of stones
    * @param last last pit to receive a stone  
    * @return last returns the last pit 
    */
    
    public static int playStonesOnOtherSide(int stones, int last, boolean playerTurn) {
    	int pit = 0;
	 	while (stones > 0 & pit < BOARDSIZE) {
	        last = playerTurn? player2.pits[pit] : player1.pits[pit];
	         if (playerTurn) 
	        	 player2.pits[pit]++;
	         	else player1.pits[pit]++;
	        pit++;
	        stones--;
	    }

	    if (stones != 0) {
	        last = playerTurn? player2.getMancala() : player1.getMancala();
	        if (playerTurn)
	        	player2.setMancala(player2.getMancala()+1);
	        	else 
	        		player1.setMancala(player1.getMancala()+1);
	        
	        stones--;
	    }

	    /* possibly, there are enough stones to get back around to the same player */

	    pit = 0;
	    while (stones > 0 & pit < MancalaBoard.BOARDSIZE) {
	        last = playerTurn? player1.pits[pit] : player2.pits[pit];
	        if (playerTurn)
	        	player1.pits[pit]++;
	        else 
	        	player2.pits[pit]++;
	        pit++;
	        stones--;
	    }
	    return last;
    }	
    
    /**
    *
    * Converts the keyboard input for player2 so that both players can use the same controls
    *
    */
   
    public static int player2InputConverter(int pit) {
    	switch (pit) {
    		case 1: return 6;
    		case 2: return 5;
    		case 3: return 4;
    		case 4: return 3;
    		case 5: return 2;
    		case 6: return 1;
    	}
    	return -1;
    }
    
    
    /**
     *
     * Verifies validity of selected pit--checking both that it's in range and that
     * the selected pit has stones to play.
     *
     * @param pit The number of the selected pit
     * @parm playerTurn true if its player1's turn, false if its player2's turn
     * @return true if selected pit is viable to play from
     *
     */
    
    boolean valid(int pit, boolean playerTurn) {
        if (pit < 1 || pit > BOARDSIZE) {
                System.out.println("That is not a valid pit number. Try again.");
                return false;
        } 
        else if (playerTurn? (player1.pits[pit-1] == 0) : (player2.pits[pit-1] == 0)) {
        	System.out.println("You can't play from an empty pit. Try again.");
            return false;
        }
        	return true;
        }   

    /**
     *
     * Displays the current state of the game.
     *
     */

    void display() {
        System.out.println("   1   2   3   4   5   6   ");
        System.out.println("--------------------------");
        displayPitsNorth(player2.pits);

        System.out.println();
        System.out.format("%4d", player2.getMancala());
        System.out.print("                ");
        System.out.format("%4d", player1.getMancala());
        System.out.println();
        System.out.println();
        displayPitsSouth(player1.pits);
        System.out.println("--------------------------");
        System.out.println();
    }

    /**
     *
     * Displays the number of stones in the pits of the "south" player.
     *
     */

    void displayPitsSouth(int[] pits) {
        for (int i=0; i<pits.length; i++) {
            System.out.format("%4d", pits[i]);
        }
        System.out.println();
    }

    /**
     *
     * Displays the number of stones in the pits of the "north" player.
     *
     */

    void displayPitsNorth(int[] pits) {
        for (int i=BOARDSIZE-1; i>=0; i--) {
            System.out.format("%4d", pits[i]);
        }
        System.out.println();
    }

    
    /**
     *
     * Add n stones to each pit and mancala.
     *
     * @param n The number of stones to add.
     *
     **/
    
    static void incrementAll(int n) {
        for (int i=0; i<BOARDSIZE; i++) {
            player1.pits[i] += n;
            player2.pits[i] += n;
        }
        player1.setMancala(player1.getMancala() +n);
        player2.setMancala(player2.getMancala() +n);
    }

    /**
     *
     * if game is over, return 1 or 2 to indicate winning player or 0 if tie; if not over return -1.
     * @return 1 or 2 to indicate winning player, 0 to indicate tie, or -1 if game not over.
     *
     **/

    int getWinner() {
    	if ((Arrays.equals(player1.pits, GAMEOVER) || (Arrays.equals(player2.pits,GAMEOVER)))) {
    		sweep();
            if (player1.getMancala() > player2.getMancala())
                return 1;
            else if (player1.getMancala() > player2.getMancala())
                return 2;
            else
                return 0;
       } 
          return -1;
    } 

    /**
     *
     * At endgame, player with remaining stones 'captures' them into her mancala.
     *
     **/
    
    void sweep() {
    	 for (int i=0; i<BOARDSIZE; i++) {
             player1.setMancala(player1.getMancala() + player1.pits[i]);
             player2.setMancala(player2.getMancala() + player2.pits[i]);
             player1.pits[i] = 0;
             player2.pits[i] = 0;
        }
    }
}



