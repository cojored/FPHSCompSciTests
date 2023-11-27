package com.cojored.APCompSciTests.programs;

import java.util.Scanner;

public class Museum {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Museum ***");
        System.out.print("\nPlease enter your age: ");
        int age = scan.nextInt();

        System.out.print("\nYour entry fee is: ");

        if (age < 5) System.out.print("free");
        else if (age >= 65) System.out.print("$1.50");
        else System.out.print("$2.50");
    }
}
