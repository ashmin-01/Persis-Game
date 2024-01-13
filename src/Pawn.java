import java.util.ArrayList;

public class Pawn {
    private int number;
    private String name;
    private PawnStatus status;
    private int locationIndex;
    private String pawnType;
//    private Player player;

    // Constructor
    public Pawn(int number, String name, PawnStatus status,Player player) {
        this.number = number;
        this.name = name;
        this.status = status;
        this.pawnType = player.getType();
//        this.player = player;
    }

    // Copy constructor
    public Pawn(Pawn other) {
        this.number = other.number;
        this.name = other.name;
        this.status = other.status;
        this.locationIndex = other.locationIndex;
        this.pawnType=other.pawnType;
//        this.player = other.player;
    }

    public enum PawnStatus {
        IN_GAME,
        OUT_GAME,
        IN_KITCHEN
    }

    // Getter and setter for status
    public PawnStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getNumber(){return number;}

    public void setStatus(PawnStatus  status) {
        this.status = status;
    }

    public void setLocationIndex(int locationIndex){this.locationIndex = locationIndex;}

    public int getLocationIndex(){return this.locationIndex;}
//    public Player getPlayer() {
//        return player;
//    }

    public String getPawnType(){return this.pawnType;}

    public Cell getCell(Player player) {
        int index = this.getLocationIndex();
        ArrayList<Cell> path = player.getPath();
        if (index >= 0 && index < path.size()) {
            return path.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid location index: " + index);
        }
    }

//    public void setPlayer(Player player) {
//        this.player = player;
//    }


}

