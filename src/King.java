import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(Player player, int x, int y) {
        super(player, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard board) {
        List<Move> moves = new ArrayList<>();

        int[] xMoves = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] yMoves = {1, 0, -1, 1, -1, 1, 0, -1};

        for (int i = 0; i < xMoves.length; i++) {
            int x = this.x + xMoves[i];
            int y = this.y + yMoves[i];
            addMoveIfLegal(moves, x, y, board);
        }

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
    public boolean canCastleLeft(ChessBoard board) {
        if (hasMoved) return false;

        Piece rook = board.getPiece(0, this.y);
        if (!(rook instanceof Rook) || rook.hasMoved()) return false;

        for (int i = 1; i < 4; i++) {
            if (board.getPiece(i, this.y) != null) return false;
            if (board.spotAttacked(i, this.y, this.getPlayer())) return false;
        }

        return true;
    }
    public boolean canCastleRight(ChessBoard board) {
        if (hasMoved) return false;

        Piece rook = board.getPiece(7, this.y);
        if (!(rook instanceof Rook) || rook.hasMoved()) return false;

        for (int i = 5; i < 7; i++) {
            if (board.getPiece(i, this.y) != null) return false;
            if (board.spotAttacked(i, this.y, this.getPlayer())) return false;
        }

        return true;
    }
}
