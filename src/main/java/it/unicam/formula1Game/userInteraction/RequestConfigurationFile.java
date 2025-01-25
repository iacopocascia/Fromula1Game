package it.unicam.formula1Game.userInteraction;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

/**
 * Utility class for requesting a configuration file from the user.
 * The file must exist and be readable to be considered valid.
 */
public class RequestConfigurationFile {
    /**
     * Prompts the user to input the name of a JSON configuration file
     * and validates its existence and readability.
     *
     * @return a {@link File} object representing the valid configuration file
     * @throws NoSuchFileException if the specified file does not exist or cannot be read
     */
    public static File requestConfigurationFile() throws NoSuchFileException {
        System.out.println("Please enter the JSON configuration file name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!(file.exists() && file.canRead())) {
            throw new NoSuchFileException("File not found or cannot be read.");
        }
        return file;
    }
}

