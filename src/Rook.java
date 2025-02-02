import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Rook chess piece.
 * Inherits from the Piece class and implements the getLegalMoves() method.
 */
public class Rook extends Piece {

    /**
     * Constructor for the Rook piece.
     * @param player The player who owns this piece.
     * @param x The initial x-coordinate of the Rook.
     * @param y The initial y-coordinate of the Rook.
     */
    public Rook(Player player, int x, int y) {
        super(player, x, y);
    }

    /**
     * Returns a list of all legal moves for the Rook.
     * The Rook moves in straight lines (horizontally and vertically).
     * @param chessBoard The current state of the chessboard.
     * @return A list of legal moves available for the Rook.
     */
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getStraightMoves(chessBoard));
    }
}
