package at.fhburgenland.view;

import at.fhburgenland.model.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerView {

    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Show existing customer        |
                | (2) Add new customer              |
                | (3) Edit existing customer         |
                | (4) Delete customer               |
                -------------------------------------
                | (0) Main Menu                     |
                =====================================
                """);

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int showExistingCustomerMenu() {
        System.out.println("""
                -------------------------------------
                | Choose filter option              |
                -------------------------------------
                | (1) Search by First Name          |
                | (2) Search by Last Name           |
                | (3) Select by ID                  |
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getCustomerFirstNameByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Customer First Name:       |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getCustomerLastNameByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Customer Last Name:        |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public int getCustomerIdByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Customer ID:               |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getCustomerIdByUserForEventAddition() {
        System.out.println("""
                -------------------------------------
                | Enter Customer ID to add event to:|
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getStreetByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Street:                    |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getZipByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Zip:                       |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getTownByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Town:                      |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getCityByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter City:                      |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getCountryByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Country:                   |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getStreetNumberByUser() {
        System.out.println("""
                -------------------------------------
                |  Enter Street Number:             |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public int showEditOptionsMenu() {
        System.out.println("""
                -------------------------------------
                | Choose edit option                |
                -------------------------------------
                | (1) Edit First Name               |
                | (2) Edit Last Name                |
                | (3) Edit Street                   |
                | (4) Edit Street Number            |
                | (5) Edit Zip                      |
                | (6) Edit Town                     |
                | (7) Edit City                     |
                | (8) Edit Country                  |
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void printCustomer(Customer customer) {
        if (customer == null) return;
        String customerPrint = String.format("""
                        -------------------------------------
                        |       Customer found in system    |
                        -------------------------------------
                        | ID:          %s
                        | First Name:  %s
                        | Last Name:   %s
                        | Street:      %s
                        | Street Nr:   %s
                        | Zip:         %s
                        | Town:        %s
                        | City:        %s
                        | Country:     %s
                        -------------------------------------
                        """,
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet() != null ? customer.getStreet().getStreet() : "N/A",
                customer.getStreetNumber() != null ? customer.getStreetNumber() : "N/A",
                customer.getZip() != null ? customer.getZip().getZipCode() : "N/A",
                customer.getTown() != null ? customer.getTown().getTownName() : "N/A",
                customer.getCity() != null ? customer.getCity().getCityName() : "N/A",
                customer.getCountry() != null ? customer.getCountry().getCountryName() : "N/A"
        );
        System.out.println(customerPrint);
    }

    public void printSearchStatistics(List<Customer> customers) {
        String statsPrint = String.format("""
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                | Total Customers found: %d             |
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """, customers.size());
        System.out.println(statsPrint);
    }


}
