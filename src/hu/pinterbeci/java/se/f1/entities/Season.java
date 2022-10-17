package hu.pinterbeci.java.se.f1.entities;

import java.util.Set;

public class Season {
    /**
     * "id"-ként fogom használni, mely alapján különítem el az idényeket.
     */
    private int year;

    private Set<Pilot> pilotsOfThisSeason;

    private Set<Result> resultOfThisSeason;

    public Season() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Pilot> getPilotsOfThisSeason() {
        return pilotsOfThisSeason;
    }

    public void setPilotsOfThisSeason(Set<Pilot> pilotsOfThisSeason) {
        this.pilotsOfThisSeason = pilotsOfThisSeason;
    }

    public Set<Result> getResultOfThisSeason() {
        return resultOfThisSeason;
    }

    public void setResultOfThisSeason(Set<Result> resultOfThisSeason) {
        this.resultOfThisSeason = resultOfThisSeason;
    }
}
