package hu.pinterbeci.java.se.f1.validation;

import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Race;

import java.util.List;

public class Validator {

    private boolean theFastestBelongTheResultList(String fullaname, List<Pilot> pilotList) {
        return pilotList.stream().anyMatch(pilot -> pilot.getFullname().equals(fullaname));
    }

    private boolean validateRace(Race race){
        return false;
    }

    private boolean validatePilot(Race race){
        return false;
    }



}
