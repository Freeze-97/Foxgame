package se.miun.chrfin.foxgame.com;

/**
 * Represents communication with the server. The status of the communication is
 * returned in response to a complete transmission. It includes any move that
 * was received, the current time slice, any winner, and the role that was
 * assigned during setup.
 * 
 * @author Christoffer Fink
 */
public class GameStatus {

	public final String move;
	public final String playerRole;
	public final String winner;
	public final long timeSlice;

	public GameStatus(String move, String role, long timeSlice) {
		this(move, role, timeSlice, null);
	}

	public GameStatus(String move, String role, long timeSlice, String winner) {
		this.move = move;
		this.playerRole = role;
		this.timeSlice = timeSlice;
		this.winner = winner;
	}

	public boolean isGameOver() {
		return winner != null;
	}
}
