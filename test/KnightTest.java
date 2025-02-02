import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class KnightTest {
    private ChessBoard board;
    private Player white;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
        white = new Player("White");
    }

    @Test
    void testKnightMoves() {
        Knight knight = new Knight(white, 4, 4);
        board.setPiece(knight);

        List<Move> moves = knight.getLegalMoves(board);
        assertEquals(8, moves.size()); // Rycerz powinien mieć 8 możliwych ruchów
    }
}
