import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindaugas on 15.11.13.
 */
public class HighestSlopeMethod {

    private final double PRECISION = 0.0001;
    private final int MAX_ITERS = 1000;
    private final String format = "Iteracijos numeris %d: x1 - %.4g x2 - %.4g x3 - %.4g x4 - %.4g. Netiktis: z1 - %.9g z2 - %.9g z3 - %.9g z4 - %.9g, artinio paklaida - %f;";
    private final String format3 = "Iteracijos numeris %d: x1 - %.4g x2 - %.4g x3 - %.4g. Netiktis: z1 - %.9g z2 - %.9g z3 - %.9g, artinio paklaida - %f;";

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
        initGuessList.add(0.0);

        List<Double> z0 = zDeflection(multiplyMatrixVector(matrixValues, initGuessList), fComponent);
        Double zScalarMultiplicationSum = vectorMultiplication(z0, z0).stream().mapToDouble(Double::doubleValue).sum();
        List<Double> previousXVector;
        previousXVector = fComponent;

        int i = 0;
        double methodPrecision = Math.pow(PRECISION, 2);
        
        System.out.println("Didziausio nuolydzio metodas");
        while(zScalarMultiplicationSum >= methodPrecision){

            List<Double> Az0 = multiplyMatrixVector(matrixValues, z0);

            Double iterationParameter = doubleFromList(vectorMultiplication(Az0, z0));

            Double t = zScalarMultiplicationSum / iterationParameter;
            List<Double> xn;

            if(i==0){
                xn = multiplyVectorByCoefficient(previousXVector, t);
            }else{
                List<Double> zkBytk = multiplyVectorByCoefficient(z0, t);
                xn = subtractMatrixVector(previousXVector, zkBytk);
            }

            previousXVector = xn;

            List<Double> tkrk = multiplyVectorByCoefficient(Az0, t);
            z0 = subtractMatrixVector(z0, tkrk);
            i++;

            //System.out.println(String.format(format3, i , xn.get(0), xn.get(1), xn.get(2), z0.get(0), z0.get(1), z0.get(2), (methodPrecision - zScalarMultiplicationSum)));
            System.out.println(String.format(format, i , xn.get(0), xn.get(1), xn.get(2), xn.get(3), z0.get(0), z0.get(1), z0.get(2), z0.get(3), (PRECISION - zScalarMultiplicationSum)));

            zScalarMultiplicationSum = doubleFromList(vectorMultiplication(z0, z0));
        }

    }

    private Double doubleFromList(List<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).sum();
    }

    private List<Double> zDeflection(List<Double> productOfMatrixVectorMultiplication, List<Double> fComponent){

        return subtractMatrixVector(productOfMatrixVectorMultiplication, fComponent);
    }

    private List<Double> multiplyMatrixVector(List<List<Double>> A, List<Double> x) {
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

    private List<Double> subtractMatrixVector(List<Double> a, List<Double> b) {
        List<Double> subtracted = new ArrayList<>();
        if(a.size()!=b.size()) throw new RuntimeException("Illegal vector dimensions.");
        for(int i = 0; i < a.size(); i++){
            subtracted.add(a.get(i) - b.get(i));
        }
        return subtracted;
    }

    private List<Double> vectorMultiplication(List<Double> a, List<Double> b) {
        List<Double> multipliedVector = new ArrayList<>();
        if(a.size()!=b.size()) throw new RuntimeException("Illegal vector dimensions.");

        for (int i = 0; i < a.size(); i++){
            multipliedVector.add(a.get(i) * b.get(i));
        }

        return multipliedVector;
    }

    private List<Double> multiplyVectorByCoefficient(List<Double> a, Double b) {
        List<Double> multipliedVector = new ArrayList<>();

        for (int i = 0; i < a.size(); i++){
            multipliedVector.add(a.get(i) * b);
        }

        return multipliedVector;
    }


}
