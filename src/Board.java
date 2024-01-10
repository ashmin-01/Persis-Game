import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    Player player1, player2;
    ArrayList<Cell> path1, path2;


    public Board(String player1Type, String player2Type){
        // Create players
        this.player1 = new Player(player1Type);
        this.player2 = new Player(player2Type);

        player1.setOpponent(player2);
        player2.setOpponent(player1);

        // Create paths
        this.path1 = Path.createFirstPath();
        this.path2 = Path.createSecondPath(path1);

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
            if(pawn.getStatus() != Pawn.PawnStatus.IN_KITCHEN){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Move> validMoves(ArrayList<Integer> stepsList, ArrayList<Pawn> pawns){
        ArrayList<Move> allMoves = new ArrayList<>();
        Move move;

        for (int i = 0; i < stepsList.size(); i++){
            for(Pawn pawn : pawns){
                // 0 steps for khal
                if(stepsList.get(i) == 1 && pawn.getStatus() == Pawn.PawnStatus.OUT_GAME) {
                    move = new Move(pawn, 0, i);
                    allMoves.add(move);
                }
                else if(pawn.getStatus() == Pawn.PawnStatus.IN_GAME){
                    move = new Move(pawn, stepsList.get(i), i);
                    allMoves.add(move);
                }
            }
        }
        return allMoves;
    }

    public void move(Player player, Pawn pawn, int steps) {

        // Check if the player toss result is bigger than the remaining spots
        int remainingSpots = player.getPath().size() - pawn.getLocationIndex();
        if (steps > remainingSpots) {
            System.out.println("Cannot move. Skip till next turn.");
            return;
        } else if (steps == remainingSpots)
        {
            System.out.println("Congrats your pawn has entered the kitchen!");
            pawn.setStatus(Pawn.PawnStatus.IN_KITCHEN);
            Cell cell = pawn.getCell();
            cell.removePawnFromCell(pawn);
            return;
        }
        // check for Khal
        if (steps == 0 && pawn.getStatus() == Pawn.PawnStatus.OUT_GAME) {
            pawn.setStatus(Pawn.PawnStatus.IN_GAME);
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
            pawnToKill.setStatus(Pawn.PawnStatus.OUT_GAME);
        }

            System.out.println("You killed enemy pawn(s)!");
    }
    
    public void printInfo() {
        player1.getPawnsStatus();
        player2.getPawnsStatus();
    }

    public void printBoard(){
        // board is divided into 3 blocks
        firstBlock();
        secondBlock();
        thirdBlock();
    }

    public void firstBlock(){
        Cell currentCell;
        int x=8 ,y=7, z=74;
        for(int j = 0; j < 8; j++){
            for(int i = 0; i < 19; i++){
                if(i==8){
                    currentCell = path1.get(x);
                    System.out.print(currentCell);
                }
                else if(i==9) {
                    currentCell = path1.get(y);
                    System.out.print(currentCell);
                }
                else if(i==10) {
                    currentCell = path1.get(z);
                    System.out.print(currentCell);
                }
                else{
                    System.out.print("  ");
                }
            }
            x++; y--; z--;
            System.out.println();
        }
    }

    public void secondBlock(){
        Cell currentCell;
        // first line
        int x=23;
        for(int i=0; i<19; i++) {
            if (i == 8 || i == 9 || i == 10) {
                System.out.print("  ");
                x = 66;
            } else {
                currentCell = path1.get(x);
                System.out.print(currentCell);
                x--;
            }
        }
        System.out.println();
        // second line
        currentCell = path1.get(24);
        System.out.print(currentCell);
        for(int i=0; i<17; i++){
            System.out.print("  ");
        }
        currentCell = path1.get(58);
        System.out.print(currentCell);
        System.out.println();
        // third line
        int y = 25;
        for(int i=0; i<19; i++) {
            if (i == 8 || i == 9 || i == 10) {
                System.out.print("  ");
                y = 50;
            } else {
                currentCell = path1.get(y);
                System.out.print(currentCell);
                y++;
            }
        }
        System.out.println();
    }

    public void thirdBlock(){
        Cell currentCell;
        int x=33 ,y=0, z=49;
        for(int j = 0; j < 8; j++){
            for(int i = 0; i < 19; i++){
                if(i==8){
                    currentCell = path1.get(x);
                    System.out.print(currentCell);
                }
                else if(i==9) {
                    currentCell = path2.get(y);
                    System.out.print(currentCell);
                }
                else if(i==10) {
                    currentCell = path1.get(z);
                    System.out.print(currentCell);
                }
                else{
                    System.out.print("  ");
                }
            }
            x++; y++; z--;
            System.out.println();
        }
    }
}
