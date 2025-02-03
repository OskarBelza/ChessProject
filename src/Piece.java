import java.util.ArrayList;
import java.util.List;

/**
 * Represents a general chess piece.
 * This is the base class for all chess pieces, providing common functionality.
 */
public class Piece {
    protected Player player; // The player who owns this piece
    protected int x, y; // Current position of the piece
    protected boolean isAlive; // Indicates if the piece is still in play
    protected boolean hasMoved = false; // Tracks if the piece has moved (important for castling and pawn moves)

    /**
     * Constructs a chess piece.
     * @param player The player who owns the piece.
     * @param x The initial x-coordinate of the piece.
     * @param y The initial y-coordinate of the piece.
     */
    public Piece(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
        isAlive = true;
        hasMoved = false;
    }

    //GETTERS & SETTERS

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Player getPlayer() {
        return player;
    }

    public String getColor() {
        return player.getColor();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void capture() {
        this.isAlive = false;
    }

    public void unCapture() {
        this.isAlive = true;
    }


    /**
     * Gets a list of diagonal moves available for this piece.
     * Used by Bishops and the Queen.
     * @param chessBoard The current chessboard state.
     * @return A list of legal diagonal moves.
     */
    public List<Move> getDiagonalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int newX = x, newY = y;
            while (true) {
                newX += dx;
                newY += dy;
                if (!addMoveIfLegal(moves, newX, newY, chessBoard)) break;
            }
        }
        return moves;
    }

    /**
     * Gets a list of straight-line moves (vertical and horizontal).
     * Used by Rooks and the Queen.
     * @param chessBoard The current chessboard state.
     * @return A list of legal straight moves.
     */
    public List<Move> getStraightMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int newX = x, newY = y;
            while (true) {
                newX += dx;
                newY += dy;
                if (!addMoveIfLegal(moves, newX, newY, chessBoard)) break;
            }
        }
        return moves;
    }

    /**
     * Checks if a move is legal and adds it to the list if valid.
     * Stops if an opponent's piece is encountered (capture move).
     * @param moves The list of legal moves to update.
     * @param x The x-coordinate of the potential move.
     * @param y The y-coordinate of the potential move.
     * @param chessBoard The current chessboard state.
     * @return False if a piece is encountered, stopping further moves in that direction.
     */
    public boolean addMoveIfLegal(List<Move> moves, int x, int y, ChessBoard chessBoard) {
        if (!chessBoard.outOfBounds(x, y)) {
            Piece targetPiece = chessBoard.getPiece(x, y);
            if (targetPiece == null) {
                moves.add(new Move(x, y, this, false, null));
                return true;
            } else if (!targetPiece.getColor().equals(this.getColor())) {
                moves.add(new Move(x, y, this, true, targetPiece));
                return false; // Stop further moves in this direction
            }
        }
        return false;
    }

    /**
     * Placeholder method for getting legal moves.
     * Each piece type should override this method with its specific movement rules.
     * @param chessBoard The current chessboard state.
     * @return A list of legal moves.
     */
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return null; // To be overridden in subclasses
    }

    /**
     * Prints piece information for debugging purposes.
     */
    public void printInfo() {
        System.out.println("Color: " + getColor());
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        System.out.println("Is Alive: " + isAlive);
    }
}
