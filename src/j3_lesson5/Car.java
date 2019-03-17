package j3_lesson5;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static volatile Car winner;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            Synhronization.countDownLatchStart.countDown();
            Synhronization.cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            //определим победителя
            if (i+1==race.getStages().size()&winner==null){
                if(!Synhronization.lock.isLocked()) {
                    Synhronization.lock.lock();
                    winner = this;
                    System.out.println(this.name + " - WIN");
                    Synhronization.lock.unlock();
                }
            }
            Synhronization.countDownLatchFinish.countDown();
        }
    }
}
