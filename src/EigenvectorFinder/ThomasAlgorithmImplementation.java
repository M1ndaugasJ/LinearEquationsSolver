package EigenvectorFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindaugas on 10/31/15.
 */
public class ThomasAlgorithmImplementation {

    List<List<Double>> matrixDiagonals;
    List<Double> dVector;
    List<Double> ckthValues = new ArrayList<>();
    List<Double> dkthValues = new ArrayList<>();
    List<Double> answers = new ArrayList<>();

    //surasyt diagonalus

    public ThomasAlgorithmImplementation(List<List<Double>> matrix) {
        int listSize = matrix.size();
        this.matrixDiagonals = matrix.subList(0, 3);
        List<Double> cValues = matrixDiagonals.get(0);
        List<Double> bValues = matrixDiagonals.get(1);
        List<Double> aValues = matrixDiagonals.get(2);
        this.dVector = matrix.get(listSize-1);
        int convergenceCheck = isMatrixConvergant(aValues,bValues,cValues);
        if (convergenceCheck == 2)
        {
            System.out.println("Matrix is not satisfying required convergence condition |bi| >= |ai| + |ci|");
            System.exit(0);

        }
        if (convergenceCheck == 0)
        {
            System.out.println("Matrix is not satisfying convergence condition: |bi| >= |ai| + |ci| (the inequality must be strict on one case at least) ");
            System.exit(0);
        }
        //forward(cValues,bValues,aValues);
        //backwards(dVector.size());
        solveLinearEquations(aValues,bValues,cValues);
    }

    public List<Double> solveLinearEquations(List<Double> aValues, List<Double> bValues, List<Double> cValues){
        forward(cValues,bValues,aValues);
        System.out.println(answers);
        return backwards(dVector.size());
    }

    public void forward(List<Double> cValues, List<Double> bValues, List<Double> aValues){
        int listSize = dVector.size();
        for(int i = 0; i < listSize; i++){
            double b = bValues.get(i);
            if(i!=listSize-1){
                double a = aValues.get(i);
                double c = cValues.get(i);
                ckthValues.add(nthCk(a, c, b, i));
            }
            if(i==0){
                dkthValues.add(nthDk(dVector.get(i), 0, b, i));
            } else {
                dkthValues.add(nthDk(dVector.get(i), aValues.get(i - 1), b, i));
            }
        }
    }

    private Double nthCk(double a, double c, double b, int index){
        double C;
        if(index == 0){
            C = (c / b) * -1;
        } else {
            C = (c / (a*ckthValues.get(index-1)+b)) * -1;
        }
        //System.out.println(C);
        return C;
    }

    private Double nthDk(double d, double a, double b, int index){
        double D;
        if(index == 0) {
            D = d / b;
        } else {
            D = (d - a * dkthValues.get(index-1))/(a*ckthValues.get(index-1)+b);
        }
        //System.out.println(D);
        return D;
    }

    public List<Double> backwards(int bValuesSize){
        int answerIndex = 0;
        for(int i = bValuesSize-1; i >= 0; i--){
            if(answers.isEmpty()){
                answers.add(dkthValues.get(bValuesSize-1));
            } else {
                double C = ckthValues.get(i);
                double x = answers.get(answerIndex);
                double D = dkthValues.get(i);
                double answer = C * x + D;
                answers.add(answer);
                answerIndex++;
            }
        }
        return answers;
    }

    private int isMatrixConvergant(List<Double> a, List<Double> b, List<Double> c)
    {
        int equal = 0;
        for (int i = 0; i < a.size(); i++)
        {
            if (Math.abs(b.get(i)) < Math.abs(a.get(i)) + Math.abs(c.get(i)))
            {
                return 2;
            }
            if (Math.abs(b.get(i)) == Math.abs(a.get(i)) + Math.abs(c.get(i)))
            {
                equal++;
            }
        }
        if (equal < b.size()) {
            return 1;
        }
        return 0;

    }

}