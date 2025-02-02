import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChessBoardTest {
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
    void testSetAndGetPiece() {
        Rook rook = new Rook(white, 0, 0);
        board.setPiece(rook);

        assertNotNull(board.getPiece(0, 0));
        assertEquals(rook, board.getPiece(0, 0));
    }

    @Test
    void testMovePiece() {
        Pawn pawn = new Pawn(white, 4, 6);
        board.setPiece(pawn);
        Move move = new Move(4, 5, pawn, false, null);

        board.movePiece(move);
        assertNull(board.getPiece(4, 6));
        assertEquals(pawn, board.getPiece(4, 5));
    }

    @Test
    void testUndoMovePiece() {
        Pawn pawn = new Pawn(white, 4, 6);
        board.setPiece(pawn);
        Move move = new Move(4, 5, pawn, false, null);

        board.movePiece(move);
        board.undoMovePiece();

        assertEquals(pawn, board.getPiece(4, 6));
        assertNull(board.getPiece(4, 5));
    }
}
