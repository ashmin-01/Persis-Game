public class TossResult {
    private int shellCount;
    private boolean isKhal;
    private int steps;

    public TossResult(int shellCount) {
        this.shellCount = shellCount;
        setStepsAndIsKhal(shellCount);
    }

    private void setStepsAndIsKhal(int shellCount) {
        switch (shellCount) {
            case 0: // شكة
                steps = 6;
                isKhal = false;
                break;
            case 1: // دست
                steps = 10;
                isKhal = true;
                break;
            case 2: // دوا
                steps = 2;
                isKhal = false;
                break;
            case 3: // 3
                steps = 3;
                isKhal = false;
                break;
            case 4: // 4
                steps = 4;
                isKhal = false;
                break;
            case 5: // بنج
                steps = 24;
                isKhal = true;
                break;
            case 6: // بارا
                steps = 12;
                isKhal = false;
                break;
            default:
                // Handle any unexpected values of shellCount
                throw new IllegalArgumentException("Invalid shell count: " + shellCount);
        }
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps){
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
