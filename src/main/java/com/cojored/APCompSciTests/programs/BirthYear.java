package com.cojored.APCompSciTests.programs;

import java.util.Scanner;

public class BirthYear {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Birth Year ***");
        System.out.print("\nPlease enter your birth year: ");
        int year = scan.nextInt();

        if (year < 1970) System.out.println("\nYou qualify for the Juke Box Rally.");
    }
}
