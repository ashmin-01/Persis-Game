import java.util.ArrayList;
import java.util.List;
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
                Move choosenMove = validMoves.get(moveIndex);
                // move the pawn
                board.move(player, choosenMove.getPawn(), choosenMove.getSteps());
                // remove from stepList
                int stepIndex = choosenMove.getStepIndex();
                stepsList.remove(stepIndex);
            }
            else{
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
        System.out.println("Computer Turn");
    }

    private void play(){
        while (true) {
            // show board
            board.printInfo();

            // player1
            System.out.println("\nPlayer1");
            humanTurn(board.player1);
            if (board.isWin(board.player1)) {
                System.out.println("Congratulations you won :'(");
                break;
            }
            board.printInfo();

            // player2
            System.out.println("\nPlayer2");
            humanTurn(board.player2);
            if (board.isWin(board.player2)) {
                System.out.println("Congratulations you won :'(");
                break;
            }

        }
    }

    public static void main(String[] args) {
        Game game = new Game("human", "human");
        game.play();
    }

}
