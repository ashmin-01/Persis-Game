import java.util.ArrayList;

public class Player {
    private final String type;
    private ArrayList<Pawn> pawns;
    private int numOfPawns = 4;
    private TossShells toss;

    public Player(String type) {
        if ("human".equalsIgnoreCase(type)) {
            this.type = "Human";
            this.pawns = new ArrayList<>();
            for(int i = 0; i < numOfPawns; i++)
                pawns.add(new Pawn("H" + i, Pawn.GameStatus.OUT_GAME));
        } else if ("computer".equalsIgnoreCase(type)) {
            this.type = "Computer";
            this.pawns = new ArrayList<>();
            for(int i = 0; i < numOfPawns; i++)
                pawns.add(new Pawn("C" + i, Pawn.GameStatus.OUT_GAME));
        } else {
            throw new IllegalArgumentException("Invalid player type: " + type);
        }

        this.toss = new TossShells();
    }

    public int Toss() {
       return toss.tossShells();
    }


}
