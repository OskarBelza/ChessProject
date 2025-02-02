import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Queen chess piece.
 * Inherits from the Piece class and implements the getLegalMoves() method.
 */
public class Queen extends Piece {

    /**
     * Constructor for the Queen piece.
     * @param player The player who owns this piece.
     * @param x The initial x-coordinate of the Queen.
     * @param y The initial y-coordinate of the Queen.
     */
    public Queen(Player player, int x, int y) {
        super(player, x, y);
    }

    /**
     * Returns a list of all legal moves for the Queen.
     * The Queen moves both diagonally (like a Bishop) and in straight lines (like a Rook).
     * @param chessBoard The current state of the chessboard.
     * @return A list of legal moves available for the Queen.
     */
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();

        // The Queen has the combined movement of a Rook and a Bishop
        moves.addAll(getDiagonalMoves(chessBoard));
        moves.addAll(getStraightMoves(chessBoard));

        return moves;
    }
}
