import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TossResult {
    private int shellCount;
    private boolean isKhal;
    private double tossProbability;
    private int steps;

    public TossResult(int shellCount) {
        this.shellCount = shellCount;
        setSteps_IsKhal_TossProbability(shellCount);
    }

    private void setSteps_IsKhal_TossProbability(int shellCount) {
        switch (shellCount) {
            case 0:
                steps = 12;
                isKhal = false;
                tossProbability = 0.047; // bara , all down , %4.7
                break;
            case 1:
                steps = 24;
                isKhal = true;
                tossProbability = 0.187; // banj , one up, %18.7
                break;
            case 2:
                steps = 2;
                isKhal = false;
                tossProbability =0.311; // deux, two up, %31.1
                break;
            case 3:
                steps = 3;
                isKhal = false;
                tossProbability =0.276; // three, three up, %27.6
                break;
            case 4:
                steps = 4;
                isKhal = false;
                tossProbability =0.138; // four , four up , %13.8
                break;
            case 5:
                steps = 10;
                isKhal = true;
                tossProbability =0.037; // dust , five up , %3.7
                break;
            case 6:
                steps = 6;
                isKhal = false;
                tossProbability =0.004; // shakka, all up , %0.4
                break;
            default:
                // Handle any unexpected values of shellCount
                throw new IllegalArgumentException("Invalid shell count: " + shellCount);
        }
    }
    public double getTossProbability() {
        return tossProbability;
    }
    public int getShellCount() {
        return shellCount;
    }
    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }

    public boolean getIsKhal() {
        return isKhal;
    }

    public void setIsKhal(boolean isKhal){
        this.isKhal = isKhal;
    }

    @Override
    public String toString() {
        return "Shell count: " + shellCount + " - Steps: " +  steps + " - Khal: " + isKhal ;
    }
}
    // for probabilityOfShellFacingDown = 0.5
     /*   map.put(0, 0.015625); // bara , all down , %1.5
        map.put(1, 0.09375); // banj , one up, %9.3
        map.put(2, 0.234375); // deux, two up, %23.43
        map.put(3, 0.3125); // three, three up, %31.25
        map.put(4, 0.234375); // four , four up , %23.43
        map.put(5, 0.09375); // dust , five up , %9.3
        map.put(6, 0.015625); // shakka, all up , %1.5 */
