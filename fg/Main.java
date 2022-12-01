package fg;

/**
 * Main class to run the project.
 *
 * @author Archer Murray
 */
public final class Main
{
	public static void main(String[] args)
	{
		GameHost game = new GameHost(new FactorGame(30, false),
				new MinimaxPlayer(true), new MinimaxPlayer(true));
		System.out.println("Game result: " + game.play(true));
	}
}