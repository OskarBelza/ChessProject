import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MoveTest {
    @Test
    void testMoveCreation() {
        Player player = new Player("White");
        Piece piece = new Pawn(player, 4, 4);
        Move move = new Move(4, 5, piece, false, null);

        assertEquals(4, move.getxStart());
        assertEquals(4, move.getyStart());
        assertEquals(4, move.getxTarget());
        assertEquals(5, move.getyTarget());
        assertFalse(move.getIsCapture());
    }

    @Test
    void testMoveNotation() {
        Player player = new Player("White");
        Piece piece = new Queen(player, 3, 3);
        Move move = new Move(3, 5, piece, false, null);

        assertEquals("Qd3", move.getChessNotation()); // Dla kolumny 'd' i rzędu 6
    }
}
