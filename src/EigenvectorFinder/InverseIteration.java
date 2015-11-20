package EigenvectorFinder;

import org.apache.commons.math.linear.*;

import java.util.Arrays;

/**
 * Created by Mindaugas on 11/20/15.
 */
public class InverseIteration {

    private final double PRECISION = 0.0001;

    //double[][] matrixData = { {5.64d,2d,0d,0d},{2d,5.64d,2d,0d}, {0d,2d,5.64d,2d},{0d, 0d,2d, 5.64d}};
    double[][] matrixData = { {2d,-1d,0d,0d},{-1d,2d,-1d,0d}, {0d,-1d,2d,-1d},{0d, 0d,-1d, 2d}};
    double[] initialEigenvector = {1,1,0,1};
    RealMatrix m = MatrixUtils.createRealMatrix(matrixData);


    public void solveInverseIteration(){
        double eigenValueApproximation = 0;
        double newEigenValueApprox = 0.0;

        RealVector previousXVector;
        RealVector xVector = MatrixUtils.createRealVector(initialEigenvector);

        do {
            RealVector yVector = getYk(eigenValueApproximation, xVector);
            eigenValueApproximation = newEigenValueApprox;

            for(int i = 0; i < yVector.getData().length; i++){
                System.out.println(yVector.getData()[i]);

            }

            previousXVector = xVector;
            xVector = yVector.mapDivide(yVector.getNorm());
            System.out.println("Eigenvector: "+ xVector);

            double[] eigenValueVector = m.preMultiply(xVector).ebeMultiply(xVector).getData();
            newEigenValueApprox = 0.0;

            for(int i = 0; i < eigenValueVector.length; i++){
                newEigenValueApprox += eigenValueVector[i];
            }

            System.out.println("Eigenvalue = " + newEigenValueApprox);

        } while(xVector.subtract(previousXVector).getNorm() > PRECISION && Math.abs(newEigenValueApprox-eigenValueApproximation) > PRECISION);


    }

    public RealVector getYk(double eigenValueApproximation, RealVector xVector){
        return inverse(matrixMinusEigenvalue(eigenValueApproximation)).preMultiply(xVector);
    }

    public RealMatrix inverse(RealMatrix matrixToInverse){
        return new LUDecompositionImpl(matrixToInverse).getSolver().getInverse();

    }

    public RealMatrix matrixMinusEigenvalue(double eigenValueApproximation){
        return m.subtract(MatrixUtils.createRealIdentityMatrix(4).scalarMultiply(eigenValueApproximation));
    }




}
