package CommonUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindaugas on 11/16/15.
 */
public class MatrixUtils {

    public static <T> List<List<T>> transpose(List<List<T>> table) {
        List<List<T>> ret = new ArrayList<>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<T> col = new ArrayList<>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

    public static List<Double> multiplyMatrixVector(List<List<Double>> A, List<Double> x) {
        int m = A.size();
        int n = A.get(0).size();
        if (x.size() != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        List<Double> multiplied = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                y[i] += A.get(i).get(j) * x.get(j);
            }
            multiplied.add(y[i]);
        }
        return multiplied;
    }

    public static List<Double> subtractVectors(List<Double> a, List<Double> b) {
        List<Double> subtracted = new ArrayList<>();
        if(a.size()!=b.size()) throw new RuntimeException("Illegal vector dimensions.");
        for(int i = 0; i < a.size(); i++){
            subtracted.add(a.get(i) - b.get(i));
        }
        return subtracted;
    }

    public static List<Double> vectorMultiplication(List<Double> a, List<Double> b) {
        List<Double> multipliedVector = new ArrayList<>();
        if(a.size()!=b.size()) throw new RuntimeException("Illegal vector dimensions.");

        for (int i = 0; i < a.size(); i++){
            multipliedVector.add(a.get(i) * b.get(i));
        }

        return multipliedVector;
    }

    public static List<Double> multiplyVectorByCoefficient(List<Double> a, Double b) {
        List<Double> multipliedVector = new ArrayList<>();

        for (int i = 0; i < a.size(); i++){
            multipliedVector.add(a.get(i) * b);
        }

        return multipliedVector;
    }

    public static List<Double> divideVectorByCoefficient(List<Double> a, Double b) {
        List<Double> divideVector = new ArrayList<>();

        for (int i = 0; i < a.size(); i++){
            divideVector.add(a.get(i) / b);
        }

        return divideVector;
    }
}
