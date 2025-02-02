import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitializeGame() {
        assertNotNull(game.getChessBoard());
        assertEquals(16, game.getCurrentPlayer().getPiecesAlive().size()); // Gracze mają 16 pionków na starcie
    }

    @Test
    void testSwitchTurns() {
        game.switchTurns();
        assertEquals("Black", game.getCurrentPlayer().getColor());
        game.switchTurns();
        assertEquals("White", game.getCurrentPlayer().getColor());
    }

    @Test
    void testGameOverCondition() {
        Player white = game.getCurrentPlayer();
        Player black = game.getOtherPlayer();
        King whiteKing = new King(white, 4, 7);
        King blackKing = new King(black, 4, 0);

        game.getChessBoard().setPiece(whiteKing);
        game.getChessBoard().setPiece(blackKing);
        white.setKing(whiteKing);
        black.setKing(blackKing);

        assertFalse(game.gameOverCondition()); // Początkowo gra nie powinna być skończona
    }
}
