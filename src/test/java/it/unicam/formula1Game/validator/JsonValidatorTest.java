package it.unicam.formula1Game.validator;

import org.junit.jupiter.api.Test;

import java.io.File;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonValidatorTest {
    JsonValidator validator=new JsonValidator();
    @Test
    public void test_validate(){
        assertTrue(validator.validate(new File(filePath)));
    }
}
