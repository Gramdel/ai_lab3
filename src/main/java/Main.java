import tree.DecisionTree;
import tree.Node;

import utils.CSVReader;
import utils.ConsoleColors;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //String filename = "MODIFIED_DATA.csv";
        String filename = "test.csv";
        try {
            CSVReader.readFile(filename, true);
            ArrayList<String[]> data = CSVReader.getData();
            ArrayList<String> header = CSVReader.getHeader();
            Node tree = DecisionTree.createTree(data, header, null);
            System.out.println("Построение дерева решений завершено.");
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "При чтении файла \"" + filename + "\" произошла ошибка!" + ConsoleColors.RESET);
        }
    }
}
