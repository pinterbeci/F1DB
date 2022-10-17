package hu.pinterbeci.java.se.f1.enums;

public enum Commands {
    RACE ("RACE"),
    RESULT ("RESULT"),
    FASTEST ("FASTEST"),
    FINISH ("FINISH"),
    QUERY ("QUERY"),
    POINT ("POINT"),
    EXIT ("EXIT");

    private final String value;

    Commands(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
