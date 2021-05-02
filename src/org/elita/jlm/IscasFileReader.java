package org.elita.jlm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IscasFileReader {

    public static String readIscasFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file);){
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
