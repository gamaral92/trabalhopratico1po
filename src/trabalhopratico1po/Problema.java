package trabalhopratico1po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    private void setPontoViavelMin() {
        for (int i = 0; i < X.length; i++) {
            X[i] = 0.0;
        }
        while (!checarRestricoesMin(X)) {
            for (int i = 0; i < X.length; i++) {
                X[i] += 1.0;
            }
        }
    }
    
    private void setPontoViavelMax() {
        for (int i = 0; i < X.length; i++) {
            X[i] = 0.0;
        }
        while (!checarRestricoesMax(X)) {
            for (int i = 0; i < X.length; i++) {
                X[i] += 1.0;
            }
        }
    }

    private boolean checarRestricoesMin(double[] X) {
        for (int i = 0; i < A.length; i++) {
            double result = 0.0;
            for (int j = 0; j < A[i].length; j++) {
                result += A[i][j] * X[j];
            }
            if (result < B[i][0]) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checarRestricoesMax(double[] X) {
        for (int i = 0; i < A.length; i++) {
            double result = 0.0;
            for (int j = 0; j < A[i].length; j++) {
                result += A[i][j] * X[j];
            }
            if (result > B[i][0]) {
                return false;
            }
        }
        return true;
    }

    public double[] minimizar() {
        setPontoViavelMin();
        double raio = 1.0;
        double erro = 0.00001;
        while (raio > erro) {
            double[] P = procurarAteARestricaoMin(raio);
            if (checarRestricoesMin(P)) {
                this.X[0] = P[0];
                this.X[1] = P[1];
            } else {
                raio /= 2.0;
            }
        }
        raio = 1.0;
        return procurarNaRestricaoMin(raio);
    }
    
    public double[] maximizar() {
        setPontoViavelMax();
        double raio = 1.0;
        double erro = 0.00001;
        while (raio > erro) {
            double[] P = procurarAteARestricaoMax(raio);
            if (checarRestricoesMax(P)) {
                this.X[0] = P[0];
                this.X[1] = P[1];
            } else {
                raio /= 2.0;
            }
        }
        raio = 1.0;
        return procurarNaRestricaoMax(raio);
    }

    private double[] procurarNaRestricaoMin(double raio) {
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
                if (checarRestricoesMin(P)) {
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

    private double[] procurarAteARestricaoMin(double raio) {
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

    private double[] procurarAteARestricaoMax(double raio) {
        double grauMin = 0.0;
        double grauMax = 360.0;
        double grauIncr = 45.0;
        double maior;
        double[] P = new double[variaveis];
        while (grauMax - grauMin >= 0.05) {
            maior = Double.MIN_VALUE;
            HashMap<Double, Double> map = new HashMap<>();
            while (grauMin <= grauMax) {
                double result = ((this.X[0] + Math.sin(grauMin * radiano) * raio) * C[0]) + ((this.X[1] + Math.cos(grauMin * radiano) * raio) * C[1]);
                if (result >= maior) {
                    maior = result;
                }
                map.put(grauMin, result);
                grauMin += grauIncr;
            }
            List<Double> sortedCollection = new ArrayList<>(map.values());
            Collections.sort(sortedCollection, new Comparator<Double>() {

                @Override
                public int compare(Double o1, Double o2) {
                    if(o1.doubleValue() > o2.doubleValue()){
                        return -1;
                    } else if(o1.doubleValue() < o2.doubleValue()){
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            grauMin = searchGrau(sortedCollection.get(1), map);
            grauMax = searchGrau(sortedCollection.get(2), map);
            if (grauMin > grauMax) {
                double aux = grauMax;
                grauMax = grauMin;
                grauMin = aux;
            }
            grauIncr /= 2.0;
        }
        P[0] = Math.abs(this.X[0] + Math.sin(((grauMax + grauMin) / 2.0) * radiano) * raio);
        P[1] = Math.abs(this.X[1] + Math.cos(((grauMax + grauMin) / 2.0) * radiano) * raio);
        return P;
    }

    private double[] procurarNaRestricaoMax(double raio) {
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
                if(P[0] > 3.72 && P[0] < 3.74 && P[1] > 2.33 && P[1] < 2.335){
                    System.out.println("");
                }
                if (checarRestricoesMax(P)) {
                    double result = (P[0] * C[0]) + (P[1] * C[1]);
                    double cost = (this.X[0] * C[0]) + (this.X[1] * C[1]);
                    if (result > cost) {
                        map.put(grauMin, result);
                    }
                }
                grauMin += grauIncr;
            }
            List<Double> sortedCollection = new ArrayList<>(map.values());
            Collections.sort(sortedCollection, new Comparator<Double>() {

                @Override
                public int compare(Double o1, Double o2) {
                    if(o1.doubleValue() > o2.doubleValue()){
                        return -1;
                    } else if(o1.doubleValue() < o2.doubleValue()){
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
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

}
