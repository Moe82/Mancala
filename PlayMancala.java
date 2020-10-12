import java.util.Scanner;

/**
 *
 * Main class for the Mancala game. The MancalaBoard class contains
 * all the game logic. The player class defines the state and behavior
 * of each player.
 * 
 * @author Mohamed Shafee
 * game logic by Scott Dexter
 * @version 1.1 3/9/2019
 *
 */

public class PlayMancala{

	/**
	 *
	 * Main method to start the game. Inputing 'Y' a game resets the board
	 * via the initialize() method.
	 *   
	 *
	 * @param args unused
	 *
	 */
	
	public static void main(String[] args) {
		
		MancalaBoard board = new MancalaBoard();
	    Scanner kbd = new Scanner(System.in);
	    
	    String input;
	    
	    System.out.println("Welcome to Mancala!");
	    
	    do {
	    	board.initialize();
	        board.play();
	        
	        System.out.print("Play again (Y for yes)? ");
	        
	        input = kbd.nextLine().toUpperCase();
	       
	    	} while (input.charAt(0) == 'Y');
	    	System.out.println("Thanks for playing.");
	}
}
