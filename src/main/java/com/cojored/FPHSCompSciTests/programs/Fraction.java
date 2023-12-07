package com.cojored.FPHSCompSciTests.programs;

import com.cojored.FPHSCompSciTests.annotations.Accessor;
import com.cojored.FPHSCompSciTests.annotations.Mutator;

public class Fraction {
    private static int numFractions = 0;

    private int numerator;
    private int denominator;

    public Fraction() {
        numFractions++;
        numerator = 1;
        denominator = 1;
    }

    public Fraction(int n, int d) {
        numFractions++;
        setNumerator(n);
        setDenominator(d);
    }

    @Accessor
    public static int getNumFractions() {
        return numFractions;
    }

    @Accessor
    public int getNumerator() {
        return numerator;
    }

    @Mutator
    public void setNumerator(int n) {
        if (n > 0) numerator = n;
    }

    @Accessor
    public int getDenominator() {
        return denominator;
    }

    @Mutator
    public void setDenominator(int d) {
        if (d > 0) denominator = d;
    }

    @Mutator
    public void add(int n, int d) {
        if (n > 0 && d > 0) {
            numerator = numerator * d + n * denominator;
            denominator = denominator * d;
        }
    }

    @Mutator
    public void add(Fraction other) {
        add(other.getNumerator(), other.getDenominator());
    }

    @Mutator
    public void multiply(int n, int d) {
        if (n > 0 && d > 0) {
            numerator = numerator * n;
            denominator = denominator * d;
        }
    }

    @Mutator
    public void multiply(Fraction other) {
        multiply(other.getNumerator(), other.getDenominator());
    }

    @Accessor
    public String toString() {
        return numerator + "/" + denominator;
    }

    @Accessor
    public String mixedNumber() {
        int number = (numerator - (numerator % denominator)) / denominator;
        Fraction fraction = new Fraction(numerator % denominator, denominator);
        if (fraction.getNumerator() == 0) return String.valueOf(number);
        else if (number == 0) return fraction.toString();
        return number + " " + fraction;
    }
}
