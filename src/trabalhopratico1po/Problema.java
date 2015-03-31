/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopratico1po;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author gabrielamaral
 */
public class Problema {

    private double[][] A;
    private double[][] B;
    private double[] C;
    private double[] X;
    private int variaveis;
    private int restricoes;
    private final double radiano = 0.0174532925;

    ;

    public Problema(int variaveis, int restricoes) {
        this.variaveis = variaveis;
        this.restricoes = restricoes;
        this.A = new double[restricoes][variaveis];
        this.B = new double[restricoes][1];
        this.C = new double[variaveis];
        this.X = new double[variaveis];
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

    public double[] getX() {
        return X;
    }

    public void setX(double[] X) {
        this.X = X;
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

    public double[] getXInicial() {
        Random r = new Random();
        for (int i = 0; i < X.length; i++) {
            X[i] = 0.0;
        }
        while (!checarRestricoes()) {
            for (int i = 0; i < X.length; i++) {
                X[i] += 5.0;
            }
        }
        return X;
    }

    public boolean checarRestricoes() {
        for (int i = 0; i < A.length; i++) {
            double result = 0.0;
            for (int j = 0; j < A[i].length; j++) {
                result += A[i][j] * X[j];
            }
            if (result <= B[i][0]) {
                return false;
            }
        }
        return true;
    }

    public double search() {
        double grauMin = 0.0;
        double grauMax = 360.0;
        double grauIncr = 45.0;
        double raio = 1.0;
        double menor = 0;
        while (grauIncr > 0.000686646) {
            menor = Double.MAX_VALUE;
            HashMap<Double, Double> map = new HashMap<>();
            while (grauMin <= grauMax) {
                double result = ((X[0] + Math.sin(grauMin) * raio) * C[0]) + ((X[1] + Math.cos(grauMin) * raio) * C[1]);
                if (result <= menor) {
                    menor = result;
                }
                map.put(grauMin, result);
                System.out.println((int) grauMin + "º --- " + (X[0] + Math.sin(grauMin * radiano) * raio) + ", " + (X[1] + Math.cos(grauMin * radiano) * raio) + " = " + result);
                grauMin += grauIncr;
            }
            List<Double> sortedCollection = new ArrayList<Double>(map.values());
            Collections.sort(sortedCollection);
            grauMin = searchGrau(sortedCollection.get(1), map);
            grauMax = searchGrau(sortedCollection.get(2), map);
            if (grauMin > grauMax) {
                double aux = grauMax;
                grauMax = grauMin;
                grauMin = aux;
            }
            grauIncr /= 2.0;
            raio *= 0.8;
        }
        return menor;
    }

    private double searchGrau(Double valueToSearch, HashMap<Double, Double> map) {
        for (Map.Entry<Double, Double> entry : map.entrySet()) {
            Double value = entry.getValue();
            if (valueToSearch == value) {
                return entry.getKey();
            }
        }
        return 0;
    }

}
