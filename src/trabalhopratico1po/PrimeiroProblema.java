package trabalhopratico1po;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabrielamaral
 */
public class PrimeiroProblema {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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
        System.out.println("(x,y) = (" + X[0] + ", " + X[1] + ")");
        double custo = (X[0] * C[0]) + (X[1] * C[1]);
        System.out.println("Custo = " + custo);
    }
    
}
