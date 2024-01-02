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
                        if (chessBoard[i][j].getName().equals("Knight")) {
                            System.out.print("n ");
                        }
                        else {
                            System.out.print(Character.toLowerCase(chessBoard[i][j].getName().charAt(0)) + " ");
                        }
                    }
                    else{
                        if (chessBoard[i][j].getName().equals("Knight")) {
                            System.out.print("N ");
                        }
                        else{
                            System.out.print(chessBoard[i][j].getName().charAt(0) + " ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
