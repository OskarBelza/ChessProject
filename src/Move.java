import java.util.Objects;

/**
 * Represents a move in the chess game.
 * Stores information about the starting position, target position, the piece being moved,
 * whether it is a capture, and special move types like castling.
 */
public class Move {
    private final int xTarget; // Target x-coordinate
    private final int yTarget; // Target y-coordinate
    private final int xStart; // Starting x-coordinate
    private final int yStart; // Starting y-coordinate
    private Piece piece; // The piece that is moving
    private final boolean isCapture; // Indicates if the move captures an opponent's piece
    private final Piece capturedPiece; // The piece that was captured (if any)
    private boolean isCastle; // Indicates if the move is a castling move
    private Piece castledRook; // The rook involved in castling (if applicable)

    /**
     * Constructor for a standard move.
     * @param xTarget Target x-coordinate.
     * @param yTarget Target y-coordinate.
     * @param piece The piece being moved.
     * @param isCapture True if the move captures an opponent's piece.
     * @param capturedPiece The captured piece (if any).
     */
    public Move(int xTarget, int yTarget, Piece piece, boolean isCapture, Piece capturedPiece) {
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.xStart = piece.getX();
        this.yStart = piece.getY();
        this.piece = piece;
        this.isCapture = isCapture;
        this.capturedPiece = capturedPiece;
    }

    /**
     * Constructor for a castling move.
     * @param xTarget Target x-coordinate for the King.
     * @param yTarget Target y-coordinate for the King.
     * @param piece The King piece performing castling.
     * @param isCapture Always false for castling.
     * @param capturedPiece Always null for castling.
     * @param isCastle True to indicate a castling move.
     * @param castledRook The Rook involved in castling.
     */
    public Move(int xTarget, int yTarget, Piece piece, boolean isCapture, Piece capturedPiece, boolean isCastle, Piece castledRook) {
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.xStart = piece.getX();
        this.yStart = piece.getY();
        this.piece = piece;
        this.isCapture = isCapture;
        this.capturedPiece = capturedPiece;
        this.isCastle = isCastle;
        this.castledRook = castledRook;
    }

    //GETTERS & SETTERS

    public Piece getCastledRook() {
        return castledRook;
    }

    public boolean getIsCastle() {
        return isCastle;
    }

    public int getxTarget() {
        return xTarget;
    }

    public int getxStart() {
        return xStart;
    }

    public int getyTarget() {
        return yTarget;
    }

    public int getyStart() {
        return yStart;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean getIsCapture() {
        return isCapture;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Prints move information for debugging.
     */
    public void printInfo() {
        System.out.println("Move to X: " + xTarget + ", Y: " + yTarget +
                " | Captured: " + isCapture + " | Piece: " + piece.getColor() +
                " " + piece.getClass().getSimpleName() + " | Castling: " + isCastle +
                " | Rook: " + castledRook);
    }

    /**
     * Checks if two moves are equal based on their target coordinates.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return xTarget == move.xTarget && yTarget == move.yTarget;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xTarget, yTarget);
    }

    /**
     * Generates chess notation for this move.
     * Includes castling, captures, and normal piece moves.
     * @return A string representing the move in standard chess notation.
     */
    public String getChessNotation() {
        StringBuilder notation = new StringBuilder();

        // Castling notation
        if (isCastle) {
            return (xTarget == 6) ? "O-O" : "O-O-O";
        }

        // Piece notation (pawns are not represented with a letter)
        String pieceSymbol = "";
        if (!(piece instanceof Pawn)) {
            pieceSymbol = piece.getClass().getSimpleName().substring(0, 1);
        }

        // Capture indication
        String captureSymbol = isCapture ? "x" : "";

        // Target square notation
        String targetSquare = getSquareNotation(xTarget, yTarget);

        notation.append(pieceSymbol).append(captureSymbol).append(targetSquare);

        return notation.toString();
    }

    /**
     * Converts board coordinates into standard chess notation (e.g., e4, d5).
     * @param x The x-coordinate on the board.
     * @param y The y-coordinate on the board.
     * @return The chess notation string.
     */
    private String getSquareNotation(int x, int y) {
        char file = (char) ('a' + x);
        int rank = 8 - y;
        return "" + file + rank;
    }
}
