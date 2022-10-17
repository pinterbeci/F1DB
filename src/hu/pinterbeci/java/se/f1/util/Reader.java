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


            while (line != null) {

                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Kivétel a fájlolvasás során! " + e.getMessage());

        }
    }

}
