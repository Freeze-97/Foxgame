package se.miun.chrfin.foxgame;

import ch.rfin.ai.games.EvalFunction;
import ch.rfin.foxgame.Pos.Impl;
import ch.rfin.foxgame.rules.State;

/*
 * Since we can't go all the way down in the 
 * game-tree, we have to use an 
 * evaluation function
 * Fox players want to maximize their points 
 * while sheep players want to minimize their points
 */

public class FoxGameEval implements EvalFunction<State> {

	@Override
	public double utilityOf(State state) {
		// Variables to calculate different points
		// will be added to a total sum and returned

		// Number of foxes and points
		int numFoxes = 0; // Number of foxes
		numFoxes = state.getFoxes().size() * 10; // Times 10 since there are 10 times more sheep
													// Meaning 1 fox = 10 sheep

		// Number of sheep and points
		int numSheep = 0; // Number of sheep
		numSheep = state.getSheep().size() * -1; // Minus since sheep tries to get low scores

		// Get number of sheep in the pen
		int numSheepPen = 0;
		numSheepPen = sheepInsidePen(state);

		// Total points to return
		int total = numFoxes + numSheep + numSheepPen;

		return total;
	}

	/*
	 * Check how many sheep are inside the pen 
	 * The pen is the top 9 positions of the table 
	 * Lower score is good for sheep 
	 * Higher score is good for fox
	 */
	private int sheepInsidePen(State state) {
		int numOfSheepInPen = 0;

		for (int x = 3; x <= 5; x++) { // x in pen area (3, 4 and 5)
			for (int y = 1; y <= 3; y++) { // y in pen area (1, 2 and 3)
				// Check if state/position has a sheep
				if (state.hasSheep(Impl.pos(x, y))) {
					numOfSheepInPen -= 2; // minus points is good for sheep player
				}
			}
		}
		return numOfSheepInPen;
	}
}