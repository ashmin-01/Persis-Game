public class Pawn {
    private int number;
    private String name;
    private GameStatus status;

    private int locationIndex;

    // Constructor
    public Pawn(int number, String name, GameStatus status) {
        this.number = number;
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

    public String getName() {
        return name;
    }

    public int getNumber(){return number;}

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setLocationIndex(int locationIndex){this.locationIndex = locationIndex;}

    public int getLocationIndex(){return this.locationIndex;}

}

