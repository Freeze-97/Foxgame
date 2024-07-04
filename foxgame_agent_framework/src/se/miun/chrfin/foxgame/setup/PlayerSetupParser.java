package se.miun.chrfin.foxgame.setup;

/**
 * Represent parsed command line arguments.
 * 
 * @author Christoffer Fink
 */
public class PlayerSetupParser {
	/** Address option (host name or IP). */
	public static final String ADDR_OPTION = "-a";
	/** Port option. */
	public static final String PORT_OPTION = "-p";
	/** Role option. */
	public static final String ROLE_OPTION = "-r";
	/** Name option. */
	public static final String NAME_OPTION = "-n";
	/** Filename option. */
	public static final String FILE_OPTION = "-f";

	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 6000;
	public static final int MAX_PORT = 65535;

	public PlayerSetup parse(String[] args) {
		String address = DEFAULT_HOST;
		int gamePort = DEFAULT_PORT;
		String playerRole = null;
		String playerName = null;
		String playerFile = null;

		for (int index = 0; index < args.length; index++) {
			String arg = args[index];
			checkExistenceOfArgumentAfterCommand(args, index);
			switch (arg) {
			case (ADDR_OPTION):
				address = args[++index];
				break;
			case (PORT_OPTION):
				gamePort = parsePort(args[++index]);
				break;
			case (ROLE_OPTION):
				playerRole = parseRole(args[++index]);
				break;
			case (NAME_OPTION):
				playerName = args[++index];
				break;
			case (FILE_OPTION):
				playerFile = args[++index];
				break;
			default:
				break;
			}
		}
		return new PlayerSetup(address, gamePort, playerRole, playerName, playerFile);
	}

	private void checkExistenceOfArgumentAfterCommand(String[] args, int index) {
		if (index + 1 >= args.length)
			throw new IllegalArgumentException("Argument missing after " + args[index]);
	}

	private int parsePort(String portArg) {
		int port = -1;
		try {
			port = Integer.parseInt(portArg);
		} catch (NumberFormatException e) {
			illegalPort(portArg);
		}
		if (port < 0 || port > MAX_PORT) {
			illegalPort(portArg);
		}
		return port;
	}

	private String parseRole(String roleArg) {
		String role = roleArg.toUpperCase();

		if (roleIsFox(role))
			return "FOX";
		else if (roleIsSheep(role))
			return "SHEEP";

		throw new IllegalArgumentException("Illegal player role entered after " + ROLE_OPTION + ": '" + roleArg
				+ "'. Valid roles are FOX and SHEEP.");
	}

	private boolean roleIsSheep(String role) {
		return "SHEEP".startsWith(role);
	}

	private boolean roleIsFox(String role) {
		return "FOX".startsWith(role);
	}

	private void illegalPort(String portArg) {
		throw new IllegalArgumentException("Illegal game port argument: " + portArg);
	}
}