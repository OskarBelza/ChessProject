import java.util.Scanner;

public class Game {
    private ChessBoard chessBoard;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private boolean gameOn = true;
    Scanner scanner = new Scanner(System.in);
    public Game() {
        chessBoard = new ChessBoard();
        blackPlayer = new Player("Black");
        whitePlayer = new Player("White");
    }
    public void gameLoop() {
        initializeGame();;

        while (gameOn) {
            chessBoard.displayBoard();

            if (blackPlayer.getIsTurn()) {
                currentPlayer = blackPlayer;
                System.out.println("Black's turn.");
            }
            else {
                currentPlayer = whitePlayer;
                System.out.println("White's turn.");
            }

            int x;
            int y;
            do{
                System.out.println("Enter the x coordinate of the piece you want to move: ");
                x = scanner.nextInt();
                System.out.println("Enter the y coordinate of the piece you want to move: ");
                y = scanner.nextInt();

                Piece pieceToMove = chessBoard.getPiece(x, y);
            }while(unvalidPiece(x, y));
            chessBoard.setPiece(null, 3,6);
            for(Move move : chessBoard.getPiece(x, y).getLegalMoves(chessBoard)){
                move.printInfo();
            }

            switchTurns();
            gameOn = false;
        }
    }
    public boolean unvalidPiece(int x, int y){
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            System.out.println("That is not a valid location.");
            return true;
        }
        else if (chessBoard.getPiece(x, y) == null) {
            System.out.println("There is no piece at that location.");
            return true;
        }
        else if (!currentPlayer.getColor().equals(chessBoard.getPiece(x, y).getColor())) {
            System.out.println("That is not your piece.");
            return true;
        }
        else{
            return false;
        }
    }
    public void switchTurns(){
        if (blackPlayer.getIsTurn()) {
            blackPlayer.setIsTurn(false);
            whitePlayer.setIsTurn(true);
        }
        else {
            blackPlayer.setIsTurn(true);
            whitePlayer.setIsTurn(false);
        }
    }
    public void initializeGame(){
        whitePlayer.setIsTurn(true);

        chessBoard.setPiece(new Rook("Black", 0, 0), 0, 0);
        chessBoard.setPiece(new Knight("Black", 1, 0), 1, 0);
        chessBoard.setPiece(new Bishop("Black", 2, 0), 2, 0);
        chessBoard.setPiece(new Queen("Black", 3, 0), 3, 0);
        chessBoard.setPiece(new King("Black", 4, 0), 4, 0);
        chessBoard.setPiece(new Bishop("Black", 5, 0), 5, 0);
        chessBoard.setPiece(new Knight("Black", 6, 0), 6, 0);
        chessBoard.setPiece(new Rook("Black", 7, 0), 7, 0);
        for(int i = 0; i < 8; i++){
            chessBoard.setPiece(new Pawn("Black", i, 1), i, 1);
        }
        for(int i = 0; i < 8; i++){
            chessBoard.setPiece(new Pawn("White", i, 6), i, 6);
        }
        chessBoard.setPiece(new Rook("White", 0, 7), 0, 7);
        chessBoard.setPiece(new Knight("White", 1, 7), 1, 7);
        chessBoard.setPiece(new Bishop("White", 2, 7), 2, 7);
        chessBoard.setPiece(new Queen("White", 3, 7), 3, 7);
        chessBoard.setPiece(new King("White", 4, 7), 4, 7);
        chessBoard.setPiece(new Bishop("White", 5, 7), 5, 7);
        chessBoard.setPiece(new Knight("White", 6, 7), 6, 7);
        chessBoard.setPiece(new Rook("White", 7, 7), 7, 7);
        for(int i = 0; i < 8; i++){
            blackPlayer.addPiece(chessBoard.getPiece(i, 0));
        }
        for(int i = 0; i < 8; i++){
            blackPlayer.addPiece(chessBoard.getPiece(i, 1));
        }
        for(int i = 0; i < 8; i++){
            whitePlayer.addPiece(chessBoard.getPiece(i, 6));
        }
        for(int i = 0; i < 8; i++){
            whitePlayer.addPiece(chessBoard.getPiece(i, 7));
        }
    }

}
