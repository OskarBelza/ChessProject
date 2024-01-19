import java.util.ArrayList;
import java.util.List;
public class Rook extends Piece{
    private boolean hasMoved = false;
    public Rook(Player player, int x, int y) {
        super(player, x, y);
    }
    public boolean getHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        return new ArrayList<>(getStraightMoves(chessBoard));
    }
}
