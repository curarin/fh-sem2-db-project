package at.fhburgenland.view;

import at.fhburgenland.model.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerView {

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

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

        return Integer.parseInt(scanner.nextLine());
    }

    public int showExistingCustomerMenu() {
        System.out.println("""
                -------------------------------------
                | Choose filter option              |
                -------------------------------------
                | (1) Search by First Name          |
                | (2) Search by Last Name           |
                | (3) Search by ID                  |
                | (4) Show all                      |
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        return Integer.parseInt(scanner.nextLine());
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
        return Integer.parseInt(scanner.nextLine());
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

    public void printCustomer(Customer customer) {
        if (customer == null) return;
        String customerPrint = String.format("""
                -------------------------------------
                |       Customer found in system    |
                -------------------------------------
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
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet() != null ? customer.getStreet().getStreet() : "N/A",
                customer.getStreetnumber() != null ? customer.getStreetnumber() : "N/A",
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
