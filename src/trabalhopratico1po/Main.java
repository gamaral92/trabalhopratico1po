package trabalhopratico1po;

/**
 * Primeiro trabalho prático de Pesquisa Operacional
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
        
        double[] X = dieta.melhorCusto();
        System.out.println("(x,y) = (" + X[0] + ", " + X[1] + ")");
        double custo = (X[0] * C[0]) + (X[1] * C[1]);
        System.out.println("Custo = " + custo);
        
        System.out.println("");
        
        double[] C1 = {300, 500};
        dieta.setC(C1);
        X = dieta.melhorCusto();
        System.out.println("(x,y) = (" + X[0] + ", " + X[1] + ")");
        custo = (X[0] * C1[0]) + (X[1] * C1[1]);
        System.out.println("Custo = " + custo);
        
    }

}
