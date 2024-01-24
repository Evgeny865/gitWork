package Race;

public class Track {

    int finishRacers;

    public Track(){
        this.finishRacers = 0;
    }

    public synchronized void setFinishRacers(){
        finishRacers++;
    }

    public synchronized int getFinishRacers(){
        return finishRacers;
    }

}
