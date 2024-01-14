import java.util.ArrayList;
import java.util.Objects;

public class Cell {
    private final int label;
    private final boolean isProtected;
    private ArrayList<Pawn> pawns = new ArrayList<>();
//    private ArrayList<Player> players = new ArrayList<>();

    public Cell(int label) {
        this.label = label;
        this.isProtected = isLabelProtected(label);
    }

    // Copy constructor
    public Cell(Cell other) {
        this.label = other.label;
        this.isProtected = other.isProtected;

        // Deep copy of pawns
        this.pawns = new ArrayList<>();
        for (Pawn pawn : other.pawns) {
            this.pawns.add(new Pawn(pawn));
        }

    }
    public boolean hasEnemyPawn(Player player){
        Player opponent = player.getOpponent();
        ArrayList<Pawn> cellPawns = this.getPawns();
        for (Pawn pawn : cellPawns)
        {
            if(Objects.equals(pawn.getPawnType(), opponent.getType()))
                return true;
        }

        return false;
    }
    private boolean isLabelProtected(int label) {
        int[] protectedLabels = {32, 38, 21, 15, 66, 4, 49, 55};
        for (int protectedLabel : protectedLabels) {
            if (label == protectedLabel) {
                return true;
            }
        }
        return false;
    }

    public void addPawnToCell(Pawn pawn) {
        pawns.add(pawn);
    }

    public void removePawnFromCell(Pawn pawn) {
        pawns.remove(pawn);
    }

    public boolean hasPawnsFromDifferentPlayers(Player enemyPlayer) {
        return getPlayerPawnsOnCell(enemyPlayer) != null;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public ArrayList<Pawn> getPlayerPawnsOnCell(Player player) {
        ArrayList<Pawn> playerPawnsOnCell = new ArrayList<>();

        for (Pawn pawn : pawns) {
            if(Objects.equals(pawn.getPawnType(), player.getType())) {
                playerPawnsOnCell.add(pawn);
            }
        }

        return playerPawnsOnCell;
    }

    // used append instead of +=
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("[");
        for (Pawn pawn : pawns){
            s.append(pawn.getName());
        }
        s.append("]");
        return s.toString();
    }

}
