import java.util.ArrayList;

public class Cell {
    private int label;
    private boolean isProtected;
    private ArrayList<Pawn> pawns = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public Cell(int label) {
        this.label = label;
        this.isProtected = isLabelProtected(label);
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
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void removePawnFromCell(Pawn pawn) {
        pawns.remove(pawn);
        // Optionally, remove the player from the list if no pawns remain for that player
        players.removeIf(player -> !pawns.stream().anyMatch(p -> p.getPlayer().equals(player)));
    }

    public boolean hasPawnsFromDifferentPlayers() {
        return players.size() > 1;
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
            if (pawn.getPlayer().equals(player)) {
                playerPawnsOnCell.add(pawn);
            }
        }

        return playerPawnsOnCell;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "label=" + label +
                ", isProtected=" + isProtected +
                ", pawns=" + pawns +
                '}';
    }
}
