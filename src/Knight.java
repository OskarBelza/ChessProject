import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(String color, int x, int y) {
        super(color, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();

        int[] xMoves = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] yMoves = {2, 1, -1, -2, -2, -1, 1, 2};

        for(int i = 0; i < xMoves.length; i++){
            int x = this.getX() + xMoves[i];
            int y = this.getY() + yMoves[i];
            addMoveIfLegal(moves, x, y, chessBoard);
        }

        return moves;
    }
}
