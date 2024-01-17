import java.util.*;

public class Game {
    private ChessBoard chessBoard;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private boolean gameOn = true;
    Scanner scanner = new Scanner(System.in);
    public Game() {
        chessBoard = new ChessBoard();
        gameLoop();
    }
    public void gameLoop() {
        initializeGame();;

        while (gameOn) {

            chessBoard.displayBoard();
            setCurrentPlayer();

            Piece pieceToMove;
            Move newMove = null;

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
                Piece capturedPiece = chessBoard.getPiece(xEnd, yEnd);
                newMove = new Move(xEnd, yEnd, pieceToMove, isCapture, capturedPiece);
                List<Move> legalMoves = pieceToMove.getLegalMoves(chessBoard);

                if(legalMoves.contains(newMove)){
                    chessBoard.movePiece(newMove);
                    if (isCheck(currentPlayer.getColor())){
                        chessBoard.undoMovePiece();
                        System.out.println("That move puts you in check.");
                    }
                    else {
                        break;
                    }
                }
                else{
                    System.out.println("That is not a legal move.");
                }

            }while(true);

            if(isCheckMate(currentPlayer.getColor())){
                System.out.println("Game over, checkmate " + currentPlayer.getColor() + " won");
                gameOn = false;
            }

            chessBoard.displayBoard();
            switchTurns();
        }
    }
    public void setCurrentPlayer() {
        if (blackPlayer.getIsTurn()) {
            currentPlayer = blackPlayer;
            System.out.println("Black's turn.");
        }
        else {
            currentPlayer = whitePlayer;
            System.out.println("White's turn.");
        }
    }
    public boolean isCheck(String color){
        if (Objects.equals(color, "Black")){
            int blackPlayerX = blackPlayer.getKing().getX();
            int blackPlayerY = blackPlayer.getKing().getY();
            for (Piece piece : whitePlayer.getPiecesAlive()) {
                if (piece.getLegalMoves(chessBoard).contains(new Move(blackPlayerX, blackPlayerY, piece, true, blackPlayer.getKing()))) {
                    return true;
                }
            }
        }
        else {
            int whitePlayerX = whitePlayer.getKing().getX();
            int whitePlayerY = whitePlayer.getKing().getY();
            for (Piece piece : blackPlayer.getPiecesAlive()) {
                if (piece.getLegalMoves(chessBoard).contains(new Move(whitePlayerX, whitePlayerY, piece, true, whitePlayer.getKing()))) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCheckMate(String color){
        if (Objects.equals(color, "White")){
            for (Piece piece : blackPlayer.getPiecesAlive()) {
                for (Move move : piece.getLegalMoves(chessBoard)) {
                    chessBoard.movePiece(move);
                    if (!isCheck("Black")) {
                        move.printInfo();
                        chessBoard.undoMovePiece();
                        return false;
                    }
                    chessBoard.undoMovePiece();
                }
            }
        }
        else {
            for (Piece piece : whitePlayer.getPiecesAlive()) {
                for (Move move : piece.getLegalMoves(chessBoard)) {
                    chessBoard.movePiece(move);
                    if (!isCheck("White")) {
                        move.printInfo();
                        chessBoard.undoMovePiece();
                        return false;
                    }
                    chessBoard.undoMovePiece();
                }
            }
        }
        return true;
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

        blackPlayer = new Player("Black", chessBoard.getPiece(4, 0));
        whitePlayer = new Player("White", chessBoard.getPiece(4, 7));

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

        whitePlayer.setIsTurn(true);
    }

}
