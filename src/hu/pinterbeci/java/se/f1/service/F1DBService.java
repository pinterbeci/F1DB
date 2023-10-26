package hu.pinterbeci.java.se.f1.service;

import hu.pinterbeci.java.se.f1.enums.Commands;
import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Race;
import hu.pinterbeci.java.se.f1.pojo.Standing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class F1DBService {

    //lényegében a menteni kívánt, és feldolgozásra szoruló adatokat ebben az eljárásban mentem
    // todo
    //  ellenőrzék hiányoznak, így azok abszolválni kell (parancsok kiadásának megfelelő sorrendje az elsődleges)
    //  megfelelő módon kell közölni a hibaüzenetek, olvashatóan és érthetően
    //  a különböző parancsokat érdemes lehet kiszervezni külön-külön eljárásokba (ez már csak szépségiszint növelése miatt esedékes)
    //  fő a megfelelő működés

    public void readAndProcessData(final InputStream inputStream) {
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {

            Map<Integer, List<Race>> seasonsDataMap = new HashMap<>();
            Map<Integer, Map<String, Standing>> pilotsStandingsPerYear = new HashMap<>();
            Map<String, Standing> standingMap = new HashMap<>();
            Standing currentPilotStanding;

            String line;
            while ((line = br.readLine()) != null) {
                Race race = null;
                String[] splitRaceLineArray = line.split(";");

                if (line.startsWith(Commands.RACE.getValue())) {
                    if (splitRaceLineArray.length == 5) {
                        if (Commands.RACE.getValue().equals(splitRaceLineArray[0])) {
                            race = new Race();
                            race.setYear(Integer.parseInt(splitRaceLineArray[1]));
                            race.setName(splitRaceLineArray[2]);
                            race.setNumber(Integer.parseInt(splitRaceLineArray[3]));
                            race.setOdds(Float.parseFloat(splitRaceLineArray[4]));

                            //léptetem egyet előre a br-t, a következő sorra
                            line = br.readLine();
                        }
                    }
                    while (!line.equals(Commands.FINISH.getValue())) {

                        //miután kaptam egy 'FASTEST' parancsot, így az adott verseny végeredményét mentem le egy
                        // 'seasonsDataMap'-nek elnevezett Map-be, amely idényenként összegzi az összes végeredményt
                        if (line.startsWith(Commands.FASTEST.getValue())) {
                            String[] splitFastestLineArray = line.split(";");
                            if (splitFastestLineArray.length == 3 && race != null) {
                                race.setFastestPilot(new Pilot(splitFastestLineArray[1], splitFastestLineArray[2]));
                            }
                            List<Race> races;
                            if (race != null && race.getYear() != null) {
                                if (!seasonsDataMap.containsKey(race.getYear())) {
                                    races = new ArrayList<>();
                                    races.add(race);
                                    seasonsDataMap.put(race.getYear(), races);
                                } else {
                                    races = seasonsDataMap.get(race.getYear());
                                    races.add(race);
                                    seasonsDataMap.put(race.getYear(), races);
                                }
                            }
                        } else {

                            //ahogy érkeznek be az eredmények, annak sorrendjében külön mentem azokat
                            // a 'pilotsStandingsPerYear' Map-be, ami tartalmazza idényenkénti piloták listáját,
                            // illetve azt, hogy az adott idényben milyen eredményeket értek el
                            if (line.startsWith(Commands.RESULT.getValue())) {
                                String[] splitResultLineArray = line.split(";");

                                if (splitResultLineArray.length == 4 && race != null) {
                                    Pilot pilot = new Pilot();
                                    pilot.setFullname(splitResultLineArray[2]);
                                    pilot.setTeamName(splitResultLineArray[3]);

                                    Integer currentPilotCurrentPosition = Integer.parseInt(splitResultLineArray[1]);
                                    pilot.setCurrentRacePosition(currentPilotCurrentPosition);

                                    currentPilotStanding = new Standing();
                                    if (!pilotsStandingsPerYear.containsKey(race.getYear())) {
                                        standingMap = new HashMap<>();

                                        Map<Integer, Integer> positionCnt = new HashMap<>();
                                        positionCnt.put(currentPilotCurrentPosition, 1);
                                        currentPilotStanding.setPositionWithCntOfPosition(positionCnt);
                                        standingMap.put(pilot.getFullname(), currentPilotStanding);

                                        pilotsStandingsPerYear.put(race.getYear(), standingMap);
                                    } else {
                                        Map<String, Standing> currentYearSavedStandingData = pilotsStandingsPerYear.get(race.getYear());
                                        if (!currentYearSavedStandingData.containsKey(pilot.getFullname())) {

                                            Map<Integer, Integer> currentPilotNewPositionData = new HashMap<>();
                                            currentPilotNewPositionData.put(currentPilotCurrentPosition, 1);
                                            currentPilotStanding.setPositionWithCntOfPosition(currentPilotNewPositionData);

                                            currentYearSavedStandingData.put(pilot.getFullname(), currentPilotStanding);
                                            pilotsStandingsPerYear.put(race.getYear(), currentYearSavedStandingData);
                                        } else {
                                            currentPilotStanding = currentYearSavedStandingData.get(pilot.getFullname());
                                            Integer currentPositionCnt = currentPilotStanding.getPositionWithCntOfPosition().get(currentPilotCurrentPosition) == null ? 0
                                                    : currentPilotStanding.getPositionWithCntOfPosition().get(currentPilotCurrentPosition);
                                            currentPositionCnt++;

                                            currentPilotStanding.getPositionWithCntOfPosition().put(currentPilotCurrentPosition, currentPositionCnt);
                                            standingMap.put(pilot.getFullname(), currentPilotStanding);
                                            pilotsStandingsPerYear.put(race.getYear(), standingMap);
                                        }
                                    }
                                    race.getResultList().add(pilot);
                                }
                            }
                        }
                        line = br.readLine();
                    }

                } else if (line.startsWith(Commands.QUERY.getValue())) {

                    String[] splitQueryLineArray = line.split(";");
                    if (splitQueryLineArray.length > 0) {
                        int queryYear;
                        int raceNumber;
                        if (splitQueryLineArray.length == 2) {
                            queryYear = Integer.parseInt(splitRaceLineArray[1]);
                        } else if (splitQueryLineArray.length == 3) {
                            queryYear = Integer.parseInt(splitRaceLineArray[1]);
                            raceNumber = Integer.parseInt(splitRaceLineArray[2]);
                        }
                    }
                } else if (line.startsWith(Commands.POINT.getValue())) {
                    String[] splitPointLineArray = line.split(";");
                    if (splitPointLineArray.length == 2) {
                        String typeOfPointing = splitPointLineArray[1];
                    }
                } else if (line.startsWith(Commands.EXIT.getValue())) {
                    break;
                }
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}
