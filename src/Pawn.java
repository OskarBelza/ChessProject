import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pawn chess piece.
 * Inherits from the Piece class and implements the getLegalMoves() method.
 */
public class Pawn extends Piece {

    /**
     * Constructor for the Pawn piece.
     * @param player The player who owns this piece.
     * @param x The initial x-coordinate of the Pawn.
     * @param y The initial y-coordinate of the Pawn.
     */
    public Pawn(Player player, int x, int y) {
        super(player, x, y);
    }

    /**
     * Returns a list of all legal moves for the Pawn.
     * The Pawn moves forward one square but captures diagonally.
     * It can also move two squares forward on its first move and perform en passant.
     * @param chessBoard The current state of the chessboard.
     * @return A list of legal moves available for the Pawn.
     */
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();
        Piece lastPieceToMove = null;

        // Get the last moved piece for en passant checks
        if (!chessBoard.moveHistory.isEmpty()) {
            lastPieceToMove = chessBoard.moveHistory.peek().getPiece();
        }

        // Define movement rules for black and white pawns
        if (this.getColor().equals("Black")) {
            if (y == 1 && chessBoard.getPiece(x, y + 1) == null) {
                addLegalMove(moves, x, y + 2, chessBoard);
            }
            addLegalMove(moves, x, y + 1, chessBoard);
            addLegalAttack(moves, x + 1, y + 1, chessBoard);
            addLegalAttack(moves, x - 1, y + 1, chessBoard);
            addEnPassant(moves, chessBoard, "Black", lastPieceToMove);
        } else {
            if (y == 6) {
                addLegalMove(moves, x, y - 2, chessBoard);
            }
            addLegalMove(moves, x, y - 1, chessBoard);
            addLegalAttack(moves, x + 1, y - 1, chessBoard);
            addLegalAttack(moves, x - 1, y - 1, chessBoard);
            addEnPassant(moves, chessBoard, "White", lastPieceToMove);
        }

        return moves;
    }

    /**
     * Adds a legal non-capturing move for the Pawn.
     * @param moves List of legal moves.
     * @param x Target x-coordinate.
     * @param y Target y-coordinate.
     * @param chessBoard The current chessboard state.
     */
    public void addLegalMove(List<Move> moves, int x, int y, ChessBoard chessBoard) {
        if (!chessBoard.outOfBounds(x, y)) {
            if (chessBoard.getPiece(x, y) == null) {
                moves.add(new Move(x, y, this, false, null));
            }
        }
    }

    /**
     * Adds a legal capturing move for the Pawn.
     * @param moves List of legal moves.
     * @param x Target x-coordinate.
     * @param y Target y-coordinate.
     * @param chessBoard The current chessboard state.
     */
    public void addLegalAttack(List<Move> moves, int x, int y, ChessBoard chessBoard) {
        if (!chessBoard.outOfBounds(x, y)) {
            if (chessBoard.getPiece(x, y) != null) {
                if (!chessBoard.getPiece(x, y).getColor().equals(this.getColor())) {
                    moves.add(new Move(x, y, this, true, chessBoard.getPiece(x, y)));
                }
            }
        }
    }

    /**
     * Handles en passant capturing for the Pawn.
     * Checks if the last moved piece was an adjacent Pawn that moved two squares.
     * @param moves List of legal moves.
     * @param chessBoard The current chessboard state.
     * @param color The color of the Pawn.
     * @param lastPieceToMove The last piece moved on the board.
     */
    public void addEnPassant(List<Move> moves, ChessBoard chessBoard, String color, Piece lastPieceToMove) {
        if (lastPieceToMove == null) return; // No last piece to check

        if (color.equals("Black")) {
            if (y == 4 && lastPieceToMove.getClass().getSimpleName().equals("Pawn") && lastPieceToMove.getColor().equals("White")) {
                if (chessBoard.moveHistory.peek().getyTarget() == 4 && chessBoard.moveHistory.peek().getyStart() == 6) {
                    if (Math.abs(chessBoard.moveHistory.peek().getxTarget() - x) == 1) {
                        moves.add(new Move(chessBoard.moveHistory.peek().getxTarget(), 5, this, true, lastPieceToMove));
                    }
                }
            }
        } else { // White Pawn
            if (y == 3 && lastPieceToMove.getClass().getSimpleName().equals("Pawn") && lastPieceToMove.getColor().equals("Black")) {
                if (chessBoard.moveHistory.peek().getyTarget() == 3 && chessBoard.moveHistory.peek().getyStart() == 1) {
                    if (Math.abs(lastPieceToMove.getX() - x) == 1) {
                        moves.add(new Move(chessBoard.moveHistory.peek().getxTarget(), 2, this, true, lastPieceToMove));
                    }
                }
            }
        }
    }
}
