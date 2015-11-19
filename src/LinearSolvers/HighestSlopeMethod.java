package LinearSolvers;

import CommonUtilities.MatrixUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindaugas on 15.11.13.
 */
public class HighestSlopeMethod {

    private final double PRECISION = 0.0000001;
    private final int MAX_ITERS = 1000;
    private final String format = "Iteracijos numeris %d: x1 - %.4g x2 - %.4g x3 - %.4g x4 - %.4g. artinio paklaida - %f. Netiktis - ";
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

        List<Double> z0 = zDeflection(MatrixUtils.multiplyMatrixVector(matrixValues, initGuessList), fComponent);
        Double zScalarMultiplicationSum = MatrixUtils.vectorMultiplication(z0, z0).stream().mapToDouble(Double::doubleValue).sum();
        List<Double> previousXVector;
        previousXVector = fComponent;

        System.out.println("Didziausio nuolydzio metodas");
        solveLinearEquations(z0, zScalarMultiplicationSum, previousXVector);

    }

    private void solveLinearEquations(List<Double> z0, Double zScalarMultiplicationSum, List<Double> previousXVector){
        int i = 0;
        double methodPrecision = Math.pow(PRECISION, 2);
        while(zScalarMultiplicationSum > methodPrecision){

            List<Double> Az0 = MatrixUtils.multiplyMatrixVector(matrixValues, z0);

            Double iterationParameter = doubleFromList(MatrixUtils.vectorMultiplication(Az0, z0));

            Double t = zScalarMultiplicationSum / iterationParameter;
            List<Double> xn;

            if(i==0){
                xn = MatrixUtils.multiplyVectorByCoefficient(previousXVector, t);
            }else{
                List<Double> zkBytk = MatrixUtils.multiplyVectorByCoefficient(z0, t);
                xn = MatrixUtils.subtractVectors(previousXVector, zkBytk);
            }

            previousXVector = xn;



            List<Double> tkrk = MatrixUtils.multiplyVectorByCoefficient(Az0, t);
            z0 = MatrixUtils.subtractVectors(z0, tkrk);
            i++;

            System.out.println(String.format(format, i , xn.get(0), xn.get(1), xn.get(2), xn.get(3), z0.get(0), (PRECISION - zScalarMultiplicationSum)));
            System.out.println(MatrixUtils.subtractVectors(MatrixUtils.multiplyMatrixVector(matrixValues, xn), fComponent));

            zScalarMultiplicationSum = doubleFromList(MatrixUtils.vectorMultiplication(z0, z0));
        }
    }

    private Double doubleFromList(List<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).sum();
    }

    private List<Double> zDeflection(List<Double> productOfMatrixVectorMultiplication, List<Double> fComponent){

        return MatrixUtils.subtractVectors(productOfMatrixVectorMultiplication, fComponent);
    }

}
