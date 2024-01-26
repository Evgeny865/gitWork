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


    public void go() throws InterruptedException {

        for (int i = 1; i <= 100; i++) {
            System.out.println("Runner: " + id + " ran " + i + " meters" );
            synchronized (Track.class){
            if (i == 100) {
                    Track.finishRacers++;
                    System.out.println("Runner: " + id + " finished " + i + " meters " +
                            Track.finishRacers + getPlace(Track.finishRacers));
                }
            }
        }
    }

    private String getPlace(int numberPlace) {

        return switch (numberPlace %10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
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
