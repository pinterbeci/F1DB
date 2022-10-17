package hu.pinterbeci.java.se.f1.runner;

import hu.pinterbeci.java.se.f1.util.Reader;

public class Runner {
    public static void main(String[] args) {
        Reader reader = new Reader();

        reader.raceReader("trial.txt");
    }
}
