package fg;

/**
 * Class representing a human player of the Factor Game.
 *
 * @author Archer Murray
 */
public class HumanPlayer implements Player
{
	@Override
	public int selectMove(FactorGame game)
	{
		while (true) {
			System.out.print("Enter move: ");
			int move = 0;
			try {
				move = Integer.parseInt(Main.in.nextLine());
				// this second nextLine shouldn't be necessary, but there is a
				// bug in the IDE that necessitates it
				Main.in.nextLine();
			} catch (NumberFormatException e) {
				// do nothing
			}
			if (game.isLegalMove(move)) {
				return move;
			}
			System.out.println("Illegal move. Please try again.");
		}
	}
}