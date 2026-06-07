package at.fhburgenland.view;

import java.util.Scanner;

public class LibraryView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Books                         |
                -------------------------------------
                | (0) Exit                          |
                =====================================
                """);
        return Integer.parseInt(scanner.nextLine());
    }
}
