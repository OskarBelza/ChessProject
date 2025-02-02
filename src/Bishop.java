import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Bishop chess piece.
 * Inherits from the Piece class and implements the getLegalMoves() method.
 */
public class Bishop extends Piece {

    /**
     * Constructor for the Bishop piece.
     * @param player The player who owns this piece.
     * @param x The initial x-coordinate of the Bishop.
     * @param y The initial y-coordinate of the Bishop.
     */
    public Bishop(Player player, int x, int y) {
        super(player, x, y);
    }

    /**
     * Returns a list of all legal moves for the Bishop.
     * The Bishop moves diagonally in any direction.
     * @param chessBoard The current state of the chessboard.
     * @return A list of legal moves available for the Bishop.
     */
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getDiagonalMoves(chessBoard));
    }
}
