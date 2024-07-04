package se.miun.chrfin.foxgame.logic;

/**
 * @author Christoffer Fink
 */
public class Position {

	public final int x;
	public final int y;

	private Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position pos(int x, int y) {
		return new Position(x, y);
	}

	public static Position valueOf(String s) {
		String[] coord = s.split(",");
		return pos(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}
