package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class DataManager {
    public static HashMap<String, ArrayList<Integer>> getValuesOfAttribute(ArrayList<String[]> data, int indexOfAttribute) {
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

    public static ArrayList<String[]> selectRowsByIndexes(ArrayList<String[]> data, ArrayList<Integer> indexes) {
        ArrayList<String[]> newData = new ArrayList<>();
        for (int i : indexes) {
            newData.add(data.get(i));
        }
        return newData;
    }

    public static ArrayList<Integer> selectPrevIndexesByIndexes(ArrayList<Integer> prevIndexes, ArrayList<Integer> indexes) {
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

    public static ArrayList<String[]> selectColumnsByIndexes(ArrayList<String[]> data, ArrayList<Integer> indexes) {
        ArrayList<String[]> newData = new ArrayList<>();
        for (String[] row : data) {
            ArrayList<String> rowAsList = new ArrayList<>();
            for (int i : indexes) {
                rowAsList.add(row[i]);
            }
            rowAsList.add(row[row.length - 1]);
            String[] newRow = new String[rowAsList.size()];
            for (int i = 0; i < newRow.length; i++) {
                newRow[i] = rowAsList.get(i);
            }
            newData.add(newRow);
        }
        return newData;
    }

    public static ArrayList<String> selectAttributesFromHeader(ArrayList<String> header, ArrayList<Integer> indexes) {
        ArrayList<String> newHeader = new ArrayList<>();
        for (int i : indexes) {
            newHeader.add(header.get(i));
        }
        newHeader.add(header.get(header.size() - 1));
        return newHeader;
    }

    public static ArrayList<String[]> dropAttributeFromData(ArrayList<String[]> data, int indexOfAttribute) {
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

    public static ArrayList<String> dropAttributeFromHeader(ArrayList<String> header, int indexOfAttribute) {
        ArrayList<String> newHeader = new ArrayList<>(header);
        newHeader.remove(indexOfAttribute);
        return newHeader;
    }

    public static void printDataAndHeader(ArrayList<String[]> data, ArrayList<String> header, ArrayList<Integer> indexes) {
        int firstColumnSize = String.valueOf(indexes == null ? data.size() : indexes.get(indexes.size() - 1) + 1).length() + 2;
        int columnSize = header.stream().max(Comparator.comparing(String::length)).get().length() + 2;

        System.out.printf("%s%-" + firstColumnSize + "s%s", ConsoleColors.WHITE_BOLD, "#", ConsoleColors.RESET);
        for (int i = 0; i < header.size(); i++) {
            if (i == header.size() - 1) {
                System.out.printf("%s%-" + columnSize + "s%s%s", ConsoleColors.WHITE_BOLD, header.get(i), ConsoleColors.RESET, "\n");
            } else {
                System.out.printf("%s%-" + columnSize + "s%s", ConsoleColors.WHITE_BOLD, header.get(i), ConsoleColors.RESET);
            }
        }

        for (int i = 0; i < data.size(); i++) {
            if (indexes == null) {
                System.out.printf("%-" + firstColumnSize + "s", (i + 1) + ".");
            } else {
                System.out.printf("%-" + firstColumnSize + "s", (indexes.get(i) + 1) + ".");
            }

            String[] row = data.get(i);
            for (int j = 0; j < row.length; j++) {
                if (j == row.length - 1) {
                    System.out.printf("%s%-" + columnSize + "s%s%s", ConsoleColors.PURPLE, row[j], ConsoleColors.RESET, "\n");
                } else {
                    System.out.printf("%-" + columnSize + "s", row[j]);
                }
            }
        }
    }
}
