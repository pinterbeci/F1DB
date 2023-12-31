package hu.pinterbeci.java.se.f1.reader;

import hu.pinterbeci.java.se.f1.counter.Counter;
import hu.pinterbeci.java.se.f1.enums.Commands;
import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.pojo.Places;
import hu.pinterbeci.java.se.f1.pojo.Race;
import hu.pinterbeci.java.se.f1.pojo.Season;
import hu.pinterbeci.java.se.f1.util.DBUtil;
import hu.pinterbeci.java.se.f1.util.Splitter;
import hu.pinterbeci.java.se.f1.validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Reader {

    public static void raceReader(String fajlUrl) {

        Map<Integer, Season> szezonok = new HashMap<>();
        Map<Integer, Map<Integer, List<Pilot>>> eredemenyekEvenkent = new HashMap<>();
        List<String> vegrehajtandoParancsok = new ArrayList<String>();
        Set<Pilot> adottIdenyPilotai = new HashSet<>();
        Set<Race> adottIdenyFutamai = new HashSet<>();
        Race adottFutam = new Race();
        Places places = new Places();
        int adottFutamEve = 0;

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fajlUrl));
            String adottOlvasandoSor = reader.readLine();
            boolean isFastestReaded = false;
            boolean isRaceReaded = false;
            int resultokSzama = 0;
            boolean voltNemMegfeleloParancs = false;

            while (adottOlvasandoSor != null && !adottOlvasandoSor.equals(Commands.EXIT.getValue())) {
                List<String> splitterList = Splitter.splitter(adottOlvasandoSor, ";");
                String parancs = Validator.validateCommand(splitterList.get(0)) ? splitterList.get(0) : "";

                float szorzo = -1;
                Pilot futamLeggyorsabbja = new Pilot();

                if (Commands.RACE.getValue().equals(parancs)) {
                    isRaceReaded = true;
                    adottFutam = new Race();

                    adottFutamEve = Integer.parseInt(splitterList.get(1));
                    String versenyNeve = splitterList.get(2);
                    int versenySorszama = Integer.parseInt(splitterList.get(3));
                    szorzo = Float.parseFloat(splitterList.get(4));


                    if (Validator.isValidRace(versenyNeve, szorzo, versenySorszama)) {
                        adottFutam.setGpName(versenyNeve);
                        adottFutam.setNumberOfCurrentGP(versenySorszama);
                        adottFutam.setOdds(szorzo);
                    }

                } else if (Commands.RESULT.getValue().equals(parancs)) {

                    resultokSzama++;

                    int helyezes = Integer.parseInt(splitterList.get(1));

                    String pilotaTeljesneve = splitterList.get(2);
                    String csapatNeve = splitterList.get(3);

                    Pilot versenyzo = new Pilot();
                    versenyzo.setFullname(pilotaTeljesneve);
                    versenyzo.setTeamName(csapatNeve);
                    versenyzo.setPoints(helyezes);

                    if (Validator.isValidPilot(versenyzo)) {
                        adottIdenyPilotai.add(versenyzo);
                        DBUtil.evenkentiHelyezesekMentese(adottFutamEve, helyezes, versenyzo, eredemenyekEvenkent);
                    }
                } else if (Commands.FASTEST.getValue().equals(parancs)) {

                    isFastestReaded = true;
                    String pilotaTeljesneve = splitterList.get(1);
                    String csapatNeve = splitterList.get(2);

                    futamLeggyorsabbja.setFullname(pilotaTeljesneve);
                    futamLeggyorsabbja.setTeamName(csapatNeve);

                    if (Validator.theFastestBelongTheResultList(pilotaTeljesneve, new ArrayList<>(adottIdenyPilotai))) {
                        adottFutam.setFastestPilot(futamLeggyorsabbja);
                        adottIdenyFutamai.add(adottFutam);
                    }
                } else if (Commands.FINISH.getValue().equals(parancs)) {

                    Season adottSzezon = new Season();

                    if (!szezonok.containsKey(adottFutamEve)) {

                        adottSzezon.setYear(adottFutamEve);
                        adottSzezon.setRacesOfSeason(adottIdenyFutamai);
                        adottSzezon.setPilotsOfThisSeason(adottIdenyPilotai);

                        int versenyzokSzamaAdottEvben = adottIdenyPilotai.size();
                        boolean mentheto = Validator.validOlvasas(isRaceReaded, voltNemMegfeleloParancs, resultokSzama, versenyzokSzamaAdottEvben, isFastestReaded);

                        if (Validator.isValidSeason(adottSzezon) && mentheto) {
                            szezonok.put(adottFutamEve, adottSzezon);
                        }

                    } else {
                        adottSzezon = szezonok.get(adottFutamEve);

                        int versenyzokSzamaAdottEvben = adottIdenyPilotai.size();
                        boolean modosithato = Validator.validOlvasas(isRaceReaded, voltNemMegfeleloParancs, resultokSzama, versenyzokSzamaAdottEvben, isFastestReaded);

                        if (Validator.isValidSeason(adottSzezon) && modosithato) {
                            adottSzezon.setYear(adottFutamEve);
                            adottSzezon.getRacesOfSeason().addAll(adottIdenyFutamai);
                            adottSzezon.setPilotsOfThisSeason(DBUtil.mergeSet(adottSzezon.getPilotsOfThisSeason(), adottIdenyPilotai));
                            szezonok.put(adottFutamEve, adottSzezon);
                        }
                    }
                    adottIdenyPilotai = new HashSet<>();
                    adottIdenyFutamai = new HashSet<>();

                    isFastestReaded = false;
                    isRaceReaded = false;
                    resultokSzama = 0;
                    voltNemMegfeleloParancs = false;
                } else if (Commands.QUERY.getValue().equals(parancs)) {
                    vegrehajtandoParancsok.add(adottOlvasandoSor);
                } else if (Commands.POINT.getValue().equals(parancs)) {
                    vegrehajtandoParancsok.add(adottOlvasandoSor);
                } else {
                    voltNemMegfeleloParancs = true;
                }
                adottOlvasandoSor = reader.readLine();
            }
            reader.close();
            DBUtil.writer(2020, Counter.trial(eredemenyekEvenkent, 2020));
        } catch (Exception e) {
            System.out.println("Kivétel a fájlolvasás során! " + e);
        }
    }
}
