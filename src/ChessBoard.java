import java.util.Stack;

/**
 * Represents the chessboard and handles piece placement, moves, and game state.
 */
public class ChessBoard {
    public Piece[][] chessBoard;
    public Stack<Move> moveHistory; // Stores past moves for undo functionality

    /**
     * Initializes an empty chessboard and move history.
     */
    public ChessBoard() {
        chessBoard = new Piece[8][8];
        moveHistory = new Stack<>();
    }

    /**
     * Places a piece on the board.
     * @param piece The piece to be placed.
     */
    public void setPiece(Piece piece) {
        chessBoard[piece.getX()][piece.getY()] = piece;
    }

    /**
     * Retrieves the piece at the given coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The piece at (x, y), or null if empty.
     */
    public Piece getPiece(int x, int y) {
        return chessBoard[x][y];
    }

    /**
     * Moves a piece on the board and updates move history.
     * Handles both normal and castling moves.
     * @param move The move to be executed.
     */
    public void movePiece(Move move) {
        if (move.getIsCastle()) {
            // Handle castling by moving both the king and the rook
            int kingTargetX = move.getxTarget();
            int rookStartX = (kingTargetX == 6) ? 7 : 0;
            int rookTargetX = (kingTargetX == 6) ? 5 : 3;

            // Move the king
            chessBoard[move.getxStart()][move.getyStart()] = null;
            chessBoard[kingTargetX][move.getyTarget()] = move.getPiece();
            move.getPiece().setX(kingTargetX);
            move.getPiece().setY(move.getyTarget());
            move.getPiece().setHasMoved(true);

            // Move the rook
            chessBoard[rookStartX][move.getyTarget()] = null;
            chessBoard[rookTargetX][move.getyTarget()] = move.getCastledRook();
            move.getCastledRook().setX(rookTargetX);
            move.getCastledRook().setY(move.getyTarget());
            move.getCastledRook().setHasMoved(true);

            moveHistory.push(move);
        } else {
            // Handle normal moves and captures
            if (move.getIsCapture()) {
                move.getCapturedPiece().capture();
                chessBoard[move.getCapturedPiece().getX()][move.getCapturedPiece().getY()] = null;
            }

            chessBoard[move.getxStart()][move.getyStart()] = null;
            chessBoard[move.getxTarget()][move.getyTarget()] = move.getPiece();
            move.getPiece().setX(move.getxTarget());
            move.getPiece().setY(move.getyTarget());
            move.getPiece().setHasMoved(true);

            moveHistory.push(move);
        }
    }

    /**
     * Undoes the last move, restoring the previous board state.
     */
    public void undoMovePiece() {
        if (!moveHistory.isEmpty()) {
            Move move = moveHistory.pop();

            if (move.getIsCastle()) {
                // Undo castling by moving both the king and the rook back
                int kingTargetX = move.getxTarget();
                int rookStartX = (kingTargetX == 6) ? 7 : 0;
                int rookTargetX = (kingTargetX == 6) ? 5 : 3;

                // Move the king back
                chessBoard[kingTargetX][move.getyTarget()] = null;
                chessBoard[move.getxStart()][move.getyStart()] = move.getPiece();
                move.getPiece().setX(move.getxStart());
                move.getPiece().setY(move.getyStart());
                move.getPiece().setHasMoved(false);

                // Move the rook back
                chessBoard[rookTargetX][move.getyTarget()] = null;
                chessBoard[rookStartX][move.getyTarget()] = move.getCastledRook();
                move.getCastledRook().setX(rookStartX);
                move.getCastledRook().setY(move.getyTarget());
                move.getCastledRook().setHasMoved(false);
            } else {
                // Undo normal move
                if (move.getIsCapture()) {
                    move.getCapturedPiece().unCapture();
                    chessBoard[move.getCapturedPiece().getX()][move.getCapturedPiece().getY()] = move.getCapturedPiece();
                } else {
                    chessBoard[move.getxTarget()][move.getyTarget()] = null;
                }

                chessBoard[move.getxStart()][move.getyStart()] = move.getPiece();
                move.getPiece().setX(move.getxStart());
                move.getPiece().setY(move.getyStart());
            }
        }
    }

    /**
     * Checks if a given square is under attack.
     * @param x The x-coordinate of the square.
     * @param y The y-coordinate of the square.
     * @param player The player being checked.
     * @return True if the square is attacked, otherwise false.
     */
    public boolean spotAttacked(int x, int y, Player player) {
        Piece pieceUnderAttack = this.getPiece(x, y);
        boolean captured = pieceUnderAttack != null;

        for (Piece piece : player.getEnemyPiecesAlive()) {
            if (!(piece instanceof King)) {
                if (piece.getLegalMoves(this).contains(new Move(x, y, piece, captured, pieceUnderAttack))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given player is in check.
     * @param player The player to check.
     * @return True if the player's king is under attack, otherwise false.
     */
    public boolean isCheck(Player player) {
        return this.spotAttacked(player.getKing().getX(), player.getKing().getY(), player);
    }

    /**
     * Determines if the given player is in checkmate.
     * @param player The player to check.
     * @return True if the player has no legal moves and is in check, otherwise false.
     */
    public boolean isCheckMate(Player player) {
        for (Piece piece : player.getPiecesAlive()) {
            for (Move move : piece.getLegalMoves(this)) {
                this.movePiece(move);
                if (!isCheck(player)) {
                    this.undoMovePiece();
                    return false;
                }
                this.undoMovePiece();
            }
        }
        return true;
    }

    /**
     * Checks if the given coordinates are outside the chessboard boundaries.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are out of bounds, otherwise false.
     */
    public boolean outOfBounds(int x, int y) {
        return x < 0 || x > 7 || y < 0 || y > 7;
    }

    /**
     * Displays the current chessboard state in the console.
     */
    public void displayBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[j][i] == null) {
                    System.out.print(". ");
                } else {
                    // Display piece based on its type and color
                    if (chessBoard[j][i].getColor().equals("Black")) {
                        System.out.print(switch (chessBoard[j][i].getClass().getSimpleName()) {
                            case "Knight" -> "n ";
                            case "Pawn" -> "p ";
                            case "Rook" -> "r ";
                            case "Bishop" -> "b ";
                            case "Queen" -> "q ";
                            case "King" -> "k ";
                            default -> "? ";
                        });
                    } else {
                        System.out.print(switch (chessBoard[j][i].getClass().getSimpleName()) {
                            case "Knight" -> "N ";
                            case "Pawn" -> "P ";
                            case "Rook" -> "R ";
                            case "Bishop" -> "B ";
                            case "Queen" -> "Q ";
                            case "King" -> "K ";
                            default -> "? ";
                        });
                    }
                }
            }
            System.out.println();
        }
    }
}
