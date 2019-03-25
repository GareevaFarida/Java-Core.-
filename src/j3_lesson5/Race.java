package j3_lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Race {
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        Synhronization.countDownLatchFinish = new CountDownLatch(this.stages.size()*Synhronization.CARS_COUNT);
    }
}
