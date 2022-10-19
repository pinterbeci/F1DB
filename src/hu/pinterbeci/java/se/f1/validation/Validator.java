package hu.pinterbeci.java.se.f1.validation;

import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Race;
import hu.pinterbeci.java.se.f1.pojo.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {

    public static boolean theFastestBelongTheResultList(String fullaname, List<Pilot> pilotList) {
        return pilotList.stream().anyMatch(pilot -> pilot.getFullname().equals(fullaname));
    }

    public static boolean isValidSeason(Race race) {
        List<Float> validOdds = new ArrayList<>(Arrays.asList(0.0f, 0.5f, 1.0f, 2.0f));
        return race != null
                && "Grand Prix".contains(race.getGpName())
                && validOdds.contains(race.getOdds())
                && race.getNumberOfCurrentGP() > 0;
    }

    public static boolean isValidPilot(Pilot pilot) {
        return pilot != null && !"".equals(pilot.getTeamName()) && "".equals(pilot.getFullname()) && pilot.getPoints() > -1;
    }

    public static boolean isValidSeason(Season season) {
        return season != null
                && (season.getYear() > 1945
                && season.getYear() < 2023)
                && season.getRacesOfSeason().size() > 0
                && season.getPilotsOfThisSeason().size() > 0;
    }

}
