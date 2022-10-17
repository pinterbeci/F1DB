package hu.pinterbeci.java.se.f1.util;

import hu.pinterbeci.java.se.f1.entities.Race;
import hu.pinterbeci.java.se.f1.entities.Season;
import hu.pinterbeci.java.se.f1.enums.Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Reader {

    public Reader() {
    }

    public void raceReader(String fileUrl) {

        //kulcs lesz ez adott év, és jönnek hozzá a versenyeredméynek
        Map<String, Season> stringSeasonMap = new HashMap<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    fileUrl));
            String line = reader.readLine();


            while (line != null && !line.equals(Commands.EXIT.getValue())) {

                if (line.startsWith(Commands.RACE.getValue())) {


                    Race currentRace;
                    String[] metaDataOfRace = line.trim().split(";");


                    if (metaDataOfRace.length != 5) {
                        String year = metaDataOfRace[1];
                        String gpName = metaDataOfRace[2];


                        int numberOfCurrentGP = -1;
                        int odds = -1;

                        //ezt is ki lehet szervezni

                        try {
                            numberOfCurrentGP = Integer.parseInt(metaDataOfRace[3]);
                            odds = Integer.parseInt(metaDataOfRace[4]);
                        } catch (NumberFormatException e) {
                            System.out.println("Nem megfelelő formátum!");
                        }
                        
                        stringSeasonMap.put(year, null);

                        //vizsgálatokat ki lehet szervezni
                        if (numberOfCurrentGP > -1 && odds > -1) {
                            currentRace = new Race(gpName, numberOfCurrentGP, odds, null);
                        }
                    }


                }

                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Kivétel a fájlolvasás során! " + e.getMessage());

        }
    }

}
