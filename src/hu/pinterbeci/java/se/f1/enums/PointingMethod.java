package hu.pinterbeci.java.se.f1.enums;

import java.util.Arrays;
import java.util.List;

public enum PointingMethod {
    CLASSIC("CLASSIC", Arrays.asList(10, 6, 4, 3, 2, 1), false),
    MODERN("MODERN", Arrays.asList(10, 8, 6, 5, 4, 3, 2, 1), false),
    NEW("NEW", Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1), false),
    PRESENT("PRESENT", Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1), true);

    private final String value;
    private final List<Integer> pointList;
    private final boolean plusPoint;

    PointingMethod(final String value, final List<Integer> pointList, final boolean plusPoint) {
        this.value = value;
        this.pointList = pointList;
        this.plusPoint = plusPoint;
    }

    public List<Integer> getPointList() {
        return pointList;
    }

    public boolean isPlusPoint() {
        return plusPoint;
    }

    public String getValue() {
        return value;
    }
}
