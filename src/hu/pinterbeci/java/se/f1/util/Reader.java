package hu.pinterbeci.java.se.f1.util;

import hu.pinterbeci.java.se.f1.entities.Pilot;
import hu.pinterbeci.java.se.f1.entities.Race;
import hu.pinterbeci.java.se.f1.entities.Result;
import hu.pinterbeci.java.se.f1.entities.Season;
import hu.pinterbeci.java.se.f1.enums.Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Reader {

    public static StringBuffer raceReader(String fileUrl) {

        Map<String, Season> races = new HashMap<>();

        BufferedReader reader;
        StringBuffer readedFile = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(fileUrl));
            String line = reader.readLine();

            Race currentRace = new Race();
            Set<Result> resultSet = new HashSet<>();
            Pilot pilot = new Pilot();
            Pilot fastestPilot = new Pilot();
            //továbbiakban setter-ekkel állítom az értékeket, és csak a finish után adok a referenciáknak új példányt

            while (line != null && !line.equals(Commands.EXIT.getValue())) {

                String[] splittedLine = splitter(line);
                String command = splittedLine[0];
                String raceDate = "";


                if (Commands.RACE.getValue().equals(command) && validRace(splittedLine)) {
                    raceDate = splittedLine[1];
                    String gpName = splittedLine[2];
                    int gpNumb = Integer.parseInt(splittedLine[3]);
                    float odds = Float.parseFloat(splittedLine[4]);

                } else if (Commands.RESULT.getValue().equals(command)) {

                } else if (Commands.FASTEST.getValue().equals(command)) {
                    //megnézem között van-e az indulóknak
                } else if (Commands.FINISH.getValue().equals(command)) {
                    //elpakolom, ha egy fastest, és az szerepel az eredmény listán
                    //illetve, ha az eredmény lista tartalma 10 vagy annál több
                    //kulcs lesz az év
                    if (currentRace != null) {
                        currentRace.setFinalResultOfGP(resultSet);
                    }
                    races.put(raceDate, new Season());

                    currentRace = new Race();
                    resultSet = new HashSet<>();
                    pilot = new Pilot();
                    fastestPilot = new Pilot();
                }

                readedFile.append(line).append("\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Kivétel a fájlolvasás során! " + e.getMessage());
        }
        return readedFile;
    }


    private boolean validPilot() {

        return true;
    }

    private static boolean validRace(String[] splittedArray) {

        int year = Integer.parseInt(Objects.requireNonNull(splittedArray)[1]);
        String nameOfGP = splittedArray[2];
        int gpNumb = Integer.parseInt(splittedArray[3]);
        float odds = Float.parseFloat(splittedArray[4]);

        Float[] validOdds = {0.0f, 0.5f, 1.0f, 2.0f};
        boolean isValidOdds = Arrays.asList(validOdds).contains(odds);
        boolean isValidGPName = nameOfGP != null && !nameOfGP.isEmpty();
        boolean isValidPlace = gpNumb > 0 && gpNumb < 30;
        boolean isValidYear = year > 1945 && year < 2023;

        return isValidGPName && isValidOdds && isValidYear && isValidPlace;

    }

    private boolean isFastestBelongTheResultList(String fullaname, List<Pilot> pilotList) {
        return pilotList.stream().anyMatch(pilot -> pilot.getFullname().equals(fullaname));
    }

    private static String[] splitter(String str) {
        try {
            String[] splittedStr = str.trim().split(";");
            String command = splittedStr[0];
            if (Commands.RACE.getValue().equals(command) && splittedStr.length == 5) {
                return splittedStr;
            } else if (Commands.RESULT.getValue().equals(command) && splittedStr.length == 4) {
                return splittedStr;
            } else if (Commands.FASTEST.getValue().equals(command) && splittedStr.length == 3) {
                return splittedStr;
            } else {
                return new String[0];
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("null érték a splittelés során str( " + str + " )");
        }
    }

}
