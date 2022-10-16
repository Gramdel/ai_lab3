import utils.CSVReader;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            ArrayList<String[]> list = CSVReader.readFile("DATA.csv", true);
            for (String[] s : list) {
                for (int i = 0; i < s.length; i++) {
                    if (i == s.length - 1) {
                        if (Integer.parseInt(s[i]) > 1) {
                            System.out.println(1 + " (was " + s[i] + ")");
                        } else {
                            System.out.println(0 + " (was " + s[i] + ")");
                        }
                    } else if (i > 0) {
                        System.out.print(s[i] + ";");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
