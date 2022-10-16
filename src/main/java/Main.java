import tree.DecisionTree;
import utils.CSVReader;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //String filename = "MODIFIED_DATA.csv";
        String filename = "test.csv";
        try {
            ArrayList<String[]> data = CSVReader.readFile(filename, false);
            System.out.println(DecisionTree.calcInfo(data));
            System.out.println(DecisionTree.calcInfoX(data, 0));
            System.out.println(DecisionTree.calcSplitInfoX(data, 0));
            System.out.println(DecisionTree.calcGainRatio(data, 0));
            /*
            for (String[] s : data) {
                for (int i = 0; i < s.length; i++) {
                    if (i == s.length - 1) {
                        System.out.println(s[i]);
                    } else {
                        System.out.print(s[i] + ";");
                    }
                }
            }
            */
        } catch (IOException e) {
            System.out.println("При чтении файла " + filename + " произошла ошибка!");
        }
    }
}
