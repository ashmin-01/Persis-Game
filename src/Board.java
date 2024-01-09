import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    Player player1, player2;

    public Board(String player1Type, String player2Type){
        // Create players
        this.player1 = new Player(player1Type);
        this.player2 = new Player(player2Type);

        player1.setOpponent(player2);
        player2.setOpponent(player1);

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

    public void move(Player player, Pawn pawn, TossResult tossResult, int steps) {

        // Check if the player toss result is bigger than the remaining spots
        int remainingSpots = player.getPath().size() - pawn.getLocationIndex();
        if (steps > remainingSpots) {
            System.out.println("Cannot move. Skip till next turn.");
            return;
        } else if (steps == remainingSpots)
        {
            System.out.println("Congrats your pawn has entered the kitchen!");
            pawn.setStatus(Pawn.GameStatus.IN_KITCHEN);
            Cell cell = pawn.getCell();
            cell.removePawnFromCell(pawn);
            return;
        }
        // check for Khal
        if (tossResult.getIsKhal() && pawn.getStatus() == Pawn.GameStatus.OUT_GAME) {
            pawn.setStatus(Pawn.GameStatus.IN_GAME);
            pawn.setLocationIndex(0);
            player.addPawnToPath(0, pawn, player);
        } else {
            player.addPawnToPath(steps, pawn, player);

            // after moving the pawn to the new location, check if it can kill an opponent's pawn
            if (canKill(pawn)) {
                killOpponentPawn(player, pawn);
            }
        }
    }

    public boolean canKill(Pawn pawn){
        Cell currentCell = pawn.getCell();
         if(!currentCell.isProtected() && currentCell.hasPawnsFromDifferentPlayers()){
             return true;
         }
        return false;
    }

    public void killOpponentPawn(Player player, Pawn pawn) {
        Player opponent = player.getOpponent();
        Cell currentCell = pawn.getCell();

        ArrayList<Pawn> enemyPawns = currentCell.getPlayerPawnsOnCell(opponent);
        for(Pawn pawnToKill : enemyPawns){
            currentCell.removePawnFromCell(pawnToKill);
            pawnToKill.setStatus(Pawn.GameStatus.OUT_GAME);
        }

            System.out.println("You killed enemy pawn(s)!");
        }


    public void printInfo() {
        player1.getPawnsStatus();
        player2.getPawnsStatus();
    }
}
