package it.unicam.formula1Game.parser;

import java.io.File;

/**
 * todo
 */
public interface ConfigurationFileValidator {
    /**
     *
     * @param configurationFile
     * @return
     */
    boolean validate(File configurationFile);
}
