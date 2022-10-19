package hu.pinterbeci.java.se.f1.pojo;

import java.util.List;

public class Pilot {

    private String fullname;

    private String teamName;

    private float points;

    private List<Integer> places;

    public Pilot() {
    }

    public String getFullname() {
        return fullname;
    }

    public List<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(List<Integer> places) {
        this.places = places;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pilot) {
            return this.fullname.equals(((Pilot) o).fullname) && this.teamName.equals(((Pilot) o).teamName);
        }
        return false;
    }




}
