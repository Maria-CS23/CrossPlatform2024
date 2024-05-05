package xml;

import java.io.File;

public class XMLFileManager {
    public static String generateNewFileName() {
        File directory = new File("XML Files");

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Failed to create directory 'XML Files'");
                return null;
            }
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".xml"));

        if (files == null || files.length == 0) {
            return "Data_1.xml";
        }

        File lastFile = files[0];
        for (File file : files) {
            if (file.getName().compareTo(lastFile.getName()) > 0) {
                lastFile = file;
            }
        }

        String lastFileName = lastFile.getName();
        return incrementNumber(lastFileName);
    }

    public static String incrementNumber(String input) {
        int startIndex = -1;
        int endIndex = -1;

        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                if (startIndex == -1) {
                    startIndex = i;
                }
                endIndex = i;
            } else if (startIndex != -1) {
                break;
            }
        }

        if (startIndex == -1) {
            return input;
        }

        int number = Integer.parseInt(input.substring(startIndex, endIndex + 1));
        number += 1;

        String replacement = Integer.toString(number);
        return input.substring(0, startIndex) + replacement + input.substring(endIndex + 1);
    }
}