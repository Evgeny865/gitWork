package Race;

public class Racer implements Runnable
{
    private static int globalId = 1;
    private int id;
    private final int speed;
    private Track track;

    public Racer (int speed, Track track){
        this.id = nextId();
        this.track = track;
        if(!checkSpeed(speed)) {
            throw new IllegalArgumentException("Speed must be between 1 and 10");
        }
        this.speed = speed;

    }

    private synchronized static int nextId()
    {
        return globalId++;
    }
    private static boolean checkSpeed(int speed){
       return speed >= 1 && speed <= 10;

    }
    public void go (Racer racer) {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Runner: " + id + " ran " + i + " meters");

            if (i == 5) {
                {
                    synchronized (racer ) {
                        track.setFinishRacers();
                        System.out.println("Runner: " + id + " finished " + i + " meters " + track.finishRacers + getPlace(track.getFinishRacers()));

                    }
                }
            }
        }
    }
    private synchronized String getPlace(int numberPlace){
        if(numberPlace >= 11 && numberPlace <= 13){
            return "th";
        }
        switch (numberPlace % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default:return "th";
        }
    }

    @Override
    public void run(){
        Thread.currentThread().setPriority(speed);
        go(this);
    }
}
