package hu.pinterbeci.java.se.f1.pojo;

public class Race {

    private String gpName;

    private int numberOfCurrentGP;

    private float odds;

    private Pilot fastestPilot;


    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public int getNumberOfCurrentGP() {
        return numberOfCurrentGP;
    }

    public void setNumberOfCurrentGP(int numberOfCurrentGP) {
        this.numberOfCurrentGP = numberOfCurrentGP;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public Pilot getFastestPilot() {
        return fastestPilot;
    }

    public void setFastestPilot(Pilot fastestPilot) {
        this.fastestPilot = fastestPilot;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Race) {
            return this.gpName.equals(((Race) o).gpName) && this.numberOfCurrentGP == ((Race) o).numberOfCurrentGP;

        }
        return false;
    }

}
