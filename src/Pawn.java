import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(String color, int x, int y) {
        super(color, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard){
        List<Move> moves = new ArrayList<>();
        int x = this.getX();
        int y = this.getY();
        if (this.getColor().equals("Black")) {
            if (y == 1 && chessBoard.getPiece(x, y + 1) == null){
                addLegalMove(moves, x, y + 2, chessBoard);
            }
            addLegalMove(moves, x, y + 1, chessBoard);
            addLegalAttack(moves, x + 1, y + 1, chessBoard);
            addLegalAttack(moves, x - 1, y + 1, chessBoard);
        }
        else {
            if (y == 6) {
                addLegalMove(moves, x, y - 2, chessBoard);
            }
            addLegalMove(moves, x, y - 1, chessBoard);
            addLegalAttack(moves, x + 1, y - 1, chessBoard);
            addLegalAttack(moves, x - 1, y - 1, chessBoard);
        }
        return moves;
    }
    public void addLegalMove(List<Move> moves, int x, int y, ChessBoard chessBoard){
        if(!chessBoard.outOfBounds(x, y)){
            if(chessBoard.getPiece(x, y) == null){
                moves.add(new Move(x, y, this, false));
            }
        }
    }
    public void addLegalAttack(List<Move> moves, int x, int y, ChessBoard chessBoard){
        if (!chessBoard.outOfBounds(x, y)) {
            if(chessBoard.getPiece(x, y) != null){
                if (!chessBoard.getPiece(x, y).getColor().equals(this.getColor())) {
                    moves.add(new Move(x, y, this, true));
                }
            }
        }
    }
}
