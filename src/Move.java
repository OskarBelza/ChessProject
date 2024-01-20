import java.util.Objects;

public class Move {
    private final int xTarget;
    private final int yTarget;
    private final int xStart;
    private final int yStart;
    private Piece piece;
    private final boolean isCapture;
    private final Piece capturedPiece;
    private boolean isCastle;
    private Piece castledRook;
    public Move(int xTarget, int yTarget, Piece piece, boolean isCapture, Piece capturedPiece) {
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.xStart = piece.getX();
        this.yStart = piece.getY();
        this.piece = piece;
        this.isCapture = isCapture;
        this.capturedPiece = capturedPiece;
    }
    public Move(int xTarget, int yTarget, Piece piece, boolean isCapture, Piece capturedPiece, boolean isCastle, Piece castledRook) {
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.xStart = piece.getX();
        this.yStart = piece.getY();
        this.piece = piece;
        this.isCapture = isCapture;
        this.capturedPiece = capturedPiece;
        this.isCastle = isCastle;
        this.castledRook = castledRook;
    }
    public Piece getCastledRook() {
        return castledRook;
    }
    public boolean getIsCastle() {
        return isCastle;
    }
    public int getxTarget() {
        return xTarget;
    }
    public int getxStart() {
        return xStart;
    }
    public int getyTarget() {
        return yTarget;
    }
    public int getyStart() {return yStart;}
    public Piece getPiece() {
        return piece;
    }
    public boolean getIsCapture() {
        return isCapture;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public Piece getCapturedPiece() {
        return capturedPiece;
    }
    public void printInfo() {
        System.out.println("Move on X: " + xTarget + " Y: " + yTarget + " Captured: " + isCapture + " Piece: " + piece.getColor() + " " + piece.getClass().getSimpleName() + " " + isCastle+ " "+ castledRook);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return xTarget == move.xTarget && yTarget == move.yTarget;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xTarget, yTarget);
    }
}
