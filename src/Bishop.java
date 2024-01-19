import java.util.ArrayList;
import java.util.List;
public class Bishop extends Piece{
    public Bishop(Player player, int x, int y) {
        super(player, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getDiagonalMoves(chessBoard));
    }
}
