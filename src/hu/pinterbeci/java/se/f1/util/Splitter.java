package hu.pinterbeci.java.se.f1.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class Splitter {

    public static List<String> splitter(String line, String regex) {

        List<String> result = new ArrayList<>();

        if (line == null || line.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            Collections.addAll(result, line.trim().split(regex));
            return result;
        } catch (PatternSyntaxException e) {
            throw new PatternSyntaxException("A splittelés során kivétel lépett fel. (kapott érték = " + line + " )",
                    regex, -1);
        }

    }
}
