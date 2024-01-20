import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(Player player, int x, int y) {
        super(player, x, y);
    }
    @Override
    public List<Move> getLegalMoves(ChessBoard chessBoard){
        List<Move> moves = new ArrayList<>();
        Piece lastPieceToMove = null;
        if (!chessBoard.moveHistory.isEmpty()) {
            lastPieceToMove = chessBoard.moveHistory.peek().getPiece();
        }
        if (this.getColor().equals("Black")) {
            if (y == 1 && chessBoard.getPiece(x, y + 1) == null){
                addLegalMove(moves, x, y + 2, chessBoard);
            }
            addLegalMove(moves, x, y + 1, chessBoard);
            addLegalAttack(moves, x + 1, y + 1, chessBoard);
            addLegalAttack(moves, x - 1, y + 1, chessBoard);
            addEnPassant(moves, chessBoard, "Black", lastPieceToMove);

        }
        else {
            if (y == 6) {
                addLegalMove(moves, x, y - 2, chessBoard);
            }
            addLegalMove(moves, x, y - 1, chessBoard);
            addLegalAttack(moves, x + 1, y - 1, chessBoard);
            addLegalAttack(moves, x - 1, y - 1, chessBoard);
            addEnPassant(moves, chessBoard, "White", lastPieceToMove);
        }
        return moves;
    }
    public void addLegalMove(List<Move> moves, int x, int y, ChessBoard chessBoard){
        if(!chessBoard.outOfBounds(x, y)){
            if(chessBoard.getPiece(x, y) == null){
                moves.add(new Move(x, y, this, false, null));
            }
        }
    }
    public void addLegalAttack(List<Move> moves, int x, int y, ChessBoard chessBoard){
        if (!chessBoard.outOfBounds(x, y)) {
            if(chessBoard.getPiece(x, y) != null){
                if (!chessBoard.getPiece(x, y).getColor().equals(this.getColor())) {
                    moves.add(new Move(x, y, this, true, chessBoard.getPiece(x, y)));
                }
            }
        }
    }
    public void addEnPassant(List<Move> moves, ChessBoard chessBoard, String color, Piece lastPieceToMove){
        if(color.equals("Black")){
            if(y == 4 && lastPieceToMove.getClass().getSimpleName().equals("Pawn") && lastPieceToMove.getColor().equals("White")){
                if(chessBoard.moveHistory.peek().getyTarget() == 4 && chessBoard.moveHistory.peek().getyStart() == 6) {
                    if(Math.abs(chessBoard.moveHistory.peek().getxTarget() - x) == 1) {
                        moves.add(new Move(chessBoard.moveHistory.peek().getxTarget(), 5, this, true, lastPieceToMove));
                    }
                }
            }
        }
        else{
            if(y == 3 && lastPieceToMove.getClass().getSimpleName().equals("Pawn") && lastPieceToMove.getColor().equals("Black")){
                if(chessBoard.moveHistory.peek().getyTarget() == 3 && chessBoard.moveHistory.peek().getyStart() == 1) {
                    if(Math.abs(lastPieceToMove.getX() - x) == 1) {
                        moves.add(new Move(chessBoard.moveHistory.peek().getxTarget(), 2, this, true, lastPieceToMove));
                    }
                }
            }
        }
    }
}
