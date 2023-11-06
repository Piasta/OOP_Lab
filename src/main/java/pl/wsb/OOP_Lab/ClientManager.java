package pl.wsb.OOP_Lab;

import com.google.gson.Gson;

import java.util.Scanner;

public class ClientManager implements ClientsManager {

    private String firstName;
    private String lastName;

    public ClientManager() {
        Scanner input = new Scanner(System.in);
        int choice;

        System.out.println("Select what you want to do.");
        System.out.println("1 - create new client.");

        choice = input.nextInt();

        if (choice == 1) {
            inputFirstName(input);
            inputLastName(input);
            ClientDataCreator cdc = new ClientDataCreator(firstName, lastName);
            String gson = new Gson().toJson(cdc);
            System.out.println(gson);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    @Override
    public String inputFirstName(Scanner keyboard) {
        System.out.print("Enter client first name: ");
        this.firstName = keyboard.next();
        return firstName;
    }

    @Override
    public String inputLastName(Scanner keyboard) {
        System.out.print("Enter client last name: ");
        this.lastName = keyboard.next();
        return lastName;
    }
}
