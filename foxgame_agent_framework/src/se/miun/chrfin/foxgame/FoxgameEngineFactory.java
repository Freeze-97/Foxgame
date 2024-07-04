package se.miun.chrfin.foxgame;

import se.miun.chrfin.foxgame.exceptions.FoxgameSetupException;
import se.miun.chrfin.foxgame.setup.PlayerSetup;

@Deprecated
public class FoxgameEngineFactory {

	public static AiGameEngine getInstance(PlayerSetup setup) throws FoxgameSetupException {
		return new FoxGameEngine(setup);
	}
}