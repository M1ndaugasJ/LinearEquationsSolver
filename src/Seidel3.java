import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindaugas on 15.11.11.
 */
public class Seidel3 {

    private final double PRECISION = 0.0001;
    private final int MAX_ITERS = 1000;
    private final String format = "Iteracijos numeris %d: x1 - %.4g x2 - %.4g x3 - %.4g. Paklaida - %f, neatitiktis - %f;";

    List<List<Double>> matrixValues;
    List<Double> bComponent;

    public Seidel3(List<List<Double>> matrixValues, List<Double> bComponent) {
        this.matrixValues = matrixValues;
        this.bComponent = bComponent;
        System.out.println("matrixValues = [" + matrixValues + "], bComponent = [" + bComponent + "]");

        solveUsingSeidelGaussMethod();
    }

    public List<Double> solveUsingSeidelGaussMethod(){
        List<Double> x1s = new ArrayList<>();
        List<Double> x2s = new ArrayList<>();
        List<Double> x3s = new ArrayList<>();
        Double x1=0.0, x2=0.0, x3=0.0, x4=0.0;
        Double prevx1 = 0.0, prevx2 = 0.0, prevx3 = 0.0, prevx4 = 0.0;
        List<Double> initialGuesses = new ArrayList<>();

        initialGuesses.add(0.0); initialGuesses.add(0.0); initialGuesses.add(0.0); initialGuesses.add(0.0);

        for(List<Double> xivalues : matrixValues){
            x1s.add(xivalues.get(0));
            x2s.add(xivalues.get(1));
            x3s.add(xivalues.get(2));
        }
        System.out.println("x1s = " + x1s);
        System.out.println("x2s = " + x2s);
        System.out.println("x3s = " + x3s);

        x1 = ((-1)*initialGuesses.get(1)*x2s.get(0) + (-1)*initialGuesses.get(2)*x3s.get(0) + bComponent.get(0)) / x1s.get(0);
        x2 = ((-1)*x1*x1s.get(1) + (-1)*initialGuesses.get(2)*x3s.get(1) + bComponent.get(1)) / x2s.get(1);
        x3 = ((-1)*x1*x1s.get(2) + (-1)*x2*x2s.get(2) + bComponent.get(2)) / x3s.get(2);

        int i = 1;
        double paklaida = Math.max(Math.max(x1 - prevx1, x2 - prevx2), Math.max(x3 - prevx3, x4 - prevx4));

        System.out.println(String.format(format, i , x1, x2, x3, x4, paklaida, (PRECISION - paklaida)));

        while(PRECISION < paklaida){
            prevx1 = x1;
            prevx2 = x2;
            prevx3 = x3;
            prevx4 = x4;
            x1 = ((-1)*x2s.get(0)*x2 + (-1)*x3s.get(0)*x3 + bComponent.get(0)) / x1s.get(0);
            x2 = ((-1)*x1s.get(1)*x1 + (-1)*x3s.get(1)*x3 + bComponent.get(1)) / x2s.get(1);
            x3 = ((-1)*x1s.get(2)*x1 + (-1)*x2s.get(2)*x2 + bComponent.get(2)) / x3s.get(2);
            paklaida = Math.max(Math.max(x1 - prevx1, x2 - prevx2), Math.max(x3 - prevx3, x4 - prevx4));

            i++;
            System.out.println(String.format(format, i , x1, x2, x3, paklaida, (PRECISION - paklaida)));

            if(MAX_ITERS <= i){
                System.out.println("Method is diverging");
                break;
            }
        }

        return null;
    }

}
