package game;
/**
 * Class from which the game is launched. The GUI screen size is optimized to the user's screen. 
 * Credit to Youtube's CodeNMore for providing tutorials for about video game basics using
 * Java Swing.
 * @author The Blue Monkeys: Mariella Nalepa, Lior Somin, Macks Tam, and Spencer Tam
 *
 */
public class Launcher {
	
	public static void main(String[] args) {
		Game game = new Game();
		// Start game thread
		game.start();

	}
}