package trabalhopratico1po;

import java.text.DecimalFormat;

/**
 * Primeiro trabalho prático de Pesquisa Operacional
 * @author gabrielamaral
 */
public class SegundoProblema {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] A = {{1, 3},
                        {3, 4},
                        {3, 1}};
        double[][] B = {{8},
                        {19},
                        {7}};
        double[] C = {50, 25};

        Problema dieta = new Problema(2, 3);
        dieta.setA(A);
        dieta.setB(B);
        dieta.setC(C);
        
        double[] X = dieta.minimizar();
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        System.out.println("P(x,y) = (" + decimalFormat.format(X[0]) + ", " + decimalFormat.format(X[1]) + ")");
        double custo = (X[0] * C[0]) + (X[1] * C[1]);
        System.out.println("Custo = " + decimalFormat.format(custo));
        
        System.out.println("-------------------------------------");
        
        double[] C1 = {300, 500};
        dieta.setC(C1);
        X = dieta.minimizar();
        System.out.println("P(x,y) = (" + decimalFormat.format(X[0]) + ", " + decimalFormat.format(X[1]) + ")");
        custo = (X[0] * C[0]) + (X[1] * C[1]);
        System.out.println("Custo = " + decimalFormat.format(custo));
        
    }

}
