import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(Player player, int x, int y) {
        super(player, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();

        int[] xMoves = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] yMoves = {1, 0, -1, 1, -1, 1, 0, -1};

        for(int i = 0; i < xMoves.length; i++){
            int x = this.x + xMoves[i];
            int y = this.y + yMoves[i];
            addMoveIfLegal(moves, x, y, chessBoard);
        }
        if (!this.hasMoved){
            if (leftCastle(chessBoard)){
                Rook rook = (Rook) chessBoard.getPiece(0, this.y);
                moves.add(new Move(0, this.y, this, false, null, true, rook));
            }
            if (rightCastle(chessBoard)){
                Rook rook = (Rook) chessBoard.getPiece(7, this.y);
                moves.add(new Move(7, this.y, this, false, null, true, rook));
            }
        }
        return moves;
    }
    public boolean leftCastle(ChessBoard chessBoard) {
        if (chessBoard.getPiece(0, this.y) instanceof Rook && !chessBoard.getPiece(0, y).getHasMoved()){
            for (int i = 0; i < 4; i++) {
                if (!castleValidSpot(chessBoard, i, this.y)) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }

    }
    public boolean rightCastle(ChessBoard chessBoard){
        if (chessBoard.getPiece(7, this.y) instanceof Rook && !chessBoard.getPiece(7, y).getHasMoved()){
            for (int i = 5; i < 7; i++) {
                if (!castleValidSpot(chessBoard, i, this.y)) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
    public boolean castleValidSpot(ChessBoard chessBoard, int x, int y){
        if(chessBoard.getPiece(x, y) == null){
            for (Piece piece : this.player.getEnemyPiecesAlive()) {
                if (!(piece instanceof King)){
                    if (piece.getLegalMoves(chessBoard).contains(new Move(x, y, piece, false, null))) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
