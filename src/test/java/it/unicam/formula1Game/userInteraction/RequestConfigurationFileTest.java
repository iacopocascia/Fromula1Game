package it.unicam.formula1Game.userInteraction;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class RequestConfigurationFileTest {
    @Test
    public void request_existing_configuration_file_test() {
        String simulatedInput = "src/jsonRaceTracks/track.json";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);
        assertEquals(RequestConfigurationFile.requestConfigurationFile().getClass(), File.class);
    }
}


