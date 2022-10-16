package tree;

import java.util.ArrayList;
import java.util.HashMap;

import utils.ConsoleColors;

public class DecisionTree {

    public static Node createTree(ArrayList<String[]> data, ArrayList<String> header, ArrayList<Integer> prevIndexes) {
        try {
            printData(data, prevIndexes);
            int indexOfAttribute = findMaxGainRatio(data, header);
            HashMap<String, ArrayList<Integer>> values = getValuesOfAttribute(data, indexOfAttribute);
            Node node = new Node(header.get(indexOfAttribute));
            for (String value : values.keySet()) {
                System.out.println("\nВыбираем записи, где \"" + ConsoleColors.GREEN + header.get(indexOfAttribute) + ConsoleColors.RESET + "\" = \"" + ConsoleColors.CYAN + value + ConsoleColors.RESET + "\":");
                node.getChildren().put(value, createTree(dropAttributeFromData(selectRowsByIndexes(data, values.get(value)), indexOfAttribute), dropAttributeFromHeader(header, indexOfAttribute), selectPrevIndexesByIndexes(prevIndexes, values.get(value))));
            }
            return node;
        } catch (NumberFormatException e) {
            Node node = new Node(data.get(0)[header.size() - 1]);
            System.out.println("Определена принадлеждность к классу \"" + ConsoleColors.PURPLE + node.getName() + ConsoleColors.RESET + "\"!");
            return node;
        }
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

    private static HashMap<String, ArrayList<Integer>> getValuesOfAttribute(ArrayList<String[]> data, int indexOfAttribute) {
        HashMap<String, ArrayList<Integer>> values = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            String value = data.get(i)[indexOfAttribute];
            ArrayList<Integer> indexes = values.get(value);
            if (indexes == null) {
                indexes = new ArrayList<>();
            }
            indexes.add(i);
            values.put(value, indexes);
        }
        return values;
    }

    private static ArrayList<String[]> selectRowsByIndexes(ArrayList<String[]> data, ArrayList<Integer> indexes) {
        ArrayList<String[]> newData = new ArrayList<>();
        for (int i : indexes) {
            newData.add(data.get(i));
        }
        return newData;
    }

    private static ArrayList<Integer> selectPrevIndexesByIndexes(ArrayList<Integer> prevIndexes, ArrayList<Integer> indexes) {
        ArrayList<Integer> newIndexes = new ArrayList<>();
        for (int i : indexes) {
            if (prevIndexes == null) {
                newIndexes.add(i);
            } else {
                newIndexes.add(prevIndexes.get(i));
            }
        }
        return newIndexes;
    }

    private static ArrayList<String[]> dropAttributeFromData(ArrayList<String[]> data, int indexOfAttribute) {
        ArrayList<String[]> newData = new ArrayList<>();
        for (String[] row : data) {
            String[] newRow = new String[row.length - 1];
            for (int i = 0; i < newRow.length; i++) {
                newRow[i] = i < indexOfAttribute ? row[i] : row[i + 1];
            }
            newData.add(newRow);
        }
        return newData;
    }

    private static ArrayList<String> dropAttributeFromHeader(ArrayList<String> header, int indexOfAttribute) {
        ArrayList<String> newHeader = new ArrayList<>(header);
        newHeader.remove(indexOfAttribute);
        return newHeader;
    }

    public static void printData(ArrayList<String[]> data, ArrayList<Integer> indexes) {
        for (int i = 0; i < data.size(); i++) {
            if (indexes == null) {
                System.out.print("\t" + (i + 1) + ". ");
            } else {
                System.out.print("\t" + (indexes.get(i) + 1) + ". ");
            }

            String[] row = data.get(i);
            for (int j = 0; j < row.length; j++) {
                if (j == row.length - 1) {
                    System.out.println(row[j]);
                } else {
                    System.out.print(row[j] + " ");
                }
            }
        }
    }
}
