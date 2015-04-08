package trabalhopratico1po;

import java.text.DecimalFormat;

/**
 *
 * @author gabrielamaral
 */
public class PrimeiroProblema {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] A = {{50, 70},
                        {80, 50},
                        {0, 1}};
        double[][] B = {{350},
                        {400},
                        {4}};
        double[] C = {230, 230};

        Problema dieta = new Problema(2, 3);
        dieta.setA(A);
        dieta.setB(B);
        dieta.setC(C);

        double[] X = dieta.maximizar();
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        System.out.println("P(x,y) = (" + decimalFormat.format(X[0]) + ", " + decimalFormat.format(X[1]) + ")");
        double custo = (X[0] * C[0]) + (X[1] * C[1]);
        System.out.println("Custo = " + decimalFormat.format(custo));
    }

}
