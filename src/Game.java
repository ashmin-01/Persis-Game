import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game {

    static Board board ;

    public Game(String player1Type, String player2Type){
        board = new Board(player1Type, player2Type);
    }

    // Copy constructor
    public Game(Game other) {
        this.board = new Board(other.board);
    }

    private void humanTurn(Player player){
        // Tossing
        ArrayList<Integer> stepsList = TossShells.tossShells(player);
        ArrayList<Pawn> pawns = player.getPawns();

        while (!stepsList.isEmpty()) {
            ArrayList<Move> validMoves = board.validMoves(stepsList, pawns);

            if(!validMoves.isEmpty()){
                // choose a move index
                int moveIndex = chooseMove(validMoves);
                Move chosenMove = validMoves.get(moveIndex);
                // move the pawn
                board.move(player, chosenMove.getPawn(), chosenMove.getSteps());
                // remove from stepList
                int stepIndex = chosenMove.getStepIndex();
                stepsList.remove(stepIndex);
            }
            else{
                System.out.println("No valid moves!!!");
                return;
            }
        }
    }

    private int chooseMove(ArrayList<Move> validMoves){
        System.out.println("Choose a move: ");
        for(int i = 0; i < validMoves.size(); i++){
            System.out.println(i + ": " + validMoves.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        boolean valid = false;

        while (!valid) {
            try {
                choice = scanner.nextInt();
                if (choice >= 0 && choice < validMoves.size()) { // Check if the choice is within the range of valid moves
                    valid = true; // Set the valid flag to true
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + (validMoves.size() - 1));
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the input buffer
            }
        }
        return choice;
    }

    private void computerTurn(Player player){
        Move bestMove = findBestMove(board,true, 3);
        if (bestMove != null) {
            board.move(player, bestMove.getPawn(), bestMove.getSteps());
        } else {
            System.out.println("No valid moves for the computer.");
        }
    }

    Move findBestMove(Board board,boolean isMaxPlayer,int depth){
        ArrayList<Integer> stepsList = TossShells.tossShells(isMaxPlayer?board.player2:board.player1);
        ArrayList<Pawn> pawns = isMaxPlayer? board.player2.getPawns():board.player1.getPawns();
        int highScore=Integer.MIN_VALUE;
        Move bestMove=null;
        List<Move>moves=board.getPossibleMoves(stepsList,pawns);
        for (Move move: moves) {
            Board newBoard=new Board(board);
            newBoard.move(isMaxPlayer?board.player2:board.player1,move.getPawn(),move.getSteps());
            ArrayList<Integer>newSteps=new ArrayList<>(stepsList);
            newSteps.remove((Object)move.getSteps());
            int value=bestValue(newBoard,newSteps,depth,isMaxPlayer);
            if(value>=highScore){
                highScore=value;
                bestMove=move;
            }
        }
        return bestMove;
    }

    int bestValue(Board board,ArrayList<Integer> toss,int depth,boolean isMaxPlayer){
        if(depth==0||board.isFinished()){
            return evaluateState(board.player2);
        }
        if(toss.isEmpty()||board.getPossibleMoves(toss,isMaxPlayer? board.player2.getPawns():board.player1.getPawns()).isEmpty())
            return chanceValue(board,toss,depth,isMaxPlayer);
        if(isMaxPlayer)
            return  expectiMax(board, depth, toss, isMaxPlayer);
        return expectiMin(board, depth, toss,isMaxPlayer);
    }

    private int  chanceValue (Board board,ArrayList<Integer> toss,int depth,boolean isMaxPlayer){
        List<Double> possibilty;
        int avg = 0;
//        List<Move> possibleMoves = board.getPossibleMoves(TossShells.tossShells(),board.player1.getPawns());
        for (int i = 0; i < 7; i++)
        {
            possibilty = TossShells.getTossProbability();
            var currentToss=TossShells.getTossSteps(i);
            int score= bestValue(board,currentToss,depth-1,!isMaxPlayer);
            avg +=possibilty.get(i) * score;
        }
        return avg;
    }
    private int expectiMax(Board board,int depth,ArrayList<Integer> toss,boolean isMaxPlayer){
        int maxValue=Integer.MIN_VALUE;
        List<Move> possibleMoves = board.getPossibleMoves(toss,board.player1.getPawns());
        for (Move move : possibleMoves) {
            Board newBoard=new Board(board);
            newBoard.move(isMaxPlayer?board.player2:board.player1,move.getPawn(),move.getSteps());
            ArrayList<Integer>newSteps=new ArrayList<>(toss);
            newSteps.remove((Object)move.getSteps());
            int score = bestValue(newBoard,toss,depth-1,isMaxPlayer);
            maxValue = Math.max(maxValue, score);
        }
        return maxValue;

    }
    private int expectiMin(Board board,int depth,ArrayList<Integer> toss,boolean isMaxPlayer){
        int minValue=Integer.MAX_VALUE;
        List<Move> possibleMoves = board.getPossibleMoves(toss,board.player1.getPawns());
        for (Move move : possibleMoves) {
            Board newBoard=new Board(board);
            newBoard.move(isMaxPlayer?board.player2:board.player1,move.getPawn(),move.getSteps());
            ArrayList<Integer>newSteps=new ArrayList<>(toss);
            newSteps.remove((Object)move.getSteps());
            int score = bestValue(newBoard,toss,depth-1,isMaxPlayer);
            minValue = Math.min(minValue, score);
        }
        return minValue;

    }
    private static int evaluateState(Player player) {
        var sum=0;
        var computer_pawns=countPawnsInGame(board.player2)*10+countPawnsInKitchen(board.player2)*50;
        var human_pawns=-(countPawnsInGame(board.player1)*10+countPawnsInKitchen(board.player1)*50);
        var computer_safe=0;
        var close_toKitchen=0;
        var attack_enemy=0;
        for(int i=0;i<countPawnsInGame(board.player2);i++) {
            if (board.isSafe(board.player2,player.getPawns().get(i))) {
                computer_safe+=15;
            }
            if(player.getPawns().get(i).getCell().isProtected()){
                computer_safe+=25;
            }
            else if(!player.getPawns().get(i).getCell().isProtected()) {
                computer_safe -= 25;
            }
        }
        for (int i=0;i<4;i++){
            if(board.distanceBetweenAllPawnsAndPrivatePath(board.player2).get(i)==0){
                close_toKitchen+=5;
            } else if (board.distanceBetweenAllPawnsAndPrivatePath(board.player2).get(i)==-1) {
                close_toKitchen-=10;
            }
            else{
                close_toKitchen+=5;
            }
//            for (int j = 0; j < 4; j++) {
//                if (board.distanceBetweenAllPawnsAndEnemyPawns(board.player2).get(i).get(j)==-1){
//                    attack_enemy+=0;
//                }
//                else{
//                    attack_enemy+=15;
//                }
//            }
        }
        if(player.getType()=="computer"&&board.isWin(player)){
            return Integer.MAX_VALUE;
        }
        else if(player.getType()!="computer"&&!board.isWin(player)){
            return Integer.MIN_VALUE;
        }
        sum=computer_safe+computer_pawns+human_pawns+close_toKitchen+attack_enemy;
        return sum;
    }

    private static int countPawnsInKitchen(Player player) {
        int count = 0;
        for (Pawn pawn : player.getPawns()) {
            if (pawn.getStatus() == Pawn.PawnStatus.IN_KITCHEN) {
                count++;
            }
        }
        return count;
    }

    private static int countPawnsInGame(Player player) {
        int count = 0;
        for (Pawn pawn : player.getPawns()) {
            if (pawn.getStatus() == Pawn.PawnStatus.IN_GAME) {
                count++;
            }
        }
        return count;
    }


    private void play(){
        while (true) {
            // show board
            board.printInfo();
            board.printBoard();
            System.out.println("distance between all pawns and enemy pawns for player 1 is :\n"
                    + board.distanceBetweenAllPawnsAndEnemyPawns(board.player1));
            System.out.println("distance between all pawns and private path for player 1 is :\n"
                    + board.distanceBetweenAllPawnsAndPrivatePath(board.player1));
            // player1
            System.out.println("\nPlayer1 Human Turn");
            humanTurn(board.player1);
            if (board.isWin(board.player1)) {
                System.out.println("Congratulations you won :'(");
                break;
            }
            board.printInfo();
            board.printBoard();

            // player2
            System.out.println("\nPlayer2 Computer Turn");
            computerTurn(board.player2);
            if (board.isWin(board.player2)) {
                System.out.println("Congratulations you won :'(");
                break;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Game game = new Game("human", "Computer");
        game.play();
    }

}
