public class Game {
    private ChessBoard chessBoard;
    private Player blackPlayer;
    private Player whitePlayer;
    public Game() {
        chessBoard = new ChessBoard();
        blackPlayer = new Player();
        whitePlayer = new Player();
    }
    public void gameLoop() {
        boolean gameOn = true;
        chessBoard.initializeGame();

        while (gameOn) {
            chessBoard.displayBoard();
            gameOn = false;
        }
    }

}
