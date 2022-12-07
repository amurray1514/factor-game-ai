package fg;

import java.util.List;

/**
 * Class representing a Factor Game player that makes legal moves at random.
 *
 * @author Archer Murray
 */
public class RandomPlayer implements Player
{
	@Override
	public int selectMove(FactorGame game)
	{
		List<Integer> allMoves = game.getAllLegalMoves();
		return allMoves.get((int)(Math.random() * allMoves.size()));
	}
}