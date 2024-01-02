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

            boolean unvalidMove = false;

            do{
                System.out.println("Enter the x coordinate of the piece you want to move: ");
                int x = scanner.nextInt();
                System.out.println("Enter the y coordinate of the piece you want to move: ");
                int y = scanner.nextInt();

                if (chessBoard.getPiece(x, y) == null) {
                    System.out.println("There is no piece at that location.");
                    unvalidMove = true;
                }
                else if (!currentPlayer.getColor().equals(chessBoard.getPiece(x, y).getColor())) {
                    System.out.println("That is not your piece.");
                    unvalidMove = true;
                }
            }while(unvalidMove);


            gameOn = false;
        }
    }
    public void initializeGame(){
        whitePlayer.setIsTurn(true);

        chessBoard.setPiece(new Piece("Rook", "Black", 0, 0), 0, 0);
        chessBoard.setPiece(new Piece("Knight", "Black", 0, 1), 0, 1);
        chessBoard.setPiece(new Piece("Bishop", "Black", 0, 2), 0, 2);
        chessBoard.setPiece(new Piece("Queen", "Black", 0, 3), 0, 3);
        chessBoard.setPiece(new Piece("King", "Black", 0, 4), 0, 4);
        chessBoard.setPiece(new Piece("Bishop", "Black", 0, 5), 0, 5);
        chessBoard.setPiece(new Piece("Knight", "Black", 0, 6), 0, 6);
        chessBoard.setPiece(new Piece("Rook", "Black", 0, 7), 0, 7);
        for(int i = 0; i < 8; i++){
            chessBoard.setPiece(new Piece("Pawn", "Black", 1, i), 1, i);
        }
        for(int i = 0; i < 8; i++){
            chessBoard.setPiece(new Piece("Pawn", "White", 6, i), 6, i);
        }
        chessBoard.setPiece(new Piece("Rook", "White", 7, 0), 7, 0);
        chessBoard.setPiece(new Piece("Knight", "White", 7, 1), 7, 1);
        chessBoard.setPiece(new Piece("Bishop", "White", 7, 2), 7, 2);
        chessBoard.setPiece(new Piece("Queen", "White", 7, 3), 7, 3);
        chessBoard.setPiece(new Piece("King", "White", 7, 4), 7, 4);
        chessBoard.setPiece(new Piece("Bishop", "White", 7, 5), 7, 5);
        chessBoard.setPiece(new Piece("Knight", "White", 7, 6), 7, 6);
        chessBoard.setPiece(new Piece("Rook", "White", 7, 7), 7, 7);
        for(int i = 0; i < 8; i++){
            blackPlayer.addPiece(chessBoard.getPiece(0, i));
        }
        for(int i = 0; i < 8; i++){
            blackPlayer.addPiece(chessBoard.getPiece(1, i));
        }
        for(int i = 0; i < 8; i++){
            whitePlayer.addPiece(chessBoard.getPiece(6, i));
        }
        for(int i = 0; i < 8; i++){
            whitePlayer.addPiece(chessBoard.getPiece(7, i));
        }
    }

}
