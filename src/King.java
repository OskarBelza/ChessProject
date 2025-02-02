import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King chess piece.
 * Inherits from the Piece class and implements the getLegalMoves() method.
 */
public class King extends Piece {

    /**
     * Constructor for the King piece.
     * @param player The player who owns this piece.
     * @param x The initial x-coordinate of the King.
     * @param y The initial y-coordinate of the King.
     */
    public King(Player player, int x, int y) {
        super(player, x, y);
    }

    /**
     * Returns a list of all legal moves for the King.
     * The King moves one square in any direction.
     * It also checks for castling opportunities if the King has not moved.
     * @param board The current state of the chessboard.
     * @return A list of legal moves available for the King.
     */
    @Override
    public List<Move> getLegalMoves(ChessBoard board) {
        List<Move> moves = new ArrayList<>();

        // Possible King movement offsets (one square in all directions)
        int[] xMoves = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] yMoves = {1, 0, -1, 1, -1, 1, 0, -1};

        // Iterate through all possible moves and add them if legal
        for (int i = 0; i < xMoves.length; i++) {
            int x = this.x + xMoves[i];
            int y = this.y + yMoves[i];
            addMoveIfLegal(moves, x, y, board);
        }

        // Check castling conditions
        if (!hasMoved) {
            if (canCastleLeft(board)) {
                Rook rook = (Rook) board.getPiece(0, this.y);
                moves.add(new Move(2, this.y, this, false, null, true, rook));
            }
            if (canCastleRight(board)) {
                Rook rook = (Rook) board.getPiece(7, this.y);
                moves.add(new Move(6, this.y, this, false, null, true, rook));
            }
        }

        return moves;
    }

    /**
     * Checks if the King can castle on the left (Queen-side).
     * @param board The current state of the chessboard.
     * @return True if castling is possible, otherwise false.
     */
    public boolean canCastleLeft(ChessBoard board) {
        if (hasMoved) return false;

        Piece rook = board.getPiece(0, this.y);
        if (!(rook instanceof Rook) || rook.hasMoved()) return false;

        // Ensure no pieces are between King and Rook, and no squares are under attack
        for (int i = 1; i < 4; i++) {
            if (board.getPiece(i, this.y) != null) return false;
            if (board.spotAttacked(i, this.y, this.getPlayer())) return false;
        }

        return true;
    }

    /**
     * Checks if the King can castle on the right (King-side).
     * @param board The current state of the chessboard.
     * @return True if castling is possible, otherwise false.
     */
    public boolean canCastleRight(ChessBoard board) {
        if (hasMoved) return false;

        Piece rook = board.getPiece(7, this.y);
        if (!(rook instanceof Rook) || rook.hasMoved()) return false;

        // Ensure no pieces are between King and Rook, and no squares are under attack
        for (int i = 5; i < 7; i++) {
            if (board.getPiece(i, this.y) != null) return false;
            if (board.spotAttacked(i, this.y, this.getPlayer())) return false;
        }

        return true;
    }
}
