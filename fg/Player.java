package fg;

/**
 * Interface representing a player of the Factor Game.
 *
 * @author Archer Murray
 */
public interface Player
{
	/**
	 * Returns the move this player would play in the passed-in game.
	 *
	 * @param game The game in which the player should select a move.
	 * @return The move this player would play.
	 */
	int selectMove(FactorGame game);
}