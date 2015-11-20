package EigenvectorFinder;

import CommonUtilities.LocalMatrixUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mindaugas on 15.11.17.
 */
public class EigenvectorFinder {

    private ThomasAlgorithmImplementation thomasAlgorithmImplementation;
    private final double PRECISION = 0.0001;
    private final String matrix_not_symmetric = "Matrix is not symmetric. Calculations will not proceed";

    public List<Double> getEigenVector(List<List<Double>> matrix, Double eigenValue){
        int size = matrix.size();
        int diagonalCount = size-1;
        List<List<Double>> diagonals = matrix.subList(0, diagonalCount);
        if(diagonalCount % 2 != 0){
            System.out.println("diagonals = " + diagonals);
            thomasAlgorithmImplementation = new ThomasAlgorithmImplementation();
            List<Double> iterationVector = thomasAlgorithmImplementation.solveLinearEquations(diagonals.get(0),getReducedDiagonalValues(diagonals.get(1), eigenValue), diagonals.get(2), matrix.get(matrix.size()-1));;
            List<Double> previousIterationVector;
            Double max;
            int index = 0;
            do{

                previousIterationVector = iterationVector;
                Double unsignedMaxValue = iterationVector.stream().mapToDouble(i -> Math.abs(i)).max().getAsDouble();
                Stream<Double> maxValuesStream = iterationVector.stream().filter(i -> i == unsignedMaxValue || Math.abs(i) == unsignedMaxValue);
                Double maxValue = maxValuesStream.findFirst().get();

                List<Double> reducedVector = LocalMatrixUtils.divideVectorByCoefficient(iterationVector, maxValue);

                System.out.println(reducedVector);
                iterationVector = thomasAlgorithmImplementation.solveLinearEquations(diagonals.get(0),getReducedDiagonalValues(diagonals.get(1), eigenValue), diagonals.get(2), reducedVector);


                max = Math.max(
                        Math.max(iterationVector.get(0) - previousIterationVector.get(0), iterationVector.get(1) - previousIterationVector.get(1)),
                        Math.max(iterationVector.get(2) - previousIterationVector.get(2), iterationVector.get(3) - previousIterationVector.get(3)));
                System.out.println(max);

                index++;
            } while (index == 1 || PRECISION < max);
            return iterationVector;
        } else {
            Logger.getAnonymousLogger().log(Level.SEVERE, matrix_not_symmetric);
            return null;
        }
    }

    private List<Double> getReducedDiagonalValues(List<Double> middleDiagonal, Double eigenValue){
        return middleDiagonal.stream().map(i -> i - eigenValue).collect(Collectors.toList());
    }

}
