public class Pawn {
    private String name;
    private GameStatus status;

    // Constructor
    public Pawn(String name, GameStatus status) {
        this.name = name;
        this.status = status;
    }

    public enum GameStatus {
        IN_GAME,
        OUT_GAME,
        IN_KITCHEN
    }

    // Getter and setter for status
    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

}

