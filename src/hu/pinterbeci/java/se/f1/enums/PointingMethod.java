package hu.pinterbeci.java.se.f1.enums;

public enum PointingMethod {
    CLASSIC ("CLASSIC"),
    MODERN ("MODERN"),
    NEW ("NEW"),
    PRESENT ("PRESENT");

    private final String value;

    PointingMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
