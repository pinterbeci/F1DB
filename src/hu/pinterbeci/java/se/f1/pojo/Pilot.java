package hu.pinterbeci.java.se.f1.pojo;

public class Pilot {

    private String fullname;

    private String teamName;

    private int points;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pilot) {
            return this.fullname.equals(((Pilot) o).fullname) && this.teamName.equals(((Pilot) o).teamName);
        }
        return false;
    }




}
