import java.util.ArrayList;

public class Pawn {
    private int number;
    private String name;
    private GameStatus status;
    private int locationIndex;
    private Player player;

    // Constructor
    public Pawn(int number, String name, GameStatus status,Player player) {
        this.number = number;
        this.name = name;
        this.status = status;
        this.player = player;
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
    public Player getPlayer() {
        return player;
    }

    public Cell getCell() {
        int index = this.getLocationIndex();
        ArrayList<Cell> path = this.getPlayer().getPath();
        if (index >= 0 && index < path.size()) {
            return path.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid location index: " + index);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}

