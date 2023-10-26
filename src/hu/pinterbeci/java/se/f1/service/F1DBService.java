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

                            if (line.startsWith(Commands.RESULT.getValue())) {
                                String[] splitResultLineArray = line.split(";");

                                if (splitResultLineArray.length == 4 && race != null) {
                                    Pilot pilot = new Pilot();
                                    pilot.setFullname(splitResultLineArray[2]);
                                    pilot.setTeamName(splitResultLineArray[3]);

                                    Integer currentPilotCurrentPosition = Integer.parseInt(splitResultLineArray[1]);
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
                                    race.getPilotList().add(pilot);
                                }
                            }
                        }
                        line = br.readLine();
                    }

                } else if (line.startsWith(Commands.QUERY.getValue())) {

                } else if (line.startsWith(Commands.POINT.getValue())) {

                } else if (line.startsWith(Commands.EXIT.getValue())) {
                    break;
                }
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}
