package se.miun.chrfin.foxgame.setup;

public class PlayerSetup {

	public final String serverIpAddress;
	public final int gamePort;
	public final String playerRole;
	public final String playerName;
	public final String fileName;

	public PlayerSetup(String ipAddress, int gamePort, String playerRole, String playerName, String fileName) {
		this.serverIpAddress = ipAddress;
		this.playerRole = playerRole;
		this.gamePort = gamePort;
		this.playerName = playerName;
		this.fileName = fileName;
	}
}