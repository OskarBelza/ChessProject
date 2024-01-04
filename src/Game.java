import java.util.ArrayList;
import java.util.List;
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

            Piece pieceToMove;
            Move newMove = null;
            chessBoard.setPiece(null,3,6);

            do{
                System.out.println("Enter the x coordinate of the piece you want to move: ");
                int xStart = scanner.nextInt();
                System.out.println("Enter the y coordinate of the piece you want to move: ");
                int yStart = scanner.nextInt();;

                if(validPiece(xStart, yStart)){
                    pieceToMove = chessBoard.getPiece(xStart, yStart);
                    break;
                }
            }while(true);

            do{
                System.out.println("Enter the x coordinate where you want to move: ");
                int xEnd = scanner.nextInt();
                System.out.println("Enter the y coordinate where you want to move: ");
                int yEnd = scanner.nextInt();

                if (chessBoard.outOfBounds(xEnd, yEnd)) {
                    System.out.println("That is not a valid location.");
                    continue;
                }

                boolean isCapture = chessBoard.getPiece(xEnd, yEnd) != null;
                newMove = new Move(xEnd, yEnd, pieceToMove, isCapture);
                List<Move> legalMoves = pieceToMove.getLegalMoves(chessBoard);

                if(legalMoves.contains(newMove)){
                    break;
                }
                else{
                    System.out.println("That is not a legal move.");
                }

            }while(true);

            chessBoard.movePiece(pieceToMove, newMove);
            chessBoard.displayBoard();

            switchTurns();
            gameOn = false;
        }
    }
    public boolean validPiece(int x, int y){
        if (chessBoard.outOfBounds(x, y)) {
            System.out.println("That is not a valid location.");
            return false;
        }
        else if (chessBoard.getPiece(x, y) == null) {
            System.out.println("There is no piece at that location.");
            return false;
        }
        else if (!currentPlayer.getColor().equals(chessBoard.getPiece(x, y).getColor())) {
            System.out.println("That is not your piece.");
            return false;
        }
        else if(chessBoard.getPiece(x, y).getLegalMoves(chessBoard).isEmpty()){
            System.out.println("That piece has no legal moves.");
            return false;
        }
        else{
            return true;
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
