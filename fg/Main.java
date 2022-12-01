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
		GameHost game = new GameHost(new FactorGame(), new HumanPlayer(),
				new GreedyPlayer());
		System.out.println("Game result: " + game.play(true));
	}
}