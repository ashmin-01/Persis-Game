import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Board board ;

    public Game(String player1Type, String player2Type){
        board = new Board(player1Type, player2Type);
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
            humanTurn(board.player2);
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
