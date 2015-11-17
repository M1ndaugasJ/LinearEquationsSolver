package CommonUtilities;

import LinearSolvers.SeidelGaussMethod;

import java.util.List;

/**
 * Created by mindaugas on 15.11.11.
 */
public class Main {

    public static void main(String[] args){
        ReadMatrixFile readMatrixFile = new ReadMatrixFile();
        //List<List<Double>> matrixFileContents = readMatrixFile.readMatrixFile();
        //SeidelGaussMethod linearCalcMethod = new SeidelGaussMethod(matrixFileContents.subList(0, 4), matrixFileContents.get(4));
        //LinearSolvers.HighestSlopeMethod slopeMethod = new LinearSolvers.HighestSlopeMethod(matrixFileContents.subList(0, 4), matrixFileContents.get(4));
        //linearCalcMethod.solveUsingSeidelGaussMethod();

    }

}
