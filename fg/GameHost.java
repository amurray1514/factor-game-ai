package fg;

import java.util.List;

/**
 * Class that hosts the Factor Game between two players with varying behaviors.
 *
 * @author Archer Murray
 */
public class GameHost
{
	private final FactorGame game;
	private final Player player1, player2;
	
	/**
	 * Creates a new game with the given players.
	 *
	 * @param g The factor game for the players to play.
	 * @param p1 The player who will go first in the game.
	 * @param p2 The player who will go second in the game.
	 */
	public GameHost(FactorGame g, Player p1, Player p2)
	{
		this.game = g;
		this.player1 = p1;
		this.player2 = p2;
	}
	
	/**
	 * Returns a string containing all elements of the passed-in list in a
	 * grammatically correct manner.
	 *
	 * @param list The list to stringify.
	 * @return The list, stringified as described above.
	 */
	public static String stringifyList(List<?> list)
	{
		if (list.isEmpty()) {
			return "";
		}
		if (list.size() == 1) {
			return list.get(0).toString();
		}
		if (list.size() == 2) {
			return list.get(0).toString() + " and " + list.get(1).toString();
		}
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			ret.append(list.get(i));
			if (i < list.size() - 1) {
				ret.append(", ");
				if (i == list.size() - 2) {
					ret.append("and ");
				}
			}
		}
		return ret.toString();
	}
	
	/**
	 * Plays the game and returns the final result.
	 *
	 * @param printResults If {@code true}, prints all events that occur in the
	 * game to the console; if {@code false}, prints nothing to the console.
	 * @return The result of the game.
	 */
	public int play(boolean printResults)
	{
		if (printResults) {
			System.out.println(this.game);
		}
		while (!this.game.isGameOver()) {
			int move = (this.game.isPlayer1Turn() ? this.player1
					: this.player2).selectMove(new FactorGame(this.game));
			if (printResults) {
				int playerNum = this.game.isPlayer1Turn() ? 1 : 2;
				System.out.print("Player " + playerNum + ' ');
				if (move == 0) {
					System.out.println("loses their turn due to a penalty.");
				} else {
					System.out.print("circles square " + move + ", causing ");
					List<Integer> factors = this.game.getOpenFactors(move);
					if (factors.isEmpty()) {
						System.out.println("them to receive a penalty.");
					} else {
						System.out.println("player " + (3 - playerNum) +
								" to circle square" +
								(factors.size() == 1 ? ' ' : "s ") +
								stringifyList(factors) + '.');
					}
				}
			}
			this.game.makeMove(move);
			if (printResults) {
				System.out.println(this.game);
			}
		}
		return this.game.getResult();
	}
}