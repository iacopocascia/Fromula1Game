package it.unicam.formula1Game.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RaceTrackValidatorTest {
    private final RaceTrackValidator validator=new RaceTrackValidator();
    @Test
    public void validate_width_test(){
        assertTrue(validator.validateWidth(50));
        assertFalse(validator.validateWidth(200));
        assertTrue(validator.validateHeight(15));
        assertFalse(validator.validateHeight(200));
        assertTrue(validator.validateNumberOfPlayers(2));
        assertFalse(validator.validateNumberOfPlayers(11));
        assertTrue(validator.validateDirection("cw") && validator.validateDirection("ccw"));
    }
}
