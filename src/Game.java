import java.util.*;

public class Game {
    private final ChessBoard chessBoard;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private Player otherPlayer;
    private boolean gameOn = true;
    Scanner scanner = new Scanner(System.in);
    public Game() {
        chessBoard = new ChessBoard();
        initializeGame();
        //gameLoop();
    }
    public void gameLoop() {
        initializeGame();;

        while (gameOn) {
            setCurrentPlayer();

            Piece pieceToMove = choosePieceToMove();
            chooseWhereToMove(pieceToMove);

            pieceToMove.setHasMoved(true);
            if (pieceToMove instanceof Pawn && (pieceToMove.getY() == 7 || pieceToMove.getY() == 0)) {
                PawnPromotion(pieceToMove);
            }

            chessBoard.displayBoard();
            gameOn = gameOverCondition();

            switchTurns();
        }
    }
    public Piece choosePieceToMove(){
        Piece pieceToMove;
        do{
            System.out.print("Enter the x coordinate of the piece you want to move: ");
            int xStart = scanner.nextInt();
            System.out.print("Enter the y coordinate of the piece you want to move: ");
            int yStart = scanner.nextInt();;

            if(validPiece(xStart, yStart)){
                pieceToMove = chessBoard.getPiece(xStart, yStart);
                return pieceToMove;
            }
        }while(true);
    }
    public void chooseWhereToMove(Piece pieceToMove){
        do{
            System.out.print("Enter the x coordinate where you want to move: ");
            int xEnd = scanner.nextInt();
            System.out.print("Enter the y coordinate where you want to move: ");
            int yEnd = scanner.nextInt();

            if (chessBoard.outOfBounds(xEnd, yEnd)) {
                System.out.println("That is not a valid location.");
                continue;
            }

            boolean isCapture = chessBoard.getPiece(xEnd, yEnd) != null;
            Piece capturedPiece = chessBoard.getPiece(xEnd, yEnd);
            Move newMove = new Move(xEnd, yEnd, pieceToMove, isCapture, capturedPiece);
            List<Move> legalMoves = pieceToMove.getLegalMoves(chessBoard);

            if(legalMoves.contains(newMove)){
                int id = legalMoves.indexOf(newMove);
                chessBoard.movePiece(legalMoves.get(id));
                if (chessBoard.isCheck(currentPlayer)){
                    chessBoard.undoMovePiece();
                    System.out.println("That move puts you in check.");
                    pieceToMove = choosePieceToMove();
                }
                else {
                    break;
                }
            }
            else{
                System.out.println("That is not a legal move.");
            }

        }while(true);
    }
    public void PawnPromotion(Piece pieceToMove){
        System.out.println("Your pawn has reached the end of the board. Enter the piece you want to promote to.");
        System.out.println("1. Queen");
        System.out.println("2. Rook");
        System.out.println("3. Bishop");
        System.out.println("4. Knight");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        Piece newPiece = switch (choice) {
            case 1 -> new Queen(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            case 2 -> new Rook(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            case 3 -> new Bishop(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            case 4 -> new Knight(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            default -> new Queen(currentPlayer, pieceToMove.getX(), pieceToMove.getY()); // Domyślnie wybieramy królową
        };

        chessBoard.setPiece(newPiece);
        newPiece.setHasMoved(true);
        currentPlayer.addPiece(newPiece);
        otherPlayer.addEnemyPiece(newPiece);

        currentPlayer.getPiecesAlive().remove(pieceToMove);
        otherPlayer.getEnemyPiecesAlive().remove(pieceToMove);
    }
    public boolean gameOverCondition(){
        boolean isOtherPlayerInCheck = chessBoard.isCheck(otherPlayer);
        boolean isOtherPlayerCheckmated = chessBoard.isCheckMate(otherPlayer);

        if (isOtherPlayerInCheck && isOtherPlayerCheckmated) {
            System.out.println("Checkmate, " + currentPlayer.getColor() + " wins!");
            return true;
        } else if (!isOtherPlayerInCheck && isOtherPlayerCheckmated) {
            System.out.println("Stalemate.");
            return true;
        }
        return false;
    }

    public void setCurrentPlayer() {
        if (blackPlayer.getIsTurn()) {
            currentPlayer = blackPlayer;
            otherPlayer = whitePlayer;
            System.out.println("Black's turn.");
        }
        else {
            currentPlayer = whitePlayer;
            otherPlayer = blackPlayer;
            System.out.println("White's turn.");
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
        if (currentPlayer == whitePlayer) {
            whitePlayer.setIsTurn(false);
            blackPlayer.setIsTurn(true);
            currentPlayer = blackPlayer;
            otherPlayer = whitePlayer;
        } else {
            blackPlayer.setIsTurn(false);
            whitePlayer.setIsTurn(true);
            currentPlayer = whitePlayer;
            otherPlayer = blackPlayer;
        }
        //System.out.println("Teraz ruch ma: " + currentPlayer.getColor());
    }

    public void initializeGame(){
        blackPlayer = new Player("Black");
        whitePlayer = new Player("White");
        chessBoard.setPiece(new Rook(blackPlayer, 0, 0));
        chessBoard.setPiece(new Knight(blackPlayer,1, 0));
        chessBoard.setPiece(new Bishop(blackPlayer, 2, 0));
        chessBoard.setPiece(new Queen(blackPlayer, 3, 0));
        chessBoard.setPiece(new King(blackPlayer, 4, 0));
        chessBoard.setPiece(new Bishop(blackPlayer, 5, 0));
        chessBoard.setPiece(new Knight(blackPlayer, 6, 0));
        chessBoard.setPiece(new Rook(blackPlayer, 7, 0));
        for(int i = 0; i < 8; i++){
            chessBoard.setPiece(new Pawn(blackPlayer, i, 1));
        }
        for(int i = 0; i < 8; i++){
            chessBoard.setPiece(new Pawn(whitePlayer, i, 6));
        }
        chessBoard.setPiece(new Rook(whitePlayer, 0, 7));
        chessBoard.setPiece(new Knight(whitePlayer, 1, 7));
        chessBoard.setPiece(new Bishop(whitePlayer, 2, 7));
        chessBoard.setPiece(new Queen(whitePlayer, 3, 7));
        chessBoard.setPiece(new King(whitePlayer, 4, 7));
        chessBoard.setPiece(new Bishop(whitePlayer, 5, 7));
        chessBoard.setPiece(new Knight(whitePlayer, 6, 7));
        chessBoard.setPiece(new Rook(whitePlayer, 7, 7));
        for(int i = 0; i < 8; i++){
            blackPlayer.addPiece(chessBoard.getPiece(i, 0));
            whitePlayer.addEnemyPiece(chessBoard.getPiece(i, 0));
        }
        for(int i = 0; i < 8; i++){
            blackPlayer.addPiece(chessBoard.getPiece(i, 1));
            whitePlayer.addEnemyPiece(chessBoard.getPiece(i, 1));
        }
        for(int i = 0; i < 8; i++){
            whitePlayer.addPiece(chessBoard.getPiece(i, 6));
            blackPlayer.addEnemyPiece(chessBoard.getPiece(i, 6));
        }
        for(int i = 0; i < 8; i++){
            whitePlayer.addPiece(chessBoard.getPiece(i, 7));
            blackPlayer.addEnemyPiece(chessBoard.getPiece(i, 7));
        }
        blackPlayer.setKing(chessBoard.getPiece(4, 0));
        whitePlayer.setKing(chessBoard.getPiece(4, 7));

        whitePlayer.setIsTurn(true);
        //chessBoard.displayBoard();

        currentPlayer = whitePlayer;
        otherPlayer = blackPlayer;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer(){
        return otherPlayer;
    }

}
