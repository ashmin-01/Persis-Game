import java.util.*;
public class Move {
    private final Pawn pawn;
    private final int steps;
    private final int stepIndex;

    // 0 steps for khal
    public Move(Pawn pawn, int steps, int stepIndex){
        this.pawn = pawn;
        this.steps = steps;
        this.stepIndex = stepIndex;
    }

    // Copy constructor
    public Move(Move other) {
        this.pawn = new Pawn(other.pawn);  // Assuming Pawn has a copy constructor
        this.steps = other.steps;
        this.stepIndex = other.stepIndex;
    }

    @Override
    public String toString() {
        if(steps == 0)
            return "add " + pawn.getName() + " to board";
        return steps +  " steps for " + pawn.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return steps == move.steps && stepIndex == move.stepIndex && Objects.equals(pawn, move.pawn);
    }

    @Override
    public int hashCode() {
         return Objects.hash(pawn, steps, stepIndex);
        }

    public int getSteps()   {return this.steps;}

    public Pawn getPawn()   {return this.pawn;}

    public  int getStepIndex()    {return this.stepIndex;}
}
