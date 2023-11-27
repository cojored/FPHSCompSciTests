package com.cojored.FPHSCompSciTests.programs;

import java.util.Scanner;

public class Ascending {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Ascending ***");
        System.out.print("\nPlease enter the first integer: ");
        int num1 = scan.nextInt();
        System.out.print("Please enter the second integer: ");
        int num2 = scan.nextInt();

        System.out.println("\nThe two integers in ascending order: " + Math.min(num1, num2) + ", " + Math.max(num1, num2));
    }
}
