package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import utils.ConsoleColors;

import static utils.DataManager.*;

public class DecisionTree {

    public static Node createTree(ArrayList<String[]> data, ArrayList<String> header, ArrayList<Integer> prevIndexes) {
        Node node;
        try {
            printDataAndHeader(data, header, prevIndexes);
            if (header.size() == 1) {
                throw new NumberFormatException();
            }

            int indexOfAttribute = findMaxGainRatio(data, header);
            HashMap<String, ArrayList<Integer>> values = getValuesOfAttribute(data, indexOfAttribute);
            node = new Node(header.get(indexOfAttribute));
            for (String value : values.keySet()) {
                System.out.println("\nВыбираем записи, где \"" + ConsoleColors.GREEN + header.get(indexOfAttribute) + ConsoleColors.RESET + "\" = \"" + ConsoleColors.CYAN + value + ConsoleColors.RESET + "\":");
                node.getChildren().put(value, createTree(dropAttributeFromData(selectRowsByIndexes(data, values.get(value)), indexOfAttribute), dropAttributeFromHeader(header, indexOfAttribute), selectPrevIndexesByIndexes(prevIndexes, values.get(value))));
            }
        } catch (NumberFormatException e) {
            String className = null;
            if (getValuesOfAttribute(data, header.size() - 1).size() == 1) {
                className = data.get(0)[header.size() - 1];
            }
            node = new Node(className);

            if (className != null) {
                System.out.println("Определена принадлеждность к классу \"" + ConsoleColors.PURPLE + node.getName() + ConsoleColors.RESET + "\"!");
            } else {
                System.out.println(ConsoleColors.RED + "Определить принадлеждность к классу невозможно!" + ConsoleColors.RESET);
            }
        }
        return node;
    }

    public static String classify(ArrayList<String> row, Node tree, ArrayList<String> header) {
        if (tree.getChildren().isEmpty()) {
            return tree.getName();
        }
        String nameOfAttribute = tree.getName();
        int indexOfAttribute = header.indexOf(nameOfAttribute);
        String valueOfAttribute = row.get(indexOfAttribute);
        Node nextNode = tree.getChildren().get(valueOfAttribute);
        return classify(dropAttributeFromHeader(row, indexOfAttribute), nextNode, dropAttributeFromHeader(header, indexOfAttribute));
    }

    public static ArrayList<String> classifyAll(ArrayList<String[]> data, Node tree, ArrayList<String> header) {
        ArrayList<String> predictedClasses = new ArrayList<>();
        for (String[] row : data) {
            predictedClasses.add(classify(new ArrayList<>(Arrays.asList(row)), tree, header));
        }
        return predictedClasses;
    }

    public static int findMaxGainRatio(ArrayList<String[]> data, ArrayList<String> header) throws NumberFormatException {
        double max = Double.NaN;
        int indexOfMax = 0;
        for (int i = 0; i < data.get(0).length - 1; i++) {
            double gainRatio = calcGainRatio(data, i);
            System.out.println("Gain Ratio для атрибута \"" + ConsoleColors.GREEN + header.get(i) + ConsoleColors.RESET + "\": " + gainRatio);
            if (!Double.isNaN(gainRatio) && (Double.isNaN(max) || gainRatio > max)) {
                max = gainRatio;
                indexOfMax = i;
            }
        }
        System.out.println("Максимальный Gain Ratio (атрибут \"" + ConsoleColors.GREEN + header.get(indexOfMax) + ConsoleColors.RESET + "\"): " + ConsoleColors.RED + max + ConsoleColors.RESET);
        if (Double.isNaN(max) || max == 0) {
            throw new NumberFormatException();
        }
        return indexOfMax;
    }

    public static double calcInfo(ArrayList<String[]> data) {
        return calcSplitInfoX(data, data.get(0).length - 1);
    }

    public static double calcInfoX(ArrayList<String[]> data, int indexOfAttribute) {
        HashMap<String, ArrayList<Integer>> x = getValuesOfAttribute(data, indexOfAttribute);
        double info = 0;
        int t = data.size();
        for (ArrayList<Integer> indexes : x.values()) {
            double count = indexes.size();
            info += (count / t) * calcInfo(selectRowsByIndexes(data, indexes));
        }
        return info;
    }

    public static double calcSplitInfoX(ArrayList<String[]> data, int indexOfAttribute) {
        HashMap<String, ArrayList<Integer>> x = getValuesOfAttribute(data, indexOfAttribute);
        double info = 0;
        int t = data.size();
        for (ArrayList<Integer> indexes : x.values()) {
            double count = indexes.size();
            info -= (count / t) * (Math.log(count / t) / Math.log(2));
        }
        return info;
    }

    public static double calcGainRatio(ArrayList<String[]> data, int indexOfAttribute) {
        return (calcInfo(data) - calcInfoX(data, indexOfAttribute)) / calcSplitInfoX(data, indexOfAttribute);
    }
}
