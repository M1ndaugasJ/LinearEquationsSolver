package EigenvectorFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindaugas on 10/31/15.
 */
public class ThomasAlgorithmImplementation {

    List<Double> dVector;
    List<Double> ckthValues = new ArrayList<>();
    List<Double> dkthValues = new ArrayList<>();

    public List<Double> solveLinearEquations(List<Double> aValues, List<Double> bValues, List<Double> cValues, List<Double> dValues){
        //int convergenceCheck = isMatrixConvergant(aValues,bValues,cValues);
//        if (convergenceCheck == 2)
//        {
//            System.out.println("Matrix is not satisfying required convergence condition |bi| >= |ai| + |ci|");
//            return null;
//
//        }
//        if (convergenceCheck == 0)
//        {
//            System.out.println("Matrix is not satisfying convergence condition: |bi| >= |ai| + |ci| (the inequality must be strict on one case at least) ");
//            return null;
//        }
        this.dVector = dValues;
        forward(cValues,bValues,aValues);
        List<Double> answers = backwards(dVector.size());
        //System.out.println("answers = " + answers);
        return answers;
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
        List<Double> answers = new ArrayList<>();
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