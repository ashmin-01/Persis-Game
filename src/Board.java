import java.util.ArrayList;
import java.util.List;

public class Board {
    Player player1, player2;

    public Board(String player1Type, String player2Type){
        // Create players
        this.player1 = new Player(player1Type);
        this.player2 = new Player(player2Type);

        // Create paths
        ArrayList<Cell> path1 = Path.createFirstPath();
        ArrayList<Cell> path2 = Path.createSecondPath(path1);

        // Assign paths to players
        player1.setPath(path1);
        player2.setPath(path2);
    }

    public boolean isFinished() {
        return isWin(player1) | isWin(player2);
    }

    public boolean isWin(Player player){
        ArrayList<Pawn> pawns = player.getPawns();

        for(Pawn pawn : pawns){
            if(pawn.getStatus() != Pawn.GameStatus.IN_KITCHEN){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> validPawnList(TossResult tossResult, ArrayList<Pawn> pawns){
        ArrayList<Integer> validPawns = new ArrayList<>();

        // check for Khal
        if(tossResult.getIsKhal()){
            for(Pawn pawn : pawns){
                if(pawn.getStatus() == Pawn.GameStatus.OUT_GAME) {
                    validPawns.add(pawn.getNumber());
                }
            }
        }

        // check for steps
        if(tossResult.getSteps() > 0){
            for(Pawn pawn : pawns){
                if(pawn.getStatus() == Pawn.GameStatus.IN_GAME)
                    validPawns.add(pawn.getNumber());
            }
        }

        return validPawns;
    }

    public void move(Player player, Pawn pawn, TossResult tossResult) {
        int steps = tossResult.getSteps();
        // check for Khal
        if(tossResult.getIsKhal() && pawn.getStatus() == Pawn.PawnStatus.OUT_GAME){
            pawn.setStatus(Pawn.PawnStatus.IN_GAME);
            pawn.setLocationIndex(0);
            player.addPawnToPath(0, pawn);
            return;
        }

        player.addPawnToPath(steps, pawn);

        // should check for errors like when the steps go out of all cells
    }

    public void printInfo() {
        player1.getPawnsStatus();
        player2.getPawnsStatus();
    }
}
