package se.miun.chrfin.foxgame;

import ch.rfin.ai.games.EvalFunction;
import ch.rfin.foxgame.Foxgame;
import ch.rfin.foxgame.rules.State;
import se.miun.chrfin.foxgame.com.GameStatus;
import se.miun.chrfin.foxgame.setup.PlayerSetup;

/**
 * @author Christoffer Fink
 */
public class FoxGameEngine implements AiGameEngine {

	private final PlayerSetup setup;
	private final Foxgame foxGame;
	private final FoxGameMiniMax miniMax;
	
	private State state;
	private EvalFunction<State> foxGameEval;

	public FoxGameEngine(PlayerSetup setup) {
		this.setup = setup;
		foxGame = new Foxgame();
		miniMax = new FoxGameMiniMax();
		state = foxGame.getRoot();
		foxGameEval = new FoxGameEval();
	}

	/**
	 * Return a move of the form "x1,y1 x2,y2".
	 */
	@Override
	public String getMove(GameStatus status) {
		// Check which player you are
		// If you are the FOX, you want to maximize your points
		// If you are the SHEEP, you want to minimize your points
		if (status.playerRole.equals("FOX")) {
			miniMax.setPlayer("MAX");
		}

		if (status.playerRole.equals("SHEEP")) {
			miniMax.setPlayer("MIN");
		}

		/*
		 * Tried with 3 depth at first but seems like it does not handle 300ms and under
		 * very well
		 * So I have 2 as fixed depth
		 */
		String move = null;
		for (int i = 1; i <= 2; i++) {
			move = miniMax.bestMove(foxGame, state, foxGameEval, i);
		}

		return move;
	}

	@Override
	public void updateState(String move) {
		state = foxGame.transition(state, move);
	}

	@Override
	public String getPlayerName() {
		return setup.playerName;
	}

}