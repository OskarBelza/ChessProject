import java.util.LinkedList;

public class Player {
    private final String color;
    private boolean isTurn;
    private final LinkedList<Piece> pieces;
    private final LinkedList<Piece> enemyPieces;
    private Piece king;
    public Player(String color) {
        this.color = color;
        this.pieces = new LinkedList<Piece>();
        this.king = null;
        isTurn = false;
        this.enemyPieces = new LinkedList<Piece>();
    }
    public String getColor() {
        return color;
    }
    public boolean getIsTurn() {
        return isTurn;
    }
    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }
    public void addEnemyPiece(Piece piece) {
        enemyPieces.add(piece);
    }
    public LinkedList<Piece> getPiecesAlive() {
        LinkedList<Piece> piecesAlive = new LinkedList<Piece>();
        for(Piece piece : pieces) {
            if(piece.getIsAlive()) {
                piecesAlive.add(piece);
            }
        }
        return piecesAlive;
    }
    public LinkedList<Piece> getEnemyPiecesAlive() {
        LinkedList<Piece> piecesAlive = new LinkedList<Piece>();
        for(Piece piece : enemyPieces) {
            if(piece.getIsAlive()) {
                piecesAlive.add(piece);
            }
        }
        return piecesAlive;
    }
    public Piece getKing() {
        return king;
    }
    public void setKing(Piece king) {
        this.king = king;
    }
    public void printPieces() {
        for (Piece piece : pieces) {
            piece.printInfo();
        }
    }
    public void printInfo() {
        System.out.println("Color: " + color);
        System.out.println("Is Turn: " + isTurn);
    }
}
