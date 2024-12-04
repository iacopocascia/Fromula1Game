package it.unicam.formula1Game.userInteraction;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class RequestConfigurationFile {
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

