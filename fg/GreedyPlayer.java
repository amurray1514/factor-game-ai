package fg;

import java.util.List;

/**
 * Class representing a Factor Game player that always maximizes its point gain
 * each turn.
 *
 * @author Archer Murray
 */
public class GreedyPlayer implements Player
{
	@Override
	public int selectMove(FactorGame game)
	{
		int maxResult = Integer.MIN_VALUE;
		int maxMove = -1;
		List<Integer> allMoves = game.getAllLegalMoves();
		for (int move: allMoves) {
			// Make the move in a different copy of the game
			FactorGame gameCopy = new FactorGame(game);
			gameCopy.makeMove(move);
			// Check if the result is better than the previous maximum
			int result = gameCopy.getResult();
			if (gameCopy.isPlayer1Turn()) {
				// If this is true, then this player is player 2 and therefore
				// wants to minimize the result
				result *= -1;
			}
			if (result >= maxResult) {
				maxResult = result;
				maxMove = move;
			}
		}
		return maxMove;
	}
}