package hu.pinterbeci.java.se.f1.counter;

import hu.pinterbeci.java.se.f1.pojo.Pilot;
import hu.pinterbeci.java.se.f1.util.DBUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Counter {

    public static Map<String, Integer> trial(Map<Integer, Map<Integer, List<Pilot>>> eredmenyekEvenkent, int adottEv) {
        Map<String, Integer> vegeredmeny = new HashMap<>();
        List<Integer> adhatoPontok = Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1);
        int loopVar = 0;

        if (eredmenyekEvenkent.containsKey(adottEv)) {
            Set<Map.Entry<Integer, List<Pilot>>> pilotakHelyezesei = eredmenyekEvenkent.get(adottEv).entrySet();
            Iterator<Map.Entry<Integer, List<Pilot>>> pilotakHelyezeseiIterator = pilotakHelyezesei.iterator();

            while (pilotakHelyezeseiIterator.hasNext() && loopVar < adhatoPontok.size()) {
                Map.Entry<Integer, List<Pilot>> currentHelyezes = pilotakHelyezeseiIterator.next();
                int pont = adhatoPontok.get(currentHelyezes.getKey() - 1);

                for (Pilot pilot : currentHelyezes.getValue()) {
                    if (!vegeredmeny.containsKey(pilot.getFullname())) {
                        vegeredmeny.put(pilot.getFullname(), pont);
                    } else {
                        vegeredmeny.put(pilot.getFullname(), vegeredmeny.get(pilot.getFullname()) + pont);
                    }
                }

                loopVar++;
            }
        }
        return DBUtil.rendezo(vegeredmeny);
    }
}
