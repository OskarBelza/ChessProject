import java.util.LinkedList;

public class Player {
    private String color;
    private boolean isTurn;
    private LinkedList<Piece> pieces;
    private Piece king;
    public Player(String color, Piece king) {
        this.color = color;
        this.pieces = new LinkedList<Piece>();
        this.king = king;
        isTurn = false;
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
    public void removePiece(Piece piece) {
        pieces.remove(piece);
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
    public Piece getKing() {
        return king;
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
