import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class BishopTest {
    private ChessBoard board;
    private Player white;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
        white = new Player("White");
    }

    @Test
    void testBishopMoves() {
        Bishop bishop = new Bishop(white, 4, 4);
        board.setPiece(bishop);

        List<Move> moves = bishop.getLegalMoves(board);
        assertEquals(13, moves.size()); // Goniec może poruszać się po przekątnych
    }

    @Test
    void testBishopBlockedByPiece() {
        Bishop bishop = new Bishop(white, 4, 4);
        Pawn pawn = new Pawn(white, 5, 5);
        board.setPiece(bishop);
        board.setPiece(pawn);

        List<Move> moves = bishop.getLegalMoves(board);
        assertFalse(moves.contains(new Move(6, 6, bishop, false, null))); // Goniec nie powinien przejść przez własny pionek
    }
}
