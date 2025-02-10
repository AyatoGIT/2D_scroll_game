package src.utils;
public class ScoreManager {
    private int timesGet;
    private int timesAvoid;
    private int timesJumpOn;
    private int score;

    public ScoreManager() {
        this.timesGet = 0;
        this.timesAvoid = 0;
        this.timesJumpOn = 0;
        this.score = 0;
    }

    public int getScore() {
        this.score = timesGet * 100 - timesAvoid * 50 + timesJumpOn * 300;
        return score;
    }

    public int getTimesGet() {
        return timesGet;
    }

    public void setTimesGet(int timesGet) {
        this.timesGet = timesGet;
    }

    public int getTimesAvoid() {
        return timesAvoid;
    }

    public void setTimesAvoid(int timesAvoid) {
        this.timesAvoid = timesAvoid;
    }

    public int getTimesJumpOn() {
        return timesJumpOn;
    }

    public void setTimesJumpOn(int timesJumpOn) {
        this.timesJumpOn = timesJumpOn;
    }

}
