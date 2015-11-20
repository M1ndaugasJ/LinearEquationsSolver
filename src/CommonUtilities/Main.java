package CommonUtilities;

import EigenvectorFinder.EigenvectorFinder;
import EigenvectorFinder.InverseIteration;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

/**
 * Created by mindaugas on 15.11.11.
 */
public class Main {

    double[][] matrixData = { {1d,2d,3d}, {2d,5d,3d}};
    RealMatrix m = MatrixUtils.createRealMatrix(matrixData);


    public static void main(String[] args){
        ReadMatrixFile readMatrixFile = new ReadMatrixFile();
        //readMatrixFile.readMatrixFile("inverseiterationmatrix.txt");
        //EigenvectorFinder eigenvectorFinder = new EigenvectorFinder();
        //eigenvectorFinder.getEigenVector(readMatrixFile.readMatrixFile("inverseiterationmatrix.txt"), 0.38197);
        //FixedIteration fixedIteration = new FixedIteration();
        //fixedIteration.doFixedIteration();


        InverseIteration inverseIteration = new InverseIteration();
        inverseIteration.solveInverseIteration();

        //List<List<Double>> matrixFileContents = readMatrixFile.readMatrixFile();
        //SeidelGaussMethod linearCalcMethod = new SeidelGaussMethod(matrixFileContents.subList(0, 4), matrixFileContents.get(4));
        //LinearSolvers.HighestSlopeMethod slopeMethod = new LinearSolvers.HighestSlopeMethod(matrixFileContents.subList(0, 4), matrixFileContents.get(4));
        //linearCalcMethod.solveUsingSeidelGaussMethod();

    }

}
