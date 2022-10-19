package hu.pinterbeci.java.se.f1.util;

import hu.pinterbeci.java.se.f1.pojo.Pilot;

import java.util.*;
import java.util.stream.Collectors;

public class DBUtil {

    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b) {
        return a.stream().filter(first -> b.stream().anyMatch(second -> !first.equals(second)))
                .collect(Collectors.toSet());
    }

    public static void evenkentiHelyezesekMentese(int adottSzezonEve, int helyezes, Pilot pilot, Map<Integer, Map<Integer, List<Pilot>>> kapott) {
        if(!kapott.containsKey(adottSzezonEve)){
            Map<Integer, List<Pilot>> integerListMap = new HashMap<>();
            pilotakHelyezeseiMap(helyezes, pilot, integerListMap);
            kapott.put(adottSzezonEve, integerListMap);
        }else{
            Map<Integer, List<Pilot>> integerListMap = kapott.get(adottSzezonEve);
            pilotakHelyezeseiMap(helyezes, pilot, integerListMap);
            kapott.put(adottSzezonEve, integerListMap);
        }
    }


    private static Map<Integer, List<Pilot>> pilotakHelyezeseiMap(int helyezes, Pilot pilot, Map<Integer, List<Pilot>> adatok) {

        if (!adatok.containsKey(helyezes)) {
            List<Pilot> pilots = new ArrayList<>();
            pilots.add(pilot);
            adatok.put(helyezes, pilots);
        } else {
            boolean vanIlyenPilota = adatok.get(helyezes).stream().anyMatch(a -> a.equals(pilot));
            List<Pilot> pilots = adatok.get(helyezes);
            if (!vanIlyenPilota) {
                pilots.add(pilot);
            }
            adatok.put(helyezes, pilots);
        }
        return adatok;
    }

}
