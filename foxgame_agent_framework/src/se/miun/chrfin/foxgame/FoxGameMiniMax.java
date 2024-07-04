package se.miun.chrfin.foxgame;

import ch.rfin.ai.games.EvalFunction;
import ch.rfin.foxgame.Foxgame;
import ch.rfin.foxgame.rules.State;

/*
 * MiniMax for the Fox game
 * This algorithm will use cut off/pruning to save time
 * Depending on if you are the fox or sheep player
 * you are going for high or low score
 * 
 * - Fox wants maximum score
 * - Sheep wants minimum score
 */
public class FoxGameMiniMax {

	private String player = null; // MAX or MIN player?
	private String action = null; // Which action to take?
	private int depthLimit = 0; // Max depth of the MiniMax tree
	private final int POINTS = 100; // 100 points returned

	// Empty constructor
	public FoxGameMiniMax() {}

	public void setPlayer(String player) {
		this.player = player;
	}

	// This will return the next move to make
	public String bestMove(Foxgame foxGame, State state, EvalFunction<State> eval, int depthLimit) {
		// Set max depth
		this.depthLimit = depthLimit;

		// Current depth we are working on
		int currDepth = 0;

		if (player == "MAX") {
			maxValue(foxGame, eval, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, currDepth, state, true);
		}

		if (player == "MIN") {
			minValue(foxGame, eval, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, currDepth, state, true);
		}

		return action;
	}

	private double maxValue(Foxgame foxGame, EvalFunction<State> eval, double alpha, double beta, int depth,
			State state, boolean firstDepth) {
		if (foxGame.terminal(state)) {
			return foxGame.utilityOf(state) * POINTS;
		}

		// If we are on the last depth
		if (depth == depthLimit) {
			return eval.utilityOf(state); // Eval function
		}

		// Check possible max values
		double maxValue = Double.NEGATIVE_INFINITY;
		for (String a : foxGame.possibleActionsIn(state)) {
			maxValue = Math.max(maxValue,
					minValue(foxGame, eval, alpha, beta, depth + 1, foxGame.transition(state, a), false));

			if (maxValue >= beta) { 
				return maxValue;
			}

			if (firstDepth)
				if (maxValue > alpha) {
					action = a;
				}
			alpha = Math.max(alpha, maxValue);
		}

		return maxValue;
	}

	private double minValue(Foxgame foxGame, EvalFunction<State> eval, double alpha, double beta, int depth,
			State state, boolean firstDepth) {
		if (foxGame.terminal(state)) {
			return foxGame.utilityOf(state) * POINTS;
		}

		// If we are on the last depth
		if (depth == depthLimit) {
			return eval.utilityOf(state); // Eval function
		}

		// Check possible minimum values
		double minValue = Double.POSITIVE_INFINITY;
		for (String a : foxGame.possibleActionsIn(state)) {
			minValue = Math.min(minValue,
					maxValue(foxGame, eval, alpha, beta, depth + 1, foxGame.transition(state, a), false));

			if (minValue <= alpha) {
				return minValue;
			}

			if (firstDepth)
				if (minValue < beta) {
					action = a;
				}
			beta = Math.min(beta, minValue);
		}

		return minValue;
	}
}