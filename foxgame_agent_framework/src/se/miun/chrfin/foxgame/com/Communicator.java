package se.miun.chrfin.foxgame.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import se.miun.chrfin.foxgame.exceptions.FoxgameNoGameToParticipateException;
import se.miun.chrfin.foxgame.setup.PlayerSetup;

/**
 * Handles communication with the server.
 * 
 * @version 1.3
 * @author Christoffer Fink
 */
public class Communicator {
	private static final String HELLO_CMD = "hello";
	private static final String NAME_CMD = "name";
	private static final String READY_CMD = "ready";
	private static final String TIME_CMD = "timeslice";
	private static final String BEGIN_CMD = "begin";
	private static final String MOVE_CMD = "move";
	private static final String DONE_CMD = "done";
	private static final String WINNER_CMD = "winner";

	private final PlayerSetup playerSetup;
	private final BufferedReader gameFlowReader;
	private final OutputStream gameSink;

	private String role;
	private String winner;
	private long timeslice = -1;
	private String move;
	private boolean receiving = true;

	public Communicator(PlayerSetup setup) throws FoxgameNoGameToParticipateException {
		this.playerSetup = setup;
		Socket socket = getSocket(setup);
		gameFlowReader = initGameFlow(socket);
		gameSink = initGameSink(socket);
	}

	private OutputStream initGameSink(Socket socket) throws FoxgameNoGameToParticipateException {
		try {
			return socket.getOutputStream();
		} catch (IOException e) {
			throw new FoxgameNoGameToParticipateException(e);
		}
	}

	private BufferedReader initGameFlow(Socket socket) throws FoxgameNoGameToParticipateException {
		try {
			return new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			throw new FoxgameNoGameToParticipateException(e);
		}
	}

	/**
	 * only for test purposes. Use the other constructor instead.
	 */
	public Communicator(PlayerSetup setup, InputStream in, OutputStream out) {
		this.playerSetup = setup;
		this.gameSink = out;
		this.gameFlowReader = new BufferedReader(new InputStreamReader(in));
	}

	private static Socket getSocket(PlayerSetup playerSetup) throws FoxgameNoGameToParticipateException {
		try {
			InetAddress address = InetAddress.getByName(playerSetup.serverIpAddress);
			return new Socket(address, playerSetup.gamePort);
		} catch (IOException e) {
			throw new FoxgameNoGameToParticipateException(playerSetup);
		}
	}

	public GameStatus setup() {
		return setup(null);
	}

	public GameStatus setup(String name) {
		sendSetupMessages(name);
		return waitForTransmission();
	}

	public GameStatus waitForTransmission() {
		// Reset state.
		receiving = true;
		move = null;
		while (receiving) {
			processMessage(getLine());
		}
		return constructComStatus();
	}

	private void sendSetupMessages(String name) {
		String helloMsg = getHelloMsg();
		String nameMsg = getNameMsg(name);
		String readyMsg = getReadyMsg();
		send(helloMsg, nameMsg, readyMsg);
	}

	public void sendMove(String move) {
		String moveMsg = getMoveMsg(move);
		String doneMsg = getDoneMsg();
		send(moveMsg, doneMsg);
	}

	private String getMoveMsg(String move) {
		return String.format("%s %s\n", MOVE_CMD, move);
	}

	private String getDoneMsg() {
		return DONE_CMD + "\n";
	}

	private String getHelloMsg() {
		String role = playerSetup.playerRole;
		return HELLO_CMD + (role != null ? " " + role : "") + "\n";
	}

	private String getNameMsg(String name) {
		String argsName = playerSetup.playerName;
		name = argsName == null ? name : argsName;
		return name != null ? String.format("%s %s\n", NAME_CMD, name) : "";
	}

	private String getReadyMsg() {
		return READY_CMD + "\n";
	}

	private String getLine() {
		String line = null;
		try {
			line = gameFlowReader.readLine();
		} catch (IOException e) {
			handleInputException(e);
		}
		if (line == null) {
			handleInputException(new IOException("EOF"));
		}
		return line;
	}

	private void processMessage(String msg) {
		String cmd = getCommand(msg);
		switch (cmd) {
		case (HELLO_CMD):
			processHello(msg);
			break;
		case (TIME_CMD):
			processTimeslice(msg);
			break;
		case (BEGIN_CMD):
			processBegin(msg);
			break;
		case (MOVE_CMD):
			processMove(msg);
			break;
		case (DONE_CMD):
			processDone(msg);
			break;
		case (WINNER_CMD):
			processWinner(msg);
			break;
		}
	}

	private static String getCommand(String s) {
		return s.split(" ")[0];
	}

	private void processHello(String msg) {
		role = msg.split(" ")[1];
	}

	private void processTimeslice(String msg) {
		timeslice = Long.parseLong(msg.split(" ")[1]);
	}

	private void processBegin(String msg) {
		stopReceiving();
	}

	private void processMove(String msg) {
		move = appendMove(msg);
	}

	private void processDone(String msg) {
		stopReceiving();
	}

	private void processWinner(String msg) {
		winner = msg.split(" ")[1];
		stopReceiving();
	}

	private void stopReceiving() {
		receiving = false;
	}

	private GameStatus constructComStatus() {
		return new GameStatus(move, role, timeslice, winner);
	}

	private String appendMove(String msg) {
		String parsed = parseMove(msg);
		if (move == null) {
			return parsed;
		}
		return move + " " + stripFirstPosition(parsed);
	}

	// Get "move x,y x,y" return "x,y x,y"
	private String parseMove(String msg) {
		msg = msg.trim();
		return msg.substring(msg.indexOf(" ") + 1);
	}

	// Get "x,y x,y" return "x,y"
	private String stripFirstPosition(String move) {
		return move.substring(move.indexOf(" ") + 1);
	}

	private void send(String... msgs) {
		try {
			for (String s : msgs) {
				gameSink.write(s.getBytes());
			}
			gameSink.flush();
		} catch (IOException e) {
			handleOutputException(e);
		}
	}

	private void handleInputException(Exception e) {
		dieOnException(e);
	}

	private void handleOutputException(Exception e) {
		dieOnException(e);
	}

	private void dieOnException(Exception e) {
		System.err.println("Communication ceased unexpectedly.");
		System.exit(1);
	}
}
