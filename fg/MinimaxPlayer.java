package fg;

import java.util.List;

/**
 * Class representing a Factor Game player that uses the minimax algorithm to
 * select moves.
 *
 * @author Archer Murray
 */
public class MinimaxPlayer implements Player
{
	private static int getMinimaxValue(FactorGame game, int move)
	{
		FactorGame gameCopy = new FactorGame(game);
		gameCopy.makeMove(move);
		if (gameCopy.isGameOver()) {
			return gameCopy.getResult();
		}
		// Find the minimax values of all possible replies
		List<Integer> allMoves = gameCopy.getAllLegalMoves();
		boolean findMin = !gameCopy.isPlayer1Turn();
		int value = findMin ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		for (int m: allMoves) {
			// Compute the minimax value of the move
			int v = getMinimaxValue(gameCopy, m);
			if (findMin ? v < value : v > value) {
				value = v;
			}
		}
		return value;
	}
	
	@Override
	public int selectMove(FactorGame game)
	{
		int maxValue = Integer.MIN_VALUE;
		int maxMove = -1;
		List<Integer> allMoves = game.getAllLegalMoves();
		for (int move: allMoves) {
			// Compute the minimax value of the move
			int value = getMinimaxValue(game, move);
			if (!game.isPlayer1Turn()) {
				// If we are here, then this player is player 2 and therefore
				// wants to minimize the minimax value
				value *= -1;
			}
			if (value >= maxValue) {
				maxValue = value;
				maxMove = move;
			}
		}
		return maxMove;
	}
}