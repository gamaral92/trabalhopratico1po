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
import java.util.Objects;

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

    public double[] setPInitial() {
        for (int i = 0; i < X.length; i++) {
            X[i] = 0.0;
        }
        while (!checarRestricoes(X)) {
            for (int i = 0; i < X.length; i++) {
                X[i] += 10.0;
            }
        }
        return X;
    }

    public boolean checarRestricoes(double[] X) {
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

    public double[] bestCost() {
        setPInitial();
        double raio = 1.0;
        double erro = 0.00001;
        while (raio > erro) {
            double[] P = search(raio);
            if (checarRestricoes(P)) {
                this.X[0] = P[0];
                this.X[1] = P[1];
            } else {
                raio /= 2.0;
            }
        }
        raio = 1.0;
        search2(raio);
        return this.X;
    }

    private void search2(double raio) {
        double grauMin = 0.0;
        double grauMax = 360.0;
        double grauIncr = 0.05;
        double menor;
        double[] P = new double[variaveis];
        while (true) {
            menor = Double.MAX_VALUE;
            HashMap<Double, Double> map = new HashMap<>();
            while (grauMin <= grauMax) {
                P[0] = (this.X[0] + Math.sin(grauMin * radiano) * raio);
                P[1] = (this.X[1] + Math.cos(grauMin * radiano) * raio);
                if (checarRestricoes(P)) {
                    double result = (P[0] * C[0]) + (P[1] * C[1]);
                    double cost = (this.X[0] * C[0]) + (this.X[1] * C[1]);
                    if (result < cost) {
                        map.put(grauMin, result);
                    }
                }
                grauMin += grauIncr;
            }
            List<Double> sortedCollection = new ArrayList<Double>(map.values());
            Collections.sort(sortedCollection);
            double grau = searchGrau(sortedCollection.get(0), map);
            P[0] = (this.X[0] + Math.sin(grau * radiano) * raio);
            P[1] = (this.X[1] + Math.cos(grau * radiano) * raio);
        }
    }

    private double[] search(double raio) {
        double grauMin = 0.0;
        double grauMax = 360.0;
        double grauIncr = 45.0;
        double menor;
        double[] P = new double[variaveis];
        while (grauMax - grauMin >= 0.05) {
            menor = Double.MAX_VALUE;
            HashMap<Double, Double> map = new HashMap<>();
            while (grauMin <= grauMax) {
                double result = ((this.X[0] + Math.sin(grauMin * radiano) * raio) * C[0]) + ((this.X[1] + Math.cos(grauMin * radiano) * raio) * C[1]);
                if (result <= menor) {
                    menor = result;
                }
                map.put(grauMin, result);
                //System.out.println((int) grauMin + "º - " + (this.X[0] + Math.sin(grauMin * radiano) * raio) + ", " + (this.X[1] + Math.cos(grauMin * radiano) * raio) + " = " + result);
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
        }
        P[0] = this.X[0] + Math.sin(((grauMax + grauMin) / 2.0) * radiano) * raio;
        P[1] = this.X[1] + Math.cos(((grauMax + grauMin) / 2.0) * radiano) * raio;
        return P;
    }

    private double searchGrau(Double valueToSearch, HashMap<Double, Double> map) {
        for (Map.Entry<Double, Double> entry : map.entrySet()) {
            Double value = entry.getValue();
            if (Objects.equals(valueToSearch, value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

}
