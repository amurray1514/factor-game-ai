package fg;

import java.util.Scanner;

/**
 * Main class to run the project.
 * 
 * @author Archer Murray
 */
public final class Main
{
	public static void main(String[] args)
	{
		FactorGame fg = new FactorGame();
		System.out.println(fg);
		try (Scanner in = new Scanner(System.in)) {
			while (!fg.isGameOver()) {
				System.out.print("Enter move: ");
				int move = 0;
				try {
					move = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					// do nothing
				}
				if (fg.makeMove(move)) {
					System.out.println(fg);
				} else {
					System.out.println("Illegal move. Please try again.");
				}
			}
		}
	}
}