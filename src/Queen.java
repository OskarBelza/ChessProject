import java.util.ArrayList;
import java.util.List;
public class Queen extends Piece{
    public Queen(String color, int x, int y) {
        super(color, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getDiagonalMoves(chessBoard));
        moves.addAll(getStraightMoves(chessBoard));
        return moves;
    }
}
