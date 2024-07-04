package se.miun.chrfin.foxgame;

import java.io.IOException;

import se.miun.chrfin.foxgame.com.Communicator;
import se.miun.chrfin.foxgame.exceptions.FoxgameSetupException;
import se.miun.chrfin.foxgame.logic.PlayerAgent;
import se.miun.chrfin.foxgame.setup.PlayerSetup;
import se.miun.chrfin.foxgame.setup.PlayerSetupParser;

/**
 * Starts a foxgame client by instantiating the player properties and connecting
 * to a server.
 * 
 * @author Christoffer Fink
 * @author feldob
 */
public class Main {
	public static void main(String[] args) throws FoxgameSetupException, IOException {
		PlayerSetup setup = new PlayerSetupParser().parse(args);
		Communicator com = new Communicator(setup);
		AiGameEngine ai = new FoxGameEngine(setup);
		PlayerAgent agent = new PlayerAgent(com, ai);
		agent.connectAndPlay();
	}
}