import java.util.*;

public class Player {
    private final String type;
    private int numOfPawns = 4;
    private ArrayList<Pawn> pawns;
    private ArrayList<Cell> path = new ArrayList<>();
    private Player opponent;


    public Player(String type) {
        // Set type and pawns for Human
        if ("human".equalsIgnoreCase(type)) {
            this.type = "Human";
            this.pawns = new ArrayList<>();
            for(int i = 1; i <= numOfPawns; i++)
                pawns.add(new Pawn(i, "H" + i, Pawn.PawnStatus.OUT_GAME, this));
        }

        // Set type and pawns for Computer
        else if ("computer".equalsIgnoreCase(type)) {
            this.type = "Computer";
            this.pawns = new ArrayList<>();
            for(int i = 1; i <= numOfPawns; i++)
                pawns.add(new Pawn(i, "C" + i, Pawn.PawnStatus.OUT_GAME, this));
        } else {
            throw new IllegalArgumentException("Invalid player type: " + type);
        }
    }

    // Copy constructor
    public Player(Player other) {
        this.type = other.type;
        this.numOfPawns = other.numOfPawns;

        // Deep copy of the pawns
        this.pawns = new ArrayList<>();
        for (Pawn pawn : other.pawns) {
            this.pawns.add(new Pawn(pawn));
        }

        // Deep copy of the path
        this.path = new ArrayList<>();
        for (Cell cell : other.path) {
            this.path.add(new Cell(cell));
        }

        // Set opponent
        this.setOpponent(other.opponent);
    }

    public void setPath(ArrayList<Cell> path){
        this.path = path;
    }

    public ArrayList<Cell> getPath() {
        return path;
    }

    public void getPawnsStatus() {
        ArrayList<String> inGame = new ArrayList<>();
        ArrayList<String> outGame = new ArrayList<>();
        ArrayList<String> inKitchen = new ArrayList<>();
        ArrayList<Pawn> inGamePawns = new ArrayList<>();

        for (Pawn pawn : pawns) {
            switch (pawn.getStatus()) {
                case IN_GAME:
                    inGame.add(pawn.getName());
                    inGamePawns.add(pawn);
                    break;
                case OUT_GAME:
                    outGame.add(pawn.getName());
                    break;
                case IN_KITCHEN:
                    inKitchen.add(pawn.getName());
                    break;
            }
        }

        String s = String.format("%s Pawns:\nIn Game: %s - Out Game: %s - In Kitchen: %s",
                type, inGame, outGame, inKitchen);

        System.out.println(s);

        for(Pawn pawn : inGamePawns){
            System.out.println(pawn.getName() + " in cell: " + pawn.getLocationIndex());
        }
    }
    public Player deepCopy(ArrayList<Cell> newPath) {
        return new Player(this, newPath);
    }

    public Player(Player other, ArrayList<Cell> newPath) {
        this.type = other.type;
        this.numOfPawns = other.numOfPawns;
        this.path = newPath;
        this.opponent = other.opponent;
        this.pawns = deepCopyPawns(other.pawns, this);
    }
    private ArrayList<Pawn> deepCopyPawns(ArrayList<Pawn> pawns, Player player) {
        ArrayList<Pawn> copy = new ArrayList<>();
        for (Pawn pawn : pawns) {
            copy.add(new Pawn(pawn.getNumber(), pawn.getName(), pawn.getStatus(), player));
        }
        return copy;
    }


    public ArrayList<Pawn> getPawns(){
        return pawns;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public Player getOpponent() {
        return opponent;
    }

    public String getType() {
        return type;
    }

    public void addPawnToPath(int steps, Pawn pawn, Player player){

        // Get original location and remove it from it
        int originalIndex = pawn.getLocationIndex();
        Cell originalCell = path.get(originalIndex);
        originalCell.removePawnFromCell(pawn);

        // set the new location
        int newIndex = originalIndex + steps;
        pawn.setLocationIndex(newIndex);
        Cell newCell = path.get(newIndex);
        newCell.addPawnToCell(pawn,this);
    }

}
