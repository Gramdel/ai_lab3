import test.Metrics;
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
            ArrayList<Integer> indexes = selectRandomIndexes(header.size());

            data = selectColumnsByIndexes(data, indexes);
            header = selectAttributesFromHeader(header, indexes);

            System.out.println(ConsoleColors.YELLOW + "Начинаем построение дерева..." + ConsoleColors.RESET);
            Node tree = DecisionTree.createTree(data, header, null);
            System.out.println(ConsoleColors.YELLOW + "Построение дерева решений завершено." + ConsoleColors.RESET);

            System.out.print(ConsoleColors.YELLOW + "\nСчитаем метрики..." + ConsoleColors.RESET);
            ArrayList<Integer> rowIndexes = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                rowIndexes.add(i);
                //System.out.println(ConsoleColors.YELLOW + "\nКоличество строк: " + ConsoleColors.YELLOW_BOLD + (i + 1) + ConsoleColors.RESET);

                int[][] confusionMatrix = Metrics.createConfusionMatrix(selectRowsByIndexes(data, rowIndexes), tree, header);

                /*
                System.out.println("True Positive(TP): " + confusionMatrix[0][0]);
                System.out.println("False Positive(FP): " + confusionMatrix[0][1]);
                System.out.println("False Negative(FN): " + confusionMatrix[1][0]);
                System.out.println("True Negative(TN): " + confusionMatrix[1][1]);
                System.out.println("Accuracy: " + Metrics.calcAccuracy(confusionMatrix));
                System.out.println("Precision: " + Metrics.calcPrecision(confusionMatrix));
                System.out.println("Recall: " + Metrics.calcRecall(confusionMatrix));
                System.out.println("TPR: " + Metrics.calcTPR(confusionMatrix));
                System.out.println("FPR: " + Metrics.calcFPR(confusionMatrix));
                 */

                System.out.println(Metrics.calcFPR(confusionMatrix) + " " + Metrics.calcTPR(confusionMatrix) + " " + Metrics.calcRecall(confusionMatrix) + " " + Metrics.calcPrecision(confusionMatrix));
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "При чтении файла \"" + ConsoleColors.RED_BOLD + filename + ConsoleColors.RED + "\" произошла ошибка!" + ConsoleColors.RESET);
        }
    }

    private static ArrayList<Integer> selectRandomIndexes(int n) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indexes.add(i);
        }

        int sqrtOfN = (int) Math.sqrt(n - 1);
        Random rand = new Random();
        ArrayList<Integer> newIndexes = new ArrayList<>();
        for (int i = 0; i < sqrtOfN; i++) {
            int randomNumber = rand.nextInt(indexes.size());
            newIndexes.add(indexes.get(randomNumber));
            indexes.remove(randomNumber);
        }
        newIndexes.add(indexes.get(indexes.size() - 1));
        newIndexes.sort(Integer::compareTo);
        return newIndexes;
    }
}
