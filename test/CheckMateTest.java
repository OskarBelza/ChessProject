import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckMateTest {
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
    void testCheckMate() {
        King king = new King(white, 4, 0);
        Rook rook1 = new Rook(black, 4, 7);
        Rook rook2 = new Rook(black, 3, 7);
        board.setPiece(king);
        board.setPiece(rook1);
        board.setPiece(rook2);

        assertTrue(board.isCheckMate(white));
    }

    @Test
    void testStalemate() {
        King king = new King(white, 0, 0);
        Rook rook1 = new Rook(black, 1, 2);
        Rook rook2 = new Rook(black, 2, 1);
        board.setPiece(king);
        board.setPiece(rook1);
        board.setPiece(rook2);

        assertTrue(board.isCheckMate(white)); // Teoretyczny pat
    }
}
