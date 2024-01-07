import java.util.*;

public class Player {
    private final String type;
    private ArrayList<Pawn> pawns;
    private int numOfPawns = 4;
    private TossShells toss;
    private ArrayList<Cell> path;

    public Player(String type, ArrayList<Cell> humanPath) {
        if ("human".equalsIgnoreCase(type)) {
            this.type = "Human";
            this.pawns = new ArrayList<>();
            for(int i = 0; i < numOfPawns; i++)
                pawns.add(new Pawn("H" + i, Pawn.GameStatus.OUT_GAME));
            this.path = Path.createHumanPath();
        } else if ("computer".equalsIgnoreCase(type)) {
            this.type = "Computer";
            this.pawns = new ArrayList<>();
            for(int i = 0; i < numOfPawns; i++)
                pawns.add(new Pawn("C" + i, Pawn.GameStatus.OUT_GAME));
            this.path = Path.createComputerPath(humanPath);
        } else {
            throw new IllegalArgumentException("Invalid player type: " + type);
        }

        this.toss = new TossShells();
    }

    public List<List<Object>> Toss() {
       return toss.getTossResults();
    }

    public ArrayList<Cell> getPath() {
        return path;
    }
}
