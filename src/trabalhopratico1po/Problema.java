package trabalhopratico1po;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Primeiro trabalho prático de Pesquisa Operacional
 * @author gabrielamaral
 */
public class Problema {

    private double[][] A;
    private double[][] B;
    private double[] C;
    private final double[] X;
    private final int variaveis;
    private final int restricoes;
    private final double radiano = 0.0174532925;

    public Problema(int variaveis, int restricoes) {
        this.variaveis = variaveis;
        this.restricoes = restricoes;
        this.A = new double[this.restricoes][this.variaveis];
        this.B = new double[this.restricoes][1];
        this.C = new double[this.variaveis];
        this.X = new double[this.variaveis];
    }

    public void setA(double[][] A) {
        this.A = A;
    }

    public void setB(double[][] B) {
        this.B = B;
    }

    public void setC(double[] C) {
        this.C = C;
    }

    private void setPontoViavel() {
        for (int i = 0; i < X.length; i++) {
            X[i] = 0.0;
        }
        while (!checarRestricoes(X)) {
            for (int i = 0; i < X.length; i++) {
                X[i] += 5.0;
            }
        }
    }

    private boolean checarRestricoes(double[] X) {
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

    public double[] melhorCusto() {
        setPontoViavel();
        double raio = 1.0;
        double erro = 0.00001;
        while (raio > erro) {
            double[] P = procurarAteARestricao(raio);
            if (checarRestricoes(P)) {
                this.X[0] = P[0];
                this.X[1] = P[1];
            } else {
                raio /= 2.0;
            }
        }
        raio = 1.0;
        return procurarNaRestricao(raio);
    }

    private double[] procurarNaRestricao(double raio) {
        double grauMin = 0.0;
        double grauMax = 360.0;
        double grauIncr = 0.01;
        double erro = 0.00001;
        double[] P = new double[variaveis];
        while (raio > erro) {
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
            List<Double> sortedCollection = new ArrayList<>(map.values());
            Collections.sort(sortedCollection);
            try {
                double grau = searchGrau(sortedCollection.get(0), map);
                this.X[0] += Math.sin(grau * radiano) * raio;
                this.X[1] += Math.cos(grau * radiano) * raio;
            } catch (IndexOutOfBoundsException exception) {
                raio /= 2.0;
            }
            grauMin = 0.0;
        }
        return this.X;
    }

    private double[] procurarAteARestricao(double raio) {
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
                grauMin += grauIncr;
            }
            List<Double> sortedCollection = new ArrayList<>(map.values());
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
