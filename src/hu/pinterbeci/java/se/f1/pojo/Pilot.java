package hu.pinterbeci.java.se.f1.pojo;

public class Pilot {
    private String fullname;
    private String teamName;
    private int currentRacePosition;
    private int point;

    public Pilot() {
    }

    public Pilot(String fullname, String teamName) {
        this.fullname = fullname;
        this.teamName = teamName;
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

    public int getCurrentRacePosition() {
        return currentRacePosition;
    }

    public void setCurrentRacePosition(int currentRacePosition) {
        this.currentRacePosition = currentRacePosition;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
