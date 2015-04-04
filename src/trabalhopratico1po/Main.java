/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopratico1po;

import java.util.Arrays;

/**
 *
 * @author gabrielamaral
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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
        

        System.out.println(Arrays.toString(dieta.bestCost()));
    }

}
