public class Move {
    private Pawn pawn;
    private int steps;
    private int stepIndex;

    // 0 steps for khal
    public Move(Pawn pawn, int steps, int stepIndex){
        this.pawn = pawn;
        this.steps = steps;
        this.stepIndex = stepIndex;
    }

    @Override
    public String toString() {
        if(steps == 0)
            return "add " + pawn.getName() + " to board";
        return steps +  " steps for " + pawn.getName();
    }

    public int getSteps()   {return this.steps;}

    public Pawn getPawn()   {return this.pawn;}

    public  int getStepIndex()    {return this.stepIndex;}
}
