package com.cojored.APCompSciTests.programs;

import java.util.Scanner;

public class DrivingAge {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Driving Age ***");
        System.out.print("\nPlease enter your name: ");
        String name = scan.nextLine();
        System.out.print("Please enter your age: ");
        int age = scan.nextInt();

        if (age > 16) System.out.print("\nIt's scary, " + name + " you are old enough to drive!");
        else System.out.print("\n" + name + ", you can drive in " + (17 - age) + " year(s).");
    }
}
