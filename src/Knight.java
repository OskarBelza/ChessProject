import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Knight chess piece.
 * Inherits from the Piece class and implements the getLegalMoves() method.
 */
public class Knight extends Piece {

    /**
     * Constructor for the Knight piece.
     * @param player The player who owns this piece.
     * @param x The initial x-coordinate of the Knight.
     * @param y The initial y-coordinate of the Knight.
     */
    public Knight(Player player, int x, int y) {
        super(player, x, y);
    }

    /**
     * Returns a list of all legal moves for the Knight.
     * The Knight moves in an "L" shape: two squares in one direction and then one perpendicular.
     * @param chessBoard The current state of the chessboard.
     * @return A list of legal moves available for the Knight.
     */
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();

        // Possible Knight movement offsets
        int[] xMoves = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] yMoves = {2, 1, -1, -2, -2, -1, 1, 2};

        // Iterate through all possible moves and add them if legal
        for(int i = 0; i < xMoves.length; i++) {
            int x = this.getX() + xMoves[i];
            int y = this.getY() + yMoves[i];
            addMoveIfLegal(moves, x, y, chessBoard);
        }

        return moves;
    }
}
