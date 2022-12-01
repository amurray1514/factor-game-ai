package fg;

import java.util.Scanner;

/**
 * Class representing a human player of the Factor Game.
 *
 * @author Archer Murray
 */
public class HumanPlayer implements Player
{
	private static final Scanner in = new Scanner(System.in);
	
	@Override
	public int selectMove(FactorGame game)
	{
		while (true) {
			System.out.print("Enter move: ");
			int move = 0;
			try {
				move = Integer.parseInt(in.nextLine());
				// this second nextLine shouldn't be necessary, but there is a
				// bug in the IDE that necessitates it
				in.nextLine();
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