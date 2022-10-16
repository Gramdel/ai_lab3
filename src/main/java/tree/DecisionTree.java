package tree;

import java.util.ArrayList;
import java.util.HashMap;

public class DecisionTree {

    public static double calcInfo(ArrayList<String[]> data) {
        return calcSplitInfoX(data, data.get(0).length - 1);
    }

    public static double calcInfoX(ArrayList<String[]> data, int indexOfAttribute) {
        HashMap<String, ArrayList<Integer>> x = getValuesOfAttribute(data, indexOfAttribute);
        double info = 0;
        int t = data.size();
        for (ArrayList<Integer> indexes : x.values()) {
            double count = indexes.size();
            info += (count / t) * calcInfo(getRowsByIndexes(data, indexes));
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

    private static ArrayList<String[]> getRowsByIndexes(ArrayList<String[]> data, ArrayList<Integer> indexes) {
        ArrayList<String[]> newData = new ArrayList<>();
        for (int i : indexes) {
            newData.add(data.get(i));
        }
        return newData;
    }

    private static ArrayList<String[]> dropAttribute(ArrayList<String[]> data, int indexOfAttribute) {
        return data;
    }
}
