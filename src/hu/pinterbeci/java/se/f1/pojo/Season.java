package hu.pinterbeci.java.se.f1.pojo;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private int year;
    private List<Race> races;

    public Season() {
        this.races = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }
}
