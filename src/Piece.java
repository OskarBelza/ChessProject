import java.util.ArrayList;
import java.util.List;
public class Piece {
    protected Player player;
    protected int x;
    protected int y;
    protected boolean isAlive;
    protected boolean hasMoved = false;
    public Piece(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
        isAlive = true;
        hasMoved = false;
    }
    public boolean hasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public Player getPlayer() {
        return player;
    }
    public String getColor() {
        return player.getColor();
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int newX) {
        x = newX;
    }
    public void setY(int newY) {
        y = newY;
    }
    public boolean getIsAlive() {
        return isAlive;
    }
    public void capture() {
        this.isAlive = false;
    }
    public void unCapture() {
        this.isAlive = true;;
    }
    public List<Move> getDiagonalMoves(ChessBoard chessBoard) {
        List<Move> moves = new ArrayList<>();
        int x = this.x;
        int y = this.y;
        while (x < 7 && y < 7) {
            x++;
            y++;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        x = this.x;
        y = this.y;
        while (x > 0 && y > 0) {
            x--;
            y--;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        x = this.x;
        y = this.y;
        while (x < 7 && y > 0) {
            x++;
            y--;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        x = this.x;
        y = this.y;
        while (x > 0 && y < 7) {
            x--;
            y++;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        return moves;
    }
    public List<Move> getStraightMoves(ChessBoard chessBoard){
        List<Move> moves = new ArrayList<>();
        int x = this.x;
        int y = this.y;
        while (x < 7) {
            x++;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        x = this.x;
        y = this.y;
        while (x > 0) {
            x--;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        x = this.x;
        y = this.y;
        while (y < 7) {
            y++;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        x = this.x;
        y = this.y;
        while (y > 0) {
            y--;
            if (!addMoveIfLegal(moves, x, y, chessBoard)){
                break;
            }
        }
        return moves;
    }
    public List<Move> getLegalMoves(ChessBoard chessBoard){
        return null;
    }
    public boolean addMoveIfLegal(List<Move> moves, int x, int y, ChessBoard chessBoard){
        if(!chessBoard.outOfBounds(x, y)){
            if(chessBoard.getPiece(x, y) == null){
                moves.add(new Move(x, y, this, false, null));
                return true;
            }
            else if(!chessBoard.getPiece(x, y).getColor().equals(this.getColor())){
                moves.add(new Move(x, y, this, true, chessBoard.getPiece(x, y)));
                return false;
            }
        }
        return false;
    }
    public void printInfo() {
        System.out.println("Color: " + getColor());
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        System.out.println("Is Alive: " + isAlive);
    }
}
