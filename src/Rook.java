import java.util.ArrayList;
import java.util.List;
public class Rook extends Piece{
    public Rook(String color, int x, int y) {
        super(color, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getStraightMoves(chessBoard));
    }
}
