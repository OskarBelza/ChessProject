import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class QueenTest {
    private ChessBoard board;
    private Player white;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
        white = new Player("White");
    }

    @Test
    void testQueenMoves() {
        Queen queen = new Queen(white, 4, 4);
        board.setPiece(queen);

        List<Move> moves = queen.getLegalMoves(board);
        assertEquals(27, moves.size()); // Królowa może poruszać się jak wieża i goniec
    }

    @Test
    void testQueenBlockedByPiece() {
        Queen queen = new Queen(white, 4, 4);
        Pawn pawn = new Pawn(white, 4, 5);
        board.setPiece(queen);
        board.setPiece(pawn);

        List<Move> moves = queen.getLegalMoves(board);
        assertFalse(moves.contains(new Move(4, 6, queen, false, null))); // Królowa nie może przejść przez własny pionek
    }
}
