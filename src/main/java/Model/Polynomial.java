package Model;

import java.util.Map;
import java.util.TreeMap;

public class Polynomial {
    private Map<Integer, Double> monomials;

    public Map<Integer, Double> getMonomials() {
        return monomials;
    }

    public Polynomial(Map<Integer, Double> monomials) {
        this.monomials = monomials;
    }

    private void addToPolynomial(int degree, double coefficient) {
        if (monomials.containsKey(degree)) {
            throw new RuntimeException("Power already exists!");
        }
        monomials.put(degree, coefficient);
    }

    public Polynomial(String string) throws RuntimeException {
        monomials = new TreeMap<>();
        int index = 0;
        int startIndex = 0;
        double coefficient;
        int degree;
        if (!string.matches("^[+-]{0,1}[1-9]{1,}[0-9]*(\\*x\\^[1-9][0-9]*|\\*x){0,1}([+-][1-9]{1,}[0-9]*(\\*x\\^[1-9][0-9]*|\\*x){0,1})*$")){
            throw new RuntimeException("Invalid format!");
        }
        while (index < string.length()) {
            // power of 0 (free term)
            if (index == string.length() - 1 || string.charAt(index) == '+' || string.charAt(index) == '-') {
                if (index == string.length() - 1) {
                    index++;
                }
                coefficient = Double.parseDouble(string.substring(startIndex, index));
                startIndex = index;
                addToPolynomial(0, coefficient);
                index++;
            } else {
                if (string.charAt(index) == '*') {
                    coefficient = Double.parseDouble(string.substring(startIndex, index));
                    if (string.charAt(index + 1) != 'x') {
                        throw new RuntimeException("Invalid Input!");
                    }
                    if (index > string.length() - 3 || string.charAt(index + 2) != '^') {
                        addToPolynomial(1, coefficient);
                        index += 3;
                        startIndex = index - 1;
                    } else {
                        index += 3;
                        startIndex = index;
                        while (index < string.length() && string.charAt(index) != '+' && string.charAt(index) != '-') {
                            index++;
                        }
                        degree = Integer.parseInt(string.substring(startIndex, index));
                        startIndex = index;
                        index++;
                        addToPolynomial(degree, coefficient);
                    }
                } else {
                    index += 1;
                }
            }
        }
        clearZeroes();
    }

    public Polynomial(int degree, double coefficient) {
        monomials = new TreeMap<>();
        monomials.put(degree, coefficient);
        clearZeroes();
    }

    public void add(Polynomial P) {
        Map<Integer, Double> secondMonomials = P.getMonomials();
        for (Integer degree : secondMonomials.keySet()) {
            if (monomials.containsKey(degree)) {
                monomials.replace(degree, secondMonomials.get(degree) + monomials.get(degree));
            } else {
                monomials.put(degree, secondMonomials.get(degree));
            }
        }
        clearZeroes();
    }

    public void subtract(Polynomial P) {
        Map<Integer, Double> secondMonomials = P.getMonomials();
        for (Integer degree : secondMonomials.keySet()) {
            if (monomials.containsKey(degree)) {
                monomials.replace(degree, monomials.get(degree) - secondMonomials.get(degree));
            } else {
                monomials.put(degree, -secondMonomials.get(degree));
            }
        }
        clearZeroes();
    }

    public Integer getDegree() {
        int lastElement = -1;
        if (monomials.isEmpty()) {
            return -1;
        }
        for (var degree : monomials.keySet()) {
            lastElement = degree;
        }
        return lastElement;
    }

    private void recursiveDivide(Polynomial P1, Polynomial P2) {
        int firstDegree = P1.getDegree();
        int secondDegree = P2.getDegree();
        if (firstDegree < secondDegree) {
            return;
        }
        double coefficientRaport = P1.getMonomials().get(firstDegree) / P2.getMonomials().get(secondDegree);
        int degreeDifference = firstDegree - secondDegree;
        Polynomial catPolynomial = new Polynomial(degreeDifference, coefficientRaport);
        monomials.put(degreeDifference, coefficientRaport);
        catPolynomial.multiply(P2);
        P1.subtract(catPolynomial);
        recursiveDivide(P1, P2);
    }

    public void divide(Polynomial P2) {
        Map<Integer, Double> mon = new TreeMap<>();
        for (Integer key : monomials.keySet()) {
            mon.put(key, monomials.get(key));
        }
        Polynomial P1 = new Polynomial(mon);
        monomials.clear();
        recursiveDivide(P1, P2);
    }

    public void multiply(Polynomial P) {
        TreeMap<Integer, Double> newMonomials = new TreeMap<>();
        Map<Integer, Double> secondMonomials = P.getMonomials();
        for (Integer degree : monomials.keySet()) {
            for (Integer secondDegree : secondMonomials.keySet()) {
                Integer currentDegree = degree + secondDegree;
                Double currentCoefficient = monomials.get(degree) * secondMonomials.get(secondDegree);
                if (newMonomials.containsKey(currentDegree)) {
                    newMonomials.replace(currentDegree, newMonomials.get(currentDegree) + currentCoefficient);
                } else {
                    newMonomials.put(currentDegree, currentCoefficient);
                }
            }
        }
        monomials = newMonomials;
        clearZeroes();
    }

    private void clearZeroes() {
        Map<Integer, Double> newMonomials = new TreeMap<>();
        for (Integer degree : monomials.keySet()) {
            if (monomials.get(degree) != 0) {
                newMonomials.put(degree, monomials.get(degree));
            }
        }
        monomials = newMonomials;
    }

    public void derivate() {
        TreeMap<Integer, Double> newMonomials = new TreeMap<>();
        for (Integer degree : monomials.keySet()) {
            if (degree > 0) {
                newMonomials.put(degree - 1, degree * monomials.get(degree));
            }
        }
        monomials = newMonomials;
        clearZeroes();
    }

    public void integrate() {
        TreeMap<Integer, Double> newMonomials = new TreeMap<>();
        for (Integer degree : monomials.keySet()) {
            newMonomials.put(degree + 1, monomials.get(degree) / (degree + 1));
        }
        monomials = newMonomials;
        clearZeroes();
    }

    @Override
    public String toString() {
        String toStr = "";
        for (Integer key : monomials.keySet()) {
            double coefficient = monomials.get(key);
            String sign = coefficient > 0 ? "+" : "";
            toStr += sign;

            if ((coefficient == Math.floor(coefficient)) && !Double.isInfinite(coefficient)) {
                int intCoefficient = (int) coefficient;
                toStr += intCoefficient;
            } else {
                toStr += coefficient;
            }

            if (key == 1) {
                toStr += "*x";
            } else {
                if (key != 0) {
                    toStr += "*x^" + key;
                }
            }
        }
        int length = toStr.length();
        if (length == 0) {
            return "0";
        }
        if (toStr.charAt(0) == '+') {
            return toStr.substring(1, length);
        }
        return toStr;
    }
}
