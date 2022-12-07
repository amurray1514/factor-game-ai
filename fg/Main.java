package fg;

import java.util.Scanner;

/**
 * Main class to run the project.
 *
 * @author Archer Murray
 */
public final class Main
{
	public static final Scanner in = new Scanner(System.in);
	
	/**
	 * Obtains user input and returns a Factor Game player of the requested
	 * type.
	 *
	 * @return A Factor Game player according to user input.
	 */
	private static Player getPlayer()
	{
		System.out.println("Player types:");
		System.out.println("1. Human Player");
		System.out.println("2. Random Player");
		System.out.println("3. Greedy Player");
		System.out.println("4. Minimax Player");
		while (true) {
			System.out.print("Enter type: ");
			int type = 0;
			try {
				type = Integer.parseInt(in.nextLine());
				// this second nextLine shouldn't be necessary, but there is a
				// bug in the IDE that necessitates it
				in.nextLine();
			} catch (NumberFormatException e) {
				// do nothing
			}
			switch (type) {
				case 1:
					return new HumanPlayer();
				case 2:
					return new RandomPlayer();
				case 3:
					return new GreedyPlayer();
				case 4:
					return new MinimaxPlayer(true);
				default:
					System.out.println("Illegal type. Please try again.");
			}
		}
	}
	
	/**
	 * The main method to run the project.
	 *
	 * @param args The array of command-line arguments (unused).
	 */
	public static void main(String[] args)
	{
		System.out.println("Welcome to the Factor Game!");
		GameHost game = new GameHost(new FactorGame(30, false),
				getPlayer(), getPlayer());
		System.out.println("Game result: " + game.play(true));
	}
}