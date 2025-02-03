import java.util.*;

public class Game {
    private final ChessBoard chessBoard;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private Player otherPlayer;
    private boolean gameOn = true;
    Scanner scanner = new Scanner(System.in);
    /**
     * Initializes a new game instance and sets up the chessboard.
     */
    public Game() {
        chessBoard = new ChessBoard();
        initializeGame();
        // gameLoop(); // Uncomment to enable console-based game loop
    }

    /**
     * Main game loop that continues until the game ends.
     * Players take turns selecting and moving pieces.
     */
    public void gameLoop() {
        initializeGame();

        while (gameOn) {
            setCurrentPlayer();

            Piece pieceToMove = choosePieceToMove();
            chooseWhereToMove(pieceToMove);

            pieceToMove.setHasMoved(true);

            // Check if a pawn has reached the opposite end and promote it
            if (pieceToMove instanceof Pawn && (pieceToMove.getY() == 7 || pieceToMove.getY() == 0)) {
                PawnPromotion(pieceToMove);
            }

            chessBoard.displayBoard();
            gameOn = gameOverCondition();

            switchTurns();
        }
    }

    /**
     * Prompts the player to select a piece to move.
     * Ensures the selected piece belongs to the current player and has legal moves.
     * @return The selected piece to be moved.
     */
    public Piece choosePieceToMove() {
        Piece pieceToMove;
        do {
            System.out.print("Enter the x coordinate of the piece you want to move: ");
            int xStart = scanner.nextInt();
            System.out.print("Enter the y coordinate of the piece you want to move: ");
            int yStart = scanner.nextInt();

            if (validPiece(xStart, yStart)) {
                pieceToMove = chessBoard.getPiece(xStart, yStart);
                return pieceToMove;
            }
        } while (true);
    }

    /**
     * Prompts the player to select a target location for the chosen piece.
     * Ensures the move is legal before execution.
     * @param pieceToMove The piece selected for movement.
     */
    public void chooseWhereToMove(Piece pieceToMove) {
        do {
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

            if (legalMoves.contains(newMove)) {
                int id = legalMoves.indexOf(newMove);
                chessBoard.movePiece(legalMoves.get(id));

                // Prevent moves that put the player in check
                if (chessBoard.isCheck(currentPlayer)) {
                    chessBoard.undoMovePiece();
                    System.out.println("That move puts you in check.");
                    pieceToMove = choosePieceToMove();
                } else {
                    break;
                }
            } else {
                System.out.println("That is not a legal move.");
            }
        } while (true);
    }
    /**
     * Handles pawn promotion when a pawn reaches the last rank.
     * Prompts the player to select a piece for promotion.
     * @param pieceToMove The pawn that is being promoted.
     */
    public void PawnPromotion(Piece pieceToMove) {
        System.out.println("Your pawn has reached the end of the board. Enter the piece you want to promote to.");
        System.out.println("1. Queen");
        System.out.println("2. Rook");
        System.out.println("3. Bishop");
        System.out.println("4. Knight");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        // Assign the new promoted piece based on user choice
        Piece newPiece = switch (choice) {
            case 1 -> new Queen(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            case 2 -> new Rook(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            case 3 -> new Bishop(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            case 4 -> new Knight(currentPlayer, pieceToMove.getX(), pieceToMove.getY());
            default -> new Queen(currentPlayer, pieceToMove.getX(), pieceToMove.getY()); // Default to Queen
        };

        chessBoard.setPiece(newPiece);
        newPiece.setHasMoved(true);
        currentPlayer.addPiece(newPiece);
        otherPlayer.addEnemyPiece(newPiece);

        currentPlayer.getPiecesAlive().remove(pieceToMove);
        otherPlayer.getEnemyPiecesAlive().remove(pieceToMove);
    }

    /**
     * Checks if the game has reached a terminal condition (checkmate or stalemate).
     * @return true if the game is over, false otherwise.
     */
    public boolean gameOverCondition() {
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

    /**
     * Sets the current player based on whose turn it is.
     */
    public void setCurrentPlayer() {
        if (blackPlayer.getIsTurn()) {
            currentPlayer = blackPlayer;
            otherPlayer = whitePlayer;
            System.out.println("Black's turn.");
        } else {
            currentPlayer = whitePlayer;
            otherPlayer = blackPlayer;
            System.out.println("White's turn.");
        }
    }

    /**
     * Validates if a selected piece belongs to the current player and has legal moves.
     * @param x The x-coordinate of the piece.
     * @param y The y-coordinate of the piece.
     * @return true if the piece is valid for movement, false otherwise.
     */
    public boolean validPiece(int x, int y) {
        if (chessBoard.outOfBounds(x, y)) {
            System.out.println("That is not a valid location.");
            return false;
        } else if (chessBoard.getPiece(x, y) == null) {
            System.out.println("There is no piece at that location.");
            return false;
        } else if (!currentPlayer.getColor().equals(chessBoard.getPiece(x, y).getColor())) {
            System.out.println("That is not your piece.");
            return false;
        } else if (chessBoard.getPiece(x, y).getLegalMoves(chessBoard).isEmpty()) {
            System.out.println("That piece has no legal moves.");
            return false;
        } else {
            return true;
        }
    }
    /**
     * Switches turns between players.
     * Updates the current player and their turn status.
     */
    public void switchTurns() {
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
        // System.out.println("Now it's " + currentPlayer.getColor() + "'s turn.");
    }

    /**
     * Initializes the chess game by setting up players and positioning pieces on the board.
     */
    public void initializeGame() {
        blackPlayer = new Player("Black");
        whitePlayer = new Player("White");

        // Place black pieces
        chessBoard.setPiece(new Rook(blackPlayer, 0, 0));
        chessBoard.setPiece(new Knight(blackPlayer, 1, 0));
        chessBoard.setPiece(new Bishop(blackPlayer, 2, 0));
        chessBoard.setPiece(new Queen(blackPlayer, 3, 0));
        chessBoard.setPiece(new King(blackPlayer, 4, 0));
        chessBoard.setPiece(new Bishop(blackPlayer, 5, 0));
        chessBoard.setPiece(new Knight(blackPlayer, 6, 0));
        chessBoard.setPiece(new Rook(blackPlayer, 7, 0));
        for (int i = 0; i < 8; i++) {
            chessBoard.setPiece(new Pawn(blackPlayer, i, 1));
        }

        // Place white pieces
        for (int i = 0; i < 8; i++) {
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

        // Assign pieces to players
        for (int i = 0; i < 8; i++) {
            blackPlayer.addPiece(chessBoard.getPiece(i, 0));
            whitePlayer.addEnemyPiece(chessBoard.getPiece(i, 0));
        }
        for (int i = 0; i < 8; i++) {
            blackPlayer.addPiece(chessBoard.getPiece(i, 1));
            whitePlayer.addEnemyPiece(chessBoard.getPiece(i, 1));
        }
        for (int i = 0; i < 8; i++) {
            whitePlayer.addPiece(chessBoard.getPiece(i, 6));
            blackPlayer.addEnemyPiece(chessBoard.getPiece(i, 6));
        }
        for (int i = 0; i < 8; i++) {
            whitePlayer.addPiece(chessBoard.getPiece(i, 7));
            blackPlayer.addEnemyPiece(chessBoard.getPiece(i, 7));
        }

        // Assign kings to players
        blackPlayer.setKing(chessBoard.getPiece(4, 0));
        whitePlayer.setKing(chessBoard.getPiece(4, 7));

        // White always starts the game
        whitePlayer.setIsTurn(true);
        currentPlayer = whitePlayer;
        otherPlayer = blackPlayer;
    }

    /**
     * Gets the chessboard instance.
     * @return The chessboard of the game.
     */
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    /**
     * Gets the current player.
     * @return The player whose turn it is.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the opponent of the current player.
     * @return The player who is not currently taking their turn.
     */
    public Player getOtherPlayer() {
        return otherPlayer;
    }
}
