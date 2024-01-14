import java.util.*;


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

    // deep copy constructor :
    public Board(Board board) {
        this.player1 = new Player(board.player1);
        this.player2 = new Player(board.player2);

        this.path1 = new ArrayList<>();
        for (Cell cell : board.path1) {
            this.path1.add(new Cell(cell));
        }
        this.player1.setPath(this.path1);

        this.path2 = new ArrayList<>();
        for (Cell cell : board.path2) {
            this.path2.add(new Cell(cell));
        }
        this.player2.setPath(this.path2);
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

    public Board deepCopy(String player1Type, String player2Type) {
        Board copy = new Board(player1Type, player2Type);
        copy.path1 = deepCopyPath(path1);
        copy.path2 = deepCopyPath(path2);
        copy.player1 = player1.deepCopy(copy.path1);
        copy.player2 = player2.deepCopy(copy.path2);
        return copy;
    }

    private ArrayList<Cell> deepCopyPath(ArrayList<Cell> path) {
        ArrayList<Cell> copy = new ArrayList<>();
        for (Cell cell : path) {
            copy.add(new Cell(cell)); // Assuming you have a copy constructor in Cell class
        }
        return copy;
    }

    public ArrayList<Move> validMoves(ArrayList<Integer> stepsList, ArrayList<Pawn> pawns){
        Set<Move> allMoves = new HashSet<>();
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
        return new ArrayList<>(allMoves);
    }

    public ArrayList<Move> getPossibleMoves(ArrayList<Integer>stepsList,ArrayList<Pawn>pawns) {
        return validMoves(stepsList,pawns);
    }

    public void move(Player player, Pawn pawn, int steps) {

        // Check if the player's next cell is protected and has an opponent's pawn in it
        if (!canMove(player, pawn, steps)){
            System.out.println("Cannot move, enemy pawn in protected cell ahead.");
            return;
        }
        // Check if the player toss result is bigger than the remaining spots
        int remainingSpots = player.getPath().size() - pawn.getLocationIndex();
        if (steps > remainingSpots) {
            System.out.println("Cannot move, Skip till next turn.");
            return;
        } else if (steps == remainingSpots)
        {
            System.out.println("Congrats your pawn has entered the kitchen!");
            pawn.setStatus(Pawn.PawnStatus.IN_KITCHEN);
            Cell cell = pawn.getCell(player);
            cell.removePawnFromCell(pawn);
            return;
        }
        // check for Khal
        if (steps == 0 && pawn.getStatus() == Pawn.PawnStatus.OUT_GAME) {
            pawn.setStatus(Pawn.PawnStatus.IN_GAME);
            pawn.setLocationIndex(0);
            player.addPawnToPath(0, pawn);
        } else {
            player.addPawnToPath(steps, pawn);

            // after moving the pawn to the new location, check if it can kill an opponent's pawn
            if (canKill(pawn)) {
                killOpponentPawn(player, pawn);
            }
        }
    }

    public boolean canMove(Player player, Pawn pawn, int steps) {
        int currentIndex = pawn.getLocationIndex();
        int nextIndex = currentIndex + steps;
        ArrayList<Cell> path = player.getPath();

        // Check if the nextIndex is within the bounds of the path
        if (nextIndex >= 0 && nextIndex < path.size()) {
            Cell nextCell = path.get(nextIndex);


            return !(nextCell.isProtected() && nextCell.hasEnemyPawn(player));
        } else {
            return false;
        }
    }

    public boolean canKill(Pawn pawn){
        // this is the pawn player
        Player enemyPlayer;
        if(Objects.equals(pawn.getPawnType(), "Human"))   enemyPlayer = player1;
        else    enemyPlayer = player2;
        Cell currentCell = pawn.getCell(enemyPlayer);
        return !currentCell.isProtected() && currentCell.hasPawnsFromDifferentPlayers(enemyPlayer);
    }

    public void killOpponentPawn(Player player, Pawn pawn) {
        Player opponent = player.getOpponent();
        Cell currentCell = pawn.getCell(player);

        ArrayList<Pawn> enemyPawns = currentCell.getPlayerPawnsOnCell(opponent);
        for(Pawn pawnToKill : enemyPawns){
            currentCell.removePawnFromCell(pawnToKill);
            pawnToKill.setStatus(Pawn.PawnStatus.OUT_GAME);
        }

        System.out.println("You killed enemy pawn(s)!");
    }

    public boolean isSafe(Player player, Pawn pawn){
        ArrayList<Cell> path = player.getPath();
        int index = pawn.getLocationIndex();

        Player enemyPlayer = player.getOpponent();

        // Ensure index - 13 is a valid index
        if (index - 13 >= 0 && index - 13 < path.size()) {
            Cell cell = path.get(index - 13);
            return !cell.hasPawnsFromDifferentPlayers(enemyPlayer);
        } else {
            return false;
        }
    }

    public int numOfEnemyPawnsInKitchen(Player player){
        int pawnsInKitchen = 0;
        Player opponent = player.getOpponent();
        ArrayList<Pawn> enemyPawns = opponent.getPawns();

        for (Pawn enemy : enemyPawns){
            if (enemy.getStatus() == Pawn.PawnStatus.IN_KITCHEN)
                pawnsInKitchen++;
        }
        return pawnsInKitchen;
    }

    public ArrayList<Integer> distanceBetweenAllPawnsAndPrivatePath(Player player){
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<Pawn> pawns = player.getPawns();
        ArrayList<Cell> path = player.getPath();
        int targetCellIndex = 76;

        for (Pawn pawn : pawns) {
            Cell pawnCell = pawn.getCell(player);
            if (pawn.getStatus() == Pawn.PawnStatus.IN_GAME && path.indexOf(pawnCell) < targetCellIndex) {
                int distanceBetweenPawnAndTarget = Math.abs(path.indexOf(pawnCell) - targetCellIndex);
                distance.add(distanceBetweenPawnAndTarget);
            } else if(pawn.getStatus()==Pawn.PawnStatus.OUT_GAME)
                distance.add(-1);
            else
                distance.add(0); // for when pawn is in kitchen or in the private path .
        }

        return distance;
    }

    public ArrayList<ArrayList<Integer>> distanceBetweenAllPawnsAndEnemyPawns(Player player) {
        Player opponent = player.getOpponent();
        ArrayList<ArrayList<Integer>> allDistances = new ArrayList<>();
        ArrayList<Pawn> playerPawns = player.getPawns();
        ArrayList<Pawn> enemyPawns = opponent.getPawns();

        for (Pawn playerPawn : playerPawns) {
            if (playerPawn.getStatus() == Pawn.PawnStatus.IN_GAME) {
                ArrayList<Integer> distancesForOnePawn = new ArrayList<>();

                for (Pawn enemyPawn : enemyPawns) {
                    if (enemyPawn.getStatus() == Pawn.PawnStatus.IN_GAME) {
                        // Check if the enemy pawn is in front of the player's pawn
                        if (enemyPawn.getLocationIndex() > playerPawn.getLocationIndex()) {
                            int distance = Math.abs(playerPawn.getLocationIndex() - enemyPawn.getLocationIndex());
                            distancesForOnePawn.add(distance);
                        } else
                            distancesForOnePawn.add(-1);
                    }
                }

                allDistances.add(distancesForOnePawn);
            }
        }

        return allDistances;
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
