package test;

import tree.DecisionTree;
import tree.Node;

import java.util.ArrayList;

import static utils.DataManager.getValuesOfAttribute;
import static utils.DataManager.selectColumnsByIndexes;

public class Metrics {
    public static int[][] createConfusionMatrix(ArrayList<String[]> data, Node tree, ArrayList<String> header) {
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(header.size() - 1);
        ArrayList<String[]> actualClasses = selectColumnsByIndexes(data, indexes);
        ArrayList<String> predictedClasses = DecisionTree.classifyAll(data, tree, header);
        ArrayList<String> classNames = new ArrayList<>(getValuesOfAttribute(data, header.size() - 1).keySet());
        int[][] confusionMatrix = new int[2][2];
        for (int i = 0; i < actualClasses.size(); i++) {
            String actualClass = actualClasses.get(i)[0];
            String predictedClass = predictedClasses.get(i);

            int indexOfActualClassname = 0;
            for (int j = 0; j < classNames.size(); j++) {
                if (classNames.get(j).equals(actualClass)) {
                    indexOfActualClassname = j;
                    break;
                }
            }

            int indexOfPredictedClassname = 0;
            for (int j = 0; j < classNames.size(); j++) {
                if (predictedClass == null) {
                    if (!classNames.get(j).equals(actualClass)) {
                        indexOfActualClassname = j;
                        break;
                    }
                } else if (classNames.get(j).equals(predictedClass)) {
                    indexOfActualClassname = j;
                    break;
                }
            }

            if (predictedClass == null) {
                for (int j = 0; j < classNames.size(); j++) {
                    if (!classNames.get(j).equals(actualClass)) {
                        indexOfPredictedClassname = j;
                        break;
                    }
                }
            } else {
                indexOfPredictedClassname = classNames.indexOf(actualClass);
            }
            confusionMatrix[indexOfActualClassname][indexOfPredictedClassname] += 1;
        }
        return confusionMatrix;
    }
}
