package hu.pinterbeci.java.se.f1.pojo;

import java.util.Map;

public class Standing {

    private Map<Integer, Integer> positionWithCntOfPosition;

    public Standing() {
    }

    public Map<Integer, Integer> getPositionWithCntOfPosition() {
        return positionWithCntOfPosition;
    }

    public void setPositionWithCntOfPosition(Map<Integer, Integer> positionWithCntOfPosition) {
        this.positionWithCntOfPosition = positionWithCntOfPosition;
    }
}
