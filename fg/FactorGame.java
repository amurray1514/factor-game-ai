package fg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing the Factor Game.
 *
 * @author Archer Murray
 */
public class FactorGame
{
	private int score1, score2;
	private final boolean[] board;
	private boolean player1Turn, penalty1, penalty2;
	private final boolean penaltiesActive;
	
	/**
	 * Creates a new factor game with the passed-in board size and boolean
	 * representing whether penalties are active.
	 *
	 * @param boardSize The number of squares on the board.
	 * @param penaltiesActive {@code true} if penalties are active;
	 * {@code false} if penalty moves are illegal.
	 */
	public FactorGame(int boardSize, boolean penaltiesActive)
	{
		if (boardSize < 1) {
			throw new IllegalArgumentException("board size must be positive");
		}
		this.score1 = 0;
		this.score2 = 0;
		this.board = new boolean[boardSize];
		this.player1Turn = true;
		this.penalty1 = false;
		this.penalty2 = false;
		this.penaltiesActive = penaltiesActive;
	}
	
	/**
	 * Creates a new Factor Game with the passed-in board size and penalties
	 * active.
	 *
	 * @param boardSize The number of squares on the board.
	 */
	public FactorGame(int boardSize)
	{
		this(boardSize, true);
	}
	
	/**
	 * Creates a new Factor Game with a board size of 30 and penalties active.
	 */
	public FactorGame()
	{
		this(30);
	}
	
	/**
	 * Returns {@code true} if the passed-in square is open and {@code false}
	 * otherwise.
	 *
	 * @param square The square to check.
	 * @return {@code true} if the passed-in square is open and {@code false}
	 * otherwise.
	 */
	public boolean isSquareOpen(int square)
	{
		return !board[square - 1];
	}
	
	/**
	 * Returns a list containing all open squares.
	 *
	 * @return A list containing all open squares.
	 */
	public List<Integer> getAllOpenSquares()
	{
		return IntStream.range(0, this.board.length)
				.filter(i -> !board[i]).mapToObj(i -> i + 1)
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a list containing all proper factors of the passed-in integer, in
	 * ascending order.
	 * <p>
	 * A number k is a proper factor of a number n if and only if
	 * {@code n % k == 0} and {@code k != n}.
	 *
	 * @param num The integer to return the proper factors of.
	 * @return A list containing all proper factors of the passed-in integer.
	 */
	public static List<Integer> getAllProperFactors(int num)
	{
		if (num < 1) {
			throw new IllegalArgumentException("num must be positive");
		}
		List<Integer> ret = new ArrayList<>();
		if (num > 1) {
			ret.add(1);
			int insertIdx = 1;
			int limit = (int)Math.sqrt(num);
			for (int i = 2; i <= limit; i++) {
				if (num % i == 0) {
					// If num is a perfect square, don't add its sqrt twice
					if (i < limit) {
						ret.add(insertIdx, num / i);
					}
					ret.add(insertIdx, i);
					insertIdx++;
				}
			}
		}
		return ret;
	}
	
	/**
	 * Returns a list containing all open squares corresponding to factors of
	 * the passed-in square, excluding the passed-in square itself.
	 * <p>
	 * That is, this method returns a list of all squares that the opponent
	 * would circle if the passed-in square was circled.
	 *
	 * @param square The square to check.
	 * @return A list containing all open squares corresponding to factors of
	 * the passed-in square.
	 */
	public List<Integer> getOpenFactors(int square)
	{
		List<Integer> factors = getAllProperFactors(square);
		return factors.stream().filter(this::isSquareOpen)
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns {@code true} if the passed-in square is a "penalty square" (that
	 * is, circling that square would incur a penalty) and {@code false}
	 * otherwise.
	 *
	 * @param square The square to check.
	 * @return {@code true} if the passed-in square is a penalty square and
	 * {@code false} otherwise.
	 */
	public boolean isPenaltySquare(int square)
	{
		return this.getOpenFactors(square).isEmpty();
	}
	
	/**
	 * Returns a list containing all open non-penalty squares.
	 *
	 * @return A list containing all open non-penalty squares.
	 * @see FactorGame#isPenaltySquare(int)
	 */
	public List<Integer> getAllNonPenaltySquares()
	{
		List<Integer> openSquares = this.getAllOpenSquares();
		return openSquares.stream().filter(i -> !this.isPenaltySquare(i))
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns {@code true} if the passed-in move is a legal move and
	 * {@code false} otherwise.
	 * <p>
	 * If penalties are active, all open squares are legal moves, but moving in
	 * a penalty square results in a penalty. On the turn lost due to the
	 * penalty, the only legal move is 0 (representing the player passing their
	 * turn, which is normally illegal). If penalties are inactive, penalty
	 * squares are illegal moves.
	 *
	 * @param move The move to check.
	 * @return {@code true} if the passed-in move is a legal move and
	 * {@code false} otherwise.
	 * @see FactorGame#isPenaltySquare(int)
	 */
	public boolean isLegalMove(int move)
	{
		if (move < 0 || move > board.length) {
			return false;
		}
		if (move == 0) {
			return this.player1Turn ? this.penalty1 : this.penalty2;
		}
		if (this.penaltiesActive) {
			return this.isSquareOpen(move);
		}
		return !this.isPenaltySquare(move);
	}
	
	/**
	 * Returns a list containing all legal moves for the current player.
	 *
	 * @return A list containing all legal moves for the current player.
	 */
	public List<Integer> getAllLegalMoves()
	{
		return IntStream.rangeClosed(0, this.board.length)
				.filter(this::isLegalMove).boxed().collect(Collectors.toList());
	}
	
	/**
	 * Returns {@code true} if the game is over and {@code false} otherwise.
	 * <p>
	 * The game ends when there are no non-penalty squares remaining on the
	 * board.
	 *
	 * @return {@code true} if the game is over and {@code false} otherwise.
	 * @see FactorGame#isPenaltySquare(int)
	 */
	public boolean isGameOver()
	{
		return this.getAllNonPenaltySquares().isEmpty();
	}
	
	/**
	 * If the passed-in move is legal, makes that move and returns {@code true};
	 * otherwise, leaves the game state unchanged and returns {@code false}.
	 *
	 * @param move The move to make.
	 * @return {@code true} if the move is legal; {@code false} otherwise.
	 */
	public boolean makeMove(int move)
	{
		if (!isLegalMove(move)) {
			return false;
		}
		if (move > 0) {
			this.board[move - 1] = true;
			List<Integer> factors = this.getOpenFactors(move);
			if (factors.isEmpty()) {
				// Penalty square
				if (this.player1Turn) {
					this.penalty1 = true;
				} else {
					this.penalty2 = true;
				}
			} else {
				// Non-penalty square
				int factorSum = factors.stream().mapToInt(i -> i).sum();
				for (int i: factors) {
					this.board[i - 1] = true;
				}
				if (this.player1Turn) {
					this.score1 += move;
					this.score2 += factorSum;
				} else {
					this.score2 += move;
					this.score1 += factorSum;
				}
			}
		} else {
			// Passed turn due to penalty
			if (this.player1Turn) {
				this.penalty1 = false;
			} else {
				this.penalty2 = false;
			}
		}
		this.player1Turn = !this.player1Turn;
		return true;
	}
	
	/**
	 * Returns a string representation of this game.
	 *
	 * @return A string representation of this game.
	 */
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		int squareWidth = Integer.valueOf(this.board.length).toString()
				.length();
		int boardWidth = (int)Math.sqrt(this.board.length);
		int boardHeight = (int)Math.ceil(
				(double)this.board.length / boardWidth);
		String horizBorder =
				('+' + "-".repeat(squareWidth)).repeat(boardWidth) + "+\n";
		for (int i = 0; i < boardHeight; i++) {
			ret.append(horizBorder);
			for (int j = 0; j < boardWidth; j++) {
				ret.append('|');
				int num = boardWidth * i + j + 1;
				if (num <= this.board.length && isSquareOpen(num)) {
					ret.append(String.format("%" + squareWidth + 'd', num));
				} else {
					ret.append(" ".repeat(squareWidth));
				}
			}
			ret.append("|\n");
		}
		ret.append(horizBorder);
		ret.append(String.format("Score: %d-%d\n", this.score1, this.score2));
		if (this.isGameOver()) {
			if (this.score1 > this.score2) {
				ret.append("Player 1 wins!");
			} else if (this.score1 < this.score2) {
				ret.append("Player 2 wins!");
			} else {
				ret.append("The game is a draw!");
			}
		} else {
			ret.append(String.format("Player %d to move.\n",
					this.player1Turn ? 1 : 2));
			if (this.penalty1) {
				ret.append("Player 1 loses their next turn.\n");
			}
			if (this.penalty2) {
				ret.append("Player 2 loses their next turn.\n");
			}
		}
		return ret.toString();
	}
}