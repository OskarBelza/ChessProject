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
        System.out.println("X: " + x);
        System.out.println("Y: " + y);}
}
