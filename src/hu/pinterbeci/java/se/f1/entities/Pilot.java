package hu.pinterbeci.java.se.f1.entities;

public class Pilot {

    private String fullname;

    private String teamName;

    private int points;

    private int fastestLapsPerYear;

    public Pilot() {
    }


    public String getFullname() {
        return fullname;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFastestLapsPerYear() {
        return fastestLapsPerYear;
    }

    public void setFastestLapsPerYear(int fastestLapsPerYear) {
        this.fastestLapsPerYear = fastestLapsPerYear;
    }
}
