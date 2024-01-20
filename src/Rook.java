import java.util.ArrayList;
import java.util.List;
public class Rook extends Piece{
    public Rook(Player player, int x, int y) {
        super(player, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getStraightMoves(chessBoard));
    }
}
