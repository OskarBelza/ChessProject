public class ChessBoard {
    public Piece[][] chessBoard;
    public ChessBoard() {
        chessBoard = new Piece[8][8];
    }
    public void setPiece(Piece piece, int x, int y) {
        chessBoard[x][y] = piece;
    }
    public Piece getPiece(int x, int y) {
        return chessBoard[x][y];
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
