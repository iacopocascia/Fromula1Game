package it.unicam.formula1Game.userInteraction;

import java.io.File;
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
     */
    public static File requestConfigurationFile() {
        Scanner scanner = new Scanner(System.in);
        File file;
        // Loop until the prompted file is valid
        while (true) {
            System.out.println("Please enter the JSON configuration file name:");
            String fileName = scanner.nextLine();

            file = new File(fileName);
            if (file.exists() && file.canRead()) {
                break; // Valid file found, exit the loop
            } else {
                System.err.println("File not found or cannot be read. Please try again.");
            }
        }
        return file;
    }
}

