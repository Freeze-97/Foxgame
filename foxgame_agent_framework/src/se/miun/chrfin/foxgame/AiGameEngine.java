package se.miun.chrfin.foxgame;

import se.miun.chrfin.foxgame.com.GameStatus;

/**
 * Basic, general-purpose AI interface. The getName method is a bit of extra
 * glitter so that an AI can have a hard-coded name that doesn't need to be set
 * using an argument.
 * 
 * @author Christoffer Fink
 */
public interface AiGameEngine {

	String getMove(GameStatus status);

	void updateState(String move);

	String getPlayerName();
}
