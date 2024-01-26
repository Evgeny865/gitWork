package Race;

public class Racer implements Runnable {
    private static int globalId = 1;
    final private int id;
    private final int speed;
    private Track track;
    private static final Object lock = new Object();

    public Racer(int speed, Track track) {
            this.id = getNextId();
            this.track = track;
            if (!checkSpeed(speed)) {
                throw new IllegalArgumentException("Speed must be between 1 and 10");
            }
            this.speed = speed;

    }
    private static int getNextId() {
        synchronized (lock) {
            return globalId++;
        }
    }

    private static boolean checkSpeed(int speed) {
        return speed >= 1 && speed <= 10;

    }
    private void runMeters(int meters){
            System.out.println("Runner: " + id + " ran " + meters + " meters" + Thread.currentThread());


    }

    public void go() throws InterruptedException {

        for (int i = 1; i <= 100; i++) {
                runMeters(i);
                    if (i == 100) {
                        synchronized (Track.class) {
                            Track.finishRacers++;
                            System.out.println("Runner: " + id + " finished " + i + " meters " +
                                    Track.finishRacers + getPlace(Track.finishRacers));
                        }
                    }
        }
    }
   private String getPlace(int numberPlace) {

        switch (numberPlace % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    @Override
    public void run(){
        Thread.currentThread().setPriority(speed);
        try {
            go();
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
           e.printStackTrace();
        }
    }
}
