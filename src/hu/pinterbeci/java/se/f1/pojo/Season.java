package hu.pinterbeci.java.se.f1.pojo;

import java.util.Set;

public class Season {
    /**
     * "id"-ként fogom használni, mely alapján különítem el az idényeket.
     */
    private int year;

    private Set<Pilot> pilotsOfThisSeason;

    private Set<Race> racesOfSeason;


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

    public Set<Race> getRacesOfSeason() {
        return racesOfSeason;
    }

    public void setRacesOfSeason(Set<Race> racesOfSeason) {
        this.racesOfSeason = racesOfSeason;
    }
}
