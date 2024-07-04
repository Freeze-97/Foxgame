package se.miun.chrfin.foxgame.exceptions;

import java.io.IOException;

import se.miun.chrfin.foxgame.setup.PlayerSetup;

public class FoxgameNoGameToParticipateException extends FoxgameSetupException {

	private static final long serialVersionUID = 1L;

	private final PlayerSetup setup;

	private final IOException ioException;

	public FoxgameNoGameToParticipateException(PlayerSetup setup) {
		this.setup = setup;
		ioException = null;
	}

	public FoxgameNoGameToParticipateException(IOException e) {
		ioException = e;
		setup = null;
	}

	@Override
	public String getMessage() {
		String message;
		if (setup != null)
			message = "Have you made sure that the server is up running on " + setup.serverIpAddress + " port "
					+ setup.gamePort + " and that a new game has been started?";
		else
			message = ioException.getMessage();
		return message;
	}
}