package it.unicam.formula1Game.userInteraction;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestConfigurationFileTest {
    @Test
    public void request_not_existing_configuration_file_test() {
        String simulatedInput = "src/jsonRaceTracks/notExisting.json";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);
        assertThrows(NoSuchFileException.class, RequestConfigurationFile::requestConfigurationFile);
    }
}


