package hu.pinterbeci.java.se.f1.entities;

public class Result {

    private int place;

    private Pilot currentPilot;

    private Pilot fastetsPilot;

    public Result() {
    }

    public Result(int place, Pilot currentPilot) {
        this.place = place;
        this.currentPilot = currentPilot;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Pilot getCurrentPilot() {
        return currentPilot;
    }

    public void setCurrentPilot(Pilot currentPilot) {
        this.currentPilot = currentPilot;
    }

    public Pilot getFastetsPilot() {
        return fastetsPilot;
    }

    public void setFastetsPilot(Pilot fastetsPilot) {
        this.fastetsPilot = fastetsPilot;
    }
}
