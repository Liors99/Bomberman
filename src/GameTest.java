import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import game.Game;

class GameTest {

	@Test
	void TestPause() {
		Game game = new Game();
		game.start();
		assertFalse("Game is not running",game.isPaused());
	}
	 
	@Test
	void TestEnd() {
		Game game = new Game();
		game.start();
		assertFalse("game is still goin", game.isEnded());
	}
	 
	@Test
	void TestNumTileX() {
		Game game = new Game();
		assertEquals("There are 19 tiles in a row", 19, game.getNumTilesX());
	}
	 
	@Test
	void TestNumTileY() {
		Game game = new Game();
		assertEquals("There are 19 tiles in a column", 19, game.getNumTilesY());
	}
}