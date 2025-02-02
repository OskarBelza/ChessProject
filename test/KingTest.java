import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class KingTest {
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
    void testKingMoves() {
        King king = new King(white, 4, 4);
        board.setPiece(king);

        List<Move> moves = king.getLegalMoves(board);
        assertEquals(8, moves.size()); // Król może się poruszać o jedno pole w każdym kierunku
    }

    @Test
    void testCastling() {
        King king = new King(white, 4, 0);
        Rook rook = new Rook(white, 7, 0);
        board.setPiece(king);
        board.setPiece(rook);

        List<Move> moves = king.getLegalMoves(board);
        assertTrue(moves.stream().anyMatch(m -> m.getxTarget() == 6 && m.getyTarget() == 0 && m.getIsCastle())); // Król może zrobić roszadę
    }
}
