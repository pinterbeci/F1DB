package hu.pinterbeci.java.se.f1.entities;

import java.util.Set;

public class Race {

    private String gpName;

    private int numberOfCurrentGP;

    private int odds;

    private Set<Result> finalResultOfGP;

    public Race() {
    }


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

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }

    public Set<Result> getFinalResultOfGP() {
        return finalResultOfGP;
    }

    public void setFinalResultOfGP(Set<Result> finalResultOfGP) {
        this.finalResultOfGP = finalResultOfGP;
    }
}
