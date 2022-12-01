package fg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a Factor Game player that uses the minimax algorithm to
 * select moves.
 *
 * @author Archer Murray
 */
public class MinimaxPlayer implements Player
{
	private final boolean printDebugInfo;
	private int minimaxCalls;
	private final List<Integer> moveSequence;
	
	/**
	 * Creates a new minimax player with the given setting on whether to print
	 * debug information for each move.
	 *
	 * @param printDebugInfo {@code true} if debug information is to be printed;
	 * {@code false} otherwise.
	 */
	public MinimaxPlayer(boolean printDebugInfo)
	{
		this.printDebugInfo = printDebugInfo;
		this.minimaxCalls = 0;
		this.moveSequence = new ArrayList<>();
	}
	
	/**
	 * Creates a new minimax player that does not print debug information.
	 */
	public MinimaxPlayer()
	{
		this(false);
	}
	
	/**
	 * Returns the minimax value of the passed-in game state.
	 *
	 * @param game The game state to compute the minimax value of.
	 * @param alpha The alpha value, for use in alpha-beta pruning.
	 * @param beta The beta value, for use in alpha-beta pruning.
	 * @return The minimax value of the passed-in game state.
	 */
	private int getMinimaxValue(FactorGame game, int alpha, int beta)
	{
		this.minimaxCalls++;
		if (this.printDebugInfo) {
			System.out.print("\rMinimax calls: " + this.minimaxCalls +
					" - analyzing move sequence " + this.moveSequence);
		}
		if (game.isGameOver()) {
			return game.getResult();
		}
		// Find the minimax values of all possible moves
		List<Integer> allMoves = game.getAllLegalMoves();
		Collections.reverse(allMoves); // small move ordering optimization
		boolean findMin = !game.isPlayer1Turn();
		int value = findMin ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		for (int m: allMoves) {
			this.moveSequence.add(m);
			// Make the move in a different copy of the game
			FactorGame gameCopy = new FactorGame(game);
			gameCopy.makeMove(m);
			// Compute the minimax value of the move
			int v = this.getMinimaxValue(gameCopy, findMin ? alpha : value,
					findMin ? value : beta);
			this.moveSequence.remove(this.moveSequence.size() - 1);
			// Alpha-beta pruning
			if (findMin && v < alpha) {
				return alpha;
			}
			if (!findMin && v > beta) {
				return beta;
			}
			// Check if move is better than current best
			if (findMin ? v < value : v > value) {
				value = v;
			}
		}
		return value;
	}
	
	@Override
	public int selectMove(FactorGame game)
	{
		this.minimaxCalls = 0;
		boolean findMin = !game.isPlayer1Turn();
		int bestValue = findMin ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		int bestMove = -1;
		List<Integer> allMoves = game.getAllLegalMoves();
		Collections.reverse(allMoves); // small move ordering optimization
		for (int move: allMoves) {
			this.moveSequence.add(move);
			// Make the move in a different copy of the game
			FactorGame gameCopy = new FactorGame(game);
			gameCopy.makeMove(move);
			// Compute the minimax value of the game after this move
			int value = getMinimaxValue(gameCopy,
					findMin ? Integer.MIN_VALUE : bestValue,
					findMin ? bestValue : Integer.MAX_VALUE);
			if (findMin ? value < bestValue : value > bestValue) {
				bestValue = value;
				bestMove = move;
			}
			this.moveSequence.remove(this.moveSequence.size() - 1);
		}
		if (this.printDebugInfo) {
			System.out.println("\rMinimax calls: " + this.minimaxCalls);
		}
		return bestMove;
	}
}