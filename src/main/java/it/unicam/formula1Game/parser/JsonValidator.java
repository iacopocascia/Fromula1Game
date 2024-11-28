package it.unicam.formula1Game.parser;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;

import java.io.File;

/**
 * TODO
 */
public class JsonValidator implements ConfigurationFileValidator{
    /**
     * @param configurationFile
     * @return
     */
    @Override
    public boolean validate(File configurationFile) {
        return configurationFile.getName().endsWith(".json");
    }
}
