package EigenvectorFinder;

import org.apache.commons.math.linear.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mindaugas on 11/20/15.
 */
public class InverseIteration {

    private final double PRECISION = 0.0001;

    double[][] matrixData = { {5.64d,2d,0d,0d},{2d,5.64d,2d,0d}, {0d,2d,5.64d,2d},{0d, 0d,2d, 5.64d}};
    //double[][] matrixData = { {2d,-1d,0d,0d},{-1d,2d,-1d,0d}, {0d,-1d,2d,-1d},{0d, 0d,-1d, 2d}};
    double[] initialEigenvector = {1d,1d,0d,1d};
    RealMatrix m = MatrixUtils.createRealMatrix(matrixData);


    public void solveInverseIteration(){

        double eigenValueApproximation = 1;

        double newEigenValueApprox = 0.0;
        int iterationIndex = 0;

        RealVector previousXVector;
        RealVector xVector = MatrixUtils.createRealVector(initialEigenvector);

        do {
            iterationIndex++;
            if(iterationIndex!=1){
                eigenValueApproximation = newEigenValueApprox;
            }

            try{
                RealVector yVector = getYk(eigenValueApproximation, xVector);
                System.out.println("Iteration number: " + iterationIndex);
                previousXVector = xVector;
                xVector = yVector.mapDivide(yVector.getNorm());
                System.out.println("Eigenvector: "+ xVector);
                for(int i = 0; i < xVector.getData().length; i++){
                    System.out.println(xVector.getData()[i]);
                }

                double[] eigenValueVector = m.preMultiply(xVector).ebeMultiply(xVector).getData();
                newEigenValueApprox = 0.0;

                for(int i = 0; i < eigenValueVector.length; i++){
                    newEigenValueApprox += eigenValueVector[i];
                }

                System.out.println("Eigenvalue = " + newEigenValueApprox + "\n");
            } catch (SingularMatrixException e){
                System.out.println("Choose another initial eigenvector");
                break;
            }

        } while(xVector.subtract(previousXVector).getNorm() > PRECISION && Math.abs(newEigenValueApprox-eigenValueApproximation) > PRECISION);

        Double maxXVectorValue = Arrays.stream(xVector.getData()).map(i -> Math.abs(i)).max().getAsDouble();
        List<Double> parametrizedXVector = Arrays.stream(xVector.getData()).map(notParametrized -> notParametrized / maxXVectorValue).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        System.out.println("Parametrized results");
        System.out.println(parametrizedXVector);

    }

    public RealVector getYk(double eigenValueApproximation, RealVector xVector) throws SingularMatrixException{
        return inverse(matrixMinusEigenvalue(eigenValueApproximation)).preMultiply(xVector);
    }

    public RealMatrix inverse(RealMatrix matrixToInverse) throws SingularMatrixException{
        return new LUDecompositionImpl(matrixToInverse).getSolver().getInverse();

    }

    public RealMatrix matrixMinusEigenvalue(double eigenValueApproximation){
        return m.subtract(MatrixUtils.createRealIdentityMatrix(4).scalarMultiply(eigenValueApproximation));
    }




}
