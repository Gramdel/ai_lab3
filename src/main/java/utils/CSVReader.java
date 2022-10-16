package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {
    public static ArrayList<String[]> readFile(String filename, boolean hasHeader) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(filename));
        ArrayList<String[]> data = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!hasHeader) {
                String[] row = line.split(";");
                data.add(row);
            } else {
                hasHeader = false;
            }
        }

        scanner.close();
        return data;
    }
}
