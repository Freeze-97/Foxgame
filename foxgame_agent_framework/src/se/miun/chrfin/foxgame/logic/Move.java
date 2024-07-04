package se.miun.chrfin.foxgame.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Christoffer Fink
 */
public class Move {

	private final List<Position> positions;

	private Move(List<Position> positions) {
		this.positions = Collections.unmodifiableList(positions);
	}

	public static Move move(Position from, Position to1, Position... ton) {
		List<Position> tmp = new ArrayList<>(ton.length + 2);
		tmp.add(from);
		tmp.add(to1);
		for (Position p : ton) {
			tmp.add(p);
		}
		return new Move(tmp);
	}

	public static Move move(List<Position> positions) {
		return new Move(positions);
	}

	/**
	 * Extend with one more hop. Returns a new move with the additional position.
	 * Useful for building longer chains of jumps.
	 */
	public Move extended(Position position) {
		List<Position> extended = new ArrayList<>(positions);
		extended.add(position);
		return new Move(extended);
	}

	public List<Position> positions() {
		return positions;
	}

	@Override
	public String toString() {
		return positions.stream().map(Position::toString).reduce((s, t) -> s + " " + t).get();
	}
}
