package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {
    private static ArrayList<String[]> data;
    private static ArrayList<String> header;

    public static void readFile(String filename, boolean hasHeader) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(filename));
        data = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] row = line.split(";");
            if (header == null) {
                header = new ArrayList<>();
                if (hasHeader) {
                    header.addAll(Arrays.asList(row));
                } else {
                    for (int i = 0; i < row.length; i++) {
                        header.add(Integer.toString(i + 1));
                    }
                    data.add(row);
                }
            } else {
                data.add(row);
            }
        }
        scanner.close();
    }

    public static ArrayList<String> parseString(String s) {
        return new ArrayList<>(Arrays.asList(s.split(";")));
    }

    public static ArrayList<String[]> getData() {
        return data;
    }

    public static ArrayList<String> getHeader() {
        return header;
    }
}
