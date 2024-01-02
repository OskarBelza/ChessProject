import java.util.LinkedList;

public class Player {
    private String color;
    private boolean isTurn;
    public LinkedList<Piece> pieces;
    public Player(String color) {
        this.color = color;
        this.pieces = new LinkedList<Piece>();
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
    public void printInfo() {
        System.out.println("Color: " + color);
        System.out.println("Is Turn: " + isTurn);
    }
}
