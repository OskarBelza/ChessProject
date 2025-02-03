import java.util.LinkedList;

/**
 * Represents a chess player.
 * Stores player information such as color, turn status, and piece lists.
 */
public class Player {
    private final String color; // Player color ("White" or "Black")
    private boolean isTurn; // Indicates if it's this player's turn
    private final LinkedList<Piece> pieces; // List of player's pieces
    private final LinkedList<Piece> enemyPieces; // List of opponent's pieces
    private Piece king; // Reference to the player's King piece

    /**
     * Constructs a Player with a specified color.
     * @param color The color of the player ("White" or "Black").
     */
    public Player(String color) {
        this.color = color;
        this.pieces = new LinkedList<>();
        this.king = null;
        isTurn = false;
        this.enemyPieces = new LinkedList<>();
    }

    //GETTERS & SETTERS

    public String getColor() {
        return color;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public Piece getKing() {
        return king;
    }

    public void setKing(Piece king) {
        this.king = king;
    }

    /**
     * Adds a piece to the player's list of controlled pieces.
     * @param piece The piece to add.
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    /**
     * Adds an enemy piece to the player's list of opponent pieces.
     * @param piece The enemy piece to add.
     */
    public void addEnemyPiece(Piece piece) {
        enemyPieces.add(piece);
    }

    /**
     * Returns a list of the player's pieces that are still in the game.
     * @return A list of active pieces owned by the player.
     */
    public LinkedList<Piece> getPiecesAlive() {
        LinkedList<Piece> piecesAlive = new LinkedList<>();
        for (Piece piece : pieces) {
            if (piece.getIsAlive()) {
                piecesAlive.add(piece);
            }
        }
        return piecesAlive;
    }

    /**
     * Returns a list of the opponent's pieces that are still in the game.
     * @return A list of active enemy pieces.
     */
    public LinkedList<Piece> getEnemyPiecesAlive() {
        LinkedList<Piece> piecesAlive = new LinkedList<>();
        for (Piece piece : enemyPieces) {
            if (piece.getIsAlive()) {
                piecesAlive.add(piece);
            }
        }
        return piecesAlive;
    }

    /**
     * Prints information about all pieces owned by the player.
     */
    public void printPieces() {
        for (Piece piece : pieces) {
            piece.printInfo();
        }
    }

    /**
     * Prints basic player information.
     */
    public void printInfo() {
        System.out.println("Color: " + color);
        System.out.println("Is Turn: " + isTurn);
    }
}
