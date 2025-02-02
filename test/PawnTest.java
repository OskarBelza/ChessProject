import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class PawnTest {
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
    void testPawnInitialMove() {
        Pawn pawn = new Pawn(white, 4, 6);
        board.setPiece(pawn);

        List<Move> moves = pawn.getLegalMoves(board);
        assertEquals(2, moves.size()); // Pionek może ruszyć się o 1 lub 2 pola
    }

    @Test
    void testPawnCapture() {
        Pawn pawn = new Pawn(white, 4, 4);
        board.setPiece(pawn);
        Pawn enemyPawn = new Pawn(black, 5, 3);
        board.setPiece(enemyPawn);

        List<Move> moves = pawn.getLegalMoves(board);
        assertTrue(moves.stream().anyMatch(m -> m.getxTarget() == 5 && m.getyTarget() == 3));
    }
}
