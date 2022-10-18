package hu.pinterbeci.java.se.f1.reader;

import hu.pinterbeci.java.se.f1.enums.Commands;
import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Race;
import hu.pinterbeci.java.se.f1.pojo.Season;
import hu.pinterbeci.java.se.f1.util.DBUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Reader {

    public static void raceReader(String fajlUrl) {

        Map<Integer, Season> szezonok = new HashMap<>();
        Set<Pilot> adottIdenyPilotai = new HashSet<>();
        Set<Race> adottIdenyFutamai = new HashSet<>();
        Race adottFutam = new Race();
        int adottFutamEve = 0;

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fajlUrl));
            String adottOlvasandoSor = reader.readLine();

            while (adottOlvasandoSor != null && !adottOlvasandoSor.equals(Commands.EXIT.getValue())) {
                String parancs = adottOlvasandoSor.trim().split(";")[0];

                float szorzo = -1;
                Pilot futamLeggyorsabbja = new Pilot();

                if (Commands.RACE.getValue().equals(parancs)) {
                    adottFutam = new Race();
                    //szerintem itt kellene két set-et nullozni.

                    String[] splittedString = adottOlvasandoSor.trim().split(";");

                    adottFutamEve = Integer.parseInt(splittedString[1]);
                    String versenyNeve = splittedString[2];
                    int versenySorszama = Integer.parseInt(splittedString[3]);
                    //todo ez fontos lehet a pontszámításnál
                    szorzo = Float.parseFloat(splittedString[4]);

                    adottFutam.setGpName(versenyNeve);
                    adottFutam.setNumberOfCurrentGP(versenySorszama);
                    adottFutam.setOdds(szorzo);

                }
                if (Commands.RESULT.getValue().equals(parancs)) {
                    //todo
                    String[] splittedString = adottOlvasandoSor.trim().split(";");

                    int helyezes = Integer.parseInt(splittedString[1]);
                    String pilotaTeljesneve = splittedString[2];
                    String csapatNeve = splittedString[3];

                    Pilot versenyzo = new Pilot();
                    versenyzo.setFullname(pilotaTeljesneve);
                    versenyzo.setTeamName(csapatNeve);

                    //todo pontszámítás szorzót figyelembe kell venni

                    versenyzo.setPoints(helyezes);
                    adottIdenyPilotai.add(versenyzo);
                }
                if (Commands.FASTEST.getValue().equals(parancs)) {
                    //todo kiszervezni
                    String[] splittedString = adottOlvasandoSor.trim().split(";");
                    String pilotaTeljesneve = splittedString[1];
                    String csapatNeve = splittedString[2];

                    futamLeggyorsabbja.setFullname(pilotaTeljesneve);
                    futamLeggyorsabbja.setTeamName(csapatNeve);

                    adottFutam.setFastestPilot(futamLeggyorsabbja);
                    adottIdenyFutamai.add(adottFutam);
                }
                if (Commands.FINISH.getValue().equals(parancs)) {
                    if (!szezonok.containsKey(adottFutamEve)) {
                        Season adottSzezon = new Season();
                        adottSzezon.setYear(adottFutamEve);
                        adottSzezon.setRacesOfSeason(adottIdenyFutamai);
                        adottSzezon.setPilotsOfThisSeason(adottIdenyPilotai);
                        szezonok.put(adottFutamEve, adottSzezon);

                    } else {
                        Season adottSzezon = szezonok.get(adottFutamEve);
                        adottSzezon.setYear(adottFutamEve);
                        adottSzezon.getRacesOfSeason().addAll(adottIdenyFutamai);
                        adottSzezon.setPilotsOfThisSeason(DBUtil.mergeSet(adottSzezon.getPilotsOfThisSeason(), adottIdenyPilotai));
                        szezonok.put(adottFutamEve, adottSzezon);
                    }
                    adottIdenyPilotai = new HashSet<>();
                    adottIdenyFutamai = new HashSet<>();
                }

                adottOlvasandoSor = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Kivétel a fájlolvasás során! " + e);
        }
    }


    //todo kód egyszerúsítés OOP-sítés!!!

    //todo kiszervezni:
    //validációk, pontszámítás
    //illetve, hogy az adott parancsok mikor adhatók ki, lehetne valamilyen boolean-nel jelezni
    //splittelést kiszervezni....

    //todo
    //elnevezések

}
