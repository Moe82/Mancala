import java.util.Arrays;

/**
*
* this is the player class. There are two instance veriables (mancala
* and pits). Mancala holds the amount of stones in each mancana 
* 
* @author Mohamed Shafee
* @version 1.1 3/09/2019
*
*/

public class Player{
	
	//this value represents the number of 'stones' in the mancala of a player
	private int mancala;
	
	// an array will hold the number of 'stones' in each player's 6 pits
	public int[] pits = new int[MancalaBoard.BOARDSIZE];
	
	
	public int getMancala() {
		return mancala;
	}
	
	public void setMancala(int num) {
		if (num >= 0 & num <=48) {
			mancala = num;
		}
	}
	
	int playMove(int pit, boolean playerTurn) {

    int stones = pits[pit-1];  // number of stones to drop 
    pits[pit-1] = 0;           // clear starting pit
    int last = 0;              // number of stones in last pit played (0 means play again)


    /* in the unlikely (impossible?) case that the pit contains enough stones
     * to go around more than once, distribute the "full laps" first */

    int numPits = 2*MancalaBoard.BOARDSIZE + 2;      // other layers' pits and mancalas
    if (stones > numPits) {
        MancalaBoard.incrementAll(stones / numPits);
        stones = stones % numPits;      			// what's left 
    }

    /* play the remaining stones:
     * in the maximum case, there will be enough stones remaining to deposit on player 1's side,
     * player 1's mancala, player 2's side, player 2's mancala, and then back to player 1 */

    /* play stones on current players side */
    
    while (stones > 0 & pit < MancalaBoard.BOARDSIZE) {
        last = pits[pit];
        pits[pit]++;
        pit++;
        stones--;
    }

    /* play stone on current players mancala, if any */

    if (stones != 0) {
        last = mancala;
        mancala++;
        stones--;
    }

    /* play stone on opposites player mancala */
    
    last = MancalaBoard.playStonesOnOtherSide(stones, last, playerTurn);
    stones = 0; // OK, but fix later. value of stones should come from the method.

    
    /* when out of stones, check to see status of last pit played */

    if (stones == 0) {
        if (last == 0 &! Arrays.equals(pits, MancalaBoard.GAMEOVER)) {
            System.out.println("\nTake another turn!\n"); 
            return 0;   // take another turn
        }    
    	else 
    		return pit; 
    	
    } else {
        System.out.println("I didn't expect to have stones left. Programmer fail.");
        return -1;
    }
}
}
