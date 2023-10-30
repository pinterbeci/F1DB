package hu.pinterbeci.java.se.f1.service;

import hu.pinterbeci.java.se.f1.enums.Commands;
import hu.pinterbeci.java.se.f1.enums.PointingMethod;
import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Race;
import hu.pinterbeci.java.se.f1.pojo.Standing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

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
            boolean isStartedRace = false;
            boolean isFinishedRace = false;

            while ((line = br.readLine()) != null) {
                Race race = null;
                String[] splitRaceLineArray = line.split(";");

                if (line.startsWith(Commands.RACE.getValue())) {
                    if (splitRaceLineArray.length == 5) {
                        if (Commands.RACE.getValue().equals(splitRaceLineArray[0])) {
                            isStartedRace = true;
                            race = new Race();
                            race.setYear(Integer.parseInt(splitRaceLineArray[1]));
                            race.setName(splitRaceLineArray[2]);
                            race.setNumber(Integer.parseInt(splitRaceLineArray[3]));
                            race.setOdds(Float.parseFloat(splitRaceLineArray[4]));

                            //léptetem egyet előre a br-t, a következő sorra
                            line = br.readLine();
                        }
                    }
                    List<String> allowedCommands = Arrays.asList(Commands.RESULT.getValue(), Commands.FINISH.getValue(), Commands.FASTEST.getValue());
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
                        }
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

                        line = br.readLine();

                        if (!checkCommandFitIn(allowedCommands, line)) {
                            System.out.println("Nem megfelelő parancs történt! Az oka: 'RESULT' parancsot csakis 'RACE és 'FINISH' parancs között szabad kiadni!");
                            break;
                        }

                        if (line.startsWith(Commands.FINISH.getValue())) {
                            //volt race parancs már
                            //ha még nem volt finish és legalább 10 result parancs volt
                        }

                    }
                } else if (line.startsWith(Commands.QUERY.getValue())) {

                } else if (line.startsWith(Commands.POINT.getValue())) {
                    String[] splitPointLineArray = line.split(";");
                    if (splitPointLineArray.length == 2) {
                        String typeOfPointing = splitPointLineArray[1];
                    }
                } else if (line.startsWith(Commands.EXIT.getValue())) {
                    //próba vb számítás
                    pilotsRatingEndOfSeason(seasonsDataMap, 2018, 8, PointingMethod.PRESENT);
                    break;
                }
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    private boolean checkCommandFitIn(final List<String> allowedCommands, final String line) {
        String[] splitLineArray = line.split(";");
        if (splitLineArray.length < 1)
            return false;
        return allowedCommands.contains(splitLineArray[0]);
    }


    private Map<String, Integer> pilotsRatingEndOfSeason(final Map<Integer, List<Race>> seasonsDataMap, final Integer queryYear,
                                                         final Integer queryRaceNumber, final PointingMethod pointingMethod) {

        Map<String, Integer> result = new HashMap<>();

        if (seasonsDataMap == null || queryYear == null) {
            return new HashMap<>();
        }

        if (!seasonsDataMap.containsKey(queryYear)) {
            return new HashMap<>();
        }

        final List<Race> querySeason = seasonsDataMap.get(queryYear);
        final List<Integer> pointingMethodPointList = getPointingMethodPointList(pointingMethod);
        final boolean plusPoint = isPlusPoint(pointingMethod);
        final boolean queryPartOfTheSeason = queryRaceNumber != null && queryRaceNumber < querySeason.size();
        Integer pointByPointMethod;

        for (Race currentRaceOfSeason : querySeason) {
            for (Pilot currentPilot : currentRaceOfSeason.getResultList()) {
                Integer reachedPointOfPilot;
                if (!result.containsKey(currentPilot.getFullname())) {
                    reachedPointOfPilot = 0;

                    if (pointingMethodPointList.size() >= currentPilot.getCurrentRacePosition()) {
                        reachedPointOfPilot = pointingMethodPointList.get(currentPilot.getCurrentRacePosition() - 1);

                        //todo
                        //  A pont szorzó értéke --> A megszerezhető pontszámot meg kell szorozni ezzel az
                        //   értékkel a világbajnokság eredményének számításakor.
                        // reachedPointOfPilot *= (int)currentRaceOfSeason.getOdds();
                    }
                } else {
                    reachedPointOfPilot = result.get(currentPilot.getFullname());
                    pointByPointMethod = 0;

                    if (pointingMethodPointList.size() >= currentPilot.getCurrentRacePosition()) {
                        pointByPointMethod = pointingMethodPointList.get(currentPilot.getCurrentRacePosition() - 1);
                    }
                    reachedPointOfPilot += pointByPointMethod;
                    //todo
                    //  A pont szorzó értéke --> A megszerezhető pontszámot meg kell szorozni ezzel az
                    //  értékkel a világbajnokság eredményének számításakor.
                    //  reachedPointOfPilot *= (int)currentRaceOfSeason.getOdds();
                }

                if (plusPoint) {
                    if (currentRaceOfSeason.getFastestPilot().getFullname().equals(currentPilot.getFullname())) {
                        reachedPointOfPilot = result.get(currentPilot.getFullname());
                        if (reachedPointOfPilot == null) {
                            reachedPointOfPilot = 0;
                        } else {
                            reachedPointOfPilot++;
                        }
                    }
                }
                result.put(currentPilot.getFullname(), reachedPointOfPilot);
            }
            if (queryPartOfTheSeason) {
                if (currentRaceOfSeason.getNumber() == queryRaceNumber) {
                    break;
                }
            }
        }
        return result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) ->
                        oldValue, LinkedHashMap::new));
    }

    private List<Integer> getPointingMethodPointList(final PointingMethod method) {
        switch (method) {
            case CLASSIC:
                return PointingMethod.CLASSIC.getPointList();
            case MODERN:
                return PointingMethod.MODERN.getPointList();
            case NEW:
                return PointingMethod.NEW.getPointList();
            case PRESENT:
                return PointingMethod.PRESENT.getPointList();
            default:
                return new ArrayList<>();
        }
    }

    private boolean isPlusPoint(final PointingMethod method) {
        switch (method) {
            case CLASSIC:
                return PointingMethod.CLASSIC.isPlusPoint();
            case MODERN:
                return PointingMethod.MODERN.isPlusPoint();
            case NEW:
                return PointingMethod.NEW.isPlusPoint();
            case PRESENT:
                return PointingMethod.PRESENT.isPlusPoint();
            default:
                return false;
        }
    }
}
