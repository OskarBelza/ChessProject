public class Piece {
    private String name;
    private final String color;
    private int x;
    private int y;
    private boolean isAlive;
    public Piece(String name, String color, int x, int y) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
        isAlive = true;
    }
    public String getName() {
        return name;
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
    public void setName(String name) {
        this.name = name;
    }
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void capture(Piece piece) {
        piece.setIsAlive(false);
    }
    public void promote(Piece piece, String name) {
        piece.setName(name);
    }
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Color: " + color);
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        System.out.println("Is Alive: " + isAlive);
    }
}
