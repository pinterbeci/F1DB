package hu.pinterbeci.java.se.f1.reader;

import hu.pinterbeci.java.se.f1.enums.Commands;
import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Race;
import hu.pinterbeci.java.se.f1.pojo.Season;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Reader {

    public static void raceReader(String fileUrl) {

        Map<Integer, Season> szezonok = new HashMap<>();
        Set<Pilot> adottEvPilotaiAdatokkal = new HashSet<>();
        Set<Race> adottEvVerseyAdatok = new HashSet<>();
        Season adottSzezon = new Season();
        Race adottVerseny = new Race();
        int versenyEVe = 0;


        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fileUrl));
            String line = reader.readLine();

            while (line != null && !line.equals(Commands.EXIT.getValue())) {
                String command = line.trim().split(";")[0];

                float szorzo = -1;
                Pilot leggyorsabb = new Pilot();

                if (Commands.RACE.getValue().equals(command)) {
                    adottVerseny = new Race();
                    //szerintem itt kellene két set-et nullozni.

                    String[] splittedString = line.trim().split(";");

                    versenyEVe = Integer.parseInt(splittedString[1]);
                    String versenyNeve = splittedString[2];
                    int hanyadikVersenyAzAdottEvben = Integer.parseInt(splittedString[3]);
                    //todo ez fontos lehet a pontszámításnál
                    szorzo = Float.parseFloat(splittedString[4]);

                    adottVerseny.setGpName(versenyNeve);
                    adottVerseny.setNumberOfCurrentGP(hanyadikVersenyAzAdottEvben);
                    adottVerseny.setOdds(szorzo);

                }
                if (Commands.RESULT.getValue().equals(command)) {
                    String[] splittedString = line.trim().split(";");

                    int helyezes = Integer.parseInt(splittedString[1]);
                    String pilotaTeljesneve = splittedString[2];
                    String csapatNeve = splittedString[3];

                    Pilot versenyzo = new Pilot();
                    versenyzo.setFullname(pilotaTeljesneve);
                    versenyzo.setTeamName(csapatNeve);
                    //ide majd kell egy függvény, ami pontot számít
                    versenyzo.setPoints(helyezes);
                    adottEvPilotaiAdatokkal.add(versenyzo);
                }
                if (Commands.FASTEST.getValue().equals(command)) {
                    String[] splittedString = line.trim().split(";");
                    String pilotaTeljesneve = splittedString[1];
                    String csapatNeve = splittedString[2];

                    leggyorsabb.setFullname(pilotaTeljesneve);
                    leggyorsabb.setTeamName(csapatNeve);

                    adottVerseny.setFastestPilot(leggyorsabb);
                    adottEvVerseyAdatok.add(adottVerseny);
                }
                if (Commands.FINISH.getValue().equals(command)) {

                    if (!szezonok.containsKey(versenyEVe)) {
                        adottSzezon = new Season();
                        adottSzezon.setYear(versenyEVe);
                        adottSzezon.setRacesOfSeason(adottEvVerseyAdatok);
                        adottSzezon.setPilotsOfThisSeason(adottEvPilotaiAdatokkal);
                        szezonok.put(versenyEVe, adottSzezon);
                    } else if (szezonok.containsKey(versenyEVe)) {

                        szezonok.get(versenyEVe).setPilotsOfThisSeason(
                                mergeSet(szezonok.get(versenyEVe).getPilotsOfThisSeason(), adottEvPilotaiAdatokkal));

                        szezonok.get(versenyEVe).setRacesOfSeason(
                                mergeSet(szezonok.get(versenyEVe).getRacesOfSeason(), adottEvVerseyAdatok));

                    }
                    adottEvPilotaiAdatokkal = new HashSet<>();
                    adottEvVerseyAdatok = new HashSet<>();
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Kivétel a fájlolvasás során! " + e);
        }
    }


    public static <T>Set<T> mergeSet(Set<T> a, Set<T> b) {

        Set<T> result = new HashSet<>();
        for (T first : a) {
            for (T second : b) {
                if (!first.equals(second)) {
                    if (result.contains(second)) {
                        result.add(first);
                    } else {
                        result.add(second);
                    }
                }
            }
        }
        return result;
    }



   /* private static boolean validRace(String[] splittedArray) {

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

    }*/

}
