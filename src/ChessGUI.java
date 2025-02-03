import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Handles the graphical user interface for the chess game.
 * Uses JavaFX to display the board, manage interactions, and update the game state.
 */
public class ChessGUI extends Application {
    private static final int TILE_SIZE = 80;
    private static final int BOARD_SIZE = 8;
    private Button[][] tiles = new Button[BOARD_SIZE][BOARD_SIZE]; // Grid buttons representing the board
    private Game game;
    private ChessBoard chessBoard;
    private Piece selectedPiece;
    private boolean gameOver = false;
    private List<Move> highlightedMoves = null; // Stores highlighted legal moves
    private BorderPane root; // Main layout
    private TextArea moveHistoryArea; // Move history display
    private Label whiteTimerLabel;
    private Label blackTimerLabel;
    private int whiteTime = 300; // 5 minutes per player
    private int blackTime = 300;
    private Timer timer;
    private boolean isWhiteTurn = true; // Tracks turn order
    private static final Map<String, Image> pieceImages = new HashMap<>();
    private Timeline timeline;


    /**
     * Initializes the GUI and starts the game.
     * @param primaryStage The main JavaFX stage.
     */
    @Override
    public void start(Stage primaryStage) {
        loadPieceImages(); // Load piece images into memory
        game = new Game();
        chessBoard = game.getChessBoard();
        startTimer(); // Start countdown timer

        GridPane boardPane = new GridPane();
        updateBoard(boardPane); // Render initial board state

        VBox sidePanel = createSidePanel(); // Sidebar with move history and controls

        root = new BorderPane();
        root.setCenter(boardPane);
        root.setRight(sidePanel);

        Scene scene = new Scene(root, BOARD_SIZE * TILE_SIZE + 200, BOARD_SIZE * TILE_SIZE);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Starts the chess game timer for both players.
     * Updates the displayed time and ends the game if time runs out.
     */
    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (isWhiteTurn) {
                whiteTime--;
                whiteTimerLabel.setText("White: " + whiteTime + "s");
            } else {
                blackTime--;
                blackTimerLabel.setText("Black: " + blackTime + "s");
            }
            if (whiteTime == 0 || blackTime == 0) {
                gameOver = true;
                moveHistoryArea.appendText("Game over! " + (whiteTime == 0 ? "Black wins!" : "White wins!") + "\n");
                timeline.stop();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    /**
     * Creates the side panel with move history, timers, and control buttons.
     * @return The VBox containing the side panel UI elements.
     */
    private VBox createSidePanel() {
        VBox sidePanel = new VBox();
        sidePanel.setSpacing(10);
        sidePanel.setPrefWidth(200);
        sidePanel.setStyle("-fx-background-color: #333333; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");

        whiteTimerLabel = new Label("White: " + whiteTime + "s");
        blackTimerLabel = new Label("Black: " + blackTime + "s");
        whiteTimerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        blackTimerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Button newGameButton = new Button("New Game");
        newGameButton.setMinWidth(180);
        newGameButton.setOnAction(e -> restartGame());

        Button undoButton = new Button("Undo Move");
        undoButton.setMinWidth(180);
        undoButton.setOnAction(e -> undoMove());

        moveHistoryArea = new TextArea();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setPrefHeight(400);
        moveHistoryArea.setWrapText(true);
        moveHistoryArea.setStyle("-fx-control-inner-background: #222222; -fx-text-fill: white;");

        sidePanel.getChildren().addAll(whiteTimerLabel, blackTimerLabel, moveHistoryArea, undoButton, newGameButton);
        return sidePanel;
    }

    /**
     * Switches turns between white and black players.
     */
    private void switchTurns() {
        isWhiteTurn = !isWhiteTurn;
        game.switchTurns();
    }
    /**
     * Undoes the last move if possible.
     */
    private void undoMove() {
        if (!game.getChessBoard().moveHistory.isEmpty()) {
            game.getChessBoard().undoMovePiece();
            moveHistoryArea.appendText("Move undone\n");
            updateBoard((GridPane) root.getCenter());
            switchTurns();
        } else {
            moveHistoryArea.appendText("No move to undo\n");
        }
    }
    /**
     * Restarts the game by resetting the board, timers, and move history.
     */
    private void restartGame() {
        game = new Game();
        chessBoard = game.getChessBoard();
        selectedPiece = null;
        highlightedMoves = null;
        gameOver = false;
        whiteTime = 300;
        blackTime = 300;
        isWhiteTurn = true;

        if (timeline != null) {
            timeline.stop();
        }

        whiteTimerLabel.setText("White: 300s");
        blackTimerLabel.setText("Black: 300s");

        startTimer();
        updateBoard((GridPane) root.getCenter());
        moveHistoryArea.clear();
        System.out.println("New Game!");
    }

    /**
     * Updates the chessboard UI by clearing and re-rendering all tiles.
     * @param gridPane The JavaFX GridPane containing the chessboard.
     */
    private void updateBoard(GridPane gridPane) {
        gridPane.getChildren().clear(); // Clear previous board state
        String basePath = "resources/pieces/";

        // Iterate over each board tile
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button tile = new Button();
                tile.setMinSize(TILE_SIZE, TILE_SIZE);

                boolean isHighlighted = false;
                boolean isSelected = (selectedPiece != null && selectedPiece.getX() == col && selectedPiece.getY() == row);

                // Check if the tile is a highlighted move
                if (highlightedMoves != null) {
                    for (Move move : highlightedMoves) {
                        if (move.getxTarget() == col && move.getyTarget() == row) {
                            isHighlighted = true;
                            break;
                        }
                    }
                }

                // Set tile background based on state
                if (isHighlighted) {
                    tile.setStyle("-fx-background-color: lightgreen; -fx-border-color: #32CD32; -fx-border-width: 3px;");
                } else if (isSelected) {
                    tile.setStyle("-fx-background-color: yellow; -fx-border-color: #FFD700; -fx-border-width: 3px;");
                } else {
                    tile.setStyle((row + col) % 2 == 0 ? "-fx-background-color: #f0d9b5; -fx-border-color: #b58863;"
                            : "-fx-background-color: #b58863; -fx-border-color: #f0d9b5;");
                }

                // Assign piece image if a piece is present
                Piece piece = chessBoard.getPiece(col, row);
                if (piece != null) {
                    String imageKey = getPieceImage(piece);
                    if (pieceImages.containsKey(imageKey)) {
                        ImageView imageView = new ImageView(pieceImages.get(imageKey));
                        imageView.setFitWidth(TILE_SIZE - 20);
                        imageView.setFitHeight(TILE_SIZE - 20);
                        tile.setGraphic(imageView);
                    }
                }

                // Handle tile click event
                final int currentRow = row;
                final int currentCol = col;
                tile.setOnAction(e -> handleTileClick(currentCol, currentRow, gridPane));

                tiles[row][col] = tile;
                gridPane.add(tile, col, row);
            }
        }
    }
    /**
     * Handles user interaction with the chessboard tiles.
     * @param col The column index of the clicked tile.
     * @param row The row index of the clicked tile.
     * @param gridPane The JavaFX GridPane containing the chessboard.
     */
    private void handleTileClick(int col, int row, GridPane gridPane) {
        if (gameOver) return; // Do nothing if the game is over

        if (selectedPiece == null) {
            selectedPiece = chessBoard.getPiece(col, row);
            if (selectedPiece == null || !selectedPiece.getColor().equals(game.getCurrentPlayer().getColor())) {
                selectedPiece = null;
                highlightedMoves = null;
            } else {
                highlightedMoves = selectedPiece.getLegalMoves(chessBoard);
            }
        } else {
            // Deselect piece if clicking it again
            if (selectedPiece == chessBoard.getPiece(col, row)) {
                selectedPiece = null;
                highlightedMoves = null;
                updateBoard(gridPane);
                return;
            }

            Move attemptedMove = null;

            // Check if the selected move is legal
            for (Move move : selectedPiece.getLegalMoves(chessBoard)) {
                if (move.getxTarget() == col && move.getyTarget() == row) {
                    attemptedMove = move;
                    break;
                }
            }

            if (attemptedMove == null) {
                moveHistoryArea.appendText("Invalid move: Not a legal move\n");
                selectedPiece = null;
                highlightedMoves = null;
                updateBoard(gridPane);
                return;
            }

            // Execute castling move if applicable
            if (attemptedMove.getIsCastle()) {
                chessBoard.movePiece(attemptedMove);
                moveHistoryArea.appendText("Castling performed!\n");
            } else {
                chessBoard.movePiece(attemptedMove);
            }

            // Prevent moves that put the player in check
            if (chessBoard.isCheck(game.getCurrentPlayer())) {
                chessBoard.undoMovePiece();
                moveHistoryArea.appendText("Invalid move: Puts player in check\n");
                selectedPiece = null;
                highlightedMoves = null;
                updateBoard(gridPane);
                return;
            }

            // Handle pawn promotion
            if (selectedPiece instanceof Pawn && (row == 0 || row == 7)) {
                Piece promotedPiece = showPromotionDialog(game.getCurrentPlayer(), col, row);
                chessBoard.setPiece(promotedPiece);
                game.getCurrentPlayer().addPiece(promotedPiece);
            }

            updateMoveHistory(attemptedMove); // Log the move in history
            selectedPiece = null;
            highlightedMoves = null;
            updateBoard(gridPane); // Refresh board UI

            if (checkGameOver()) {
                gameOver = true;
                return;
            }

            switchTurns(); // Swap turns between players
        }
        updateBoard(gridPane);
    }
    /**
     * Displays a dialog for pawn promotion, allowing the player to choose a piece.
     * @param player The player who is promoting the pawn.
     * @param x The x-coordinate where the pawn is being promoted.
     * @param y The y-coordinate where the pawn is being promoted.
     * @return The new piece chosen by the player.
     */
    private Piece showPromotionDialog(Player player, int x, int y) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Pawn Promotion");
        dialog.setHeaderText("Choose your promotion piece:");

        GridPane grid = new GridPane();
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        ButtonType[] types = new ButtonType[4];

        // Create buttons for promotion choices
        for (int i = 0; i < options.length; i++) {
            types[i] = new ButtonType(options[i], ButtonBar.ButtonData.OK_DONE);
            grid.add(new Label(options[i]), i, 0);
        }

        dialog.getDialogPane().getButtonTypes().addAll(types);
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        ButtonType result = dialog.getResult();
        return switch (result.getText()) {
            case "Rook" -> new Rook(player, x, y);
            case "Bishop" -> new Bishop(player, x, y);
            case "Knight" -> new Knight(player, x, y);
            default -> new Queen(player, x, y);
        };
    }

    /**
     * Updates the move history area with the latest move notation.
     * @param move The move to be added to the history log.
     */
    private void updateMoveHistory(Move move) {
        String notation = move.getChessNotation();
        String playerPrefix = (move.getPiece().getColor().equals("White")) ? "W:" : "B:";
        moveHistoryArea.appendText(playerPrefix + notation + "\n");
    }

    /**
     * Checks if the game is over by determining checkmate or stalemate.
     * @return true if the game has ended, false otherwise.
     */
    private boolean checkGameOver() {
        boolean isOtherPlayerInCheck = chessBoard.isCheck(game.getOtherPlayer());
        boolean isOtherPlayerCheckmated = chessBoard.isCheckMate(game.getOtherPlayer());

        if (isOtherPlayerInCheck && isOtherPlayerCheckmated) {
            String message = "Checkmate, " + game.getCurrentPlayer().getColor() + " wins!";
            moveHistoryArea.appendText("\n" + message + "\n");
            System.out.println(message);
            return true;
        } else if (!isOtherPlayerInCheck && isOtherPlayerCheckmated) {
            moveHistoryArea.appendText("\nStalemate.\n");
            System.out.println("Stalemate.");
            return true;
        }
        return false;
    }

    /**
     * Retrieves the image file name for a given chess piece.
     * @param piece The chess piece.
     * @return The corresponding image file name.
     */
    private String getPieceImage(Piece piece) {
        return switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> piece.getColor().equals("Black") ? "black_pawn" : "white_pawn";
            case "Rook" -> piece.getColor().equals("Black") ? "black_rook" : "white_rook";
            case "Knight" -> piece.getColor().equals("Black") ? "black_knight" : "white_knight";
            case "Bishop" -> piece.getColor().equals("Black") ? "black_bishop" : "white_bishop";
            case "Queen" -> piece.getColor().equals("Black") ? "black_queen" : "white_queen";
            case "King" -> piece.getColor().equals("Black") ? "black_king" : "white_king";
            default -> "";
        };
    }
    /**
     * Loads chess piece images from the resources folder into memory.
     */
    private void loadPieceImages() {
        String basePath = "resources/pieces/";

        // Array containing the names of all chess piece image files
        String[] pieceNames = {"black_pawn", "white_pawn", "black_rook", "white_rook",
                "black_knight", "white_knight", "black_bishop", "white_bishop",
                "black_queen", "white_queen", "black_king", "white_king"};

        // Load each piece image and store it in the map
        for (String name : pieceNames) {
            File file = new File(basePath + name + ".png");
            if (file.exists()) {
                pieceImages.put(name, new Image(file.toURI().toString()));
            } else {
                System.out.println("Error loading image: " + file.getPath());
            }
        }
    }

    /**
     * Launches the JavaFX application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
