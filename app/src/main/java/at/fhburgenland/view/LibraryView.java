package at.fhburgenland.view;

import java.util.Scanner;

/**
 * Handles library related views which are further passed into library controlling layer
 */
public class LibraryView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Books                         |
                | (2) Events                        |
                -------------------------------------
                | (0) Exit                          |
                =====================================
                """);
        return Integer.parseInt(scanner.nextLine());
    }
}
