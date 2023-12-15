package pl.wsb.OOP_Lab;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userChoice;

        ClientService cs = new ClientService();
        WarehouseService ws = new WarehouseService(cs);

        boolean exitProgram = false;
        while (!exitProgram) {
            System.out.println("Select service: ");
            System.out.println("1 - Client Service");
            System.out.println("2 - Warehouse Service");
            System.out.println();
            System.out.println("0 - Exit");

            userChoice = input.next();

            switch (userChoice) {
                case "1":
                    cs.clientServiceRun();
                    break;
                case "2":
                    ws.warehouseServiceRun();
                    break;
                case "0":
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}