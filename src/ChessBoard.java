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
                if(chessBoard[i][j] == null){
                    System.out.print(". ");
                }
                else{
                    if (chessBoard[i][j].getColor().equals("Black")) {
                        if(chessBoard[i][j] instanceof Knight){
                            System.out.print("n ");
                        }
                        else if(chessBoard[i][j] instanceof Pawn){
                            System.out.print("p ");
                        }
                        else if(chessBoard[i][j] instanceof Rook){
                            System.out.print("r ");
                        }
                        else if(chessBoard[i][j] instanceof Bishop){
                            System.out.print("b ");
                        }
                        else if(chessBoard[i][j] instanceof Queen){
                            System.out.print("q ");
                        }
                        else if(chessBoard[i][j] instanceof King){
                            System.out.print("k ");
                        }
                    }
                    else{
                        if(chessBoard[i][j] instanceof Knight){
                            System.out.print("N ");
                        }
                        else if(chessBoard[i][j] instanceof Pawn){
                            System.out.print("P ");
                        }
                        else if(chessBoard[i][j] instanceof Rook){
                            System.out.print("R ");
                        }
                        else if(chessBoard[i][j] instanceof Bishop){
                            System.out.print("B ");
                        }
                        else if(chessBoard[i][j] instanceof Queen){
                            System.out.print("Q ");
                        }
                        else if(chessBoard[i][j] instanceof King){
                            System.out.print("K ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
