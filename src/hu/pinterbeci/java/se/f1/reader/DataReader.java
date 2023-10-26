package hu.pinterbeci.java.se.f1.reader;

import hu.pinterbeci.java.se.f1.service.F1DBService;

import java.io.InputStream;

public class DataReader {

    private final F1DBService service;

    {
        this.service = new F1DBService();
    }

    public void readData() {
        try {
            final ClassLoader classLoader = getClass().getClassLoader();
            final InputStream inputStream = classLoader.getResourceAsStream("resources/input-hf.txt");
            this.service.readAndProcessData(inputStream);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}
