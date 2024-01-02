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
    public boolean getIsAlive() {
        return isAlive;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void capture(Piece piece) {
        piece.setIsAlive(false);
    }
    public void printInfo() {
        System.out.println("Color: " + color);
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        System.out.println("Is Alive: " + isAlive);
    }
}
