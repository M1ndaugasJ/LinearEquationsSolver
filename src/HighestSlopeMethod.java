import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mindaugas on 15.11.13.
 */
public class HighestSlopeMethod {

    private final double PRECISION = 0.0001;
    private final int MAX_ITERS = 1000;
    private final String format = "Iteracijos numeris %d: x1 - %.4g x2 - %.4g x3 - %.4g x4 - %.4g. Paklaida - %f, netiktis - %f;";

    List<List<Double>> matrixValues;
    List<Double> fComponent;

    public HighestSlopeMethod(List<List<Double>> matrixValues, List<Double> fComponent) {
        this.matrixValues = matrixValues;
        this.fComponent = fComponent;
        System.out.println("matrixValues = [" + matrixValues + "], fComponent = [" + fComponent + "]");
        List<Double> initGuessList = new ArrayList<>();
        initGuessList.add(0.0);
        initGuessList.add(0.0);
        initGuessList.add(0.0);
        List<Double> multipliedVector = multiplyMatrixVector(matrixValues, initGuessList);
        List<Double> z0 = subtractMatrixVector(multipliedVector, fComponent);

        List<Double> Az0 = multiplyMatrixVector(matrixValues, z0);

        vectorMultiplication(z0, z0);

        Double zDeflectionSum = vectorMultiplication(z0, z0).stream().mapToDouble(Double::doubleValue).sum();
        Double iterationParameter = vectorMultiplication(Az0, z0).stream().mapToDouble(Double::doubleValue).sum();

        System.out.println(zDeflectionSum);
        System.out.println(iterationParameter);

        Double t = zDeflectionSum / iterationParameter;

        System.out.println(multiplyVectorByACoefficient(fComponent, t));

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

    public static List<Double> subtractMatrixVector(List<Double> a, List<Double> b) {
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

    public static List<Double> multiplyVectorByACoefficient(List<Double> a, Double b) {
        List<Double> multipliedVector = new ArrayList<>();

        for (int i = 0; i < a.size(); i++){
            multipliedVector.add(a.get(i) * b);
        }

        return multipliedVector;
    }

    public double zDeflection(){

        return 0.0;
    }

}
