package hu.pinterbeci.java.se.f1.enums;

import java.util.Arrays;
import java.util.List;

public enum PointingMethod {
    CLASSIC (Arrays.asList(10, 6, 4, 3, 2, 1), false),
    MODERN (Arrays.asList(10, 8, 6, 5, 4, 3, 2, 1), false),
    NEW (Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1), false),
    PRESENT (Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1), true);

    private final List<Integer> value;
    private final boolean plusPoint;

    PointingMethod(List<Integer> value, boolean plusPoint) {
        this.value = value;
        this.plusPoint = plusPoint;
    }

    public List<Integer> getValue() {
        return value;
    }

    public boolean isPlusPoint() {
        return plusPoint;
    }
}
