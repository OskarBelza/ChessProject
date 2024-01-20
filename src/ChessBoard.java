import java.util.Stack;

public class ChessBoard {
    public Piece[][] chessBoard;
    public Stack<Move> moveHistory;
    public ChessBoard() {
        chessBoard = new Piece[8][8];
        moveHistory = new Stack<>();
    }
    public void setPiece(Piece piece) {
        chessBoard[piece.getX()][piece.getY()] = piece;
    }
    public Piece getPiece(int x, int y) {
        return chessBoard[x][y];
    }
    public void movePiece(Move move) {
        if(move.getIsCastle())
        {
            chessBoard[move.getxStart()][move.getyStart()] = move.getCastledRook();
            chessBoard[move.getxTarget()][move.getyTarget()] = move.getPiece();
            move.getPiece().setX(move.getxTarget());
            move.getPiece().setY(move.getyTarget());
            move.getCastledRook().setX(move.getxStart());
            move.getCastledRook().setY(move.getyStart());
            moveHistory.push(move);
        }
        else{
            if(move.getIsCapture()){
                move.getCapturedPiece().capture();
                chessBoard[move.getCapturedPiece().getX()][move.getCapturedPiece().getY()] = null;
            }
            chessBoard[move.getxStart()][move.getyStart()] = null;
            chessBoard[move.getxTarget()][move.getyTarget()] = move.getPiece();
            move.getPiece().setX(move.getxTarget());
            move.getPiece().setY(move.getyTarget());
            moveHistory.push(move);
        }
    }
    public void undoMovePiece() {
        if(!moveHistory.isEmpty()) {
            Move move = moveHistory.pop();
            if (move.getIsCastle()) {
                chessBoard[move.getxStart()][move.getyStart()] = move.getPiece();
                chessBoard[move.getxTarget()][move.getyTarget()] = move.getCastledRook();
                move.getPiece().setX(move.getxStart());
                move.getPiece().setY(move.getyStart());
                move.getCastledRook().setX(move.getxTarget());
                move.getCastledRook().setY(move.getyTarget());
            } else {
                if (move.getIsCapture()) {
                    move.getCapturedPiece().unCapture();
                    chessBoard[move.getCapturedPiece().getX()][move.getCapturedPiece().getY()] = move.getCapturedPiece();
                } else {
                    chessBoard[move.getxTarget()][move.getyTarget()] = null;
                }
                chessBoard[move.getxStart()][move.getyStart()] = move.getPiece();
                move.getPiece().setX(move.getxStart());
                move.getPiece().setY(move.getyStart());
            }
        }
    }
    public boolean spotAttacked(int x, int y, Player player){
        Piece pieceUnderAttack = this.getPiece(x, y);
        boolean captured = pieceUnderAttack != null;
        for(Piece piece : player.getEnemyPiecesAlive()){
            if (!(piece instanceof King)){
                if (piece.getLegalMoves(this).contains(new Move(x, y, piece, captured, pieceUnderAttack))){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCheck(Player player){
        return this.spotAttacked(player.getKing().getX(), player.getKing().getY(), player);
    }
    public boolean isCheckMate(Player player){
        for (Piece piece : player.getPiecesAlive()) {
            for (Move move : piece.getLegalMoves(this)) {
                this.movePiece(move);
                if (!isCheck(player)) {
                    this.undoMovePiece();
                    return false;
                }
                this.undoMovePiece();
            }
        }
        return true;
    }
    public boolean outOfBounds(int x, int y){
        return x < 0 || x > 7 || y < 0 || y > 7;
    }
    public void displayBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(chessBoard[j][i] == null){
                    System.out.print(". ");
                }
                else{
                    if (chessBoard[j][i].getColor().equals("Black")) {
                        if(chessBoard[j][i] instanceof Knight){
                            System.out.print("n ");
                        }
                        else if(chessBoard[j][i] instanceof Pawn){
                            System.out.print("p ");
                        }
                        else if(chessBoard[j][i] instanceof Rook){
                            System.out.print("r ");
                        }
                        else if(chessBoard[j][i] instanceof Bishop){
                            System.out.print("b ");
                        }
                        else if(chessBoard[j][i] instanceof Queen){
                            System.out.print("q ");
                        }
                        else if(chessBoard[j][i] instanceof King){
                            System.out.print("k ");
                        }
                    }
                    else{
                        if(chessBoard[j][i] instanceof Knight){
                            System.out.print("N ");
                        }
                        else if(chessBoard[j][i] instanceof Pawn){
                            System.out.print("P ");
                        }
                        else if(chessBoard[j][i] instanceof Rook){
                            System.out.print("R ");
                        }
                        else if(chessBoard[j][i] instanceof Bishop){
                            System.out.print("B ");
                        }
                        else if(chessBoard[j][i] instanceof Queen){
                            System.out.print("Q ");
                        }
                        else if(chessBoard[j][i] instanceof King){
                            System.out.print("K ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
