package racing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class Race {
    private ArrayList<Stage> stages;
    private CyclicBarrier cyclicBarrier;
    private volatile boolean isWon = false;
    private volatile boolean isReady = false;

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(int numberOfParticipants, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.cyclicBarrier = new CyclicBarrier(numberOfParticipants);
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}