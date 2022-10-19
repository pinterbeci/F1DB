package hu.pinterbeci.java.se.f1.pojo;

import java.util.List;
import java.util.Map;

public class Places {
    private Map<Integer, Map<Integer, List<Pilot>>> placesPerYear;

    public Map<Integer, Map<Integer, List<Pilot>>> getPlacesPerYear() {
        return placesPerYear;
    }

    public void setPlacesPerYear(Map<Integer, Map<Integer, List<Pilot>>> placesPerYear) {
        this.placesPerYear = placesPerYear;
    }
}
