package hu.pinterbeci.java.se.f1.util;

import java.util.Set;
import java.util.stream.Collectors;

public class DBUtil {

    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b) {
        return a.stream().filter(first -> b.stream().anyMatch(second -> !first.equals(second)))
                .collect(Collectors.toSet());
    }
}
