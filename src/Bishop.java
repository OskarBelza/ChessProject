import java.util.ArrayList;
import java.util.List;
public class Bishop extends Piece{
    public Bishop(String color, int x, int y) {
        super(color, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getDiagonalMoves(chessBoard));
    }
}
