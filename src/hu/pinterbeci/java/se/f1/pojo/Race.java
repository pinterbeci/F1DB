package hu.pinterbeci.java.se.f1.pojo;

import java.util.HashSet;
import java.util.Set;

public class Race {
    private Integer year;
    private String name;
    private int number;
    private float odds;
    private Pilot fastestPilot;
    private Set<Pilot> resultList;

    public Race() {
        this.resultList = new HashSet<>();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public Set<Pilot> getResultList() {
        return resultList;
    }

    public void setResultList(Set<Pilot> resultList) {
        this.resultList = resultList;
    }

}
