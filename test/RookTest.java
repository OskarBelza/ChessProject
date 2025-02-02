import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class RookTest {
    private ChessBoard board;
    private Player white;
    private Player black;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
        white = new Player("White");
        black = new Player("Black");
    }

    @Test
    void testRookMoves() {
        Rook rook = new Rook(white, 4, 4);
        board.setPiece(rook);

        List<Move> moves = rook.getLegalMoves(board);
        assertEquals(14, moves.size()); // Rook może poruszać się w pionie i poziomie
    }

    @Test
    void testRookBlockedByOwnPiece() {
        Rook rook = new Rook(white, 4, 4);
        Pawn pawn = new Pawn(white, 4, 5);
        board.setPiece(rook);
        board.setPiece(pawn);

        List<Move> moves = rook.getLegalMoves(board);
        assertFalse(moves.contains(new Move(4, 6, rook, false, null))); // Rook nie powinien przejść przez własnego pionka
    }
}
