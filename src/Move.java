import java.util.Objects;

public class Move {
    private int x;
    private int y;
    private Piece piece;
    private boolean isCapture;
    public Move(int x, int y, Piece piece, boolean isCapture) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.isCapture = isCapture;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Piece getPiece() {
        return piece;
    }
    public boolean getIsCapture() {
        return isCapture;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y= y;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public void setIsCapture(boolean isCapture) {
        this.isCapture = isCapture;
    }
    public void printInfo() {
        System.out.println("Move on X: " + x + " Y: " + y + " Captured: " + isCapture + " Piece: " + piece.getColor() + " " + piece.getClass().getSimpleName());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return x == move.x && y == move.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
