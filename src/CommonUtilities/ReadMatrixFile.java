package CommonUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindaugas on 15.11.11.
 */
public class ReadMatrixFile {

    List<Double> matrixValues;
    List<List<Double>> matrixWithB = new ArrayList<>();

    public List<List<Double>> readMatrixFile() {

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("matrix.txt"));
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split(" ");
                matrixValues = new ArrayList<>();
                for(int i = 0; i < parts.length; i++){
                    matrixValues.add(Double.parseDouble(parts[i]));
                }
                matrixWithB.add(matrixValues);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("shits on fire yo");
        }
        return matrixWithB;
    }
}
