package com.cojored.APCompSciTests.programs;

import java.util.Scanner;

public class Telegram {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Telegram ***");
        System.out.print("\nPlease enter the number of words: ");
        int words = scan.nextInt();

        double cost = 8.5 + (words > 15 ? ((words - 15) * 0.25) : 0);
        System.out.println("\nThe telegram cost is: " + cost);
    }
}
