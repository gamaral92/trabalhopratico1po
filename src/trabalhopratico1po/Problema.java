/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhopratico1po;

/**
 *
 * @author gabrielamaral
 */
public class Problema {
    
    private double[][] A;
    private double[][] B;
    private double[] C;
    private int variaveis;
    private int restricoes;

    public Problema(int variaveis, int restricoes) {
        this.variaveis = variaveis;
        this.restricoes = restricoes;
        this.A = new double[restricoes][variaveis];
        this.B = new double[restricoes][1];
        this.C = new double[variaveis];
    }

    public double[][] getA() {
        return A;
    }

    public void setA(double[][] A) {
        this.A = A;
    }

    public double[][] getB() {
        return B;
    }

    public void setB(double[][] B) {
        this.B = B;
    }

    public double[] getC() {
        return C;
    }

    public void setC(double[] C) {
        this.C = C;
    }

    public int getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(int variaveis) {
        this.variaveis = variaveis;
    }

    public int getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(int restricoes) {
        this.restricoes = restricoes;
    }
    
}