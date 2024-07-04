package se.miun.chrfin.foxgame.logic;

import se.miun.chrfin.foxgame.AiGameEngine;
import se.miun.chrfin.foxgame.com.Communicator;
import se.miun.chrfin.foxgame.com.GameStatus;

/**
 * Plays the game using the given Com and AI instances.
 * 
 * @author Christoffer Fink
 */
public class PlayerAgent {
	private final Communicator com;
	private final AiGameEngine ai;

	public PlayerAgent(Communicator com, AiGameEngine ai) {
		this.com = com;
		this.ai = ai;
	}

	public void connectAndPlay() {
		GameStatus status = com.setup(ai.getPlayerName());
		makeFirstMoveIfFox(status);
		playUntilGameOver(status);
	}

	private void makeFirstMoveIfFox(GameStatus status) {
		if (status.playerRole.equals("FOX")) {
			generateAndSendMove(status);
		}
	}

	private void playUntilGameOver(GameStatus status) {
		while (!status.isGameOver()) {
			status = com.waitForTransmission();
			playRound(status);
		}
	}

	private void playRound(GameStatus status) {
		if (status.isGameOver())
			return;

		ai.updateState(status.move);
		generateAndSendMove(status);
	}

	private void generateAndSendMove(GameStatus status) {
		String move = ai.getMove(status);
		com.sendMove(move);
		ai.updateState(move);
	}
}
