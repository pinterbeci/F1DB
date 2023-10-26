package hu.pinterbeci.java.se.f1.pojo;

import java.util.HashMap;
import java.util.Map;

public class Pilot {
    private String fullname;
    private String teamName;
    private Map<Integer, Integer> positionList;

    public Pilot() {
        this.positionList = new HashMap<>();
    }

    public Pilot(String fullname, String teamName) {
        this.fullname = fullname;
        this.teamName = teamName;
        this.positionList = new HashMap<>();
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

    public Map<Integer, Integer> getPositionList() {
        return positionList;
    }

    public void setPositionList(Map<Integer, Integer> positionList) {
        this.positionList = positionList;
    }
}
