public class ChessBoard {
    public Piece[][] chessBoard;
    public ChessBoard() {
        chessBoard = new Piece[8][8];
    }
    public void initializeGame(){
        chessBoard[0][0] = new Piece("Rook", "Black", 0, 0);
        chessBoard[0][1] = new Piece("Knight", "Black", 0, 1);
        chessBoard[0][2] = new Piece("Bishop", "Black", 0, 2);
        chessBoard[0][3] = new Piece("Queen", "Black", 0, 3);
        chessBoard[0][4] = new Piece("King", "Black", 0, 4);
        chessBoard[0][5] = new Piece("Bishop", "Black", 0, 5);
        chessBoard[0][6] = new Piece("Knight", "Black", 0, 6);
        chessBoard[0][7] = new Piece("Rook", "Black", 0, 7);
        for(int i = 0; i < 8; i++){
            chessBoard[1][i] = new Piece("Pawn", "Black", 1, i);
        }
        for(int i = 0; i < 8; i++){
            chessBoard[6][i] = new Piece("Pawn", "White", 6, i);
        }
        chessBoard[7][0] = new Piece("Rook", "White", 7, 0);
        chessBoard[7][1] = new Piece("Knight", "White", 7, 1);
        chessBoard[7][2] = new Piece("Bishop", "White", 7, 2);
        chessBoard[7][3] = new Piece("Queen", "White", 7, 3);
        chessBoard[7][4] = new Piece("King", "White", 7, 4);
        chessBoard[7][5] = new Piece("Bishop", "White", 7, 5);
        chessBoard[7][6] = new Piece("Knight", "White", 7, 6);
        chessBoard[7][7] = new Piece("Rook", "White", 7, 7);
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
