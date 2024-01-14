import java.util.ArrayList;
import java.util.List;
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
        ArrayList<Integer> stepsList = TossShells.tossShells();
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
        ArrayList<Integer> stepsList = TossShells.tossShells();
        ArrayList<Pawn> pawns = board.player2.getPawns();
        ArrayList<Board>Nodes=new ArrayList<>();
        ArrayList<Move>Moves=new ArrayList<>();
        ArrayList<Integer> stepsListCopy = new ArrayList<>(stepsList);
        while(!stepsList.isEmpty()){
            Move bestMove = findBestMove(board,true, 3,stepsList,pawns,Nodes);

            if (bestMove != null) {
                Moves.add(bestMove);
                board.move(player, bestMove.getPawn(), bestMove.getSteps());
            } else {
                System.out.println("No valid moves for the computer.");
                break;
            }
            stepsList.remove(bestMove.getSteps() == 0 ? (Object)1 : (Object)bestMove.getSteps());
        }
        for (Move m:
                Moves) {
            System.out.println("Move:"+m);
        }
        System.out.println("number of visited Nodes:"+Nodes.size());
        System.out.println("steps list:");
        for (Integer integer : stepsListCopy) System.out.print(integer + " ");
    }

    Move findBestMove(Board board,boolean isMaxPlayer,int depth,ArrayList<Integer>stepsList,ArrayList<Pawn>pawns,ArrayList<Board>Nodes){

        int highScore=Integer.MIN_VALUE;
        Move bestMove=null;
        List<Move>moves=board.getPossibleMoves(stepsList,pawns);
        if(moves.size() == 1)
            return moves.get(0);
        for (Move move: moves) {
            Move move1=new Move(move);
            Board newBoard=new Board(board);
            newBoard.move(newBoard.player2,move1.getPawn(),move1.getSteps());
            ArrayList<Integer>newSteps=new ArrayList<>(stepsList);
            newSteps.remove(move.getSteps() == 0 ? (Object)1 : (Object)move.getSteps());
            System.out.println("Depth:"+depth);
            Nodes.add(newBoard);
            int value=bestValue(newBoard,newSteps,depth,isMaxPlayer,Nodes);
            if(value>=highScore){
                highScore=value;
                bestMove=move;
            }
        }
        return bestMove;
    }

    int bestValue(Board board,ArrayList<Integer> toss,int depth,boolean isMaxPlayer,ArrayList<Board>Nodes){
        if(depth==0||board.isFinished()){
            return evaluateState(board.player2,board, depth);
        }
        if(toss.isEmpty()||board.getPossibleMoves(toss,isMaxPlayer? board.player2.getPawns():board.player1.getPawns()).isEmpty())
            return chanceValue(board,depth,isMaxPlayer,Nodes);
        if(isMaxPlayer)
            return  expectiMax(board, depth, toss, isMaxPlayer,Nodes);
        return expectiMin(board, depth, toss,isMaxPlayer,Nodes);
    }

    private int  chanceValue (Board board,int depth,boolean isMaxPlayer,ArrayList<Board>Nodes){
        List<Double> possibilty;
        int avg = 0;
        for (int i = 0; i < 7; i++)
        {
            possibilty = TossShells.getTossProbability();
            var currentToss=TossShells.getTossSteps(i);
            Board newBoard=new Board(board);
//            newBoard.move(newBoard.player2,newBoard.player2.getPawns().get(0),-1);
            System.out.println("Depth:"+depth);
            System.out.println("Chance Node");
            Nodes.add(newBoard);
            int score= bestValue(newBoard,currentToss,depth-1,!isMaxPlayer,Nodes);
            avg += (int) (possibilty.get(i) * score);
        }
        return avg;
    }

    private int expectiMax(Board board,int depth,ArrayList<Integer> toss,boolean isMaxPlayer,ArrayList<Board>Nodes){
        int maxValue=Integer.MIN_VALUE;
        List<Move> possibleMoves = board.getPossibleMoves(toss,board.player1.getPawns());
        for (Move move : possibleMoves) {
            Move move1=new Move(move);
            Board newBoard=new Board(board);
            newBoard.move(isMaxPlayer?newBoard.player2:newBoard.player1,move1.getPawn(),move1.getSteps());
            ArrayList<Integer>newSteps=new ArrayList<>(toss);
            newSteps.remove(move.getSteps() == 0 ? (Object)1 : (Object)move.getSteps());
            System.out.println("Depth:"+depth);
            System.out.println("Max Node:"+move1);
            Nodes.add(newBoard);
            int score = bestValue(newBoard, toss, depth-1,isMaxPlayer,Nodes);
            maxValue = Math.max(maxValue, score);
        }
        return maxValue;
    }

    private int expectiMin(Board board,int depth,ArrayList<Integer> toss,boolean isMaxPlayer,ArrayList<Board>Nodes){
        int minValue=Integer.MAX_VALUE;
        List<Move> possibleMoves = board.getPossibleMoves(toss,board.player1.getPawns());
        for (Move move : possibleMoves) {
            Move move1=new Move(move);
            Board newBoard=new Board(board);
            newBoard.move(isMaxPlayer?newBoard.player2:newBoard.player1,move1.getPawn(),move1.getSteps());
            ArrayList<Integer>newSteps=new ArrayList<>(toss);
            newSteps.remove(move.getSteps() == 0 ? (Object)1 : (Object)move.getSteps());
            System.out.println("Depth:"+depth);
            System.out.println("Min Node:"+move1);
            Nodes.add(newBoard);
            int score = bestValue(newBoard,toss,depth-1,isMaxPlayer,Nodes);
            minValue = Math.min(minValue, score);
        }
        return minValue;
    }

    private static int evaluateState(Player player,Board board, int depth) {
        var sum=0;
        var computer_pawns=countPawnsInGame(board.player2)*30+countPawnsInKitchen(board.player2)*10;
        var human_pawns=-(countPawnsInGame(board.player1)*30+countPawnsInKitchen(board.player1)*10);
        var computer_safe=0;
        var close_toKitchen=0;
        var attack_enemy=0;
        for(int i=0;i<countPawnsInGame(board.player2);i++) {
            if (board.isSafe(board.player2,player.getPawns().get(i))) {
                computer_safe+=10;
            }
            if(player.getPawns().get(i).getCell(player).isProtected()){
                computer_safe+=10;
            }
            else if(!player.getPawns().get(i).getCell(player).isProtected()) {
                computer_safe -= 10;
            }
            for (int j = 0; j < countPawnsInGame(board.player1); j++) {
                if (board.distanceBetweenAllPawnsAndEnemyPawns(board.player2).get(i).get(j)==-1){
                    attack_enemy-=10;
                }
                else{
                    attack_enemy+=10;
                }
            }
        }
        for (int i=0;i<4;i++){
            if(board.distanceBetweenAllPawnsAndPrivatePath(board.player2).get(i)==0){
                close_toKitchen+=5;
            } else if (board.distanceBetweenAllPawnsAndPrivatePath(board.player2).get(i)==-1) {
                close_toKitchen-=5;
            }
            else{
                close_toKitchen+=5;
            }

        }
        if(board.isWin(board.player2)){
            return Integer.MAX_VALUE;
        }
        else if(board.isWin(board.player1)){
            return Integer.MIN_VALUE;
        }
        sum=computer_safe+computer_pawns+human_pawns+close_toKitchen+attack_enemy;
        System.out.println("evaluate value in depth "+ depth+ ": "+ sum);
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
            int userInput;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Enter 0 to let the computer take its turn:");
                userInput = scanner.nextInt();

                if (userInput == 0) {
                    computerTurn(board.player2);

                    if (board.isWin(board.player2)) {
                        System.out.println("Congratulations you won :'(");
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter 0 to let the computer take its turn.");
                }
            } while (userInput != 0);

            System.out.println();
        }
    }

    public static void main(String[] args) {
        Game game = new Game("human", "Computer");
        game.play();
    }

}
