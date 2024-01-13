import java.util.ArrayList;

public class Cell {
    private int label;
    private boolean isProtected;
    private ArrayList<Pawn> pawns = new ArrayList<>();
//    private ArrayList<Player> players = new ArrayList<>();

    public Cell(int label) {
        this.label = label;
        this.isProtected = isLabelProtected(label);
    }
    // Copy constructor
// Copy constructor
    public Cell(Cell other) {
        this.label = other.label;
        this.isProtected = other.isProtected;

        // Deep copy of pawns
        this.pawns = new ArrayList<>();
        for (Pawn pawn : other.pawns) {
            this.pawns.add(new Pawn(pawn));
        }

//        this.players = new ArrayList<>(other.players);
    }
    public boolean hasEnemyPawn(Player player){
        Player opponent = player.getOpponent();
        ArrayList<Pawn> cellPawns = this.getPawns();
        for (Pawn pawn : cellPawns)
        {
            if(pawn.getPawnType() == opponent.getType())
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

    public void addPawnToCell(Pawn pawn, Player player) {
        pawns.add(pawn);
//        if (!players.contains(player)) {
//            players.add(player);
//        }
    }

    public void removePawnFromCell(Pawn pawn) {
        pawns.remove(pawn);
        // Optionally, remove the player from the list if no pawns remain for that player
//        players.removeIf(player -> !pawns.stream().anyMatch(p -> p.getPlayer().equals(player)));
    }

    public boolean hasPawnsFromDifferentPlayers(Player enemyPlayer) {
        // check for pawns of this player in ()
        // if != null then true he has pawns
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
            if(pawn.getPawnType() == player.getType()) {
                playerPawnsOnCell.add(pawn);
            }
        }

        return playerPawnsOnCell;
    }

    @Override
    public String toString(){
        String s = "[";
        for (Pawn pawn : pawns){
            s += pawn.getName();
        }
        s += "]";
        return s;
    }

}
