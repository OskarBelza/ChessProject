import java.util.ArrayList;
import java.util.List;
public class Piece {
    private final String color;
    private int x;
    private int y;
    private boolean isAlive;
    public Piece(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        isAlive = true;
    }
    public String getColor() {
        return color;
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
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public void capture() {
        this.setIsAlive(false);
    }
    public void unCapture() {
        this.setIsAlive(true);
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
        System.out.println("Color: " + color);
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        System.out.println("Is Alive: " + isAlive);
    }
}
