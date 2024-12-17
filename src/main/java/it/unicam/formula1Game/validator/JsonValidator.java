package it.unicam.formula1Game.validator;

import java.io.File;

/**
 * The {@code JsonValidator} class implements the {@link ConfigurationFileValidator} interface.
 * It provides functionality to validate whether a configuration file is in JSON format.
 */
public class JsonValidator implements ConfigurationFileValidator{
    /**
     * Validates the given configuration file by checking if its name ends with ".json".
     *
     * @param configurationFile The {@link File} object representing the configuration file to validate.
     * @return {@code true} if the file is a valid JSON file, {@code false} otherwise.
     */
    @Override
    public boolean validate(File configurationFile) {
        return configurationFile.getName().endsWith(".json");
    }
}
