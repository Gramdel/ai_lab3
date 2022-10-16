import tree.DecisionTree;
import tree.Node;

import utils.CSVReader;
import utils.ConsoleColors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static utils.DataManager.*;

public class Main {
    public static void main(String[] args) {
        String filename = "MODIFIED_DATA.csv";
        //String filename = "test.csv";
        try {
            CSVReader.readFile(filename, true);
            ArrayList<String[]> data = CSVReader.getData();
            ArrayList<String> header = CSVReader.getHeader();
            ArrayList<Integer> indexes = selectRandomIndexes(header.size() - 1);

            data = selectColumnsByIndexes(data, indexes);
            header = selectAttributesFromHeader(header, indexes);

            System.out.println("Начинаем построение дерева...");
            Node tree = DecisionTree.createTree(data, header, null);
            System.out.println("Построение дерева решений завершено.");

            /*
            System.out.println(DecisionTree.classify(CSVReader.parseString("Sunny;Hot;High;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Sunny;Hot;High;True"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Overcast;Hot;High;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Rain;Mild;High;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Rain;Cool;Normal;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Rain;Cool;Normal;True"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Overcast;Cool;Normal;True"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Sunny;Mild;High;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Sunny;Cool;Normal;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Rain;Mild;Normal;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Sunny;Mild;Normal;True"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Overcast;Mild;High;True"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Overcast;Hot;Normal;False"), tree, header));
            System.out.println(DecisionTree.classify(CSVReader.parseString("Rain;Mild;High;True"), tree, header));
             */
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "При чтении файла \"" + filename + "\" произошла ошибка!" + ConsoleColors.RESET);
        }
    }

    private static ArrayList<Integer> selectRandomIndexes(int n) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indexes.add(i);
        }

        int sqrtOfN = (int) Math.sqrt(n);
        Random rand = new Random();
        ArrayList<Integer> newIndexes = new ArrayList<>();
        for (int i = 0; i < sqrtOfN; i++) {
            int randomNumber = rand.nextInt(indexes.size());
            newIndexes.add(indexes.get(randomNumber));
            indexes.remove(randomNumber);
        }
        newIndexes.sort(Integer::compareTo);
        return newIndexes;
    }
}
