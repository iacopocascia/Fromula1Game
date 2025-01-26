package it.unicam.formula1Game.application;

import it.unicam.formula1Game.engine.CpuGameEngine;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.validator.JsonValidator;
import it.unicam.formula1Game.validator.RaceTrackValidator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Formula1ApplicationCpuTest {
    private final Formula1ApplicationCpu application=new Formula1ApplicationCpu(
            new JsonParser(),
            new JsonValidator(),
            new RaceTrackValidator(),
            new CpuGameEngine()
    );
    @Test
    public void run_test() {
        File validConfigurationFile=new File(filePath);
        assertDoesNotThrow(()->this.application.run(validConfigurationFile));
    }
}
